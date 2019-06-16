package com.springframework.api.v2.mapper;

import com.springframework.api.v2.model.RecipeDTO;
import com.springframework.models.Recipe;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {NotesMapper.class, CategoryMapper.class, IngredientMapper.class})
public interface RecipeMapper {
    RecipeDTO recipeToRecipeDTO(Recipe recipe);
    Recipe recipeDtoToRecipe(RecipeDTO recipeDTO);
}
