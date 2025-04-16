package ru.yandex.praktikum.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.yandex.praktikum.shop.dto.OrderDto;
import ru.yandex.praktikum.shop.model.Order;
import ru.yandex.praktikum.shop.model.OrderItem;
import ru.yandex.praktikum.shop.service.CartService;
import ru.yandex.praktikum.shop.service.OrderService;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @GetMapping
    public String getOrders(Model model) {
        List<Order> orders = orderService.findAll();
        List<OrderDto> orderDtos = orders.stream().map(this::toOrderDto).toList();
        model.addAttribute("orders", orderDtos);
        return "orders";
    }

    @GetMapping("/{id}")
    public String getOrders(@PathVariable("id") long id, Model model) {
        Order order = orderService.findById(id).orElseThrow();
        OrderDto orderDto = toOrderDto(order);
        model.addAttribute("order", orderDto);
        return "order";
    }

    private OrderDto toOrderDto(Order order) {
        List<OrderItem> orderItems = order.getOrderItems();
        double total = orderItems.stream().mapToDouble(orderItem ->
                orderItem.getPrice() * orderItem.getAmount()
        ).sum();
        return new OrderDto(order.getId(), order.getOrderItems(), total);
    }
}
