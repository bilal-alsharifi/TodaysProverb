package com.digitalsoft.TodaysProverb.Helpers;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper 
{
	public static void saveString(Context context, String key, String value)
	{
		SharedPreferences prefs = context.getSharedPreferences("PREFS", 0);
	    SharedPreferences.Editor editor = prefs.edit();
	    editor.putString(key, value);
	    editor.commit();    
	}
	public static String loadString(Context context, String key)
	{
		SharedPreferences prefs = context.getSharedPreferences("PREFS", 0);
	    return prefs.getString(key, null);
	}
	
}
