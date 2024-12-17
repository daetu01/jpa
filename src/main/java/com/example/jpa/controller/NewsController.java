package com.example.jpa.controller;

import com.example.jpa.Repository.NewsRepository;
import com.example.jpa.domain.News;
import com.example.jpa.dto.NewsDto;
import com.example.jpa.mapper.NewsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
@RequestMapping("/news/*")
public class NewsController {
    private final NewsRepository newsRepository;

    private final NewsMapper newsMapper;

    @GetMapping("/new")
    public String newArticleForm() {
        return "news/new";
    }

    @GetMapping("/{newsId}")
    public String getNews(@PathVariable("newsId")Long newsId, Model model) {
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 뉴스가 존재하지 않습니다."));
        model.addAttribute("news", news);
        return "news/detail";
    }

    @PostMapping("/create")
    public String createNews(NewsDto.Post post) {
        // 1. DTO를 Entity로 변환
        News news = newsMapper.newsPostDtoToNews(post);

        // 2. save() 메소드를 통해 엔티티를 DB에 저장.
        newsRepository.save(news);
        return "redirect:/news/list";
    }

    @GetMapping("/list")
    public String getNewsList(Model model, @RequestParam(name = "page", defaultValue = "1") int page) {
        // 페이지 요청 준비 .
        Pageable pageable = PageRequest.of(page - 1,7);
        Page<News> newsPage = newsRepository.findAll(pageable);

        int totalPages = newsPage.getTotalPages(); // 전체 페이지 수

        // 페이지 번호들과 현재 페이지 여부 저장할 리스트.
        List<Map<String, Object>> pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .mapToObj(pageNum -> {
                            Map<String, Object> pageMap = new HashMap<>();
                            pageMap.put("pageNumber", pageNum) ;
                            pageMap.put("isCurrentPage" , pageNum == page) ;
                            return pageMap;
                        }).collect(Collectors.toList());
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("newsPage", newsPage);
        model.addAttribute("prev", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("hasNext", newsPage.hasNext());
        model.addAttribute("hasPrev", newsPage.hasPrevious());


        return "news/list";
    }

    @GetMapping("/{newsId}/delete")
    public String deleteNews(@PathVariable("newsId")Long newsId) {
        newsRepository.deleteById(newsId);
        return "redirect:/news/list";
    }

    @GetMapping("/{newsId}/edit")
    public String editNewsForm(@PathVariable("newsId") Long newsId, Model model) {
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 뉴스가 존재하지 않습니다."));
        model.addAttribute("news", news) ;
        return "news/edit";
    }

    @PostMapping("/{newsId}/update")
    public String updateNews(@PathVariable("newsId") Long newsId, NewsDto.Patch patch) {
        News news = newsRepository.findById(newsId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 뉴스가 존재하지 않습니다."));
        newsMapper.PatchDtoToNews(patch, news);
        newsRepository.save(news);

        return "redirect:/news/" + news.getNewsId();
    }

}
