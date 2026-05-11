package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.PageableDto;
import ru.yandex.practicum.request.SetProductQuantityStateRequest;
import ru.yandex.practicum.dto.ProductDto;
import ru.yandex.practicum.enums.ProductCategory;

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