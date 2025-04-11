package ru.yandex.praktikum.shop.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.praktikum.shop.model.Order;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CheckoutControllerTest extends ControllerTest {

    @Test
    void buy() throws Exception {
        Order order = mock();
        when(order.getId()).thenReturn(10L);
        when(buyService.createOrder()).thenReturn(order);

        mockMvc.perform(post("/buy"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/orders/10"));

        verify(buyService).createOrder();
    }
}