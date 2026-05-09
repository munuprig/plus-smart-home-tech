package ru.yandex.practicum.warehouse.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.interactionapi.dto.AddressDto;
import ru.yandex.practicum.interactionapi.dto.BookedProductsDto;
import ru.yandex.practicum.interactionapi.dto.ShoppingCartDto;
import ru.yandex.practicum.interactionapi.request.AddProductToWarehouseRequest;
import ru.yandex.practicum.interactionapi.request.NewProductInWarehouseRequest;
import ru.yandex.practicum.warehouse.service.WarehouseService;

@Slf4j
@RestController
@RequestMapping("/api/v1/warehouse")
@RequiredArgsConstructor
public class WarehouseController {
    private final WarehouseService warehouseService;

    @PutMapping
    public void newProductInWarehouse(@RequestBody @Valid NewProductInWarehouseRequest requestDto) {
        log.info("Добавление нового товара на склад {}", requestDto);
        warehouseService.newProductInWarehouse(requestDto);
    }

    @PostMapping("/check")
    public BookedProductsDto checkProductQuantityEnoughForShoppingCart(@RequestBody @Valid ShoppingCartDto shoppingCartDto) {
        log.info("Проверка достаточности продуктов для данной корзины {}", shoppingCartDto);
        return warehouseService.checkProductQuantityEnoughForShoppingCart(shoppingCartDto);
    }

    @PostMapping("/add")
    public void addProductToWarehouse(@RequestBody @Valid AddProductToWarehouseRequest requestDto) {
        log.info("Принятие товара на склад {}", requestDto);
        warehouseService.addProductToWarehouse(requestDto);
    }

    @GetMapping("/address")
    public AddressDto getAddress() {
        log.info("Получение адреса.");
        return warehouseService.getAddress();
    }
}
