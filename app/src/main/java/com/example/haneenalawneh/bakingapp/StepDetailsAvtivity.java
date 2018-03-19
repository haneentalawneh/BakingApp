package com.example.haneenalawneh.bakingapp;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import Recipes.Ingredient;
import Recipes.Step;

public class StepDetailsAvtivity extends AppCompatActivity {
    ArrayList<Step> steps = new ArrayList<Step>();
    int stepIndex;
    String description;
    String uri;
    String uriT;
    ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
    String widgetRecipe;
    String recipeName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details_avtivity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent != null) {
            steps = intent.getParcelableArrayListExtra("steps");
            ingredients = intent.getParcelableArrayListExtra("ingredients");
            recipeName = intent.getStringExtra("name");
            widgetRecipe = intent.getStringExtra("widgetRecipe");
            stepIndex = intent.getIntExtra("index", 0);

        }

        if (savedInstanceState == null) {
            StepDetailsFragment stepsDetailsFragment = new StepDetailsFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();

            setFragmentData(stepsDetailsFragment);

            fragmentManager.beginTransaction()
                    .add(R.id.details_containe, stepsDetailsFragment)
                    .commit();

        }

    }

    public void moveToNext(View view) {
        stepIndex++;
        StepDetailsFragment newStepsDetailsFragment = new StepDetailsFragment();


        FragmentManager fragmentManager = getSupportFragmentManager();

        setFragmentData(newStepsDetailsFragment);

        fragmentManager.beginTransaction()
                .replace(R.id.details_containe, newStepsDetailsFragment)
                .commit();
        ;


    }

    public void setFragmentData(StepDetailsFragment fragment) {
        description = steps.get(stepIndex).getStepDescription();
        uri = steps.get(stepIndex).getStepVideoURL();

        uriT = steps.get(stepIndex).getStepThumbnailURL();

        if (uri.equals("")) {
            Toast.makeText(this, "There's no video available for this step", Toast.LENGTH_SHORT).show();
        }


        fragment.setThumbnail(uriT);
        fragment.setStepVideo(uri);
        fragment.setStepDescription(description);
        setNavigationVisibilty(fragment);


    }

    public void moveToPrevious(View view) {

        stepIndex--;
        StepDetailsFragment newStepsDetailsFragment = new StepDetailsFragment();


        FragmentManager fragmentManager = getSupportFragmentManager();

        setFragmentData(newStepsDetailsFragment);

        fragmentManager.beginTransaction()
                .replace(R.id.details_containe, newStepsDetailsFragment)
                .commit();
        ;
    }

    public void setNavigationVisibilty(StepDetailsFragment fragment) {
        if (stepIndex == 0) {
            fragment.setIsPrevVisible(false);
        } else if (stepIndex == (steps.size() - 1)) {
            fragment.setIsNextVisible(false);
        }

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