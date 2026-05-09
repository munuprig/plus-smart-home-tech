package ru.yandex.practicum.interactionapi.feign;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.interactionapi.dto.PageableDto;
import ru.yandex.practicum.interactionapi.dto.ProductDto;
import ru.yandex.practicum.interactionapi.enums.ProductCategory;
import ru.yandex.practicum.interactionapi.enums.QuantityState;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "shopping-store", path = "/api/v1/shopping-store")
public interface ShopingStoreClient {

    @GetMapping
    List<ProductDto> getProducts(@RequestParam ProductCategory productCategory, @Valid PageableDto pageableDto);

    @PutMapping
    ProductDto createNewProduct(@RequestBody @Valid ProductDto productDto);

    @PostMapping
    ProductDto updateProduct(@RequestBody @Valid ProductDto productDto);

    @PostMapping("/removeProductFromStore")
    Boolean removeProductFromStore(@RequestBody @NotNull UUID productId);

    @PostMapping("/quantityState")
    Boolean setProductQuantityState(@RequestParam UUID productId, @RequestParam QuantityState quantityState);

    @GetMapping("/{productId}")
    ProductDto getProduct(@PathVariable @NotNull UUID productId);
}
