package ru.yandex.practicum.interactionapi.dto;

import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageableDto {
    @Min(0)
    Integer page;
    @Min(1)
    Integer size;
    List<String> sort;
}
