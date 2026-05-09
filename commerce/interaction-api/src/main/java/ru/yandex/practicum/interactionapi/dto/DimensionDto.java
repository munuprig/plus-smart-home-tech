package ru.yandex.practicum.interactionapi.dto;

import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DimensionDto {
    @Min(1)
    Double width;
    @Min(1)
    Double height;
    @Min(1)
    Double depth;
}
