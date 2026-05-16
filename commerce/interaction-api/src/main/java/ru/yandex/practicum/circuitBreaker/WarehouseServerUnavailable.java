package ru.yandex.practicum.circuitBreaker;

public class WarehouseServerUnavailable extends RuntimeException {
    public WarehouseServerUnavailable(String message) {
        super(message);
    }
}