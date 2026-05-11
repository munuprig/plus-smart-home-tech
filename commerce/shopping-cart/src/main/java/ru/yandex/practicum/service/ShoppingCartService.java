package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.BookedProductsDto;
import ru.yandex.practicum.request.ChangeProductQuantityRequest;
import ru.yandex.practicum.dto.ShoppingCartDto;

import java.util.Map;
import java.util.UUID;

public interface ShoppingCartService {

    ShoppingCartDto getShoppingCart(String username);

    ShoppingCartDto addProductToShoppingCart(String username, Map<UUID, Long> request);

    void deactivateCurrentShoppingCart(String username);

    ShoppingCartDto removeFromShoppingCart(String username, Map<UUID, Long> request);

    ShoppingCartDto changeProductQuantity(String username, ChangeProductQuantityRequest requestDto);

    BookedProductsDto bookingProductsForUser(String username);
}