package ru.yandex.practicum.shoppingcart.service;

import ru.yandex.practicum.interactionapi.dto.BookedProductsDto;
import ru.yandex.practicum.interactionapi.request.ChangeProductQuantityRequest;
import ru.yandex.practicum.interactionapi.dto.ShoppingCartDto;

import java.util.Map;
import java.util.UUID;

public interface ShoppingCartService {

    ShoppingCartDto getShoppingCart(String username);

    ShoppingCartDto addProductToShoppingCart(String username, Map<UUID, Long> request);

    void deactivateCurrentShoppingCart(String username);

    ShoppingCartDto removeFromShoppingCart(String username, Map<UUID, Long> request);

    ShoppingCartDto changeProductQuantity(String username, ChangeProductQuantityRequest requestDto);

    BookedProductsDto bookingProducts(String username);
}
