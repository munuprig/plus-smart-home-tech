package ru.yandex.practicum.exception;

public class AnotherServiceBadRequestException extends RuntimeException {
    public AnotherServiceBadRequestException(String message) {
        super(message);
    }
}