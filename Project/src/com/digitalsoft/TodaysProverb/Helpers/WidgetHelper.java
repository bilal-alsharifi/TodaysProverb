package com.digitalsoft.TodaysProverb.Helpers;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

public class WidgetHelper 
{
	public static void makeClickable(Context context, Class<?> cls, int viewId, RemoteViews views)
	{
		Intent intent = new Intent(context, cls);
        PendingIntent pending = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(viewId, pending);	
	}
	public static void finishing(Activity context, int appWidgetId)
	{
		Intent resultVlaue = new Intent();
		resultVlaue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
		context.setResult(Activity.RESULT_OK, resultVlaue);
		context.finish();
	}
	public static int getAppWidgetId(Activity context)
	{
		int appWidgetId = 0;
		context.setResult(Activity.RESULT_CANCELED);
		Bundle extras = context.getIntent().getExtras();
		if(extras != null)
		{
			appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
		}
		return appWidgetId;
	}
}
