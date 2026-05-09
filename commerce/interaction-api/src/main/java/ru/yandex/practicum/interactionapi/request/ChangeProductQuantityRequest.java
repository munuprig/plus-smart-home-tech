package ru.yandex.practicum.interactionapi.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChangeProductQuantityRequest {
    @NotNull
    UUID productId;
    @NotNull
    @Min(0)
    Long newQuantity;
}
