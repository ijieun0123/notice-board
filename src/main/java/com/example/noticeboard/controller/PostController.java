package com.example.noticeboard.controller;

import com.example.noticeboard.dto.PostDto;
import com.example.noticeboard.entity.PostEntity;
import com.example.noticeboard.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// PostController는 클라이언트의 요청을 처리하고, PostDto를 사용하여 데이터를 주고받습니다.

@Controller
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시글 목록
    @GetMapping
    public String list(Model model) {
        List<PostDto> posts = postService.findAll(); // DTO로 데이터 가져오기
        model.addAttribute("posts", posts);
        return "posts/list";
    }

    // 게시글 작성 폼
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("post", new PostDto()); // 빈 DTO 객체 전달
        return "posts/form";
    }

    // 게시글 저장
    @PostMapping
    public String create(@ModelAttribute PostDto postDto) {
        postService.save(postDto); // DTO를 서비스에 전달하여 저장
        return "redirect:/posts";
    }

    // 게시글 상세보기
    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        PostDto postDto = postService.findById(id); // DTO로 데이터 가져오기
        model.addAttribute("post", postDto);
        return "posts/detail";
    }

    // 게시글 수정 폼
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        PostDto postDto = postService.findById(id);
        model.addAttribute("post", postDto);
        return "posts/form";
    }

    // 게시글 수정하기
    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute PostDto postDto) {
        PostDto existingPost = postService.findById(id); // 기존 게시글 조회
        existingPost.setTitle(postDto.getTitle());  // DTO에서 값을 가져와 엔티티 업데이트
        existingPost.setContent(postDto.getContent());
        existingPost.setAuthor(postDto.getAuthor());
        postService.save(existingPost);  // 수정된 Dto 저장
        return "redirect:/posts";
    }

    // 게시글 삭제하기
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id){
        postService.delete(id);
        return "redirect:/posts";
    }
}


