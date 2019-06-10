package com.springframework.api.v2.model;

import com.springframework.models.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDTO {
    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private String source;
    private String servings;
    private String url;
    private String directions;
    private Difficulty difficulty;
    private Byte[] images;

    private NotesDTO notes;
    private List<CategoryDTO> categories = new ArrayList<>();
    private List<IngredientDTO> ingredients = new ArrayList<>();
}
