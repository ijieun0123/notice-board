package com.example.noticeboard.controller;

import com.example.noticeboard.dto.UserDto;
import com.example.noticeboard.model.UserModel;
import com.example.noticeboard.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    // 로그인
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        logger.debug("Login attempt - Username: {}, Password: {}", username, password);

        boolean isAuthenticated = userService.authenticateUser(username, password);

        if (isAuthenticated) {
            logger.debug("Login successful, redirecting to /posts");
            // 로그인 성공 시, 세션에 사용자 정보 저장
            session.setAttribute("username", username);

            // 인증된 사용자의 권한 확인
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                logger.debug("Authenticated User: " + authentication.getName());  // 사용자 이름
                logger.debug("Authorities: " + authentication.getAuthorities());  // 권한 목록
            } else {
                logger.debug("No authentication found");
            }

            // 로그인 성공 시, 홈 페이지로 리다이렉트
            return "redirect:/posts";
        } else {
            logger.debug("Login failed, redirecting back to login page with error");
            // 로그인 실패 시, 로그인 페이지로 다시 리다이렉트
            return "redirect:/login?error=true";
        }
    }

    /**
     * 로그인 페이지 렌더링
     */
    @GetMapping("/login")
    public String loginPage() {
        return "posts/login"; // signup.html 페이지를 반환
    }

    /**
     * 회원가입 페이지 렌더링
     */
    @GetMapping("/signup")
    public String signupPage() {
        return "posts/signup"; // signup.html 페이지를 반환
    }

    // 회원가입
    @PostMapping("/signup")
    public String registerUser(@ModelAttribute UserDto userDto) {
        // 사용자 등록 처리
        UserModel userModel = userService.registerUser(userDto);

        // 사용자 정보 페이지로 리다이렉트 (ID 기반)
        return "redirect:/api/users/" + userModel.getModelId();
    }

//    /**
//     * 사용자 정보 조회
//     *
//     * @param id 사용자 ID
//     * @return 사용자 데이터 (DTO)
//     */
//    @GetMapping("/{id}")
//    public String getUserById(@PathVariable Long id, Model model) {
//        UserModel userModel = userService.getUserById(id);
//        model.addAttribute("user", userModel);  // 사용자 정보를 모델에 추가
//        return "posts/userInfo";  // userInfo.html 템플릿을 반환
//    }

//    /**
//     * 사용자 정보 업데이트
//     *
//     * @param id      사용자 ID
//     * @param userDto 업데이트할 사용자 데이터 (DTO)
//     * @return 업데이트된 사용자 데이터 (DTO)
//     */
//    @PutMapping("/{id}")
//    public UserDto updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
//        UserModel updatedModel = userService.updateUser(id, userDto);
//        return userService.convertToDto(updatedModel);
//    }

//    /**
//     * 사용자 삭제
//     *
//     * @param id 사용자 ID
//     * @return 성공 메시지
//     */
//    @DeleteMapping("/{id}")
//    public String deleteUser(@PathVariable Long id) {
//        userService.deleteUser(id);
//        return "User deleted successfully";
//
//    }
}
