package com.springframework.api.v2.mapper;

import com.springframework.api.v2.model.CategoryDTO;
import com.springframework.models.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel="spring")
public interface CategoryMapper {
    CategoryDTO categoryToCategoryDTO(Category category);
}
