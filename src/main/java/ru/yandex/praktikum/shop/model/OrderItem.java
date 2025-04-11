package ru.yandex.praktikum.shop.model;

import jakarta.persistence.*;

@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    private double price;
    private int amount;

    protected OrderItem() {}

    public OrderItem(Long id, Product product, Order order, double price, int amount) {
        this.id = id;
        this.product = product;
        this.order = order;
        this.price = price;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public Order getOrder() {
        return order;
    }

    public double getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
