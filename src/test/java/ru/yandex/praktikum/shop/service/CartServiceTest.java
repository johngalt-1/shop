package ru.yandex.praktikum.shop.service;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.yandex.praktikum.shop.controller.ActionType;
import ru.yandex.praktikum.shop.model.CartItem;
import ru.yandex.praktikum.shop.model.Product;
import ru.yandex.praktikum.shop.repo.CartItemRepo;
import ru.yandex.praktikum.shop.repo.ProductRepo;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class CartServiceTest {

    @MockitoBean
    private CartItemRepo cartItemRepo;

    @MockitoBean
    private ProductRepo productRepo;

    @Autowired
    private CartService cartService;

    @Captor
    private ArgumentCaptor<CartItem> captor;

    @Test
    void createCartItem() {
        Product product = createProduct();
        when(productRepo.findById(anyLong())).thenReturn(Optional.of(product));
        when(cartItemRepo.findByProductId(anyLong())).thenReturn(Optional.empty());

        cartService.updateCartItem(1, ActionType.PLUS);

        verify(cartItemRepo).save(captor.capture());
        CartItem value = captor.getValue();
        assertNotNull(value);
        assertEquals(1, value.getProduct().getId());
        assertEquals(1, value.getAmount());
    }

    @Test
    void increaseCartItemAmount() {
        mockCartItemRepoBehaviour(1);

        cartService.updateCartItem(1, ActionType.PLUS);

        verify(cartItemRepo).save(captor.capture());
        assertEquals(2, captor.getValue().getAmount());
        verify(cartItemRepo, never()).delete(any());
    }

    @Test
    void decreaseCartItemAmount() {
        mockCartItemRepoBehaviour(2);

        cartService.updateCartItem(1, ActionType.MINUS);

        verify(cartItemRepo).save(captor.capture());
        assertEquals(1, captor.getValue().getAmount());
        verify(cartItemRepo, never()).delete(any());
    }

    @Test
    void decreaseCartItemAmountToZero() {
        mockCartItemRepoBehaviour(1);

        cartService.updateCartItem(1, ActionType.MINUS);

        verify(cartItemRepo).delete(any());
        verify(cartItemRepo, never()).save(any());
    }

    @Test
    void deleteCartItem() {
        mockCartItemRepoBehaviour(1);

        cartService.updateCartItem(1, ActionType.DELETE);

        verify(cartItemRepo).delete(any());
        verify(cartItemRepo, never()).save(any());
    }

    private void mockCartItemRepoBehaviour(int amount) {
        Product product = createProduct();
        CartItem cartItem = new CartItem(1L, product, amount, OffsetDateTime.now());
        when(cartItemRepo.findByProductId(anyLong())).thenReturn(Optional.of(cartItem));
    }

    private Product createProduct() {
        return new Product(1L, "Colgate", "Зубная паста", 200.0, "image.png", OffsetDateTime.now(), OffsetDateTime.now(), false);
    }
}
