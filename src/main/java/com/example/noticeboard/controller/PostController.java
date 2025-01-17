package com.example.noticeboard.controller;

import com.example.noticeboard.dto.PostDto;
import com.example.noticeboard.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// PostController는 클라이언트의 요청을 처리하고, PostDto를 사용하여 데이터를 주고받습니다.

@Controller
@RequestMapping("/api/posts")
public class PostController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시글 목록 (페이징 포함)
    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public String list(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "2") int size,
                       @AuthenticationPrincipal User user,
                       Model model) {
        // 페이징된 게시글 가져오기
        Page<PostDto> pagedPosts = postService.getPosts(page, size);

        // 모델에 데이터 추가
        model.addAttribute("posts", pagedPosts.getContent()); // 현재 페이지의 게시글
        model.addAttribute("pagedPosts", pagedPosts);         // 페이징 정보 포함
        model.addAttribute("username", user.getUsername());   // 사용자 이름

        return "posts/list"; // 동일한 템플릿 사용
    }

    // 게시글 작성 폼
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("post", new PostDto()); // 빈 DTO 객체 전달
        return "posts/form";
    }

    // 게시글 저장
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public String create(@ModelAttribute PostDto postDto) {
        postService.save(postDto); // DTO를 서비스에 전달하여 저장
        return "redirect:/api/posts";
    }

    // 게시글 상세보기
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        PostDto postDto = postService.findById(id); // DTO로 데이터 가져오기
        if (postDto == null) {
            throw new IllegalArgumentException("Post not found for id: " + id);
        }
        model.addAttribute("post", postDto);
        return "posts/detail";
    }

    // 게시글 수정 폼
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        PostDto postDto = postService.findById(id);
        model.addAttribute("post", postDto);
        return "posts/form";
    }

    // 게시글 수정하기
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute PostDto postDto) {
        PostDto existingPost = postService.findById(id); // 기존 게시글 조회
        existingPost.setTitle(postDto.getTitle());  // DTO에서 값을 가져와 엔티티 업데이트
        existingPost.setContent(postDto.getContent());
        existingPost.setAuthor(postDto.getAuthor());
        postService.save(existingPost);  // 수정된 Dto 저장
        return "redirect:/api/posts";
    }

    // 게시글 삭제하기
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        postService.delete(id);
        return "redirect:/api/posts";
    }
}


