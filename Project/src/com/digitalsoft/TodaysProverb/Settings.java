package com.digitalsoft.TodaysProverb;

import java.util.ArrayList;
import java.util.Arrays;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.digitalsoft.TodaysProverb.Helpers.GeneralHelper;
import com.digitalsoft.TodaysProverb.Helpers.OptionsMenuHelper;
import com.digitalsoft.TodaysProverb.Helpers.SharedPreferencesHelper;


public class Settings extends Activity 
{
	private Activity context;
	private Spinner changeLanguage_spn;
	private String language;
	private ArrayList<String> locales;
	private ArrayAdapter<CharSequence> adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        context = this;
        setContentView(R.layout.settings);

        changeLanguage_spn = (Spinner) findViewById(R.id.spinner1);

        //setting the language spinner
        adapter = ArrayAdapter.createFromResource(context, R.array.languages, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        changeLanguage_spn.setAdapter(adapter);
        
        String [] localesArray = context.getResources().getStringArray(R.array.locales);
        locales = new ArrayList<String>(Arrays.asList(localesArray));
        
        language = SharedPreferencesHelper.loadString(context, "language");
        if (language == null)
        {
        	language = GeneralHelper.getLocale();
        }
        changeLanguage_spn.setSelection(locales.indexOf(language));
       
        
        changeLanguage_spn.setOnItemSelectedListener(new OnItemSelectedListener() 
        {
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) 
			{
				Integer selectedItemPosition = arg2;
				String oldLanguage = language;
				language = locales.get(selectedItemPosition);				
				if (!language.equals(oldLanguage))
				{
					GeneralHelper.setLocale(context, language);
					SharedPreferencesHelper.saveString(context, "language", language);	
					Toast.makeText(context, context.getResources().getString(R.string.restartMessage), Toast.LENGTH_SHORT).show();
				}
			}
			public void onNothingSelected(AdapterView<?> arg0) 
			{
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