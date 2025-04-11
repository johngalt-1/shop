package ru.yandex.praktikum.shop.model;

import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;
    private int amount;
    private OffsetDateTime creationTime;

    protected CartItem() {}

    public CartItem(Long id, Product product, int amount, OffsetDateTime creationTime) {
        this.id = id;
        this.product = product;
        this.amount = amount;
        this.creationTime = creationTime;
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public int getAmount() {
        return amount;
    }

    public OffsetDateTime getCreationTime() {
        return creationTime;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
