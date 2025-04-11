package ru.yandex.praktikum.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.yandex.praktikum.shop.model.CartItem;
import ru.yandex.praktikum.shop.model.Product;
import ru.yandex.praktikum.shop.service.CartService;
import ru.yandex.praktikum.shop.service.ProductService;

@Controller
@RequestMapping("/items/{id}")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @GetMapping
    public String getProduct(@PathVariable("id") long id, Model model) {
        Product product = productService.findProductById(id);
        int amount = cartService.findByProductId(id).map(CartItem::getAmount).orElse(0);
        model.addAttribute("item", product);
        model.addAttribute("amount", amount);
        return "item";
    }

    @PostMapping
    public String changeAmount(
            @PathVariable("id") long id,
            @RequestParam("action") ActionType actionType
    ) {
        cartService.updateCartItem(id, actionType);
        return "redirect:/items/" + id;
    }
}
