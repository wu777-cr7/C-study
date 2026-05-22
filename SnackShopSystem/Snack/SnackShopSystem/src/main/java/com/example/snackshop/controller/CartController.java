package com.example.snackshop.controller;

import com.example.snackshop.model.User;
import com.example.snackshop.service.CartService;
import com.example.snackshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    private Long getCurrentUserId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findByUsername(username).getId();
    }

    @GetMapping("/cart")
    public String cart(Model model) {
        Long userId = getCurrentUserId();
        model.addAttribute("cartItems", cartService.getCartItemsWithProducts(userId));
        return "cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Long productId, @RequestParam int quantity) {
        Long userId = getCurrentUserId();
        cartService.addToCart(userId, productId, quantity);
        return "redirect:/products";
    }

    @PostMapping("/cart/update")
    public String updateCart(@RequestParam Long productId, @RequestParam int quantity) {
        Long userId = getCurrentUserId();
        cartService.updateQuantity(userId, productId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam Long productId) {
        Long userId = getCurrentUserId();
        cartService.removeCartItem(userId, productId);
        return "redirect:/cart";
    }
}