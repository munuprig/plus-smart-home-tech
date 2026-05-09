package ru.yandex.practicum.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressDto {
    String country;
    String city;
    String street;
    String house;
    String flat;
}