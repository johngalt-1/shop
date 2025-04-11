package ru.yandex.praktikum.shop.controller;

import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.yandex.praktikum.shop.model.CartItem;
import ru.yandex.praktikum.shop.model.Product;
import ru.yandex.praktikum.shop.service.CartService;
import ru.yandex.praktikum.shop.service.ProductService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/main/items")
public class MainController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @GetMapping
    public String getAllProducts(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(name = "sort", defaultValue = "NO") SortType sortType,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            Model model
    ) {
        Sort sort = switch (sortType) {
            case NO -> Sort.unsorted();
            case ALPHA -> Sort.by("title");
            case PRICE -> Sort.by("price");
        };
        Page<Product> products = productService.findProducts(search, PageRequest.of(pageNumber, pageSize, sort));
        List<List<Product>> items = ListUtils.partition(products.getContent(), 3);
        model.addAttribute("items", items);
        List<CartItem> cartItems = cartService.findAllCartItems();
        Map<Long, Integer> cartAmounts = cartItems.stream().collect(Collectors.toMap(
                item -> item.getProduct().getId(),
                CartItem::getAmount
        ));
        model.addAttribute("cartAmounts", cartAmounts);
        model.addAttribute("search", search);
        model.addAttribute("paging", products.getPageable());
        model.addAttribute("hasPrevious", products.hasPrevious());
        model.addAttribute("hasNext", products.hasNext());
        model.addAttribute("sort", sortType.name());
        return "main";
    }

    @PostMapping("/{id}")
    public String changeAmount(
            @PathVariable("id") long productId,
            @RequestParam("action") ActionType actionType
    ) {
        cartService.updateCartItem(productId, actionType);
        return "redirect:/main/items";
    }

    public enum SortType {
        NO, ALPHA, PRICE;
    }
}
