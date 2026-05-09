package ru.yandex.practicum.interactionapi.circuitBreaker;

import jakarta.validation.Valid;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.interactionapi.dto.AddressDto;
import ru.yandex.practicum.interactionapi.dto.BookedProductsDto;
import ru.yandex.practicum.interactionapi.dto.ShoppingCartDto;
import ru.yandex.practicum.interactionapi.feign.WarehouseClient;
import ru.yandex.practicum.interactionapi.request.AddProductToWarehouseRequest;
import ru.yandex.practicum.interactionapi.request.NewProductInWarehouseRequest;

@Component
public class WarehouseClientFallback implements WarehouseClient {

    @Override
    public void newProductInWarehouse(NewProductInWarehouseRequest requestDto) {
        throw new WarehouseServerUnavailable("Fallback response: сервис временно недоступен.");
    }

    @Override
    public BookedProductsDto checkProductQuantityEnoughForShoppingCart(ShoppingCartDto shoppingCartDto) {
        throw new WarehouseServerUnavailable("Fallback response: сервис временно недоступен.");
    }

    @Override
    public void addProductToWarehouse(AddProductToWarehouseRequest requestDto) {
        throw new WarehouseServerUnavailable("Fallback response: сервис временно недоступен.");
    }

    @Override
    public AddressDto getAddress() {
        throw new WarehouseServerUnavailable("Fallback response: сервис временно недоступен.");
    }

    @Override
    public BookedProductsDto bookingProducts(ShoppingCartDto shoppingCartDto) {
        throw new WarehouseServerUnavailable("Fallback response: сервис временно недоступен.");
    }
}
