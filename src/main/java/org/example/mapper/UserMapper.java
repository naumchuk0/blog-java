package org.example.mapper;

import org.example.dto.account.UserCreateDTO;
import org.example.dto.account.UserItemDTO;
import org.example.entities.CategoryEntity;
import org.example.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity userCreateDTO(UserCreateDTO dto);
    UserItemDTO uesrItemDTO(UserEntity user);
}

