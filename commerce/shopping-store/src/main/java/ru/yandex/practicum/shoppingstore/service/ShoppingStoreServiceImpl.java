package ru.yandex.practicum.shoppingstore.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import ru.yandex.practicum.interactionapi.request.SetProductQuantityStateRequest;
import ru.yandex.practicum.interactionapi.dto.PageableDto;
import ru.yandex.practicum.interactionapi.dto.ProductDto;
import ru.yandex.practicum.interactionapi.enums.ProductCategory;
import ru.yandex.practicum.interactionapi.enums.ProductState;
import ru.yandex.practicum.shoppingstore.exception.ProductNotFoundException;
import ru.yandex.practicum.shoppingstore.mapper.ProductMapper;
import ru.yandex.practicum.shoppingstore.model.Product;
import ru.yandex.practicum.shoppingstore.repository.ShoppingStoreRepository;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ShoppingStoreServiceImpl implements ShoppingStoreService {

    private final ShoppingStoreRepository shoppingStoreRepository;
    private final ProductMapper productMapper;

    @Transactional(readOnly = true)
    public List<ProductDto> getProducts(ProductCategory productCategory, PageableDto pageableDto) {
        Pageable pageRequest = PageRequest.of(pageableDto.getPage(), pageableDto.getSize(),
                Sort.by(Sort.DEFAULT_DIRECTION, String.join(",", pageableDto.getSort())));
        List<Product> products = shoppingStoreRepository.findAllByProductCategory(productCategory, pageRequest);
        if (CollectionUtils.isEmpty(products)) {
            return Collections.emptyList();
        } else {
            return productMapper.productsToProductsDto(products);
        }
    }

    @Override
    public ProductDto createNewProduct(ProductDto productDto) {
        Product newProduct = productMapper.productDtoToProduct(productDto);
        return productMapper.productToProductDto(shoppingStoreRepository.save(newProduct));
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) {
        Product oldProduct = shoppingStoreRepository.findByProductId(productDto.getProductId())
                .orElseThrow(() -> new ProductNotFoundException(
                        String.format("Ошибка, товар по id %s в БД не найден", productDto.getProductId()))
                );
        Product newProduct = productMapper.productDtoToProduct(productDto);
        newProduct.setProductId(oldProduct.getProductId());
        return productMapper.productToProductDto(shoppingStoreRepository.save(newProduct));
    }

    @Override
    public boolean removeProductFromStore(UUID productId) {
        Product product = shoppingStoreRepository.findByProductId(productId).orElseThrow(
                () -> new ProductNotFoundException(String.format("Ошибка, товар по id %s в БД не найден", productId))
        );
        product.setProductState(ProductState.DEACTIVATE);
        return true;
    }

    @Override
    public boolean setProductQuantityState(SetProductQuantityStateRequest setProductQuantityStateRequest) {
        Product product = shoppingStoreRepository.findByProductId(setProductQuantityStateRequest.getProductId())
                .orElseThrow(
                        () -> new ProductNotFoundException(String.format("Ошибка, товар по id %s в БД не найден", setProductQuantityStateRequest.getProductId()))
                );
        product.setQuantityState(setProductQuantityStateRequest.getQuantityState());
        return true;
    }

    @Override
    public ProductDto getProduct(UUID productId) {
        Product product = shoppingStoreRepository.findByProductId(productId).orElseThrow(
                () -> new ProductNotFoundException(String.format("Ошибка, товар по id %s в БД не найден", productId))
        );
        return productMapper.productToProductDto(product);
    }
}
