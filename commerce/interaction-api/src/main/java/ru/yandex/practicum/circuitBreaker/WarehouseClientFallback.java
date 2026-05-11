package ru.yandex.practicum.circuitBreaker;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.AddressDto;
import ru.yandex.practicum.dto.BookedProductsDto;
import ru.yandex.practicum.dto.ShoppingCartDto;
import ru.yandex.practicum.feign.WarehouseClient;
import ru.yandex.practicum.request.AddProductToWarehouseRequest;
import ru.yandex.practicum.request.AssemblyProductsForOrderRequest;
import ru.yandex.practicum.request.NewProductInWarehouseRequest;
import ru.yandex.practicum.request.ShippedToDeliveryRequest;

import java.util.Map;
import java.util.UUID;

@Component
public class WarehouseClientFallback implements WarehouseClient {

    private static final String MESSAGE_FALLBACK = "Fallback response: сервис временно недоступен.";

    @Override
    public void newProductInWarehouse(NewProductInWarehouseRequest requestDto) {
        throw new WarehouseServerUnavailable(MESSAGE_FALLBACK);
    }

    @Override
    public void shippedToDelivery(ShippedToDeliveryRequest deliveryRequest) {
        throw new WarehouseServerUnavailable(MESSAGE_FALLBACK);
    }

    @Override
    public void acceptReturn(Map<UUID, Long> products) {
        throw new WarehouseServerUnavailable(MESSAGE_FALLBACK);
    }

    @Override
    public BookedProductsDto checkProductQuantityEnoughForShoppingCart(ShoppingCartDto shoppingCartDto) {
        throw new WarehouseServerUnavailable(MESSAGE_FALLBACK);
    }

    @Override
    public BookedProductsDto assemblyProductForOrder(AssemblyProductsForOrderRequest assemblyProductsForOrder) {
        throw new WarehouseServerUnavailable(MESSAGE_FALLBACK);
    }

    @Override
    public void addProductToWarehouse(AddProductToWarehouseRequest requestDto) {
        throw new WarehouseServerUnavailable(MESSAGE_FALLBACK);
    }

    @Override
    public AddressDto getWarehouseAddress() {
        throw new WarehouseServerUnavailable(MESSAGE_FALLBACK);
    }

    @Override
    public BookedProductsDto bookingProducts(ShoppingCartDto shoppingCartDto) {
        throw new WarehouseServerUnavailable(MESSAGE_FALLBACK);
    }
}