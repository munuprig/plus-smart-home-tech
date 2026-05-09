package ru.yandex.practicum.warehouse.exception;

public class ProductInShoppingCartLowQuantityInWarehouseException extends RuntimeException {
    public ProductInShoppingCartLowQuantityInWarehouseException(String message) {
        super(message);
    }
}
