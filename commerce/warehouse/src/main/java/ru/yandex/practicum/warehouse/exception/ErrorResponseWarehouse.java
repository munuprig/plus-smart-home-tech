package ru.yandex.practicum.warehouse.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.interactionapi.error.ErrorResponse;

import java.util.Arrays;

@Slf4j
@RestControllerAdvice
public class ErrorResponseWarehouse {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleCommonException(RuntimeException e) {
        log.error("500 {}", e.getMessage());
        return ErrorResponse.builder()
                .cause(e.getCause())
                .stackTrace(Arrays.asList(e.getStackTrace()))
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .userMessage(e.getMessage())
                .message("Internal Server Error")
                .suppressed(Arrays.asList(e.getSuppressed()))
                .localizedMessage(e.getLocalizedMessage())
                .build();
    }

    @ExceptionHandler({MissingServletRequestParameterException.class,
            MethodArgumentNotValidException.class,
            NoSpecifiedProductInWarehouseException.class,
            ProductInShoppingCartLowQuantityInWarehouseException.class,
            SpecifiedProductAlreadyInWarehouseException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequestException(RuntimeException e) {
        log.error("400 {}", e.getMessage());
        return ErrorResponse.builder()
                .cause(e.getCause())
                .stackTrace(Arrays.asList(e.getStackTrace()))
                .httpStatus(HttpStatus.BAD_REQUEST.name())
                .userMessage(e.getMessage())
                .message("Bad request")
                .suppressed(Arrays.asList(e.getSuppressed()))
                .localizedMessage(e.getLocalizedMessage())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleProductNotFoundException(ProductNotFoundInWarehouseException e) {
        log.error("404 {}", e.getMessage());
        return ErrorResponse.builder()
                .cause(e.getCause())
                .stackTrace(Arrays.asList(e.getStackTrace()))
                .httpStatus(HttpStatus.NOT_FOUND.name())
                .userMessage(e.getMessage())
                .message("Not Found")
                .suppressed(Arrays.asList(e.getSuppressed()))
                .localizedMessage(e.getLocalizedMessage())
                .build();
    }
}
