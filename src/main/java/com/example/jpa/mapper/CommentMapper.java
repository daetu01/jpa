package com.example.jpa.mapper;

import com.example.jpa.domain.Comment;
import com.example.jpa.dto.CommentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target="commendId", ignore = true)
    @Mapping(target = "news", ignore = true)
    Comment commentDtoToEntity(CommentDto.Post post);
}
