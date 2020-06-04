package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class RecipeControllerTest {

    RecipeController controller;

    MockMvc mockMvc;

    @Mock
    RecipeService recipeService;

    @Mock
    Model model;

    @Before
    public void setUp() {
        // INITIALIZE MOCKS
        MockitoAnnotations.initMocks(this); // alternatively use injectMocks with JUnit5
        controller = new RecipeController(recipeService);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("Returns status 200 and returns recipe/recipeform view template")
    public void shouldReturnStatusOkAndRecipeFormView() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/new").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    @DisplayName("Returns a single recipe using the recipeService")
    public void shouldGetASingleRecipe() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1l);
        // TELL THE MOCK WHAT TO RETURN
        when(recipeService.findById(anyLong())).thenReturn(recipe);

        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        String viewName = controller.showById("1", model);

        assertEquals("recipe/show", viewName);

        verify(recipeService, times(1)).findById(anyLong());
        verify(model, times(1)).addAttribute(eq("recipe"), argumentCaptor.capture());

        Recipe fetched = argumentCaptor.getValue();
        System.out.println(fetched);

        assertTrue(fetched instanceof Recipe);
        assertEquals(1l, fetched.getId());
    }

    @Test
    @DisplayName("Returns status 3xx and returns the recipe/{id}/show view template")
    public void shouldReturnStatus3xxAndSingleRecipeView() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId(2l);

        when(recipeService.saveRecipeCommand(any())).thenReturn(command);

        mockMvc.perform(MockMvcRequestBuilders.post("/recipe/2/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "2")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/show"));
    }

    @Test
    @DisplayName("Saves a single recipe using the recipe service")
    public void shouldSaveASingleRecipe() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId(3l);

        when(recipeService.saveRecipeCommand(any())).thenReturn(command);

        String viewName = controller.saveOrUpdate(command);

        assertEquals("redirect:/recipe/3/show", viewName);

        verify(recipeService, times(1)).saveRecipeCommand(command);
    }
}
