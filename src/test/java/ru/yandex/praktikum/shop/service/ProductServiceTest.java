package ru.yandex.praktikum.shop.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.yandex.praktikum.shop.repo.ProductRepo;

import static org.mockito.Mockito.*;

@SpringBootTest
class ProductServiceTest {

    @MockitoBean
    private ProductRepo productRepo;

    @Autowired
    private ProductService productService;

    @Test
    void findAllProducts() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        productService.findProducts("", pageRequest);
        verify(productRepo).findByDeletedFalse(pageRequest);
        verify(productRepo, never()).searchProducts(anyString(), any());
    }

    @Test
    void searchProducts() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        productService.findProducts("швабра", pageRequest);
        verify(productRepo).searchProducts("швабра", pageRequest);
        verify(productRepo, never()).findByDeletedFalse(any());
    }
}