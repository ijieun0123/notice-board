package com.example.noticeboard.controller;

import com.example.noticeboard.dto.UserDto;
import com.example.noticeboard.entity.UserEntity;
import com.example.noticeboard.model.UserModel;
import com.example.noticeboard.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Controller
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 회원가입 페이지 렌더링
     */
    @GetMapping("/signup")
    public String signupPage() {
        return "posts/signup"; // signup.html 페이지를 반환
    }

    // 회원가입 처리 후 사용자 정보 페이지로 리다이렉트 ( HTML 폼 데이터에 적합 )
//    @PostMapping("/signup")
//    public String registerUser(@ModelAttribute UserDto userDto) {
//        System.out.println("UserController/registerUser - Received UserDto: " + userDto);
//
//        UserModel userModel = userService.registerUser(userDto);
//        return "redirect:/api/users/" + userModel.getModelId();  // 회원가입 후 사용자 정보 페이지로 리다이렉트
//    }

    // JSON 데이터 매핑에 적합
    @PostMapping("/signup")
    public String registerUser(@ModelAttribute UserDto userDto) {
        System.out.println("UserController/registerUser - Received UserDto: " + userDto);

        // 사용자 등록 처리
        UserModel userModel = userService.registerUser(userDto);

        System.out.println("UserController/registerUser - Created UserModel: " + userModel);

        // 사용자 정보 페이지로 리다이렉트 (ID 기반)
        return "redirect:/api/users/" + userModel.getModelId();
    }

    /**
     * 사용자 정보 조회
     * @param id 사용자 ID
     * @return 사용자 데이터 (DTO)
     */
    @GetMapping("/{id}")
    public String getUserById(@PathVariable Long id, Model model) {
        UserModel userModel = userService.getUserById(id);
        model.addAttribute("user", userModel);  // 사용자 정보를 모델에 추가
        return "posts/userInfo";  // userInfo.html 템플릿을 반환
    }

    /**
     * 사용자 정보 조회
     * @param id 사용자 ID
     * @return 사용자 데이터 (DTO)
     */
//    @GetMapping("/{id}")
//    public UserDto getUserById(@PathVariable Long id) {
//        UserModel userModel = userService.getUserById(id);
//        return userService.convertToDto(userModel);
//    }

    /**
     * 사용자 정보 업데이트
     * @param id 사용자 ID
     * @param userDto 업데이트할 사용자 데이터 (DTO)
     * @return 업데이트된 사용자 데이터 (DTO)
     */
    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        UserModel updatedModel = userService.updateUser(id, userDto);
        return userService.convertToDto(updatedModel);
    }

//    @GetMapping("/user/{username}")
//    public UserEntity getUserByUsername(@PathVariable String username) {
//        return userRepository.findByUsername(username);
//    }

    /**
     * 사용자 삭제
     * @param id 사용자 ID
     * @return 성공 메시지
     */
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "User deleted successfully";
    }
}

