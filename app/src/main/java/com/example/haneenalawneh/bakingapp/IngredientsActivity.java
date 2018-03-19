package com.example.haneenalawneh.bakingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;

import Adapters.InredientsAdapter;
import Recipes.Ingredient;
import Recipes.Step;
import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsActivity extends AppCompatActivity {
  @BindView(R.id.ingredients_list) RecyclerView recyclerView;
    InredientsAdapter adapter;
    ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
    ArrayList<Step> steps = new ArrayList<Step>();
    String widgetRecipe;
    String recipeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        ingredients = intent.getParcelableArrayListExtra("ingredients");
        steps = intent.getParcelableArrayListExtra("steps");
        recipeName = intent.getStringExtra("name");
        widgetRecipe = intent.getStringExtra("widgetRecipe");

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        adapter = new InredientsAdapter(ingredients, this);
        recyclerView.setAdapter(adapter);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, StepsActivity.class);
                intent.putParcelableArrayListExtra("ingredients", ingredients);
                intent.putParcelableArrayListExtra("steps", steps);
                intent.putExtra("name", recipeName);
                intent.putExtra("widgetRecipe", widgetRecipe);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
