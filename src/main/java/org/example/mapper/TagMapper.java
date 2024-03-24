package org.example.mapper;

import org.example.dto.tag.TagCreateDTO;
import org.example.dto.tag.TagEditDTO;
import org.example.dto.tag.TagItemDTO;
import org.example.entities.TagEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {
    TagEntity tagCreateDTO(TagCreateDTO dto);
    TagItemDTO tagItemDTO(TagEntity category);
    TagEntity tagEditDto(TagEditDTO dto);
}
