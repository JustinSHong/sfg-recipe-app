package guru.springframework.bootstrap;

import guru.springframework.domain.*;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DataLoader implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public DataLoader(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        getRecipes();
    }

    private List<Recipe> getRecipes() {
        List<Recipe> recipes = new ArrayList<>(2);

        // check units of measure are present in the database
        Optional<UnitOfMeasure> teaSpoonOptional = unitOfMeasureRepository.findByDescription("Teaspoon");
        if (!teaSpoonOptional.isPresent()) {
            throw new RuntimeException("Expected Unit of Measure not found");
        }

        Optional<UnitOfMeasure> tableSpoonOptional = unitOfMeasureRepository.findByDescription("Tablespoon");
        if (!tableSpoonOptional.isPresent()) {
            throw new RuntimeException("Expected Unit of Measure not found");
        }

        Optional<UnitOfMeasure> cupOptional = unitOfMeasureRepository.findByDescription("Cup");
        if (!cupOptional.isPresent()) {
            throw new RuntimeException("Expected Unit of Measure not found");
        }

        Optional<UnitOfMeasure> pinchOptional = unitOfMeasureRepository.findByDescription("Pinch");
        if (!pinchOptional.isPresent()) {
            throw new RuntimeException("Expected Unit of Measure not found");
        }

        Optional<UnitOfMeasure> ounceOptional = unitOfMeasureRepository.findByDescription("Ounce");
        if (!ounceOptional.isPresent()) {
            throw new RuntimeException("Expected Unit of Measure not found");
        }

        Optional<UnitOfMeasure> dashOptional = unitOfMeasureRepository.findByDescription("Dash");
        if (!dashOptional.isPresent()) {
            throw new RuntimeException("Expected Unit of Measure not found");
        }

        Optional<UnitOfMeasure> pintOptional = unitOfMeasureRepository.findByDescription("Pint");
        if (!pintOptional.isPresent()) {
            throw new RuntimeException("Expected Unit of Measure not found");
        }

        Optional<UnitOfMeasure> eachOptional = unitOfMeasureRepository.findByDescription("Each");
        if (!eachOptional.isPresent()) {
            throw new RuntimeException("Expected Unit of Measure not found");
        }

        // get optionals
        UnitOfMeasure teaSpoonUom = teaSpoonOptional.get();
        UnitOfMeasure tableSpoonUom = tableSpoonOptional.get();
        UnitOfMeasure cupUom = cupOptional.get();
        UnitOfMeasure pinchUom = pinchOptional.get();
        UnitOfMeasure ounceUom = ounceOptional.get();
        UnitOfMeasure dashUom = dashOptional.get();
        UnitOfMeasure pintUom = pintOptional.get();
        UnitOfMeasure eachUom = eachOptional.get();

        // check categories are present in the database
        Optional<Category> americanCategoryOptional = categoryRepository.findByDescription("American");
        if (!americanCategoryOptional.isPresent()) {
            throw new RuntimeException("Expected Category not found");
        }

        Optional<Category> italianCategoryOptional = categoryRepository.findByDescription("Italian");
        if (!italianCategoryOptional.isPresent()) {
            throw new RuntimeException("Expected Category not found");
        }

        Optional<Category> mexicanCategoryOptional = categoryRepository.findByDescription("Mexican");
        if (!mexicanCategoryOptional.isPresent()) {
            throw new RuntimeException("Expected Category not found");
        }

        Optional<Category> fastFoodOptional = categoryRepository.findByDescription("Fast Food");
        if (!fastFoodOptional.isPresent()) {
            throw new RuntimeException("Expected Category not found");
        }

        // get categories
        Category americanCategory = americanCategoryOptional.get();
        Category mexicanCategory = mexicanCategoryOptional.get();
        Category italianCategory = italianCategoryOptional.get();
        Category fastFoodCategory = fastFoodOptional.get();

        // create guacamole recipe
        Recipe guacamoleRecipe = new Recipe();
        guacamoleRecipe.setDescription("Perfect Guacamole");
        guacamoleRecipe.setPrepTime(10);
        guacamoleRecipe.setCookTime(10);
        guacamoleRecipe.setServings(4);
        guacamoleRecipe.setSource("");
        guacamoleRecipe.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        guacamoleRecipe.setDirections("1. Cut the avocado, remove flesh: Cut the avocados in half. Remove the pit. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl.\n"
                + "2. Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.) \n"
                + "3. Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                        "\n" +
                        "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                        "\n" +
                        "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                        "\n" +
                        "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving. \n"
                + "4. Serve: Serve immediately, or if making a few hours ahead, place plastic wrap on the surface of the guacamole and press down to cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve."
        );
        guacamoleRecipe.setDifficulty(Difficulty.EASY);

        Notes guacamoleNotes = new Notes();
        guacamoleRecipe.setNotes(guacamoleNotes);

        guacamoleRecipe.addIngredient(new Ingredient("ripe avocados", new BigDecimal(2), eachUom));
        guacamoleRecipe.addIngredient(new Ingredient("Kosher salt", new BigDecimal(".5"), teaSpoonUom));
        guacamoleRecipe.addIngredient(new Ingredient("fresh lime juice or lemon juice", new BigDecimal(2), tableSpoonUom));
        guacamoleRecipe.addIngredient(new Ingredient("minced red onion or thinly sliced green onion", new BigDecimal(2), tableSpoonUom));
        guacamoleRecipe.addIngredient(new Ingredient("serrano chiles, stems and seeds removed, minced", new BigDecimal(2), eachUom));
        guacamoleRecipe.addIngredient(new Ingredient("Cilantro", new BigDecimal(2), tableSpoonUom));
        guacamoleRecipe.addIngredient(new Ingredient("freshly grated black pepper", new BigDecimal(2), dashUom));
        guacamoleRecipe.addIngredient(new Ingredient("ripe tomato, seeds and pulp removed, chopped", new BigDecimal(".5"), eachUom));

        // create spicy chicken taco recipe
        Recipe tacosRecipe = new Recipe();
        tacosRecipe.setDescription("Spicy Grilled Chicken Tacos");
        tacosRecipe.setPrepTime(20);
        tacosRecipe.setCookTime(15);
        tacosRecipe.setServings(6);
        tacosRecipe.setSource("");
        tacosRecipe.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
        tacosRecipe.setDirections(
                "1. Prepare a gas or charcoal grill for medium-high, direct heat.\n"
                + "2. Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
                        "\n" +
                        "Set aside to marinate while the grill heats and you prepare the rest of the toppings."
                + "3. 3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n"
                + "4. 4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +
                        "\n" +
                        "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n"
                + "5. 5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges. \n"
        );
//        tacosRecipe.setIngredients();
//        tacosRecipe.setImage();
        tacosRecipe.setDifficulty(Difficulty.HARD);

        Notes tacoNotes = new Notes();
        tacosRecipe.setNotes(tacoNotes);

        tacosRecipe.addIngredient(new Ingredient("Ancho Chili Powder", new BigDecimal(2), tableSpoonUom));
        tacosRecipe.addIngredient(new Ingredient("Dried Oregano", new BigDecimal(1), teaSpoonUom));
        tacosRecipe.addIngredient(new Ingredient("Dried Cumin", new BigDecimal(1), teaSpoonUom));
        tacosRecipe.addIngredient(new Ingredient("Sugar", new BigDecimal(1), teaSpoonUom));
        tacosRecipe.addIngredient(new Ingredient("Salt", new BigDecimal(".5"), teaSpoonUom));
        tacosRecipe.addIngredient(new Ingredient("Clove of Garlic, Choppedr", new BigDecimal(1), eachUom));
        tacosRecipe.addIngredient(new Ingredient("finely grated orange zestr", new BigDecimal(1), tableSpoonUom));
        tacosRecipe.addIngredient(new Ingredient("fresh-squeezed orange juice", new BigDecimal(3), tableSpoonUom));
        tacosRecipe.addIngredient(new Ingredient("Olive Oil", new BigDecimal(2), tableSpoonUom));
        tacosRecipe.addIngredient(new Ingredient("boneless chicken thighs", new BigDecimal(4), tableSpoonUom));
        tacosRecipe.addIngredient(new Ingredient("small corn tortillasr", new BigDecimal(8), eachUom));
        tacosRecipe.addIngredient(new Ingredient("packed baby arugula", new BigDecimal(3), cupUom));
        tacosRecipe.addIngredient(new Ingredient("medium ripe avocados, slic", new BigDecimal(2), eachUom));
        tacosRecipe.addIngredient(new Ingredient("radishes, thinly sliced", new BigDecimal(4), eachUom));
        tacosRecipe.addIngredient(new Ingredient("cherry tomatoes, halved", new BigDecimal(".5"), pintUom));
        tacosRecipe.addIngredient(new Ingredient("red onion, thinly sliced", new BigDecimal(".25"), eachUom));
        tacosRecipe.addIngredient(new Ingredient("Roughly chopped cilantro", new BigDecimal(4), eachUom));
        tacosRecipe.addIngredient(new Ingredient("cup sour cream thinned with 1/4 cup milk", new BigDecimal(4), cupUom));
        tacosRecipe.addIngredient(new Ingredient("lime, cut into wedges", new BigDecimal(4), eachUom));

        // add recipes to the list of recipes

        return recipes;
    }
}
