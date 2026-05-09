package ru.yandex.practicum.interactionapi.circuitBreaker;

public class WarehouseServerUnavailable extends RuntimeException {
    public WarehouseServerUnavailable(String message) {
        super(message);
    }
}
