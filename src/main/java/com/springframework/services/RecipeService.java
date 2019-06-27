package com.springframework.services;

import com.springframework.api.v2.model.RecipeDTO;
import com.springframework.models.Recipe;

import java.util.List;

public interface RecipeService {

    List<RecipeDTO> getAllRecipes(int page, int limit);

    RecipeDTO getRecipeById(Long id);

    RecipeDTO createNewRecipe(RecipeDTO recipeDto);

    RecipeDTO saveRecipeByDTO(Long id, RecipeDTO recipeDto);

    void deleteRecipeById(Long id);
}
