package com.example.haneenalawneh.bakingapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.haneenalawneh.bakingapp.Provider.IngredientsContract;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import Adapters.RecpieAdapter;
import Recipes.Ingredient;
import Recipes.Recipe;
import Recipes.Step;
import timber.log.Timber;


public class MainActivity extends AppCompatActivity implements RecpieAdapter.ListItemClickListener, LoaderManager.LoaderCallbacks<Cursor> {
    @BindView(R.id.recpie_recycler_view) RecyclerView recyclerView;

    RecpieAdapter mAdapter;
    ArrayList<Recipe> recipes = new ArrayList<Recipe>();
    String widgetRecipe = "none";
    int smallestWidth;
    @Nullable
    private SimpleIdlingResource mIdlingResource;

    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Timber.plant(new Timber.DebugTree());

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        recyclerView.setHasFixedSize(true);

        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        if (tabletSize) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        } else {

            recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        }


        URL u = Network.buildUrl();
        new FetchRecipesData().execute(u);
        getSupportLoaderManager().initLoader(5, null, this);

    }

    @Override
    public void onListItemClick(int clickedItemIndex) {


        Intent intent = new Intent(this, StepsActivity.class);

        ArrayList<Step> steps = new ArrayList<Step>();
        ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();

        String recipeName;

        steps = recipes.get(clickedItemIndex).getRecipeSteps();
        ingredients = recipes.get(clickedItemIndex).getRecpieIngredients();
        recipeName = recipes.get(clickedItemIndex).getRecipeName();


        intent.putParcelableArrayListExtra("steps", steps);
        intent.putParcelableArrayListExtra("ingredients", ingredients);
        intent.putExtra("name", recipeName);
        intent.putExtra("widgetRecipe", widgetRecipe);


        startActivity(intent);
    }


    public class FetchRecipesData extends AsyncTask<URL, Void, ArrayList<Recipe>> {

        @Override
        protected ArrayList<Recipe> doInBackground(URL... urls) {


            if (urls.length == 0)
                return null;
            try {
                String s = Network.getResponseFromHttpUrl(urls[0]);
                try {
                    recipes = DataUtils.getRecipesData(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return recipes;


        }

        @Override
        protected void onPostExecute(ArrayList<Recipe> recipes) {
            if (recipes == null) {
                Toast.makeText(MainActivity.this, "No Internet Connection ", Toast.LENGTH_LONG).show();
            } else {
                mAdapter = new RecpieAdapter(recipes, MainActivity.this, MainActivity.this);
                recyclerView.setAdapter(mAdapter);

            }
        }
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {

            // Initialize a Cursor, this will hold all the task data
            Cursor mData = null;

            // onStartLoading() is called when a loader first starts loading data
            @Override
            protected void onStartLoading() {
                if (mData != null) {
                    // Delivers any previously loaded data immediately
                    deliverResult(mData);
                } else {
                    // Force a new load
                    forceLoad();
                }
            }

            // loadInBackground() performs asynchronous loading of data
            @Override
            public Cursor loadInBackground() {
                // Will implement to load data

                // Query and load all task data in the background; sort by priority

                try {
                    return getContentResolver().query(IngredientsContract.ingredientEntry.CONTENT_URI,
                            null,
                            null,
                            null,
                            null);


                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            // deliverResult sends the result of the load, a Cursor, to the registered listener
            public void deliverResult(Cursor data) {
                mData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.getCount() > 0) {
            data.moveToNext();
            int index = data.getColumnIndex(IngredientsContract.ingredientEntry.RECPIE_NAME);
            widgetRecipe = data.getString(index);
        }


    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


}
