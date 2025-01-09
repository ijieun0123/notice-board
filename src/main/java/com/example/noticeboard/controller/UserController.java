package com.example.noticeboard.controller;

import com.example.noticeboard.model.UserModel;
import com.example.noticeboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 회원가입 페이지
    @GetMapping("/register")
    public String showRegisterPage() {
        return "posts/register";
    }

    // 회원가입 처리
    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password) {
        String encodedPassword = passwordEncoder.encode(password);
        UserModel newUser = new UserModel(username, encodedPassword);
        userService.saveUser(newUser);
        return "redirect:/login";  // 회원가입 후 로그인 페이지로 리다이렉트
    }
}

