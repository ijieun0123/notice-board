package com.example.noticeboard.controller;

import com.example.noticeboard.dto.UserDto;
import com.example.noticeboard.entity.UserEntity;
import com.example.noticeboard.repository.UserRepository;
import com.example.noticeboard.service.UserService;
import com.example.noticeboard.util.PasswordEncoderUtil;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Controller
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoderUtil passwordEncoderUtil;

    public UserController(UserRepository userRepository, PasswordEncoderUtil passwordEncoderUtil) {
        this.userRepository = userRepository;
        this.passwordEncoderUtil = passwordEncoderUtil;
    }

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
            return "redirect:/api/posts";
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
        return "users/login"; // signup.html 페이지를 반환
    }

    /**
     * 회원가입 페이지 렌더링
     */
    @GetMapping("/signup")
    public String signupPage() {
        return "users/signup"; // signup.html 페이지를 반환
    }

    @PostMapping("/signup")
    public String registerUser(@ModelAttribute UserDto userDto) {
        // 프로필 이미지 파일 처리
        MultipartFile profileImage = userDto.getProfileImage();
        String profileImagePath = null; // 프로필 이미지 경로 초기화

        if (profileImage != null && !profileImage.isEmpty()) {
            // 파일 저장 처리 (절대 경로 사용)
            String fileName = UUID.randomUUID() + "_" + profileImage.getOriginalFilename(); // 고유 파일명 생성
            try {
                // 절대 경로로 파일 저장 (예: /home/user/uploads/ 디렉토리)
                Path path = Paths.get("/Users/ijieun/Desktop/springboot/noticeBoard/src/main/resources/public/uploads/" + fileName); // 절대 경로 지정
                Files.createDirectories(path.getParent()); // 디렉토리가 없으면 생성
                Files.copy(profileImage.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

                // 웹에서 접근할 수 있는 경로로 설정 (예: /uploads/ 파일명)
                profileImagePath = "/uploads/" + fileName; // 절대 경로가 아닌 웹에서 접근 가능한 상대 경로
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoderUtil.encode(userDto.getPassword());

        // UserDto -> UserEntity 변환
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDto.getUsername());
        userEntity.setPassword(encodedPassword);

        // 프로필 이미지 경로 설정
        userEntity.setProfileImagePath(profileImagePath);

        // 권한 설정 (예: "USER" 권한 부여)
        Set<String> authorities = new HashSet<>();
        authorities.add("ROLE_USER");
        userEntity.setAuthorities(authorities);

        // 데이터베이스에 저장
        userRepository.save(userEntity);

        return "redirect:/api/users/logout";
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
