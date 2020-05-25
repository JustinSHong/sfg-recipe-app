package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

public class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Before
    public void setUp() throws Exception {
        // initialize the mock --> recipeRepository
        MockitoAnnotations.initMocks(this);

        // initialize object and inject mocked dependency
        recipeService = new RecipeServiceImpl(recipeRepository);
    }

    @Test
    public void getRecipeByIdTest() throws Exception {
        // given
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe); // may or may not exist
        // when
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        // then
        Recipe result = recipeService.findById(1L);

        assertNotNull("Null recipe returned", result);
        verify(recipeRepository).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    public void getRecipes() {
        // initialize a recipe and add it to the set
        Recipe recipe = new Recipe();
        HashSet recipesData = new HashSet();
        recipesData.add(recipe);

        // when findAll() is invoked return recipesData
        when(recipeRepository.findAll()).thenReturn(recipesData);

        Set<Recipe> recipes = recipeService.getRecipes();

        assertEquals(recipes.size(), 1);
        // test mock interaction
        // findAll() should be called only 1 time
        verify(recipeRepository, times(2)).findAll();
    }
}