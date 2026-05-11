package ru.yandex.practicum.shoppingstore.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.interactionapi.enums.ProductCategory;
import ru.yandex.practicum.shoppingstore.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ShoppingStoreRepository extends JpaRepository<Product, UUID> {

    List<Product> findAllByProductCategory(ProductCategory productCategory, Pageable pageable);

    Optional<Product> findByProductId(UUID productId);
}
