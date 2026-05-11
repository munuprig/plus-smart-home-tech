package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.AddressDto;
import ru.yandex.practicum.request.AssemblyProductsForOrderRequest;
import ru.yandex.practicum.dto.BookedProductsDto;
import ru.yandex.practicum.dto.ShoppingCartDto;
import ru.yandex.practicum.request.AddProductToWarehouseRequest;
import ru.yandex.practicum.request.NewProductInWarehouseRequest;
import ru.yandex.practicum.request.ShippedToDeliveryRequest;
import ru.yandex.practicum.service.WarehouseService;

import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/warehouse")
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    @PutMapping
    public void newProductInWarehouse(@RequestBody @Valid NewProductInWarehouseRequest requestDto) {
        try {
            log.info("Добавить новый товар на склад {}", requestDto);
            warehouseService.newProductInWarehouse(requestDto);
        } catch (Exception e) {
            log.error("Ошибка добавления нового товара на склад.");
            throw e;
        }
    }

    @PostMapping("/shipped")
    public void shippedToDelivery(ShippedToDeliveryRequest deliveryRequest) {
        try {
            log.info("Передать товары в доставку {}", deliveryRequest);
            warehouseService.shippedToDelivery(deliveryRequest);
        } catch (Exception e) {
            log.error("Ошибка передачи товаров в доставку.");
            throw e;
        }
    }

    @PostMapping("/return")
    public void acceptReturn(@RequestBody Map<UUID, Long> products) {
        try {
            log.info("Принять возврат товаров на склад {}", products);
            warehouseService.acceptReturn(products);
        } catch (Exception e) {
            log.error("Ошибка возврата товаров на склад.");
            throw e;
        }
    }

    @PostMapping("/check")
    public BookedProductsDto checkProductQuantityEnoughForShoppingCart(@RequestBody @Valid ShoppingCartDto shoppingCartDto) {
        try {
            log.info("Предварительно проверить что количество товаров на складе достаточно для данной корзины продуктов {}", shoppingCartDto);
            return warehouseService.checkProductQuantityEnoughForShoppingCart(shoppingCartDto);
        } catch (Exception e) {
            log.error("Ошибка проверки.");
            throw e;
        }
    }

    @PostMapping("/assembly")
    public BookedProductsDto assemblyProductsForOrder(@RequestBody @Valid AssemblyProductsForOrderRequest assemblyProductsForOrder) {
        try {
            log.info("Собрать товары к заказу для подготовки к отправке {}",  assemblyProductsForOrder);
            return warehouseService.assemblyProductsForOrder(assemblyProductsForOrder);
        } catch (Exception e) {
            log.error("Ошибка сборки товаров.");
            throw e;
        }
    }

    @PostMapping("/add")
    public void addProductToWarehouse(@RequestBody @Valid AddProductToWarehouseRequest requestDto) {
        try {
            log.info("Принять товар на склад {}", requestDto);
            warehouseService.addProductToWarehouse(requestDto);
        } catch (Exception e) {
            log.error("Ошибка принятия товара на склад.");
            throw e;
        }
    }

    @GetMapping("/address")
    public AddressDto getWarehouseAddress() {
        try {
            log.info("Предоставить адрес склада для расчёта доставки.");
            return warehouseService.getWarehouseAddress();
        } catch (Exception e) {
            log.error("Ошибка предоставления адреса склада.");
            throw e;
        }
    }

    @PostMapping("/booking")
    public BookedProductsDto bookingProducts(@RequestBody @Valid ShoppingCartDto shoppingCartDto) {
        try {
            log.info("Бронирование корзины покупок {}", shoppingCartDto);
            return warehouseService.bookingProducts(shoppingCartDto);
        } catch (Exception e) {
            log.error("Ошибка бронирования корзины покупок.");
            throw e;
        }
    }
}