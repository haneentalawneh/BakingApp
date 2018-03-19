package com.example.haneenalawneh.bakingapp.Widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.haneenalawneh.bakingapp.Provider.IngredientsContract;
import com.example.haneenalawneh.bakingapp.R;

/**
 * Created by haneenalawneh on 9/24/17.
 */

public class RecipeWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context context;
    private Cursor cursor;

    public RecipeWidgetRemoteViewsFactory(Context applicationContext, Intent intent) {
        context = applicationContext;
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        if (cursor != null) {
            cursor.close();
        }

        final long identityToken = Binder.clearCallingIdentity();
        cursor = context.getContentResolver().query(IngredientsContract.ingredientEntry.CONTENT_URI,
                null,
                null,
                null,
                IngredientsContract.ingredientEntry._ID + " ASC");
        // rv.setTextViewText(R.id.widgetTitleLabel, cursor.getString(cursor.getColumnIndex(IngredientsContract.ingredientEntry.RECPIE_NAME)));

        Binder.restoreCallingIdentity(identityToken);

    }

    @Override
    public void onDestroy() {
        if (cursor != null) {
            cursor.close();
        }
    }

    @Override
    public int getCount() {
        return cursor == null ? 0 : cursor.getCount();

    }

    @Override
    public RemoteViews getViewAt(int i) {
        if (i == AdapterView.INVALID_POSITION ||
                cursor == null || !cursor.moveToPosition(i)) {
            return null;
        }
        RemoteViews rv2 = new RemoteViews(context.getPackageName(), R.layout.recpie_widget_provider);
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget_list_item);
        rv.setTextViewText(R.id.widgetItemTaskNameLabel, cursor.getString(cursor.getColumnIndex(IngredientsContract.ingredientEntry.FULL_DESCRIPTION)));
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public long getItemId(int i) {
        return cursor.moveToPosition(i) ? cursor.getLong(0) : i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
