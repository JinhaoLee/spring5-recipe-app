package com.springframework.services.impl;

import com.springframework.api.v2.mapper.RecipeMapper;
import com.springframework.api.v2.model.RecipeDTO;
import com.springframework.exceptions.NotFoundException;
import com.springframework.models.Recipe;
import com.springframework.repositories.RecipeRepository;
import com.springframework.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private RecipeMapper recipeMapper;

    @Override
    public List<RecipeDTO> getAllRecipes(int page, int limit) {
        Pageable pageableRequest = PageRequest.of(page, limit);

        return recipeRepository
                .findAll(pageableRequest)
                .stream()
                .map(recipe -> recipeMapper.recipeToRecipeDTO(recipe))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RecipeDTO getRecipeById(Long id) {
        return recipeRepository
                .findById(id)
                .map(recipeMapper::recipeToRecipeDTO)
                .orElseThrow(() -> new NotFoundException("Recipe Not Found. For ID value: " + id.toString()));
    }

    @Override
    @Transactional
    public RecipeDTO createNewRecipe(RecipeDTO recipeDto) {
        Recipe recipe = recipeMapper.recipeDtoToRecipe(recipeDto);
        return saveAndReturnDTO(recipe);
    }

    @Override
    @Transactional
    public RecipeDTO saveRecipeByDTO(Long id, RecipeDTO recipeDto) {
        Recipe recipe = recipeMapper.recipeDtoToRecipe(recipeDto);
        recipe.setId(id);
        return saveAndReturnDTO(recipe);
    }

    @Override
    public void deleteRecipeById(Long id) {
        recipeRepository.deleteById(id);
    }

    private RecipeDTO saveAndReturnDTO(Recipe recipe) {
        Recipe savedRecipe = recipeRepository.save(recipe);
        return recipeMapper.recipeToRecipeDTO(savedRecipe);
    }


}
