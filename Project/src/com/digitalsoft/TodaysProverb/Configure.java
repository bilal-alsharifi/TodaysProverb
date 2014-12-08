package com.digitalsoft.TodaysProverb;

import com.digitalsoft.TodaysProverb.R;
import com.digitalsoft.TodaysProverb.Helpers.DataBaseHelper;
import com.digitalsoft.TodaysProverb.Helpers.OptionsMenuHelper;
import com.digitalsoft.TodaysProverb.Helpers.SharedPreferencesHelper;
import com.digitalsoft.TodaysProverb.Helpers.WidgetHelper;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RemoteViews;

public class Configure extends Activity 
{
	private Configure context;
	private int appWidgetId;
	private AppWidgetManager appWidgetManager;
	private ListView lv;

	 
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.configure);
		
		context = this;
		appWidgetId = WidgetHelper.getAppWidgetId(context);
		appWidgetManager = AppWidgetManager.getInstance(context);
		lv = (ListView) findViewById(R.id.listView1);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, DataBaseHelper.getAllPackages(context));
		lv.setAdapter(adapter);
		
		lv.setOnItemClickListener(new OnItemClickListener() 
		{
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) 
			{
				Integer selectedItemPosition = arg2;
				String packageName = lv.getItemAtPosition(selectedItemPosition).toString();
				String tableName = DataBaseHelper.getTableName(context, packageName);
				SharedPreferencesHelper.saveString(context, "appWidget" + appWidgetId, tableName); 
				RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
				views.setTextViewText(R.id.packageName_textView, packageName);
			    views.setTextViewText(R.id.text_textView, DataBaseHelper.getRandomText(context, tableName));
			    WidgetHelper.makeClickable(context, Main.class, R.id.text_textView, views);
				appWidgetManager.updateAppWidget(appWidgetId, views);
				WidgetHelper.finishing(context, appWidgetId);	
			}	
		});	
	}
	
    
    // options menu methods must be included in all activities that has the options menu enabled
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
    	OptionsMenuHelper.onCreateOptionsMenu(context, menu);
    	return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) 
    {
    	OptionsMenuHelper.onMenuOpened(context, menu);
    	return super.onMenuOpened(featureId, menu);
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
    	OptionsMenuHelper.onPrepareOptionsMenu(menu, false);  	
    	return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
    	OptionsMenuHelper.onOptionsItemSelected(context, item);
    	return super.onOptionsItemSelected(item);
    }
    // end option menu methods
    
}
