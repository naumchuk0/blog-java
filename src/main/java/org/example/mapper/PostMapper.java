package org.example.mapper;

import org.example.dto.post.PostCreateDTO;
import org.example.dto.post.PostEditDTO;
import org.example.dto.post.PostItemDTO;
import org.example.entities.PostEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostEntity postCreateDTO(PostCreateDTO dto);
    PostItemDTO postItemDTO(PostEntity category);
    PostEntity postEditDto(PostEditDTO dto);
}
