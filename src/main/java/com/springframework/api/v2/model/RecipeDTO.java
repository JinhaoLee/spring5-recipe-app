package com.springframework.api.v2.model;

import com.springframework.models.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDTO {
    private Long id;

    @NotBlank(message = "Please provide a description")
    @Size(min = 3, max = 255)
    private String description;

    @Min(value = 1, message = "Preparation time must not be less than 1")
    @Max(value = 999, message = "Preparation time must not be greater than 999")
    private Integer prepTime;

    @Min(value = 1, message = "Cooking time must not be less than 1")
    @Max(value = 999, message = "Cooking time must not be greater than 999")
    private Integer cookTime;

    @Min(value = 1, message = "Servings must not be less than 1")
    @Max(value = 100, message = "Servings must not be greater than 100")
    private Integer servings;

    private String source;

    @URL(message = "Invalid URL")
    private String url;

    @NotBlank(message = "Please provide directions")
    private String directions;

    private NotesDTO notes;
    private Difficulty difficulty;
    private Byte[] image;
    private List<CategoryDTO> categories = new ArrayList<>();
    private List<IngredientDTO> ingredients = new ArrayList<>();
}
