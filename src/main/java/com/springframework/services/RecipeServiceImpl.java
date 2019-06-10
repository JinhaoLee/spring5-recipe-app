package com.springframework.services;

import com.springframework.api.v2.mapper.RecipeMapper;
import com.springframework.api.v2.model.RecipeDTO;
import com.springframework.exceptions.NotFoundException;
import com.springframework.models.Recipe;
import com.springframework.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private RecipeMapper recipeMapper;

    @Override
    public List<RecipeDTO> getAllRecipes() {
        return recipeRepository
                .findAll()
                .stream()
                .map( recipe -> recipeMapper.recipeToRecipeDTO(recipe))
                .collect(Collectors.toList());
    }

    @Override
    public RecipeDTO getRecipeById(Long id) {
        return recipeRepository
                .findById(id)
                .map(recipeMapper::recipeToRecipeDTO)
                .orElseThrow(()-> new NotFoundException("Recipe Not Found. For ID value: " + id.toString() ));
    }

    @Override
    public RecipeDTO createNewRecipe(RecipeDTO recipeDto) {
        Recipe recipe = recipeMapper.recipeDtoToRecipe(recipeDto);
        return saveAndReturnDTO(recipe);
    }

    @Override
    public RecipeDTO saveRecipeByDTO(Long id, RecipeDTO recipeDto) {
        Recipe recipe = recipeMapper.recipeDtoToRecipe(recipeDto);
        recipe.setId(id);
        return saveAndReturnDTO(recipe);
    }

    @Override
    public void deleteRecipeById(Long id) {
        recipeRepository.deleteById(id);
    }

    private RecipeDTO saveAndReturnDTO(Recipe recipe){
        Recipe savedRecipe = recipeRepository.save(recipe);
        return recipeMapper.recipeToRecipeDTO(savedRecipe);
    }


}
