package com.example.haneenalawneh.bakingapp.Provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by haneenalawneh on 9/24/17.
 */

public class IngredientsContract {
    public static final String AUTHORITY = "com.example.haneenalawneh.bakingapp";

    // The base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Define the possible paths for accessing data in this contract
    public static final String PATH_INGREDIENTS = "ingredients";

    public static final class ingredientEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_INGREDIENTS).build();

        public static final String TABLE_NAME = "ingredients";

        public static final String FULL_DESCRIPTION = "description";
        public static final String RECPIE_NAME = "name";


    }


}
