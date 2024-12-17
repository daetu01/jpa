package com.example.jpa.controller;

import com.example.jpa.Repository.CommentRepository;
import com.example.jpa.Repository.NewsRepository;
import com.example.jpa.domain.Comment;
import com.example.jpa.domain.News;
import com.example.jpa.dto.CommentDto;
import com.example.jpa.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final NewsRepository newsRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @PostMapping("/comment/create/{newsId}")
    public String createComment(@PathVariable("newsId") Long newsId, CommentDto.Post post) {
        News findNews = newsRepository.findById(newsId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 뉴스가 존재하지 않습니다."));

        Comment comment = commentMapper.commentDtoToEntity(post);
        comment.setNews(findNews);
        commentRepository.save(comment);

        return "redirect:/news/" + newsId;
    }

    // 댓글 삭제하기
    @PostMapping("delete/{commentId}")
    public String deleteComment(@PathVariable("commendId") Long commentId, @RequestParam("password") String password) {
        Comment findComment = findVerifiedComment(commentId);
        News findNews = findComment.getNews();

        if (!findComment.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }
        commentRepository.delete(findComment);

        return "redirect:/news/" + findNews.getNewsId();
    }

    public Comment findVerifiedComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 댓글이 존재하지 않습니다."));
    }

}
