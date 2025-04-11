package ru.yandex.praktikum.shop.model;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;
    private OrderStatus status;
    private OffsetDateTime creationTime;
    private OffsetDateTime updateTime;

    public enum OrderStatus {
        CREATED, CANCELLED, FINISHED;
    }

    protected Order() {
    }

    public Order(Long id, List<OrderItem> orderItems, OrderStatus status, OffsetDateTime creationTime, OffsetDateTime updateTime) {
        this.id = id;
        this.orderItems = orderItems;
        this.status = status;
        this.creationTime = creationTime;
        this.updateTime = updateTime;
    }

    public Long getId() {
        return id;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public OffsetDateTime getCreationTime() {
        return creationTime;
    }

    public OffsetDateTime getUpdateTime() {
        return updateTime;
    }
}