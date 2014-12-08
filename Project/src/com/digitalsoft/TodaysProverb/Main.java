package com.digitalsoft.TodaysProverb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.digitalsoft.TodaysProverb.Helpers.GeneralHelper;
import com.digitalsoft.TodaysProverb.Helpers.OptionsMenuHelper;
import com.digitalsoft.TodaysProverb.Helpers.SharedPreferencesHelper;


public class Main extends Activity 
{
	private Button addPacakges_btn;
	private Button editPacakges_btn;
	private String language;
	private Activity context;
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        context = this;
        language = SharedPreferencesHelper.loadString(context, "language");
        GeneralHelper.setLocale(context, language);
            
        setContentView(R.layout.main);
	
        addPacakges_btn = (Button) findViewById(R.id.addPackages_btn);
        editPacakges_btn = (Button) findViewById(R.id.editPackages_btn);
    
        
 
        addPacakges_btn.setOnClickListener(new OnClickListener() 
        {	
			public void onClick(View arg0) 
			{
				Intent intent = new Intent(context,AddPackages.class);
				startActivity(intent);				
			}
		});       
        editPacakges_btn.setOnClickListener(new OnClickListener() 
        {	
			public void onClick(View arg0) 
			{
				Intent intent = new Intent(context,EditPackages.class);
				startActivity(intent);			
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