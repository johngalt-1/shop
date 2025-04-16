package ru.yandex.praktikum.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.praktikum.shop.service.BuyService;
import ru.yandex.praktikum.shop.service.CartService;
import ru.yandex.praktikum.shop.service.OrderService;
import ru.yandex.praktikum.shop.service.ProductService;

@WebMvcTest
public class ControllerTest {
    @MockitoBean
    protected ProductService productService;

    @MockitoBean
    protected CartService cartService;

    @MockitoBean
    protected OrderService orderService;

    @MockitoBean
    protected BuyService buyService;

    @Autowired
    protected MockMvc mockMvc;
}
