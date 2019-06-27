package com.springframework.controllers.v2;

import com.springframework.api.v2.model.IngredientDTO;
import com.springframework.api.v2.model.RecipeDTO;
import com.springframework.api.v2.model.ResponseDTO;
import com.springframework.exceptions.NotFoundException;
import com.springframework.services.IngredientService;
import com.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(IngredientController.BASE_URL)
public class IngredientController {
    final static String BASE_URL = "api/v2/recipe/{recipeId}/ingredients";

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private RecipeService recipeService;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<IngredientDTO> getListOfIngredients(@PathVariable Long recipeId) {
        log.debug("Getting id: " + recipeId);
        return ingredientService.findIngredientsByRecipeId(recipeId);
    }

    @GetMapping("{ingredientId}")
    @ResponseStatus(HttpStatus.OK)
    public IngredientDTO getRecipeIngredient(@PathVariable Long recipeId, @PathVariable Long ingredientId) {
        return ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IngredientDTO saveRecipeIngredient(@PathVariable Long recipeId,
                                              @Valid @RequestBody IngredientDTO ingredientDTO) {
        RecipeDTO recipeDTO = recipeService.getRecipeById(recipeId);

        // make sure id is valid
        if (recipeDTO == null) {
            throw new NotFoundException("Recipe not found for id " + recipeId.toString());
        }

        return ingredientService.saveIngredientDTO(ingredientDTO);
    }

    @PostMapping("{ingredientId}")
    @ResponseStatus(HttpStatus.OK)
    public IngredientDTO updateRecipeIngredient(@PathVariable Long recipeId,
                                                @PathVariable Long ingredientId,
                                                @Valid @RequestBody IngredientDTO ingredientDTO) {
        RecipeDTO recipeDTO = recipeService.getRecipeById(recipeId);

        // make sure id is valid
        if (recipeDTO == null) {
            throw new NotFoundException("Recipe not found for id " + recipeId.toString());
        }

        IngredientDTO existingIngredientDTO = ingredientService.findByRecipeIdAndIngredientId(recipeId,ingredientId);

        if (existingIngredientDTO != null)
            return ingredientService.saveIngredientDTO(ingredientDTO);
        else {
            throw new NotFoundException("Ingredient not found for id " + ingredientId.toString());
        }
    }

    @DeleteMapping("{ingredientId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO deleteIngredient(@PathVariable Long recipeId,
                                        @PathVariable Long ingredientId) {
        try {
            ingredientService.deleteById(recipeId,ingredientId);
        } catch (NotFoundException e){
            return new ResponseDTO(e.getMessage());
        }

        return new ResponseDTO("success");
    }
}
