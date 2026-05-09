package ru.yandex.practicum.shoppingcart.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "shopping_cart")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID shoppingCartId;
    String username;
    boolean active;
    @ElementCollection
    @CollectionTable(name = "shopping_cart_items", joinColumns = @JoinColumn(name = "cart_id"))
    @MapKeyColumn(name = "product_id")
    @Column(name = "quantity")
    Map<UUID, Long> products;
}
