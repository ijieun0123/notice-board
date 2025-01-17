package com.example.noticeboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.example.noticeboard.entity")  // 엔티티 패키지 지정
public class NoticeBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(NoticeBoardApplication.class, args);
    }

}
