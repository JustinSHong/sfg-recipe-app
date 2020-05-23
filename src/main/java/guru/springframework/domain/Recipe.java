package guru.springframework.domain;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Data
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-generated id in sequence
    private Long id;

    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;

    @Lob
    private String directions;

    // mappedBy = [property on child class], recipe will be stored on the recipe property of each ingredient class
    // use mappedBy to establish a bi-directional relationship between Recipe and Ingredients
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients = new HashSet<>();

    @Lob // large object annotation
    private Byte[] image; // Blob - binary large object

    @Enumerated(value = EnumType.STRING) // persist in DB as string values instead of ordinal values (1,2,3)
    private Difficulty difficulty;

    @OneToOne(cascade = CascadeType.ALL)
    private Notes notes;

    @ManyToMany
    @JoinTable(name = "recipe_category", joinColumns = @JoinColumn(name = "recipe_id"), inverseJoinColumns =  @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    public Recipe addIngredient(Ingredient ingredient){
        log.debug("***** DEBUGGING ADDING INGREDIENT *****");

        ingredient.setRecipe(this); // associate recipe with ingredients
        this.ingredients.add(ingredient);
        return this;
    }
}
