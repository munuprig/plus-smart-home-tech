package ru.yandex.practicum.shoppingstore.service;

import ru.yandex.practicum.interactionapi.dto.PageableDto;
import ru.yandex.practicum.interactionapi.request.SetProductQuantityStateRequest;
import ru.yandex.practicum.interactionapi.dto.ProductDto;
import ru.yandex.practicum.interactionapi.enums.ProductCategory;

import java.util.List;
import java.util.UUID;

public interface ShoppingStoreService {

    List<ProductDto> getProducts(ProductCategory productCategory, PageableDto pageableDto);

    ProductDto createNewProduct(ProductDto productDto);

    ProductDto updateProduct(ProductDto productDto);

    boolean removeProductFromStore(UUID productId);

    boolean setProductQuantityState(SetProductQuantityStateRequest setProductQuantityStateRequest);

    ProductDto getProduct(UUID productId);
}
