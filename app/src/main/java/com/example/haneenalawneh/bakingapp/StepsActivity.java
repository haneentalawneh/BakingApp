package com.example.haneenalawneh.bakingapp;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;


import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.haneenalawneh.bakingapp.Provider.IngredientsContract;
import com.example.haneenalawneh.bakingapp.Widget.RecpieWidgetProvider;

import Recipes.Ingredient;
import Recipes.Step;
import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsActivity extends AppCompatActivity implements StepsFragment.OnListFragmentInteractionListener {


    ArrayList<Step> steps = new ArrayList<Step>();
    ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
    String recipeName;
    boolean mTwoPane = false;
    String description;
    String uri;
    String uriT;
    int stepIndex = 0;
   @BindView (R.id.ingredients) TextView currentRecipe;
    boolean isInWidget = false;
    String widgetRecipe;
    public final String MY_SHARED_PREFERNCES = "homewidgetRecipe";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        currentRecipe = (TextView) findViewById(R.id.ingredients);

         ButterKnife.bind(this);
        SharedPreferences shared = getSharedPreferences(MY_SHARED_PREFERNCES, 0);


        Intent intent = getIntent();
        if (intent != null) {


            steps = intent.getParcelableArrayListExtra("steps");
            ingredients = intent.getParcelableArrayListExtra("ingredients");
            recipeName = intent.getStringExtra("name");
            widgetRecipe = intent.getStringExtra("widgetRecipe");

            currentRecipe.setText(recipeName + " ingredients");
            if (!widgetRecipe.equals("none")) {

                isInWidget = true;

            }
            if (shared != null) {

                String name = shared.getString("name", "none");

                if (name.equals(recipeName)) {
                    findViewById(R.id.floatingActionButton).setVisibility(View.INVISIBLE);


                }

            }

//


            if (findViewById(R.id.details_linear_layout) != null) {

                mTwoPane = true;
                if (savedInstanceState == null) {

                    StepDetailsFragment stepsDetailsFragment = new StepDetailsFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();


                    setFragmentData(stepsDetailsFragment);
                    fragmentManager.beginTransaction()
                            .add(R.id.details_container, stepsDetailsFragment)
                            .commit();


                    StepsFragment stepsFragment = new StepsFragment();

                    // Set the list of image id's for the head fragment and set the position to the second image in the list
                    stepsFragment.setSteps(steps);


                    // Add the fragment to its container using a FragmentManager and a Transaction
                    fragmentManager.beginTransaction()
                            .add(R.id.container, stepsFragment)
                            .commit();
                }

            } else {
                if (savedInstanceState == null) {
                    android.support.v4.app.FragmentManager fm = getSupportFragmentManager();

                    StepsFragment stepsFragment = new StepsFragment();

                    // Set the list of image id's for the head fragment and set the position to the second image in the list
                    stepsFragment.setSteps(steps);

                    FragmentManager fragmentManager = getSupportFragmentManager();

                    // Add the fragment to its container using a FragmentManager and a Transaction
                    fragmentManager = getSupportFragmentManager();

                    fragmentManager.beginTransaction()
                            .add(R.id.container, stepsFragment)
                            .commit();


                }
            }
        }

    }

    @Override
    public void onListFragmentInteraction(int index) {
        if (mTwoPane) {
            stepIndex = index;

            StepDetailsFragment newDetailsFragment = new StepDetailsFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();
            setFragmentData(newDetailsFragment);
            fragmentManager.beginTransaction()
                    .replace(R.id.details_container, newDetailsFragment)
                    .commit();

        } else {
            Intent intent = new Intent(this, StepDetailsAvtivity.class);
            intent.putParcelableArrayListExtra("steps", steps);
            intent.putExtra("index", index);
            intent.putParcelableArrayListExtra("ingredients", ingredients);
            intent.putExtra("name", recipeName);
            intent.putExtra("widgetRecipe", widgetRecipe);

            startActivity(intent);

        }
    }

    public void moveToIngredients(View view) {
        if (ingredients == null) {
            Snackbar.make(getCurrentFocus(), "Sorry, no ingredients available", Snackbar.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(this, IngredientsActivity.class);
            intent.putParcelableArrayListExtra("ingredients", ingredients);
            intent.putParcelableArrayListExtra("steps", steps);
            intent.putExtra("name", recipeName);
            intent.putExtra("widgetRecipe", widgetRecipe);
            startActivity(intent);

        }
    }

    public void setFragmentData(StepDetailsFragment fragment) {
        description = steps.get(stepIndex).getStepDescription();
        uri = steps.get(stepIndex).getStepVideoURL();
        uriT = steps.get(stepIndex).getStepThumbnailURL();

        if (uri.equals("")) {
            Snackbar.make(getCurrentFocus(), "There's no video available for this step", Snackbar.LENGTH_SHORT).show();
        }


        fragment.setThumbnail(uriT);
        fragment.setStepVideo(uri);
        fragment.setStepDescription(description);

        fragment.setIsNextVisible(false);
        fragment.setIsPrevVisible(false);


    }

    public void addToWidget(View view) {
        if (isInWidget) {
            getContentResolver().delete(IngredientsContract.ingredientEntry.CONTENT_URI, null, null);
            ContentValues[] cv = new ContentValues[ingredients.size()];
            for (int i = 0; i < ingredients.size(); i++) {
                cv[i] = new ContentValues();
                cv[i].put(IngredientsContract.ingredientEntry.FULL_DESCRIPTION, ingredients.get(i).getFullDescription());
                cv[i].put(IngredientsContract.ingredientEntry.RECPIE_NAME, recipeName);
                Log.i("h", cv[i].getAsString(IngredientsContract.ingredientEntry.FULL_DESCRIPTION));
            }
            getContentResolver().bulkInsert(IngredientsContract.ingredientEntry.CONTENT_URI, cv);
            RecpieWidgetProvider.sendRefreshBroadcast(this);

            SharedPreferences settings = getSharedPreferences(MY_SHARED_PREFERNCES, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("name", recipeName);

            editor.commit();
            Snackbar snackbar = Snackbar
                    .make(getCurrentFocus(), "Home widget ingredients are updated", Snackbar.LENGTH_LONG);

            snackbar.show();


        } else {
            ContentValues[] cv = new ContentValues[ingredients.size()];
            for (int i = 0; i < ingredients.size(); i++) {
                cv[i] = new ContentValues();
                cv[i].put(IngredientsContract.ingredientEntry.FULL_DESCRIPTION, ingredients.get(i).getFullDescription());
                cv[i].put(IngredientsContract.ingredientEntry.RECPIE_NAME, recipeName);
                Log.i("h", cv[i].getAsString(IngredientsContract.ingredientEntry.FULL_DESCRIPTION));
            }
            getContentResolver().bulkInsert(IngredientsContract.ingredientEntry.CONTENT_URI, cv);
            RecpieWidgetProvider.sendRefreshBroadcast(this);

            Snackbar.make(getCurrentFocus(), "Recipe ingredients are added to Home widget", Snackbar.LENGTH_LONG).show();
            SharedPreferences settings = getSharedPreferences(MY_SHARED_PREFERNCES, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("name", recipeName);

            editor.commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("steps", steps);
        outState.putParcelableArrayList("ingredients", ingredients);
        outState.putString("widget", widgetRecipe);
        outState.putString("name", recipeName);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}



