package ru.yandex.praktikum.shop.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.praktikum.shop.model.CartItem;
import ru.yandex.praktikum.shop.model.Product;

import java.time.OffsetDateTime;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CartControllerTest extends ControllerTest {

    @Test
    void getCartItems() throws Exception {
        Product product1 = new Product(1L, "product1", "just product", 100.0, "image.jpg", OffsetDateTime.now(), OffsetDateTime.now(), false);
        Product product2 = new Product(2L, "product2", "just product", 200.0, "image.jpg", OffsetDateTime.now(), OffsetDateTime.now(), false);
        CartItem cartItem1 = new CartItem(1L, product1, 10, OffsetDateTime.now());
        CartItem cartItem2 = new CartItem(2L, product2, 15, OffsetDateTime.now());
        when(cartService.findAllCartItems()).thenReturn(List.of(cartItem1, cartItem2));

        mockMvc.perform(get("/cart/items"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("cart"))
                .andExpect(model().attribute(
                        "items",
                        allOf(
                                hasItem(
                                        allOf(
                                                hasProperty("id", equalTo(1L)),
                                                hasProperty("productId", equalTo(1L)),
                                                hasProperty("title", equalTo("product1")),
                                                hasProperty("description", equalTo("just product")),
                                                hasProperty("image", equalTo("image.jpg")),
                                                hasProperty("price", equalTo(1000.0)),
                                                hasProperty("amount", equalTo(10))
                                        )
                                ),
                                hasItem(
                                        allOf(
                                                hasProperty("id", equalTo(2L)),
                                                hasProperty("productId", equalTo(2L)),
                                                hasProperty("title", equalTo("product2")),
                                                hasProperty("description", equalTo("just product")),
                                                hasProperty("image", equalTo("image.jpg")),
                                                hasProperty("price", equalTo(3000.0)),
                                                hasProperty("amount", equalTo(15))
                                        )
                                )
                        ))
                ).andExpect(model().attribute("total", 4000.0))
                .andExpect(model().attribute("empty", false));
    }

    @Test
    void changeAmount() throws Exception {
        mockMvc.perform(post("/cart/items/1").param("action", "PLUS"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cart/items"));
        verify(cartService).updateCartItem(1, ActionType.PLUS);
    }
}