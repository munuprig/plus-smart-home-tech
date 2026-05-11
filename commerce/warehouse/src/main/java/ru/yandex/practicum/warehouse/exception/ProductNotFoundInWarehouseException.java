package ru.yandex.practicum.warehouse.exception;

public class ProductNotFoundInWarehouseException extends RuntimeException {
    public ProductNotFoundInWarehouseException(String message) {
        super(message);
    }
}
