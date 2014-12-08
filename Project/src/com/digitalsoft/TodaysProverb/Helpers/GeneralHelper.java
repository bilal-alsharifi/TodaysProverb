package com.digitalsoft.TodaysProverb.Helpers;

import java.util.Locale;
import android.content.Context;
import android.content.res.Configuration;

public class GeneralHelper 
{
	public static String getLocale()
	{
		String result = null;
		result = Locale.getDefault().toString();
		return result;	
	}
	public static void setLocale(Context context, String language)
	{
		if (language != null)
		{
			Locale locale = new Locale(language);
	        Locale.setDefault(locale);
	        Configuration config = new Configuration();
	        config.locale = locale;
	        context.getApplicationContext().getResources().updateConfiguration(config, null);
		}
	}
}
