package ru.yandex.praktikum.shop.dto;

public class CartItemDto {
    private long id;
    private long productId;
    private String title;
    private String description;
    private String image;
    private Double price;
    private int amount;

    public CartItemDto(long id, long productId, String title, String description, String image, Double price, int amount) {
        this.id = id;
        this.productId = productId;
        this.title = title;
        this.description = description;
        this.image = image;
        this.price = price;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public long getProductId() {
        return productId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public Double getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }
}
