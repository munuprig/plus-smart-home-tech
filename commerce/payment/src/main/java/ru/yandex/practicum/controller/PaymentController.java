package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.dto.PaymentDto;
import ru.yandex.practicum.service.PaymentService;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public PaymentDto createPayment(@RequestBody @Valid OrderDto orderDto) {
        try {
            log.info("Формирование оплаты для заказа (переход в платежный шлюз): {}", orderDto);
            return paymentService.createPayment(orderDto);
        } catch (Exception e) {
            log.error("Ошибка формирования оплаты для заказа.");
            throw e;
        }
    }

    @PostMapping("/totalCost")
    public Double getTotalCost(@RequestBody @Valid OrderDto orderDto) {
        try {
            log.info("Расчёт полной стоимости заказа: {}", orderDto);
            return paymentService.getTotalCost(orderDto);
        } catch (Exception e) {
            log.error("Ошибка расчёта полной стоимости заказа: .");
            throw e;
        }
    }

    @PostMapping("/refund")
    public void paymentSuccess(@RequestBody UUID orderId) {
        try {
            log.info("Метод для эмуляции успешной оплаты в платежного шлюза: {}", orderId);
            paymentService.paymentSuccess(orderId);
        } catch (Exception e) {
            log.error("Ошибка метода.");
            throw e;
        }
    }

    @PostMapping("/productCost")
    public Double productCost(@RequestBody @Valid OrderDto orderDto) {
        try {
            log.info("Расчёт стоимости товаров в заказе: {}", orderDto);
            return paymentService.productCost(orderDto);
        } catch (Exception e) {
            log.error("Ошибка расчёта стоимости товаров в заказе.");
            throw e;
        }
    }

    @PostMapping("/failed")
    public void paymentFailed(@RequestBody UUID orderId) {
        try {
            log.info("Метод для эмуляции отказа в оплате платежного шлюза: {}", orderId);
            paymentService.paymentFailed(orderId);
        } catch (Exception e) {
            log.error("Ошибка метода.");
            throw e;
        }
    }
}