package com.example.jpa.mapper;

import com.example.jpa.domain.News;
import com.example.jpa.dto.NewsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface NewsMapper {
    @Mapping(target = "newsId", ignore = true)
    @Mapping(target = "comments", ignore = true)
    News newsPostDtoToNews(NewsDto.Post post);

    @Mapping(target = "newsId", ignore = true)
    @Mapping(target = "comments", ignore = true)
    void PatchDtoToNews(NewsDto.Patch patch, @MappingTarget News news);
}
