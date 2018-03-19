package com.example.haneenalawneh.bakingapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Recipes.Ingredient;
import Recipes.Recipe;
import Recipes.Step;

/**
 * Created by haneenalawneh on 9/18/17.
 */
//ArrayList<Recipe>
public class DataUtils {
    public static ArrayList<Recipe> getRecipesData(String JsonStr) throws JSONException {

        ArrayList<Recipe> recipes = new ArrayList<Recipe>();

        JSONArray json = new JSONArray(JsonStr);
        for (int i = 0; i < json.length(); i++) {
            Recipe new_recpie;
            JSONObject Recipe = json.getJSONObject(i);


            ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
            ArrayList<Step> steps = new ArrayList<Step>();

            String id = Recipe.getString("id");
            String name = Recipe.getString("name");

            JSONArray ingredients_array = Recipe.getJSONArray("ingredients");

            for (int j = 0; j < ingredients_array.length(); j++) {
                Ingredient new_ingredient;
                JSONObject ingredient = ingredients_array.getJSONObject(j);

                Double quantity = ingredient.getDouble("quantity");
                String measure = ingredient.getString("measure");
                String ingredient_description = ingredient.getString("ingredient");

                new_ingredient = new Ingredient(quantity, ingredient_description, measure);
                ingredients.add(new_ingredient);

            }

            JSONArray steps_array = Recipe.getJSONArray("steps");

            for (int k = 0; k < steps_array.length(); k++) {
                Step new_step;
                JSONObject step = steps_array.getJSONObject(k);

                int step_id = step.getInt("id");
                String short_description = step.getString("shortDescription");
                String description = step.getString("description");
                String video_URL = step.getString("videoURL");
                String thumbnail_URL = step.getString("thumbnailURL");

                new_step = new Step(step_id, short_description, description, video_URL, thumbnail_URL);
                steps.add(new_step);


            }

            int servings = Recipe.getInt("servings");
            String image = Recipe.getString("image");

            new_recpie = new Recipe(id, name, ingredients, steps, servings, image);
            recipes.add(new_recpie);


        }
        return recipes;
    }
}