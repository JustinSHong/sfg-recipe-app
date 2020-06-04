package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@Slf4j
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("/recipe/{id}/show")
    public String showById(@PathVariable String id, Model model) {
        log.debug("***** FETCHING A RECIPE *****");

        model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));

        return "recipe/show";
    }

    // renders the form for creating a new recipe
    @RequestMapping("/recipe/new")
    public String createRecipe(Model model) {
        log.debug("***** SHOWING RECIPE FORM ***** ");

        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeform";
    }

    // @ModelAttribute bind the form post params to the recipe command object
    @PostMapping("recipe/{id}/update")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command){
        log.debug("***** SAVING/UPDATING RECIPE *****");

        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);

        // redirect to the single recipe's form page
        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }
}
