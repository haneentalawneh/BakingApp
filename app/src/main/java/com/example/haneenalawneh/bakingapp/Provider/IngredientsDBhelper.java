package com.example.haneenalawneh.bakingapp.Provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by haneenalawneh on 9/24/17.
 */

public  class IngredientsDBhelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "ingredients.db";

    public static final int DATABASE_VERSION = 9;

    public IngredientsDBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_INGREDIENTS_TABLE = "CREATE TABLE " + IngredientsContract.ingredientEntry.TABLE_NAME + " (" +
                IngredientsContract.ingredientEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                IngredientsContract.ingredientEntry.RECPIE_NAME       + " STRING NOT NULL, "                 +

                IngredientsContract.ingredientEntry.FULL_DESCRIPTION + " STRING NOT NULL)";

        sqLiteDatabase.execSQL(SQL_CREATE_INGREDIENTS_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + IngredientsContract.ingredientEntry.TABLE_NAME);

        onCreate(sqLiteDatabase);


    }


}
