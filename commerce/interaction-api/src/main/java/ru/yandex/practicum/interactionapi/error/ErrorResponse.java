package ru.yandex.practicum.interactionapi.error;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorResponse {
    Throwable cause;
    List<StackTraceElement> stackTrace;
    String httpStatus;
    String userMessage;
    String message;
    List<Throwable> suppressed;
    String localizedMessage;
}
