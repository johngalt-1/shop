package ru.yandex.praktikum.shop.service;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.yandex.praktikum.shop.model.CartItem;
import ru.yandex.praktikum.shop.model.Order;
import ru.yandex.praktikum.shop.model.OrderItem;
import ru.yandex.praktikum.shop.model.Product;
import ru.yandex.praktikum.shop.repo.CartItemRepo;
import ru.yandex.praktikum.shop.repo.OrderRepo;

import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.yandex.praktikum.shop.model.Order.OrderStatus.CREATED;

@SpringBootTest
class BuyServiceTest {

    @MockitoBean
    private OrderRepo orderRepo;

    @MockitoBean
    private CartItemRepo cartItemRepo;

    @Autowired
    private BuyService buyService;

    @Captor
    private ArgumentCaptor<Order> captor;

    @Test
    void createOrder() {
        CartItem cartItem1 = createCartItem(1, 1, 700.0);
        CartItem cartItem2 = createCartItem(2, 2, 1000.0);
        CartItem cartItem3 = createCartItem(3, 10, 100.0);
        when(cartItemRepo.findAll()).thenReturn(List.of(cartItem1, cartItem2, cartItem3));
        buyService.createOrder();

        verify(orderRepo).save(captor.capture());
        Order order = captor.getValue();
        assertEquals(CREATED, order.getStatus());
        List<OrderItem> orderItems = order.getOrderItems();
        assertEquals(3, orderItems.size());
        assertCartItem(orderItems.get(0), 1, 1, 700.0);
        assertCartItem(orderItems.get(1), 2, 2, 1000.0);
        assertCartItem(orderItems.get(2), 3, 10, 100.0);

        verify(cartItemRepo).deleteAll();
    }

    private void assertCartItem(OrderItem orderItem, long id, int amount, double price) {
        assertEquals(id, orderItem.getProduct().getId());
        assertEquals(amount, orderItem.getAmount());
        assertEquals(price, orderItem.getPrice());
    }

    private CartItem createCartItem(long id, int amount, double price) {
        return new CartItem(id, createProduct(id, price), amount, OffsetDateTime.now());
    }

    private Product createProduct(long id, double price) {
        return new Product(id, "test", "test", price, "image.png", OffsetDateTime.now(), OffsetDateTime.now(), false);
    }
}