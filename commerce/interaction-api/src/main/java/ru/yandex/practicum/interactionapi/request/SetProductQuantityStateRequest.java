package ru.yandex.practicum.interactionapi.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.interactionapi.enums.QuantityState;

import java.util.UUID;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SetProductQuantityStateRequest {
    @NotNull
    UUID productId;
    @NotNull
    QuantityState quantityState;
}
