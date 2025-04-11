package ru.yandex.praktikum.shop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.praktikum.shop.model.CartItem;

import java.util.Optional;

public interface CartItemRepo extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByProductId(long productId);
}