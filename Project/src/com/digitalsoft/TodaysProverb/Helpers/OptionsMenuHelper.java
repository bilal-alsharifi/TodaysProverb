package com.digitalsoft.TodaysProverb.Helpers;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.digitalsoft.TodaysProverb.About;
import com.digitalsoft.TodaysProverb.R;
import com.digitalsoft.TodaysProverb.Settings;

public class OptionsMenuHelper 
{
	public static void onCreateOptionsMenu(Activity context, Menu menu)
	{
    	MenuInflater inflater = context.getMenuInflater();
    	inflater.inflate(R.menu.optionsmenu, menu);
	}
	public static void onMenuOpened(Activity context, Menu menu)
	{

	}
    public static void onPrepareOptionsMenu(Menu menu, Boolean showAppOtions)
    {

    }
	public static void onOptionsItemSelected(Activity context, MenuItem item)
	{
    	if (item.getItemId() == R.id.about_menuItem)
    	{
    		Intent intent = new Intent(context, About.class);
			context.startActivity(intent);
    	}
    	else if (item.getItemId() == R.id.settings_menuItem)
    	{
    		Intent intent = new Intent(context, Settings.class);
			context.startActivity(intent);
    	}
    	else if (item.getItemId() == R.id.exit_menuItem)
    	{
    		Intent intent = new Intent();
    		intent.setAction(Intent.ACTION_MAIN);
    		intent.addCategory(Intent.CATEGORY_HOME);
    		context.startActivity(intent); 
    		context.finish();   
    	}
	}
}
