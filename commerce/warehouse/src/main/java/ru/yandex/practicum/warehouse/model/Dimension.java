package ru.yandex.practicum.warehouse.model;

import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Dimension {
    Double width;
    Double height;
    Double depth;
}
