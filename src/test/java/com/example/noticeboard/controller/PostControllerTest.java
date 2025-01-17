package com.example.noticeboard.controller;

import com.example.noticeboard.dto.PostDto;
import com.example.noticeboard.security.SecurityConfig;
import com.example.noticeboard.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class)
@Import(SecurityConfig.class)
public class PostControllerTest {

    @MockitoBean
    private PostService postService;

    @Autowired
    private MockMvc mockMvc;

    // 게시글 목록
    @Test
    void testListPosts_withAuthenticatedUser() throws Exception {
        when(postService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/posts")
                        .with(user("testUser").roles("USER"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("posts"))
                .andExpect(model().attributeExists("username"))
                .andExpect(view().name("posts/list"));
    }

    @Test
    void testListPosts_withUnauthorizedUser() throws Exception {
        when(postService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/posts")
                        .with(user("testUser").roles("GUEST"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())  // 302 Found 상태 코드
                .andExpect(redirectedUrl("/api/users/login"));  // 로그인 페이지로 리다이렉트
    }

    // 게시글 작성 폼
    @Test
    @WithMockUser(roles = "USER")  // "USER" 역할을 가진 사용자로 인증
    public void createForm_AuthenticatedUser_ReturnsFormView() throws Exception {
        mockMvc.perform(get("/posts/new"))
                .andExpect(status().isOk())  // HTTP 상태 코드 200
                .andExpect(view().name("posts/form"));  // "posts/form" 뷰 이름이 반환되는지 확인
    }

    @Test
    public void createForm_UnauthenticatedUser_ReturnsRedirect() throws Exception {
        mockMvc.perform(get("/posts/new"))
                .andExpect(status().is3xxRedirection())  // 3xx 리다이렉션 상태 코드 확인
                .andExpect(redirectedUrl("http://localhost/api/users/login"));  // 절대 경로로 리다이렉션 URL 확인
    }

    // 게시글 저장
    @Test
    @WithMockUser(roles = "USER")  // USER 역할을 가진 사용자로 테스트
    public void create_AuthenticatedUser_RedirectsToPosts() throws Exception {
        mockMvc.perform(post("/posts")
                        .param("title", "Test Post")  // PostDto의 필드와 맞는 파라미터 전달
                        .param("content", "This is a test post"))
                .andExpect(status().is3xxRedirection())  // 리다이렉션 상태 코드 확인
                .andExpect(redirectedUrl("/posts"));  // /posts로 리다이렉션 되는지 확인
    }

    // 게시글 상세보기
    @Test
    @WithMockUser(roles = "USER")
    public void view_AuthenticatedUser_ReturnsCorrectPostDetail() throws Exception {
        Long postId = 1L;
        PostDto postDto = new PostDto();
        postDto.setId(postId);
        postDto.setTitle("Test Post");
        postDto.setContent("This is a test post.");

        given(postService.findById(postId)).willReturn(postDto);

        mockMvc.perform(get("/posts/{id}", postId))
                .andExpect(status().isOk())
                .andExpect(view().name("posts/detail"))
                .andExpect(model().attribute("post", postDto));
    }

    @Test
    public void view_UnauthenticatedUser_ReturnsRedirectToLogin() throws Exception {
        Long postId = 1L;  // 테스트할 포스트 ID

        mockMvc.perform(get("/posts/{id}", postId))  // 인증되지 않은 사용자가 GET 요청
                .andExpect(status().is3xxRedirection())  // 리다이렉션 상태 코드 확인
                .andExpect(redirectedUrl("http://localhost/api/users/login"));  // 로그인 페이지로 리다이렉션
    }

    // 게시글 수정 폼
    @Test
    @WithMockUser(roles = "USER")  // 인증된 사용자로 테스트
    public void edit_AuthenticatedUser_ReturnsEditFormView() throws Exception {
        Long postId = 1L;
        PostDto postDto = new PostDto();
        postDto.setId(postId);
        postDto.setTitle("Test Post");
        postDto.setContent("This is a test post.");

        // Mocking PostService's findById method
        given(postService.findById(postId)).willReturn(postDto);

        mockMvc.perform(get("/posts/{id}/edit", postId))
                .andExpect(status().isOk())  // HTTP 상태 코드 200 확인
                .andExpect(view().name("posts/form"))  // 반환된 뷰 이름 확인
                .andExpect(model().attribute("post", postDto));  // 모델에 전달된 postDto 확인
    }

    @Test
    public void edit_UnauthenticatedUser_ReturnsRedirectToLogin() throws Exception {
        Long postId = 1L;

        mockMvc.perform(get("/posts/{id}/edit", postId))  // 인증되지 않은 사용자가 접근
                .andExpect(status().is3xxRedirection())  // 리다이렉션 상태 코드 확인
                .andExpect(redirectedUrl("http://localhost/api/users/login"));  // 로그인 페이지로 리다이렉션 확인
    }

    // 게시글 수정하기
    @Test
    @WithMockUser(roles = "USER")  // 인증된 사용자로 테스트
    public void update_AuthenticatedUser_UpdatesPostAndRedirects() throws Exception {
        Long postId = 1L;

        // Mocking PostService's findById and save methods
        PostDto existingPost = new PostDto();
        existingPost.setId(postId);
        existingPost.setTitle("Old Title");
        existingPost.setContent("Old Content");
        existingPost.setAuthor("Old Author");

        given(postService.findById(postId)).willReturn(existingPost);

        MockHttpServletRequestBuilder request = post("/posts/{id}", postId)
                .param("title", "Updated Title")
                .param("content", "Updated Content")
                .param("author", "Updated Author")
                .with(csrf());  // CSRF 토큰 추가

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())  // 리다이렉션 상태 코드 확인
                .andExpect(redirectedUrl("/posts"));  // "/posts"로 리다이렉션 확인

        // Verify the service was called with updated data
        verify(postService).save(argThat(post ->
                post.getId().equals(postId) &&
                        post.getTitle().equals("Updated Title") &&
                        post.getContent().equals("Updated Content") &&
                        post.getAuthor().equals("Updated Author")
        ));
    }

    @Test
    public void update_UnauthenticatedUser_ReturnsRedirectToLogin() throws Exception {
        Long postId = 1L;

        MockHttpServletRequestBuilder request = post("/posts/{id}", postId)
                .param("title", "Updated Title")
                .param("content", "Updated Content")
                .param("author", "Updated Author")
                .with(csrf());  // CSRF 토큰 추가

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())  // 리다이렉션 상태 코드 확인
                .andExpect(redirectedUrl("http://localhost/api/users/login"));  // 로그인 페이지로 리다이렉션 확인
    }

    // 게시글 삭제하기
    @Test
    @WithMockUser(roles = "USER")  // 인증된 사용자로 테스트
    public void delete_AuthenticatedUser_DeletesPostAndRedirects() throws Exception {
        Long postId = 1L;

        MockHttpServletRequestBuilder request = post("/posts/{id}/delete", postId)
                .with(csrf());  // CSRF 토큰 추가

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())  // 리다이렉션 상태 코드 확인
                .andExpect(redirectedUrl("/posts"));  // "/posts"로 리다이렉션 확인

        // Verify the service was called with the correct ID
        verify(postService).delete(postId);
    }

    @Test
    public void delete_UnauthenticatedUser_ReturnsRedirectToLogin() throws Exception {
        Long postId = 1L;

        MockHttpServletRequestBuilder request = post("/posts/{id}/delete", postId)
                .with(csrf());  // CSRF 토큰 추가

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())  // 리다이렉션 상태 코드 확인
                .andExpect(redirectedUrl("http://localhost/api/users/login"));  // 로그인 페이지로 리다이렉션 확인
    }
}
