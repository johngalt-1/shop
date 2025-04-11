package ru.yandex.praktikum.shop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.OffsetDateTime;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Double price;
    private String image;
    private OffsetDateTime creationTime;
    private OffsetDateTime updateTime;
    private boolean deleted;

    protected Product() {}

    public Product(Long id, String title, String description, Double price, String image, OffsetDateTime creationTime, OffsetDateTime updateTime, boolean deleted) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.image = image;
        this.creationTime = creationTime;
        this.updateTime = updateTime;
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public OffsetDateTime getCreationTime() {
        return creationTime;
    }

    public OffsetDateTime getUpdateTime() {
        return updateTime;
    }

    public boolean isDeleted() {
        return deleted;
    }
}
