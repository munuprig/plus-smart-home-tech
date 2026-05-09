package ru.yandex.practicum.shoppingcart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.shoppingcart.model.ShoppingCart;

import java.util.UUID;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, UUID> {

    ShoppingCart findByUsername(String username);

}
