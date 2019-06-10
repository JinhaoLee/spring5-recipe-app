package com.springframework.controllers.v2;

import com.springframework.api.v2.model.RecipeDTO;
import com.springframework.api.v2.model.RecipeListDTO;
import com.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(RecipeController.BASE_URL)
public class RecipeController {

    public final static String BASE_URL = "api/v2/recipes";

//    @Autowired //todo add DI
    private RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public RecipeListDTO getListOfRecipes() {
        return new RecipeListDTO(recipeService.getAllRecipes());
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public RecipeDTO getRecipeById(@PathVariable Long id) {
        log.debug("Getting id: " + id);
        return recipeService.getRecipeById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RecipeDTO saveRecipe(@RequestBody RecipeDTO recipeDTO) {
        return recipeService.createNewRecipe(recipeDTO);
    }

    @PostMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public RecipeDTO updateRecipe(@PathVariable Long id, @RequestBody RecipeDTO recipeDTO) {
        return recipeService.saveRecipeByDTO(id, recipeDTO);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteRecipe(@PathVariable Long id) {
        log.debug("Deleting id: " + id);
        recipeService.deleteRecipeById(id);
    }


}
