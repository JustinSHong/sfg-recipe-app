package guru.springframework.controllers;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

// SPRING MOCK MVC - gives you the ability to test controllers and unit test them
    // bring in mock servlet context
    // no need to bring a web server
    // controller unit tests become much more light-weight
    // webAppContextSetup() ---> brings up Spring context
    // standaloneSetup() ---> unit tests for faster tests


public class IndexControllerTest {

    IndexController indexController;

    @Mock
    RecipeService recipeService;

    @Mock
    Model model;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        indexController = new IndexController(recipeService);
    }

    // RECOMMENDED WAY TO TEST CONTROLLERS - FAST, LIGHTWEIGHT
    @Test
    public void testMockMVC() throws Exception {
        // INITIALIZE MOCKMVC
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build(); // for fast unit tests

        // test status 200 and view name is index
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void getIndexPage() {
        // GIVEN
        Set<Recipe> recipes = new HashSet<>();
        Recipe recipe1 = new Recipe();
        recipe1.setId(1L);
        recipes.add(recipe1);

        Recipe recipe2 = new Recipe();
        recipe2.setId(2L);
        recipes.add(recipe2);

        when(recipeService.getRecipes()).thenReturn(recipes);

        // NEED TO VERIFY THE SET OF RECIPES RETURNED - can also use @Captor
        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        // WHEN
        // verify proper string returned
        String viewName = indexController.getIndexPage(model);

        //THEN
        assertEquals("index", viewName);
        // verify mocks interactions
        verify(recipeService, times(1)).getRecipes();
        // eq(value) ---> attribute should match value
        // argumentCaptor.capture ---> captures whatever is passed in to the mock
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());

        Set<Recipe> setInController = argumentCaptor.getValue();

        assertEquals(2, setInController.size());
    }
}