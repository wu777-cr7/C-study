package com.example.snackshop.controller;

import com.example.snackshop.model.User;
import com.example.snackshop.service.OrderService;
import com.example.snackshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    private Long getCurrentUserId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findByUsername(username).getId();
    }

    @GetMapping("/checkout")
    public String checkout(Model model) {
        User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("user", user);
        return "checkout";
    }

    @PostMapping("/checkout/submit")
    public String submitOrder(@RequestParam String receiverName,
                              @RequestParam String receiverPhone,
                              @RequestParam String receiverAddress) throws Exception {
        Long userId = getCurrentUserId();
        String orderNumber = orderService.createOrder(userId, receiverName, receiverPhone, receiverAddress);
        return "redirect:/payment?orderNumber=" + orderNumber;
    }

    @GetMapping("/payment")
    public String paymentPage(@RequestParam String orderNumber, Model model) {
        model.addAttribute("orderNumber", orderNumber);
        return "payment";
    }

    @PostMapping("/payment/do")
    public String doPay(@RequestParam String orderNumber) throws Exception {
        orderService.payOrder(orderNumber);
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String orders(Model model) {
        Long userId = getCurrentUserId();
        model.addAttribute("orders", orderService.getUserOrders(userId));
        model.addAttribute("orderService", orderService);
        return "orders";
    }

    @PostMapping("/order/cancel")
    public String cancelOrder(@RequestParam String orderNumber) throws Exception {
        orderService.cancelOrder(orderNumber);
        return "redirect:/orders";
    }
}