package Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.haneenalawneh.bakingapp.R;

import java.util.ArrayList;

import Recipes.Ingredient;

/**
 * Created by haneenalawneh on 9/21/17.
 */

public class InredientsAdapter extends RecyclerView.Adapter<InredientsAdapter.IngredientViewHolder> {
    ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();

    public InredientsAdapter(ArrayList<Ingredient> ingredients, Context context) {
        this.ingredients = ingredients;
        Log.i("sizzze", ingredients.size() + "");


    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.ingredient_layout;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        InredientsAdapter.IngredientViewHolder viewHolder = new InredientsAdapter.IngredientViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        holder.Bind(position);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {
        TextView ingredientsDescriptionTV;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            ingredientsDescriptionTV = (TextView) itemView.findViewById(R.id.ingredient_description);
        }

        public void Bind(int position) {
            String ingredientDescription = ingredients.get(position).getFullDescription();

            ingredientsDescriptionTV.setText(ingredientDescription);
        }
    }
}
