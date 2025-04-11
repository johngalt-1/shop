package ru.yandex.praktikum.shop.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ru.yandex.praktikum.shop.model.Product;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductRepoTest extends DatabaseTest {

    @Autowired
    private ProductRepo productRepo;

    @Test
    void findAll() {
        Page<Product> allProducts = productRepo.findByDeletedFalse(PageRequest.of(0, 10));
        assertEquals(3, allProducts.getContent().size());
    }

    @Test
    void searchProduct() {
        Page<Product> adidasProducts = productRepo.searchProducts("adidas", PageRequest.of(0, 10));
        assertEquals(2, adidasProducts.getContent().size());
        Page<Product> nikeProducts = productRepo.searchProducts("nike", PageRequest.of(0, 10));
        assertEquals(1, nikeProducts.getContent().size());
        Page<Product> hoodyProducts = productRepo.searchProducts("толстовка", PageRequest.of(0, 10));
        assertEquals(2, hoodyProducts.getContent().size());
        Page<Product> sneakersProducts = productRepo.searchProducts("кросс", PageRequest.of(0, 10));
        assertEquals(1, sneakersProducts.getContent().size());
    }
}