package ru.yandex.praktikum.shop.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.praktikum.shop.model.CartItem;
import ru.yandex.praktikum.shop.model.Product;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class CartItemRepoTest extends DatabaseTest {

    @Autowired
    private CartItemRepo cartItemRepo;

    @Autowired
    private ProductRepo productRepo;

    @Test
    void save() {
        Product product = productRepo.findById(2L).orElseThrow();
        CartItem cartItem = new CartItem(null, product, 10, OffsetDateTime.now());
        cartItemRepo.save(cartItem);
        Optional<CartItem> foundCartItem = cartItemRepo.findByProductId(2);
        assertTrue(foundCartItem.isPresent());
        assertEquals(2, foundCartItem.get().getProduct().getId());
        assertEquals(10, foundCartItem.get().getAmount());
    }

    @Test
    @Sql(statements =
            """
                    INSERT INTO shop.cart_item (product_id, amount, creation_time) VALUES (1, 1, '2025-04-01T00:00:00');
                    """,
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    void findByProductId() {
        Optional<CartItem> cartItem = cartItemRepo.findByProductId(1);
        assertTrue(cartItem.isPresent());
        assertEquals(1, cartItem.get().getProduct().getId());
    }
}