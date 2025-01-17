package com.example.noticeboard.model;

import com.example.noticeboard.dto.PostDto;
import lombok.Getter;
import lombok.Setter;

// PostModel은 게시글을 화면에 표시할 때 필요한 데이터를 처리하거나, 비즈니스 로직을 구현하는 클래스입니다.
// 데이터베이스와 매핑되지 않으며, 필요한 데이터만 포함합니다.

@Getter
@Setter
public class PostModel {
    private Long id; // 게시글 ID
    private String title; // 제목
    private String author;
    private String summary; // 요약된 내용

    // 비즈니스 로직: 게시글 내용 요약
    public void generateSummary(String content) {
        this.summary = content.length() > 50 ? content.substring(0, 50) + "..." : content;
    }

    // PostModel을 PostDTO로 변환하는 메소드
    public PostDto toDto() {
        PostDto dto = new PostDto();
        dto.setId(this.id);
        dto.setTitle(this.title);
        dto.setAuthor(this.author);
        return dto;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public String getAuthor() {
        return author;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}


