package com.springframework.controllers.v2;

import com.springframework.api.v2.model.RecipeDTO;
import com.springframework.api.v2.model.RecipeListDTO;
import com.springframework.api.v2.model.ResponseDTO;
import com.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(RecipeController.BASE_URL)
public class RecipeController {

  static final String BASE_URL = "api/v2/recipes";

  @Autowired private RecipeService recipeService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @Transactional
  public RecipeListDTO getListOfRecipes(
      @RequestParam(value = "page", defaultValue = "0", required = false) int page,
      @RequestParam(value = "limit", defaultValue = "30", required = false) int limit,
      @RequestParam(value = "sort", defaultValue = "id:asc", required = false) String sort) {
    log.info("Getting all recipes for page: " + page + " limit: " + limit + " sortBy: " + sort);
    return new RecipeListDTO(recipeService.getAllRecipes(page, limit, sort));
  }

  @GetMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public RecipeDTO getRecipeById(@PathVariable Long id) {
    log.info("Getting id: " + id);
    return recipeService.getRecipeById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public RecipeDTO saveRecipe(@Valid @RequestBody RecipeDTO recipeDTO) {
    log.info("Saving a new recipe");
    return recipeService.createNewRecipe(recipeDTO);
  }

  @PostMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public RecipeDTO updateRecipe(@PathVariable Long id, @RequestBody RecipeDTO recipeDTO) {
    log.info("Updating id: " + id);
    return recipeService.saveRecipeByDTO(id, recipeDTO);
  }

  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public ResponseDTO deleteRecipe(@PathVariable Long id) {
    log.info("Deleting id: " + id);
    recipeService.deleteRecipeById(id);
    return new ResponseDTO("success");
  }
}
