package ru.yandex.praktikum.shop.controller;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;
import ru.yandex.praktikum.shop.dto.OrderDto;
import ru.yandex.praktikum.shop.model.Order;
import ru.yandex.praktikum.shop.model.OrderItem;
import ru.yandex.praktikum.shop.model.Product;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class OrderControllerTest extends ControllerTest {

    @Test
    void getOrders() throws Exception {
        Product product1 = new Product(1L, "product1", "just product", 100.0, "image.jpg", OffsetDateTime.now(), OffsetDateTime.now(), false);
        Product product2 = new Product(2L, "product2", "just product", 200.0, "image.jpg", OffsetDateTime.now(), OffsetDateTime.now(), false);
        OrderItem orderItem1 = new OrderItem(1L, product1, null, 10.5, 2);
        OrderItem orderItem2 = new OrderItem(2L, product2, null, 22.0, 3);
        Order order1 = new Order(1L, List.of(orderItem1), Order.OrderStatus.CREATED, OffsetDateTime.now(), OffsetDateTime.now());
        Order order2 = new Order(2L, List.of(orderItem2), Order.OrderStatus.CREATED, OffsetDateTime.now(), OffsetDateTime.now());
        orderItem1.setOrder(order1);
        orderItem2.setOrder(order2);
        when(orderService.findAll()).thenReturn(List.of(order1, order2));

        MvcResult result = mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("orders"))
                .andReturn();

        List<OrderDto> orderDtos = (List<OrderDto>) Objects.requireNonNull(result.getModelAndView()).getModel().get("orders");
        OrderDto orderDto1 = orderDtos.get(0);
        OrderDto orderDto2 = orderDtos.get(1);
        assertEquals(21.0, orderDto1.getTotalSum());
        assertEquals(66.0, orderDto2.getTotalSum());
    }
}