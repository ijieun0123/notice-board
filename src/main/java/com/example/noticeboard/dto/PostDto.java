package com.example.noticeboard.dto;

// DTO는 클라이언트와 서버 간의 데이터 전송을 최적화하는 역할을 합니다.
// PostDTO는 클라이언트에게 필요한 게시글의 정보만 전달합니다.

import com.example.noticeboard.entity.PostEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostDto {
    private Long id; // 게시글 ID
    private String title; // 제목
    private String author; // 작성자
    private String content;
    private LocalDateTime createdDate;

    // 엔티티에서 DTO를 생성하는 메소드
    public static PostDto fromEntity(PostEntity postEntity) {
        PostDto dto = new PostDto();
        dto.setId(postEntity.getId());
        dto.setTitle(postEntity.getTitle());
        dto.setAuthor(postEntity.getAuthor());
        dto.setContent(postEntity.getContent());
        dto.setCreatedDate(postEntity.getCreatedDate());
        return dto;
    }

    // DTO를 엔티티로 변환하는 메소드
    public PostEntity toEntity() {
        PostEntity postEntity = new PostEntity();
        postEntity.setId(this.id);
        postEntity.setTitle(this.title);
        postEntity.setAuthor(this.author);
        postEntity.setContent(this.content);
        postEntity.setCreatedDate(this.createdDate);
        return postEntity;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}

