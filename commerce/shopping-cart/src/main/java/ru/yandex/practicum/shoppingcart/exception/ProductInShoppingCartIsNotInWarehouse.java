package ru.yandex.practicum.shoppingcart.exception;

public class ProductInShoppingCartIsNotInWarehouse extends RuntimeException {
    public ProductInShoppingCartIsNotInWarehouse(String message) {
        super(message);
    }
}
