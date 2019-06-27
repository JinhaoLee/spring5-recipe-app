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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService {
  @Autowired private RecipeRepository recipeRepository;

  @Autowired private RecipeMapper recipeMapper;

  @Override
  public List<RecipeDTO> getAllRecipes(int page, int limit, String sortBy) {
    Sort allSort;
    try {
      allSort =
          Sort.by(
              Arrays.stream(sortBy.split(","))
                  .map(sort -> sort.split(":"))
                  .map(
                      sortParam ->
                          sortParam.length == 2
                              ? new Sort.Order(
                                      replaceOrderStringThroughDirection(sortParam[1]),
                                      sortParam[0])
                                  .ignoreCase()
                              : new Sort.Order(
                                      replaceOrderStringThroughDirection("asc"), sortParam[0])
                                  .ignoreCase())
                  .collect(Collectors.toList()));
    } catch (Exception e) {
      throw new NotFoundException("parameters not found");
    }

    Pageable pageableRequest = PageRequest.of(page, limit, allSort);

    return recipeRepository.findAll(pageableRequest).stream()
        .map(recipe -> recipeMapper.recipeToRecipeDTO(recipe))
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public RecipeDTO getRecipeById(Long id) {
    return recipeRepository
        .findById(id)
        .map(recipeMapper::recipeToRecipeDTO)
        .orElseThrow(
            () -> new NotFoundException("Recipe Not Found. For ID value: " + id.toString()));
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

  private Sort.Direction replaceOrderStringThroughDirection(String sortDirection) {
    if (sortDirection.equalsIgnoreCase("DESC")) {
      return Sort.Direction.DESC;
    }
    return Sort.Direction.ASC;
  }
}
