package ru.yandex.practicum.exception;

public class ProductInShoppingCartIsNotInWarehouseException extends RuntimeException {
    public ProductInShoppingCartIsNotInWarehouseException(String message) {
        super(message);
    }
}