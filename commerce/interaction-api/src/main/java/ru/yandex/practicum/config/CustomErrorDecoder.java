package ru.yandex.practicum.config;

import feign.Response;
import feign.codec.ErrorDecoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.InternalServerErrorException;
import ru.yandex.practicum.error.ErrorResponse;
import ru.yandex.practicum.exception.AnotherServiceBadRequestException;
import ru.yandex.practicum.exception.AnotherServiceNotFoundException;

import java.io.IOException;
import java.io.InputStream;

public class CustomErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultDecoder = new Default();

    @Override
    public Exception decode(String s, Response response) {

        ErrorResponse errorResponse;

        try (InputStream body = response.body().asInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            errorResponse = mapper.readValue(body, ErrorResponse.class);
        } catch (IOException ex) {
            return new Exception(ex.getMessage());
        }

        return switch (response.status()) {
            case 400 -> new AnotherServiceBadRequestException(
                    errorResponse.getUserMessage() != null ? errorResponse.getUserMessage() : "Bad request"
            );
            case 404 -> new AnotherServiceNotFoundException(
                    errorResponse.getUserMessage() != null ? errorResponse.getUserMessage() : "Not found"
            );
            case 500 -> new InternalServerErrorException(
                    errorResponse.getUserMessage() != null ? errorResponse.getUserMessage() : "Internal server error"
            );
            default -> defaultDecoder.decode(s, response);
        };
    }
}