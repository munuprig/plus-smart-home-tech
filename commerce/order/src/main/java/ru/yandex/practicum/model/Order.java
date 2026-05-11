package ru.yandex.practicum.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.enums.OrderState;

import java.util.Map;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID orderId;
    UUID shoppingCartId;

    @ElementCollection
    @CollectionTable(name = "order_items", joinColumns = @JoinColumn(name = "order_id"))
    @MapKeyColumn(name = "product_id")
    @Column(name = "quantity")
    Map<UUID, Integer> products;
    UUID paymentId;
    UUID deliveryId;

    @Enumerated(EnumType.STRING)
    OrderState state;

    double deliveryWeight;
    double deliveryVolume;
    boolean fragile;
    double totalPrice;
    double deliveryPrice;
    double productPrice;
}