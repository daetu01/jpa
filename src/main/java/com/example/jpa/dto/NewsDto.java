package com.example.jpa.dto;

import lombok.*;

public class NewsDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class Post {
        public String getTitle() {
            return title;
        }

        public String getContent() {
            return content;
        }

        private String title;
        private String content;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Patch {
        private String title;
        private String content;
    }

}
