package com.example.jpa.Repository;

import com.example.jpa.domain.Comment;
import com.example.jpa.domain.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByNews(News news);
}
