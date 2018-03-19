package Recipes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by haneenalawneh on 9/18/17.
 */

public class Ingredient implements Parcelable {
    Double ingredient_quantity;
    String ingredient_description;
    String ingredient_measure;

    public Ingredient(Double quantity, String description, String measure) {
        ingredient_quantity = quantity;
        ingredient_description = description;
        ingredient_measure = measure;
    }

    private Ingredient(Parcel in) {
        ingredient_quantity = in.readDouble();
        ingredient_description = in.readString();
        ingredient_measure = in.readString();

    }

    public Double getIngredientQuantity() {
        return ingredient_quantity;
    }

    public String getIngredientDescription() {
        return ingredient_description;
    }

    public String getIngredientMeasure() {
        return ingredient_measure;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeDouble(ingredient_quantity);
        parcel.writeString(ingredient_description);
        parcel.writeString(ingredient_measure);

    }

    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public String getFullDescription() {
        String description = ingredient_quantity + " " + ingredient_measure + " of " + ingredient_description;
        return description;

    }
}
