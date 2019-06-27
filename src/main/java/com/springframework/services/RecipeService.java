package com.springframework.services;

import com.springframework.api.v2.model.RecipeDTO;

import java.util.List;

public interface RecipeService {

  List<RecipeDTO> getAllRecipes(int page, int limit, String sortBy);

  RecipeDTO getRecipeById(Long id);

  RecipeDTO createNewRecipe(RecipeDTO recipeDto);

  RecipeDTO saveRecipeByDTO(Long id, RecipeDTO recipeDto);

  void deleteRecipeById(Long id);
}
