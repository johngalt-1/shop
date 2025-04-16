package ru.yandex.praktikum.shop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.praktikum.shop.model.Order;

public interface OrderRepo extends JpaRepository<Order, Long> {}