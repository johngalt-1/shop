package ru.yandex.praktikum.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.yandex.praktikum.shop.service.BuyService;

@Controller
@RequestMapping("/buy")
public class CheckoutController {
    @Autowired
    private BuyService buyService;

    @PostMapping
    public String buy() {
        long orderId = buyService.createOrder().getId();
        return "redirect:/orders/" + orderId;
    }
}
