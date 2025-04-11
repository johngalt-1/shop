package ru.yandex.praktikum.shop.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.praktikum.shop.model.Order;
import ru.yandex.praktikum.shop.model.OrderItem;
import ru.yandex.praktikum.shop.model.Product;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderRepoTest extends DatabaseTest {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ProductRepo productRepo;

    @Test
    void save() {
        Product product1 = productRepo.findById(1L).orElseThrow();
        Product product2 = productRepo.findById(2L).orElseThrow();
        OrderItem orderItem1 = new OrderItem(null, product1, null, 10.0, 1);
        OrderItem orderItem2 = new OrderItem(null, product2, null, 10.0, 1);
        Order order = new Order(null, List.of(orderItem1, orderItem2), Order.OrderStatus.CREATED, OffsetDateTime.now(), OffsetDateTime.now());
        orderItem1.setOrder(order);
        orderItem2.setOrder(order);
        Order createdOrder = orderRepo.save(order);

        long orderId = createdOrder.getId();
        Optional<Order> foundOrder = orderRepo.findById(orderId);
        assertTrue(foundOrder.isPresent());
        List<OrderItem> foundOrderItems = foundOrder.get().getOrderItems();
        assertEquals(2, foundOrderItems.size());

        OrderItem foundOrderItem1 = foundOrderItems.getFirst();
        assertEquals(1, foundOrderItem1.getProduct().getId());

        OrderItem foundOrderItem2 = foundOrderItems.getLast();
        assertEquals(2, foundOrderItem2.getProduct().getId());
    }
}