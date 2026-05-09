package ru.yandex.practicum.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShoppingCartDto {
    @NotNull
    UUID shoppingCartId;

    @NotNull
    @NotEmpty
    Map<@NotNull UUID, @NotNull @Positive Integer> products;


}