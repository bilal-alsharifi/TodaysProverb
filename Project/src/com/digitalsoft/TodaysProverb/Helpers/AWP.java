package com.digitalsoft.TodaysProverb.Helpers;

import com.digitalsoft.TodaysProverb.Main;
import com.digitalsoft.TodaysProverb.R;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

public class AWP extends AppWidgetProvider 
{
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,int[] appWidgetIds) 
	{
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		
		for(int i = 0; i < appWidgetIds.length ; i++)
		{
			int appWidgetId = appWidgetIds[i];
		    String tableName = SharedPreferencesHelper.loadString(context, "appWidget" + appWidgetId);    
		    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
		    if(tableName != null && DataBaseHelper.tableExists(context, tableName))
		    {
		    	String packageName = DataBaseHelper.getPackageName(context, tableName);
			    views.setTextViewText(R.id.packageName_textView, packageName);
				views.setTextViewText(R.id.text_textView, DataBaseHelper.getRandomText(context, tableName));	
		    }
		    else
		    {
		    	views.setTextViewText(R.id.text_textView, context.getResources().getString(R.string.thePackageHasBeenDeletedMessage));
		    }
		    WidgetHelper.makeClickable(context, Main.class, R.id.text_textView, views);
			appWidgetManager.updateAppWidget(appWidgetId, views);
		}
		
	}
}
