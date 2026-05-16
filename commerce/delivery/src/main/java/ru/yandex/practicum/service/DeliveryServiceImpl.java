package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.AddressDto;
import ru.yandex.practicum.dto.DeliveryDto;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.enums.DeliveryState;
import ru.yandex.practicum.exception.NoDeliveryFoundException;
import ru.yandex.practicum.feign.OrderClient;
import ru.yandex.practicum.feign.WarehouseClient;
import ru.yandex.practicum.mapper.DeliveryMapper;
import ru.yandex.practicum.model.Delivery;
import ru.yandex.practicum.repository.DeliveryRepository;
import ru.yandex.practicum.request.ShippedToDeliveryRequest;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryMapper deliveryMapper;
    private final OrderClient orderClient;
    private final WarehouseClient warehouseClient;

    @Value("${delivery.base_cost}")
    private double baseCost;

    @Value("${delivery.warehouse_address_ratio}")
    private double warehouseAddressRatio;

    @Value("${delivery.fragile_ratio}")
    private double fragileRatio;

    @Value("${delivery.weight_ratio}")
    private double weightRatio;

    @Value("${delivery.volume_ratio}")
    private double volumeRatio;

    @Value("${delivery.delivery_address_ratio}")
    private double deliveryAddressRatio;

    private static final String ADDRESS1 = "ADDRESS_1";
    private static final String ADDRESS2 = "ADDRESS_2";

    @Override
    public DeliveryDto planDelivery(DeliveryDto deliveryDto) {
        Delivery delivery = deliveryMapper.toDelivery(deliveryDto);
        delivery.setDeliveryState(DeliveryState.CREATED);
        return deliveryMapper.toDeliveryDto(deliveryRepository.save(delivery));
    }

    @Override
    public void deliverySuccessful(UUID deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(
                () -> new NoDeliveryFoundException("Не найдена доставка"));
        delivery.setDeliveryState(DeliveryState.DELIVERED);
        orderClient.complete(delivery.getOrderId());
    }

    @Override
    public void deliveryPicked(UUID deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(
                () -> new NoDeliveryFoundException("Не найдена доставка для выдачи"));
        delivery.setDeliveryState(DeliveryState.IN_PROGRESS);
        orderClient.assembly(delivery.getOrderId());
        ShippedToDeliveryRequest deliveryRequest = new ShippedToDeliveryRequest(
                delivery.getOrderId(), delivery.getDeliveryId());
        warehouseClient.shippedToDelivery(deliveryRequest);
    }

    @Override
    public void deliveryFailed(UUID deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(
                () -> new NoDeliveryFoundException("Не найдена доставка для сбоя"));
        delivery.setDeliveryState(DeliveryState.FAILED);
        orderClient.assemblyFailed(delivery.getOrderId());
    }

    @Override
    @Transactional(readOnly = true)
    public Double deliveryCost(OrderDto orderDto) {
        UUID orderId = orderDto.getOrderId();
        log.debug("Начало расчета стоимости доставки для заказа {}", orderId);

        Delivery delivery = getDelivery(orderId);
        AddressDto warehouseAddress = getWarehouseAddress(orderId);

        double base = calculateBaseCost(orderId, warehouseAddress);
        double fragileCost = calculateFragileCost(orderId, orderDto, base);
        double weightCost = calculateWeightCost(orderId, orderDto);
        double volumeCost = calculateVolumeCost(orderId, orderDto);
        double streetCost = calculateStreetCost(orderId, delivery, warehouseAddress);

        double totalCost = base + fragileCost + weightCost + volumeCost + streetCost;

        log.info("Расчет стоимости доставки для заказа {} завершен. Итоговая сумма: {}", orderId, totalCost);
        return totalCost;
    }

    private Delivery getDelivery(UUID orderId) {
        return deliveryRepository.findByOrderId(orderId)
                .orElseThrow(() -> new NoDeliveryFoundException("Не найдена доставка для расчета заказа " + orderId));
    }

    private AddressDto getWarehouseAddress(UUID orderId) {
        AddressDto warehouseAddress = warehouseClient.getWarehouseAddress();
        log.info("Адрес склада для заказа {}: город {}, улица {}",
                orderId, warehouseAddress.getCity(), warehouseAddress.getStreet());
        return warehouseAddress;
    }

    private double calculateBaseCost(UUID orderId, AddressDto warehouseAddress) {
        double addressCost = switch (warehouseAddress.getCity()) {
            case ADDRESS1 -> baseCost;
            case ADDRESS2 -> baseCost * warehouseAddressRatio;
            default -> throw new IllegalStateException("Неизвестный адрес доставки: " + warehouseAddress.getCity());
        };
        double result = baseCost + addressCost;
        log.debug("Базовая ставка доставки для заказа {} составляет {}", orderId, result);
        return result;
    }

    private double calculateFragileCost(UUID orderId, OrderDto orderDto, double base) {
        if (Boolean.TRUE.equals(orderDto.getFragile())) {
            double fragileSurcharge = base * fragileRatio;
            log.debug("Надбавка за хрупкость увеличивает стоимость доставки для заказа {} до {}",
                    orderId, base + fragileSurcharge);
            return fragileSurcharge;
        }
        return 0.0;
    }

    private double calculateWeightCost(UUID orderId, OrderDto orderDto) {
        double cost = orderDto.getDeliveryWeight() * weightRatio;
        log.debug("Стоимость за вес заказа {}: {}", orderId, cost);
        return cost;
    }

    private double calculateVolumeCost(UUID orderId, OrderDto orderDto) {
        double cost = orderDto.getDeliveryVolume() * volumeRatio;
        log.debug("Стоимость за объем заказа {}: {}", orderId, cost);
        return cost;
    }

    private double calculateStreetCost(UUID orderId, Delivery delivery, AddressDto warehouseAddress) {
        if (!warehouseAddress.getStreet().equals(delivery.getToAddress().getStreet())) {
            double surcharge = baseCost * deliveryAddressRatio;
            log.debug("Дополнительная плата за разные улицы для заказа {}: {}", orderId, surcharge);
            return surcharge;
        }
        return 0.0;
    }
}
