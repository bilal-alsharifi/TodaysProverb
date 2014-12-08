package com.digitalsoft.TodaysProverb;

import com.digitalsoft.TodaysProverb.Helpers.OptionsMenuHelper;

import android.app.Activity;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class About extends Activity 
{
	private TextView developer_tv;
	private TextView version_tv;
	private TextView developerWebsite_tv;
	private TextView developerSupportEmail_tv;
	private Activity context;
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.about);
        
        
        // get pointers to the views
        developer_tv = (TextView) findViewById(R.id.developer_textView);
        version_tv = (TextView) findViewById(R.id.version_textView);
        developerWebsite_tv = (TextView) findViewById(R.id.website_textView);
        developerSupportEmail_tv = (TextView) findViewById(R.id.email_textView);
        
        context = this;
        
              
        // set the values for the text views
        developer_tv.setText(getResources().getString(R.string.by) + " " +getResources().getString(R.string.app_DeveloperName)); 
        String versionName = null;      
        try 
        {
			versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
		} 
        catch (NameNotFoundException e) 
        {
			e.printStackTrace();
		}
        version_tv.append(" " + versionName);
        developerWebsite_tv.setText(developerWebsite_tv.getText() + " " + getResources().getString(R.string.app_SupportWebsite)); 
        developerSupportEmail_tv.setText(developerSupportEmail_tv.getText() + " " + getResources().getString(R.string.app_supportEmail)); 
      
        
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