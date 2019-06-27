package com.springframework.api.v2.mapper;

import com.springframework.api.v2.model.IngredientDTO;
import com.springframework.models.Ingredient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UnitOfMeasureMapper.class)
public interface IngredientMapper {
  @Mapping(target = "recipeId", source = "recipe.id")
  @Mapping(target = "uom", source = "unitOfMeasure")
  IngredientDTO ingredientToIngredientDTO(Ingredient ingredient);

  @Mapping(target = "recipe.id", source = "recipeId")
  @Mapping(target = "unitOfMeasure", source = "uom")
  @Mapping(target = "recipe", ignore = true)
  Ingredient ingredientDtoToIngredient(IngredientDTO ingredientDTO);
}
