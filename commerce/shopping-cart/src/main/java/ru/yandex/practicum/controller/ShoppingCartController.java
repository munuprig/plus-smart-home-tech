package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.BookedProductsDto;
import ru.yandex.practicum.request.ChangeProductQuantityRequest;
import ru.yandex.practicum.dto.ShoppingCartDto;
import ru.yandex.practicum.service.ShoppingCartService;

import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/shopping-cart")
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @GetMapping
    public ShoppingCartDto getShoppingCart(@RequestParam String username) {
        try {
            log.info("Получение актуальной корзины для авторизованного пользователя. {}", username);
            return shoppingCartService.getShoppingCart(username);
        } catch (Exception e) {
            log.error("Ошибка получения актуальной корзины.");
            throw e;
        }
    }


    @PutMapping
    public ShoppingCartDto addProductToShoppingCart(@RequestParam String username, @RequestBody Map<UUID, Long> request) {
        try {
            log.info("Добавление товара в корзину {}", username);
            return shoppingCartService.addProductToShoppingCart(username, request);
        } catch (Exception e) {
            log.error("Ошибка добавления товара в корзину.");
            throw e;
        }
    }

    @DeleteMapping
    public void deactivateCurrentShoppingCart(@RequestParam String username) {
        try {
            log.info("Деактивация корзины товаров для пользователя {}", username);
            shoppingCartService.deactivateCurrentShoppingCart(username);
        } catch (Exception e) {
            log.error("Ошибка деактивации корзины товаров для пользователя.");
            throw e;
        }
    }

    @PostMapping("/remove")
    public ShoppingCartDto removeFromShoppingCart(@RequestParam String username, @RequestBody Map<UUID, Long> request) {
        try {
            log.info("Изменение состава товаров в корзине {}", username);
            return shoppingCartService.removeFromShoppingCart(username, request);
        } catch (Exception e) {
            log.error("Ошибка изменения состава товаров в корзине.");
            throw e;
        }
    }

    @PostMapping("/change-quantity")
    public ShoppingCartDto changeProductQuantity(@RequestParam String username,
                                                 @RequestBody @Valid ChangeProductQuantityRequest requestDto) {
        try {
            log.info("Изменение количества товаров в корзине. {}", username);
            return shoppingCartService.changeProductQuantity(username, requestDto);
        } catch (Exception e) {
            log.error("Ошибка изменения количества товаров в корзине.");
            throw e;
        }
    }

    @PostMapping("/booking")
    public BookedProductsDto bookingProductsForUser(@RequestParam String username) {
        try {
            log.info("Бронирование корзины покупок для пользователя {}", username);
            return shoppingCartService.bookingProductsForUser(username);
        } catch (Exception e) {
            log.error("Ошибка бронирования корзины покупок для пользователя.");
            throw e;
        }
    }
}