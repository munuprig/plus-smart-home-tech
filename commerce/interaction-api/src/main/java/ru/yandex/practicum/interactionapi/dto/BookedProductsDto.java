package ru.yandex.practicum.interactionapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookedProductsDto {
    @NotNull
    Double deliveryWeight;
    @NotNull
    Double deliveryVolume;
    @NotNull
    Boolean fragile;
}
