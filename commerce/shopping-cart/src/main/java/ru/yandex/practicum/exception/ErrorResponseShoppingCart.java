package ru.yandex.practicum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.error.ErrorResponse;

import java.util.Arrays;

@RestControllerAdvice
public class ErrorResponseShoppingCart {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleCommonException(RuntimeException e) {
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
            MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequestException(RuntimeException e) {
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
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleUnauthorizedException(NotAuthorizedUserException e) {
        return ErrorResponse.builder()
                .cause(e.getCause())
                .stackTrace(Arrays.asList(e.getStackTrace()))
                .httpStatus(HttpStatus.UNAUTHORIZED.name())
                .userMessage(e.getMessage())
                .message("Not Authorized")
                .suppressed(Arrays.asList(e.getSuppressed()))
                .localizedMessage(e.getLocalizedMessage())
                .build();
    }

    @ExceptionHandler({NoProductsInShoppingCartException.class,
            ProductInShoppingCartIsNotInWarehouse.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(RuntimeException e) {
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