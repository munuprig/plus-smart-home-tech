package ru.yandex.practicum.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.enums.ProductCategory;
import ru.yandex.practicum.enums.ProductState;
import ru.yandex.practicum.enums.QuantityState;

import java.util.UUID;

@Entity
@Table(name = "products")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id", updatable = false, nullable = false)
    UUID productId;

    @Column(name = "product_name")
    String productName;
    String description;

    @Column(name = "image_src")
    String imageSrc;

    @Enumerated(EnumType.STRING)
    @Column(name = "quantity_state")
    QuantityState quantityState;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_state")
    ProductState productState;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_category")
    ProductCategory productCategory;

    double price;
    int rating;
}