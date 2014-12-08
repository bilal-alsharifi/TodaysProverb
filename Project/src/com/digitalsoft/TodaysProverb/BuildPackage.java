package com.digitalsoft.TodaysProverb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.digitalsoft.TodaysProverb.Helpers.DataBaseHelper;
import com.digitalsoft.TodaysProverb.Helpers.OptionsMenuHelper;


public class BuildPackage extends Activity 
{
	private Activity context;
    private EditText et;
    private Button b;
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        context = this;
        setContentView(R.layout.buildpackage);
	
        et = (EditText) findViewById(R.id.packageName_editText);
        b = (Button) findViewById(R.id.save_button);
        
        b.setOnClickListener(new OnClickListener() 
        {
			public void onClick(View arg0) 
			{
				String category = "user";
				String packageName = et.getText().toString();
				// the prefix category just to distinguish user tables from other tables installed from Internet
				String tableName = category + System.currentTimeMillis();
				DataBaseHelper.addPackage(context, tableName, packageName, category);
				
				//go to new activity to start adding texts
				context.finish();
				Intent intent = new Intent(context , EditPackage.class);
				intent.putExtra("tableName", tableName);
				context.startActivity(intent);
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