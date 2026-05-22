package com.example.snackshop.controller;

import com.example.snackshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public String products(Model model) {
        model.addAttribute("products", productService.getAllOnSaleProducts());
        return "products";
    }
}