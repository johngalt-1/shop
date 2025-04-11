package ru.yandex.praktikum.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.praktikum.shop.controller.ActionType;
import ru.yandex.praktikum.shop.model.CartItem;
import ru.yandex.praktikum.shop.model.Product;
import ru.yandex.praktikum.shop.repo.CartItemRepo;
import ru.yandex.praktikum.shop.repo.ProductRepo;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private final CartItemRepo cartItemRepo;
    private final ProductRepo productRepo;

    @Autowired
    public CartService(CartItemRepo cartItemRepo, ProductRepo productRepo) {
        this.cartItemRepo = cartItemRepo;
        this.productRepo = productRepo;
    }

    public void updateCartItem(long productId, ActionType actionType) {
        Optional<CartItem> cartItem = cartItemRepo.findByProductId(productId);
        if (cartItem.isEmpty()) {
            createCartItem(productId);
        } else if (actionType == ActionType.DELETE) {
            deleteCartItem(cartItem.get());
        } else {
            updateCartItem(cartItem.get(), actionType);
        }
    }

    public List<CartItem> findAllCartItems() {
        return cartItemRepo.findAll();
    }

    public Optional<CartItem> findByProductId(long productId) {
        return cartItemRepo.findByProductId(productId);
    }

    private void updateCartItem(CartItem cartItem, ActionType actionType) {
        int newAmount = switch (actionType) {
            case PLUS -> cartItem.getAmount() + 1;
            case MINUS -> cartItem.getAmount() - 1;
            default -> throw new IllegalArgumentException();
        };
        if (newAmount == 0) {
            deleteCartItem(cartItem);
        } else {
            cartItem.setAmount(newAmount);
            cartItemRepo.save(cartItem);
        }
    }

    private void deleteCartItem(CartItem cartItem) {
        cartItemRepo.delete(cartItem);
    }

    private void createCartItem(long productId) {
        Product product = productRepo.findById(productId).orElseThrow();
        CartItem cartItem = new CartItem(null, product, 1, OffsetDateTime.now());
        cartItemRepo.save(cartItem);
    }
}
