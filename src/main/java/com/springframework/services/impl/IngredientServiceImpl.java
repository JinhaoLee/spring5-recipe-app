package com.springframework.services.impl;

import com.springframework.api.v2.mapper.IngredientMapper;
import com.springframework.api.v2.model.IngredientDTO;
import com.springframework.exceptions.NotFoundException;
import com.springframework.models.Ingredient;
import com.springframework.models.Recipe;
import com.springframework.repositories.RecipeRepository;
import com.springframework.repositories.UnitOfMeasureRepository;
import com.springframework.services.IngredientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UnitOfMeasureRepository unitOfMeasureRepository;

    @Autowired
    private IngredientMapper ingredientMapper;

    @Override
    public List<IngredientDTO> findIngredientsByRecipeId(Long recipeId) {
        return recipeRepository.findById(recipeId)
                .orElseThrow(()-> new NotFoundException("Recipe Not Found. For ID value: " + recipeId.toString()))
                .getIngredients()
                .stream()
                .map(ingredientMapper::ingredientToIngredientDTO)
                .collect(Collectors.toList());
    }

    @Override
    public IngredientDTO findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        return recipeRepository.findById(recipeId)
                .orElseThrow(() -> new NotFoundException("Recipe Not Found. For ID value: " + recipeId.toString()))
                .getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredientMapper::ingredientToIngredientDTO)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Ingredient Not Found. For ID value: " + ingredientId.toString()));
    }

    @Override
    public IngredientDTO saveIngredientDTO(IngredientDTO ingredientDTO) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientDTO.getRecipeId());

        // if recipe not found
        if (!recipeOptional.isPresent()) {
            throw new NotFoundException("Recipe not found for id: " + ingredientDTO.getRecipeId());
        } else {
            // existing recipe
            Recipe recipe = recipeOptional.get();

            // try get ingredient
            Optional<Ingredient> ingredientOptional = recipe.
                    getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientDTO.getId()))
                    .findFirst();

            // if ingredient is present
            if (ingredientOptional.isPresent()) {
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(ingredientDTO.getDescription());
                ingredientFound.setAmount((ingredientDTO.getAmount()));
                ingredientFound.setUnitOfMeasure(unitOfMeasureRepository.findById(ingredientDTO.getUom().getId())
                        .orElseThrow(() -> new NotFoundException("Ingredient Not Found")));
            } else {
                // add new ingredient
                Ingredient ingredient = ingredientMapper.ingredientDtoToIngredient(ingredientDTO);
                ingredient.setRecipe(recipe);
                recipe.addIngredient(ingredient);
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            Optional<Ingredient> savedIngredientOptional = savedRecipe
                    .getIngredients()
                    .stream()
                    .filter(recipeIngredient -> recipeIngredient.getId().equals(ingredientDTO.getId()))
                    .findFirst();

            //check by description
            if (!savedIngredientOptional.isPresent()) {
                savedIngredientOptional = savedRecipe
                        .getIngredients()
                        .stream()
                        .filter(recipeIngredients -> recipeIngredients.getDescription().equals(ingredientDTO.getDescription()))
                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(ingredientDTO.getAmount()))
                        .filter(recipeIngredients -> recipeIngredients.getUnitOfMeasure().getId().equals(ingredientDTO.getUom().getId()))
                        .findFirst();
            }

            if(savedIngredientOptional.isPresent()) {
                return ingredientMapper.ingredientToIngredientDTO(savedIngredientOptional.get());
            } else {
                throw new NotFoundException("Saved ingredient not found");
            }
        }

    }

    @Override
    public void deleteById(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if(recipeOptional.isPresent()){
            Recipe recipe = recipeOptional.get();
            log.debug("found recipe");

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientId))
                    .findFirst();

            if(ingredientOptional.isPresent()){
                log.debug("found Ingredient");
                Ingredient ingredientToDelete = ingredientOptional.get();
                ingredientToDelete.setRecipe(null);
                recipe.getIngredients().remove(ingredientOptional.get());
                recipeRepository.save(recipe);
            }else {
                throw new NotFoundException("Ingredient Id Not found. Id: " + ingredientId);
            }
        } else {
            throw new NotFoundException("Recipe Id Not found. Id: " + recipeId);
        }
    }
}
