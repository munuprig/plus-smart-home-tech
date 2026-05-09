package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.AddressDto;
import ru.yandex.practicum.dto.BookedProductsDto;
import ru.yandex.practicum.dto.ShoppingCartDto;
import ru.yandex.practicum.request.AddProductToWarehouseRequest;
import ru.yandex.practicum.request.NewProductInWarehouseRequest;

public interface WarehouseService {
    void newProductInWarehouse(NewProductInWarehouseRequest newProductInWarehouseRequest);

    BookedProductsDto checkProductQuantityEnoughForShoppingCart(ShoppingCartDto shoppingCartDto);

    void addProductToWarehouse(AddProductToWarehouseRequest requestDto);

    AddressDto getAddress();

}