package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.PageableDto;
import ru.yandex.practicum.request.SetProductQuantityStateRequest;
import ru.yandex.practicum.dto.ProductDto;
import ru.yandex.practicum.enums.ProductCategory;
import ru.yandex.practicum.service.ShoppingStoreService;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/shopping-store")
@RequiredArgsConstructor
public class ShoppingStoreController {

    private final ShoppingStoreService shoppingStoreService;

    @GetMapping
    public List<ProductDto> getProducts(@RequestParam ProductCategory productCategory, @Valid PageableDto pageableDto) {
        try {
            log.info("Получение списка товаров по типу в пагинированном виде.");
            return shoppingStoreService.getProducts(productCategory, pageableDto);
        } catch (Exception e) {
            log.error("Ошибка получения списка товаров.");
            throw e;
        }
    }

    @PutMapping
    public ProductDto createNewProduct(@RequestBody @Valid ProductDto productDto) {
        try {
            log.info("Создание нового товара в ассортименте {}", productDto);
            return shoppingStoreService.createNewProduct(productDto);
        } catch (Exception e) {
            log.error("Ошибка создания нового товара.");
            throw e;
        }
    }

    @PostMapping
    public ProductDto updateProduct(@RequestBody @Valid ProductDto productDto) {
        try {
            log.info("Обновление товара в ассортименте {}", productDto);
            return shoppingStoreService.updateProduct(productDto);
        } catch (Exception e) {
            log.error("Ошибка обновления товара.");
            throw e;
        }
    }

    @PostMapping("/removeProductFromStore")
    public Boolean removeProductFromStore(@RequestBody @NotNull UUID productId) {
        try {
            log.info("Удаление товара из ассортимента магазина. Функция для менеджерского состава. {}", productId);
            return shoppingStoreService.removeProductFromStore(productId);
        } catch (Exception e) {
            log.error("Ошибка удаления товара из магазина.");
            throw e;
        }
    }

    @PostMapping("/quantityState")
    public Boolean setProductQuantityState(@Valid SetProductQuantityStateRequest setProductQuantityStateRequest) {
        try {
            log.info("Установка статуса по товару {}", setProductQuantityStateRequest);
            return shoppingStoreService.setProductQuantityState(setProductQuantityStateRequest);
        } catch (Exception e) {
            log.error("Ошибка установки статуса.");
            throw e;
        }
    }

    @GetMapping("/{productId}")
    public ProductDto getProduct(@PathVariable @NotNull UUID productId) {
        try {
            log.info("Получение сведений по товару из БД: {}", productId);
            return shoppingStoreService.getProduct(productId);
        } catch (Exception e) {
            log.error("Ошибка получения сведений по товару из БД.");
            throw e;
        }
    }
}