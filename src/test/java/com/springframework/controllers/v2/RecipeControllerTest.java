package com.springframework.controllers.v2;

import com.springframework.api.v2.model.RecipeDTO;
import com.springframework.services.RecipeService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(RecipeController.class)
class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeService recipeService;

    private final String BASE_URL = "http://localhost:8080/" + RecipeController.BASE_URL;
    @Test
    void getListOfRecipes() throws Exception {
        //given
        RecipeDTO recipe1 = new RecipeDTO();
        RecipeDTO recipe2 = new RecipeDTO();
        recipe1.setId(1L);
        recipe2.setId(2L);

        when(recipeService.getAllRecipes()).thenReturn(Arrays.asList(recipe1,recipe2));

        //when/then
        mockMvc.perform(get(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipes", hasSize(2)));
    }

    @Test
    void getRecipeById() throws Exception {
        //given
        RecipeDTO recipe = new RecipeDTO();
        recipe.setId(1L);
        recipe.setDescription("Perfect Guacamole");

        when(recipeService.getRecipeById(anyLong())).thenReturn(recipe);

        //when/then
        mockMvc.perform(get(BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.description", equalTo("Perfect Guacamole")));
    }

    @Test
    void saveRecipe() {
    }

    @Test
    void updateRecipe() {
    }

    @Test
    void deleteRecipe() {
    }
}