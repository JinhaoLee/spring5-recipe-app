package com.springframework.services;

import com.springframework.api.v2.model.IngredientDTO;
import com.springframework.api.v2.model.IngredientListDTO;

import java.util.List;

public interface IngredientService {
    List<IngredientDTO> findIngredientsByRecipeId(Long recipeId);

    IngredientDTO findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

    IngredientDTO saveIngredientDTO(IngredientDTO ingredientDTO);

    void deleteById(Long recipeId, Long ingredientId);
}
