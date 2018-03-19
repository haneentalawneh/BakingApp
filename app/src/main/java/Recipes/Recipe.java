package Recipes;

import java.util.ArrayList;

/**
 * Created by haneenalawneh on 9/18/17.
 */

public class Recipe {
    String recipe_id;
    String recipe_name;
    ArrayList<Ingredient> recpie_ingredients;
    ArrayList<Step> recipe_steps;
    int recpie_servings;
    String recpie_image;

    public Recipe(String id, String name, ArrayList<Ingredient> ingredients, ArrayList<Step> steps, int servings, String image) {
        recipe_id = id;

        recipe_name = name;
        recpie_ingredients = ingredients;
        recipe_steps = steps;
        recpie_servings = servings;
        recpie_image = image;
    }

    public String getRecipeId() {
        return recipe_id;
    }

    public String getRecipeName() {
        return recipe_name;
    }

    public ArrayList<Ingredient> getRecpieIngredients() {
        return recpie_ingredients;
    }

    public ArrayList<Step> getRecipeSteps() {
        return recipe_steps;
    }

    public int getRecpieServings() {
        return recpie_servings;
    }

    public String getRecpieImage() {
        return recpie_image;
    }


}
