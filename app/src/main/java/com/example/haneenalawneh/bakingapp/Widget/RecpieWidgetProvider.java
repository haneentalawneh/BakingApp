package com.example.haneenalawneh.bakingapp.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.haneenalawneh.bakingapp.MainActivity;
import com.example.haneenalawneh.bakingapp.R;

/**
 * Implementation of App Widget functionality.
 */
public  class RecpieWidgetProvider extends AppWidgetProvider {
     static String title="oo";
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        Intent intent = new Intent(context, RecipeWidgetRemoteViewsService.class);
        Intent intent1=new Intent(context,MainActivity.class);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recpie_widget_provider);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, 0);
        views.setRemoteAdapter(R.id.widgetListView, intent);
        // Instruct the widget manager to update the widget
        views.setOnClickPendingIntent(R.id.add_button,pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
    // this method is used so whenever the user changes the recipe he wants to be shown in the widget , the widget has a broadcast reviever so it's updated
    public static void sendRefreshBroadcast(Context context) {
        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.setComponent(new ComponentName(context, RecpieWidgetProvider.class));
        context.sendBroadcast(intent);
    }
    @Override
    public void onReceive(final Context context, Intent intent) {
        final String action = intent.getAction();
        if (action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            // refresh all your widgets
            AppWidgetManager mgr = AppWidgetManager.getInstance(context);
            ComponentName cn = new ComponentName(context, RecpieWidgetProvider.class);
            mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.widgetListView);
        }
        super.onReceive(context, intent);
    }
}

