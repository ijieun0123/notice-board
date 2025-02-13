package com.example.noticeboard.service;

import com.example.noticeboard.dto.PostDto;
import com.example.noticeboard.entity.PostEntity;
import com.example.noticeboard.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// PostService는 비즈니스 로직을 처리하고, PostDto와 PostEntity 간의 변환을 담당합니다.

@Service
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 게시글 목록 가져오기
    public List<PostDto> findAll() {
        List<PostEntity> posts = postRepository.findAll();
        return posts.stream()
                .map(PostDto::fromEntity) // 엔티티를 DTO로 변환
                .collect(Collectors.toList());
    }

    // 게시글 저장하기
    public void save(PostDto postDto) {
        PostEntity postEntity = postDto.toEntity(); // DTO를 엔티티로 변환
        postRepository.save(postEntity);
    }

    // 게시글 찾기
    public PostDto findById(Long id) {
        PostEntity postEntity = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return PostDto.fromEntity(postEntity); // 엔티티를 DTO로 변환
    }

    // 게시글 삭제
    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    // 페이징 ( 서치 x )
    public Page<PostDto> getPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return postRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(PostDto::fromEntity); // 엔티티를 DTO로 변환
    }

    // 페이징 ( 서치 o )
    public Page<PostDto> searchPosts(String search, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return postRepository.findByTitleContainingOrContentContaining(search, search, pageable)
                .map(PostDto::fromEntity); // 검색어가 포함된 게시글만 반환
    }
}

