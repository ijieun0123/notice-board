//package com.example.noticeboard.controller;
//
//import com.example.noticeboard.service.UserService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@Controller
//public class AuthController {
//
//    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
//
//    private UserService userService;
//
//    @GetMapping("/login")
//    public String login() {
//        logger.info("Login page accessed");
//        return "posts/login"; // `login.html` 템플릿 반환
//    }
//
//    @PostMapping("/login")
//    public String login(@RequestParam String username, @RequestParam String password) {
//        logger.info("Login attempt for username: {}", username);
//        logger.info("Login attempt for password: {}", password);
//
//        if (userService.checkPassword(username, password)) {
//            logger.info("Login successful for username: {}", username);
//            // 로그인 성공
//            return "redirect:/posts"; // 로그인 후 게시판 페이지로 리다이렉트
//        } else {
//            logger.info("Login failed for username: {}", username);
//            // 로그인 실패
//            return "redirect:/login?error"; // 로그인 실패 시 에러 페이지로 리다이렉트
//        }
//    }
//}
//
