package com.example.jpa.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long newsId;

    @Column(name = "news_title", nullable = false, length = 255)
    private String title;

    @Column(nullable = false)
    private String content;
//    @ManyToOne
//    @JoinColumn(name="user_id")
//    @JsonBackReference
//    private Users users;

//    public static News toEntity(NewsDto.Post post) {
//        return new News(null, post.getTitle(), post.getContent());
//    }

    @OneToMany(mappedBy = "news")
    @JsonManagedReference
    private List<Comment> comments;

}
