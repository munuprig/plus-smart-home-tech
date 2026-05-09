package ru.yandex.practicum.warehouse.service;

import ru.yandex.practicum.interactionapi.dto.AddressDto;
import ru.yandex.practicum.interactionapi.dto.BookedProductsDto;
import ru.yandex.practicum.interactionapi.dto.ShoppingCartDto;
import ru.yandex.practicum.interactionapi.request.AddProductToWarehouseRequest;
import ru.yandex.practicum.interactionapi.request.NewProductInWarehouseRequest;

public interface WarehouseService {
    void newProductInWarehouse(NewProductInWarehouseRequest newProductInWarehouseRequest);

    BookedProductsDto checkProductQuantityEnoughForShoppingCart(ShoppingCartDto shoppingCartDto);

    void addProductToWarehouse(AddProductToWarehouseRequest requestDto);

    AddressDto getAddress();

//    void returnProductToWarehouse(Map<UUID, Long> products);
//
//    BookedProductsDto assemblyProductForOrder(AssemblyProductForOrderFromShoppingCartDto assemblyProductDto);

}
