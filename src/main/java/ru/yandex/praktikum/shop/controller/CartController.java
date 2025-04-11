package ru.yandex.praktikum.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.yandex.praktikum.shop.dto.CartItemDto;
import ru.yandex.praktikum.shop.model.CartItem;
import ru.yandex.praktikum.shop.service.CartService;

import java.util.List;

@Controller
@RequestMapping("/cart/items")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public String getCartItems(Model model) {
        List<CartItem> cartItems = cartService.findAllCartItems();
        List<CartItemDto> items = cartItems.stream().map(cartItem ->
                new CartItemDto(
                        cartItem.getId(),
                        cartItem.getProduct().getId(),
                        cartItem.getProduct().getTitle(),
                        cartItem.getProduct().getDescription(),
                        cartItem.getProduct().getImage(),
                        cartItem.getProduct().getPrice() * cartItem.getAmount(),
                        cartItem.getAmount()
                )
        ).toList();
        double total = items.stream().mapToDouble(CartItemDto::getPrice).sum();
        model.addAttribute("items", items);
        model.addAttribute("total", total);
        model.addAttribute("empty", items.isEmpty());
        return "cart";
    }

    @PostMapping("/{id}")
    public String changeAmount(
            @PathVariable("id") long productId,
            @RequestParam("action") ActionType actionType
    ) {
        cartService.updateCartItem(productId, actionType);
        return "redirect:/cart/items";
    }
}
