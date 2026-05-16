package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.dto.PaymentDto;
import ru.yandex.practicum.dto.ProductDto;
import ru.yandex.practicum.enums.PaymentState;
import ru.yandex.practicum.feign.OrderClient;
import ru.yandex.practicum.feign.ShoppingStoreClient;
import ru.yandex.practicum.exception.NoPaymentFoundException;
import ru.yandex.practicum.exception.NotEnoughInfoInOrderToCalculateException;
import ru.yandex.practicum.mapper.PaymentMapper;
import ru.yandex.practicum.model.Payment;
import ru.yandex.practicum.repository.PaymentRepository;

import java.util.Map;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentMapper paymentMapper;
    private final PaymentRepository paymentRepository;
    private final ShoppingStoreClient shoppingStoreClient;
    private final OrderClient orderClient;

    @Value("${payment.fee_ratio}")
    private double feeRatio;


    private final String MESSAGE_NOT_INFORMATION = "Недостаточно информации в заказе для расчёта.";
    private final String MESSAGE_PAYMENT_NOT_FOUND = "Указанная оплата не найдена.";

    @Override
    public PaymentDto createPayment(OrderDto orderDto) {
        checkOrder(orderDto);
        Payment payment = Payment.builder()
                .orderId(orderDto.getOrderId())
                .totalPayment(orderDto.getTotalPrice())
                .deliveryTotal(orderDto.getDeliveryPrice())
                .productsTotal(orderDto.getProductPrice())
                .feeTotal(getTax(orderDto.getTotalPrice()))
                .status(PaymentState.PENDING)
                .build();
        return paymentMapper.toPaymentDto(paymentRepository.save(payment));
    }

    @Override
    @Transactional(readOnly = true)
    public Double getTotalCost(OrderDto orderDto) {
        if (orderDto.getDeliveryPrice() == null) {
            throw new NotEnoughInfoInOrderToCalculateException(MESSAGE_NOT_INFORMATION);
        }
        return orderDto.getProductPrice() + getTax(orderDto.getProductPrice()) + orderDto.getDeliveryPrice();
    }

    @Override
    public void paymentSuccess(UUID uuid) {
        Payment payment = paymentRepository.findById(uuid).orElseThrow(
                () -> new NoPaymentFoundException(MESSAGE_PAYMENT_NOT_FOUND));
        payment.setStatus(PaymentState.SUCCESS);
        orderClient.payment(payment.getOrderId());
    }

    @Override
    @Transactional(readOnly = true)
    public Double productCost(OrderDto orderDto) {
        double productCost = 0.0;
        Map<UUID, Long> products = orderDto.getProducts();
        if (products == null) {
            throw new NotEnoughInfoInOrderToCalculateException(MESSAGE_NOT_INFORMATION);
        }
        for (Map.Entry<UUID, Long> entry : products.entrySet()) {
            ProductDto product = shoppingStoreClient.getProduct(entry.getKey());
            productCost += product.getPrice() * entry.getValue();
        }
        return productCost;
    }

    @Override
    public void paymentFailed(UUID uuid) {
        Payment payment = paymentRepository.findById(uuid).orElseThrow(
                () -> new NoPaymentFoundException(MESSAGE_PAYMENT_NOT_FOUND));
        payment.setStatus(PaymentState.FAILED);
        orderClient.paymentFailed(payment.getOrderId());
    }

    private void checkOrder(OrderDto orderDto) {
        if (orderDto.getDeliveryPrice() == null) {
            throw new NotEnoughInfoInOrderToCalculateException(MESSAGE_NOT_INFORMATION);
        }
        if (orderDto.getProductPrice() == null) {
            throw new NotEnoughInfoInOrderToCalculateException(MESSAGE_NOT_INFORMATION);
        }
        if (orderDto.getTotalPrice() == null) {
            throw new NotEnoughInfoInOrderToCalculateException(MESSAGE_NOT_INFORMATION);
        }
    }

    private double getTax(double totalPrice) {
        return totalPrice * feeRatio;
    }
}