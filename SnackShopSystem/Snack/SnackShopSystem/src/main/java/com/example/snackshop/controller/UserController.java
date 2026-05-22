package com.example.snackshop.controller;

import com.example.snackshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/doRegister")
    public String doRegister(@RequestParam String username, @RequestParam String password,
                             @RequestParam String realName, @RequestParam String address,
                             @RequestParam String phone, Model model) {
        boolean success = userService.register(username, password, realName, address, phone);
        if (success) {
            return "redirect:/login";
        } else {
            model.addAttribute("error", "用户名已存在");
            return "register";
        }
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }
}