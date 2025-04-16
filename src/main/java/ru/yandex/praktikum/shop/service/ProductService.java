package ru.yandex.praktikum.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.yandex.praktikum.shop.model.Product;
import ru.yandex.praktikum.shop.repo.ProductRepo;

@Service
public class ProductService {
    private final ProductRepo productRepo;

    @Autowired
    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public Product findProductById(long productId) {
        return productRepo.findById(productId).orElseThrow();
    }

    public Page<Product> findProducts(String search, PageRequest pageRequest) {
        if (search.isBlank()) {
            return productRepo.findByDeletedFalse(pageRequest);
        } else {
            return productRepo.searchProducts(search, pageRequest);
        }
    }
}
