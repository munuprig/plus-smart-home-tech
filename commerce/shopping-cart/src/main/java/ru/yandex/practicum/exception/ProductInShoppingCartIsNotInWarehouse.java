package ru.yandex.practicum.exception;

public class ProductInShoppingCartIsNotInWarehouse extends RuntimeException {
    public ProductInShoppingCartIsNotInWarehouse(String message) {
        super(message);
    }
}