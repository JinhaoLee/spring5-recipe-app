package com.springframework.api.v2.mapper;

import com.springframework.api.v2.model.IngredientDTO;
import com.springframework.models.Ingredient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel="spring", uses = UnitOfMeasureMapper.class)
public interface IngredientMapper {
    IngredientMapper INSTANCE = Mappers.getMapper(IngredientMapper.class);

    @Mapping(target = "recipeId", source = "recipe.id")
    @Mapping(target = "uom", source = "unitOfMeasure")
    IngredientDTO ingredientToIngredientDTO(Ingredient ingredient);

    @Mapping(target = "recipe.id", source = "recipeId")
    @Mapping(target = "unitOfMeasure", source = "uom")
    Ingredient ingredientDtoToIngredient(IngredientDTO ingredientDTO);
}
