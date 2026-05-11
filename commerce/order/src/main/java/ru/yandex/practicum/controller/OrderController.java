package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.request.CreateNewOrderRequest;
import ru.yandex.practicum.request.ProductReturnRequest;
import ru.yandex.practicum.service.OrderService;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public List<OrderDto> getClientOrders(@RequestParam String username,
                                          @RequestParam(defaultValue = "0") Integer page,
                                          @RequestParam(defaultValue = "10") Integer size) {
        try {
            log.info("Получить заказы пользователя {}", username);
            return orderService.getClientOrders(username, page, size);
        } catch (Exception e) {
            log.error("Ошибка получения заказов пользователя.");
            throw e;
        }
    }

    @PutMapping
    public OrderDto createNewOrder(@RequestBody @Valid CreateNewOrderRequest newOrderRequest) {
        try {
            log.info("Создать новый заказ в системе {}", newOrderRequest);
            return orderService.createNewOrder(newOrderRequest);
        } catch (Exception e) {
            log.error("Ошибка создания нового заказа в системе.");
            throw e;
        }
    }

    @PostMapping("/return")
    public OrderDto productReturn(@RequestBody @Valid ProductReturnRequest returnRequest) {
        try {
            log.info("Возврат заказа {}", returnRequest);
            return orderService.productReturn(returnRequest);
        } catch (Exception e) {
            log.error("Ошибка возврата заказа.");
            throw e;
        }
    }

    @PostMapping("/payment")
    public OrderDto payment(@RequestBody UUID orderId) {
        try {
            log.info("Оплата заказа {}", orderId);
            return orderService.payment(orderId);
        } catch (Exception e) {
            log.error("Ошибка оплаты заказа.");
            throw e;
        }
    }

    @PostMapping("/payment/failed")
    public OrderDto paymentFailed(@RequestBody UUID orderId) {
        try {
            log.info("Оплата заказа произошла с ошибкой {}", orderId);
            return orderService.paymentFailed(orderId);
        } catch (Exception e) {
            log.error("Ошибка оплаты.");
            throw e;
        }
    }

    @PostMapping("/delivery")
    public OrderDto delivery(@RequestBody UUID orderId) {
        try {
            log.info("Доставка заказа {}", orderId);
            return orderService.delivery(orderId);
        } catch (Exception e) {
            log.error("Ошибка доставки заказа.");
            throw e;
        }
    }

    @PostMapping("/delivery/failed")
    public OrderDto deliveryFailed(@RequestBody UUID orderId) {
        try {
            log.info("Доставка заказа произошла с ошибкой {}", orderId);
            return orderService.deliveryFailed(orderId);
        } catch (Exception e) {
            log.error("Ошибка доставки.");
            throw e;
        }
    }

    @PostMapping("/completed")
    public OrderDto complete(@RequestBody UUID orderId) {
        try {
            log.info("Завершение заказа {}", orderId);
            return orderService.complete(orderId);
        } catch (Exception e) {
            log.error("Ошибка завершения заказа.");
            throw e;
        }
    }

    @PostMapping("/calculate/total")
    public OrderDto calculateTotalCost(@RequestBody UUID orderId) {
        try {
            log.info("Расчёт стоимости заказа {}", orderId);
            return orderService.calculateTotalCost(orderId);
        } catch (Exception e) {
            log.error("Ошибка расчета стоимости заказа.");
            throw e;
        }
    }

    @PostMapping("/calculate/delivery")
    public OrderDto calculateDeliveryCost(@RequestBody UUID orderId) {
        try {
            log.info("Расчёт стоимости доставки заказа {}", orderId);
            return orderService.calculateDeliveryCost(orderId);
        } catch (Exception e) {
            log.error("Ошибка расчета стоимости доставки заказа.");
            throw e;
        }
    }

    @PostMapping("/assembly")
    public OrderDto assembly(@RequestBody UUID orderId) {
        try {
            log.info("Сборка заказа {}", orderId);
            return orderService.assembly(orderId);
        } catch (Exception e) {
            log.error("Ошибка сборки заказа.");
            throw e;
        }
    }

    @PostMapping("/assembly/failed")
    public OrderDto assemblyFailed(@RequestBody UUID orderId) {
        try {
            log.info("Сборка заказа произошла с ошибкой {}", orderId);
            return orderService.assemblyFailed(orderId);
        } catch (Exception e) {
            log.error("Ошибка сборки заказа.");
            throw e;
        }
    }
}