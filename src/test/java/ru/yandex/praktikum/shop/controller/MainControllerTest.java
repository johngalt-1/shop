package ru.yandex.praktikum.shop.controller;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.yandex.praktikum.shop.model.CartItem;
import ru.yandex.praktikum.shop.model.Product;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class MainControllerTest extends ControllerTest {

    @Test
    void getAllProducts() throws Exception {
        Product product1 = mock();
        when(product1.getId()).thenReturn(1L);
        Product product2 = mock();
        when(product2.getId()).thenReturn(2L);
        when(productService.findProducts(eq("prod"), any())).thenReturn(
                new PageImpl<>(List.of(product1, product2), Pageable.ofSize(10), 2)
        );
        when(cartService.findAllCartItems()).thenReturn(
                List.of(
                        new CartItem(1L, product1, 2, OffsetDateTime.now()),
                        new CartItem(2L, product2, 5, OffsetDateTime.now())
                )
        );

        mockMvc.perform(get("/main/items")
                        .param("search", "prod")
                        .param("sort", "ALPHA")
                        .param("pageSize", "10")
                        .param("pageNumber", "0")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("main"))
                .andExpect(model().attribute("items", List.of(List.of(product1, product2))))
                .andExpect(model().attribute("cartAmounts", Map.of(1L, 2, 2L, 5)))
                .andExpect(model().attribute("search", "prod"))
                .andExpect(model().attribute("paging", Pageable.ofSize(10)))
                .andExpect(model().attribute("sort", "ALPHA"))
                .andExpect(model().attributeExists("hasPrevious"))
                .andExpect(model().attributeExists("hasNext"));

        verify(productService).findProducts("prod", PageRequest.of(0, 10, Sort.by("title")));
        verify(cartService).findAllCartItems();
    }

    @Test
    void changeAmount() throws Exception {
        mockMvc.perform(post("/main/items/1").param("action", "DELETE"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/main/items"));
        verify(cartService).updateCartItem(1, ActionType.DELETE);
    }
}