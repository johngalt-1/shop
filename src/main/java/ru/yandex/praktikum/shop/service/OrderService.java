package ru.yandex.praktikum.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.praktikum.shop.model.Order;
import ru.yandex.praktikum.shop.repo.OrderRepo;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepo orderRepo;

    @Autowired
    public OrderService(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    public List<Order> findAll() {
        return orderRepo.findAll();
    }

    public Optional<Order> findById(long id) {
        return orderRepo.findById(id);
    }
}
