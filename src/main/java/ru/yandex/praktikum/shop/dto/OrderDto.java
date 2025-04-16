package ru.yandex.praktikum.shop.dto;

import ru.yandex.praktikum.shop.model.OrderItem;

import java.util.List;

public class OrderDto {
    private long id;
    private List<OrderItem> orderItems;
    private double totalSum;

    public OrderDto(long id, List<OrderItem> orderItems, double totalSum) {
        this.id = id;
        this.orderItems = orderItems;
        this.totalSum = totalSum;
    }

    public long getId() {
        return id;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public double getTotalSum() {
        return totalSum;
    }
}
