package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.service.DeliveryService;
import ru.yandex.practicum.dto.DeliveryDto;
import ru.yandex.practicum.dto.OrderDto;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @PutMapping
    public DeliveryDto planDelivery(@RequestBody @Valid DeliveryDto deliveryDto) {
        try {
            log.info("Создать новую доставку в БД {}", deliveryDto);
            return deliveryService.planDelivery(deliveryDto);
        } catch (Exception e) {
            log.error("Ошибка создания новой доставки.", e);
            throw e;
        }
    }

    @PostMapping("/successful")
    public void deliverySuccessful(@RequestBody UUID deliveryId) {
        try {
            log.info("Эмуляция успешной доставки товара {}", deliveryId);
            deliveryService.deliverySuccessful(deliveryId);
        } catch (Exception e) {
            log.error("Ошибка эмуляции.");
            throw e;
        }
    }

    @PostMapping("/picked")
    public void deliveryPicked(@RequestBody UUID deliveryId) {
        try {
            log.info("Эмуляция получения товара в доставку {}", deliveryId);
            deliveryService.deliveryPicked(deliveryId);
        } catch (Exception e) {
            log.error("Ошибка эмуляции.");
            throw e;
        }
    }

    @PostMapping("/failed")
    public void deliveryFailed(@RequestBody UUID deliveryId) {
        try {
            log.info("Эмуляция неудачного вручения товара {}", deliveryId);
            deliveryService.deliveryFailed(deliveryId);
        } catch (Exception e) {
            log.error("Ошибка эмуляции.");
            throw e;
        }
    }

    @PostMapping("/cost")
    public Double deliveryCost(@RequestBody @Valid OrderDto orderDto) {
        try {
            log.info("Расчёт полной стоимости доставки заказа {}", orderDto);
            return deliveryService.deliveryCost(orderDto);
        } catch (Exception e) {
            log.error("Ошибка расчёта полной стоимости доставки заказа.");
            throw e;
        }
    }
}