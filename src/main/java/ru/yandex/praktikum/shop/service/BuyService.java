package ru.yandex.praktikum.shop.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.praktikum.shop.model.CartItem;
import ru.yandex.praktikum.shop.model.Order;
import ru.yandex.praktikum.shop.model.OrderItem;
import ru.yandex.praktikum.shop.repo.CartItemRepo;
import ru.yandex.praktikum.shop.repo.OrderRepo;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class BuyService {

    private final OrderRepo orderRepo;
    private final CartItemRepo cartItemRepo;

    @Autowired
    public BuyService(OrderRepo orderRepo, CartItemRepo cartItemRepo) {
        this.orderRepo = orderRepo;
        this.cartItemRepo = cartItemRepo;
    }

    @Transactional
    public Order createOrder() {
        List<CartItem> cartItems = cartItemRepo.findAll();
        List<OrderItem> orderItems = cartItems.stream().map(item ->
                new OrderItem(null, item.getProduct(), null, item.getProduct().getPrice(), item.getAmount())
        ).toList();
        Order order = new Order(null, orderItems, Order.OrderStatus.CREATED, OffsetDateTime.now(), OffsetDateTime.now());
        orderItems.forEach(orderItem -> orderItem.setOrder(order));
        Order createdOrder = orderRepo.save(order);
        cartItemRepo.deleteAll();
        return createdOrder;
    }
}
