package com.example.noticeboard.controller;

import com.example.noticeboard.dto.UserDto;
import com.example.noticeboard.model.UserModel;
import com.example.noticeboard.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private HttpSession session;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;
    @Mock
    private UserDto userDto;

    @Mock
    private UserModel userModel;

    // 로그인
    @Test
    public void testLogin_Success() {
        // Given
        String username = "testUser";
        String password = "password";

        // Mocking
        when(userService.authenticateUser(username, password)).thenReturn(true);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(username);
        when(authentication.getAuthorities()).thenReturn(Collections.emptyList());

        // Mock SecurityContextHolder.getContext() to return the mocked securityContext
        SecurityContextHolder.setContext(securityContext);

        // When
        String result = userController.login(username, password, session);

        // Then
        assertEquals("redirect:/posts", result);
        verify(session).setAttribute("username", username);  // 세션에 사용자 정보 저장
        verify(userService).authenticateUser(username, password);  // authenticateUser 호출 검증
        verify(securityContext).getAuthentication();  // 인증 정보 확인
    }

    @Test
    public void testLogin_Failure() {
        // Given
        String username = "testUser";
        String password = "wrongPassword";

        // Mocking
        when(userService.authenticateUser(username, password)).thenReturn(false);

        // When
        String result = userController.login(username, password, session);

        // Then
        assertEquals("redirect:/login?error=true", result);  // 로그인 실패 리다이렉트 확인
        verify(userService).authenticateUser(username, password);  // authenticateUser 호출 검증
        verifyNoInteractions(securityContext);  // 인증 정보 확인이 호출되지 않음을 검증
    }

    // 회원가입
    @Test
    public void testRegisterUser() {
        // Given
        Long userId = 1L;

        // Mocking userService.registerUser() to return a UserModel with ID
        when(userService.registerUser(any(UserDto.class))).thenReturn(userModel);
        when(userModel.getModelId()).thenReturn(userId);

        // UserDto 객체 생성 후 필드 설정
        UserDto userDto = new UserDto();
        userDto.setUsername("testUser");
        userDto.setPassword("password");

        // When
        String result = userController.registerUser(userDto);

        // Then
        assertEquals("redirect:/api/users/" + userId, result);  // 리다이렉트 URL 검증
        verify(userService).registerUser(any(UserDto.class));  // userService.registerUser() 호출 검증
    }
}
