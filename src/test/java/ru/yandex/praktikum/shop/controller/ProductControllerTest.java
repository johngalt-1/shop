package ru.yandex.praktikum.shop.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.praktikum.shop.model.Product;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProductControllerTest extends ControllerTest {

    @Test
    void getProduct() throws Exception {
        Product product = mock();
        when(productService.findProductById(anyLong())).thenReturn(product);

        mockMvc.perform(get("/items/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("item"))
                .andExpect(model().attributeExists("item"))
                .andExpect(model().attributeExists("amount"));

        verify(productService).findProductById(1);
        verify(cartService).findByProductId(1);
    }

    @Test
    void changeAmount() throws Exception {
        mockMvc.perform(post("/items/1").param("action", "PLUS"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/items/1"));
        verify(cartService).updateCartItem(1, ActionType.PLUS);
    }
}
