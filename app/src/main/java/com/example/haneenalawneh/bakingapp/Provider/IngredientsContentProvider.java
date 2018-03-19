package com.example.haneenalawneh.bakingapp.Provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by haneenalawneh on 9/24/17.
 */

public class IngredientsContentProvider extends ContentProvider {
    private IngredientsDBhelper dBhelper;

    @Override
    public boolean onCreate() {
        dBhelper = new IngredientsDBhelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArguments, @Nullable String sortOrder) {
        Cursor cursor;


        cursor = dBhelper.getReadableDatabase().query(
                IngredientsContract.ingredientEntry.TABLE_NAME,

                projection,

                null,
                selectionArguments,
                null,
                null,
                sortOrder);

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;


    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    // WE DON'T NEED TO IMPLEMENT THIS BECAUSE WE INSERT DESCRIPTION AS BULKS NOT AS INDIVIDUALS
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    // we needn't to implement this
    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int deleted;
        final SQLiteDatabase db = dBhelper.getWritableDatabase();
        deleted = db.delete(IngredientsContract.ingredientEntry.TABLE_NAME, null, null);

        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        int updated = 0;
        final SQLiteDatabase db = dBhelper.getWritableDatabase();

        // this's the only case we are going to implement because we are going to update the whole table
        updated = db.update(IngredientsContract.ingredientEntry.TABLE_NAME, contentValues, null, strings);
        return updated;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = dBhelper.getWritableDatabase();


        db.beginTransaction();
        int rowsInserted = 0;
        try {
            for (ContentValues value : values) {


                long _id = db.insert(IngredientsContract.ingredientEntry.TABLE_NAME, null, value);
                if (_id != -1) {
                    rowsInserted++;
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        if (rowsInserted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsInserted;


    }


}
