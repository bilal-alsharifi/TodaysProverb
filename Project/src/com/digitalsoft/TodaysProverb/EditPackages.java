package com.digitalsoft.TodaysProverb;

import com.digitalsoft.TodaysProverb.R;
import com.digitalsoft.TodaysProverb.Helpers.DataBaseHelper;
import com.digitalsoft.TodaysProverb.Helpers.OptionsMenuHelper;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class EditPackages extends Activity 
{
	private Activity context;
	private ListView lv;
	private Button b;
	private ArrayAdapter<String> adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editpackages);
			
		context = this; 
		lv = (ListView) findViewById(R.id.listView1);
		b = (Button) findViewById(R.id.buildPackage_button);
		
		adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, DataBaseHelper.getAllPackages(context));
		lv.setAdapter(adapter);
		
		//to enable the context menu
		registerForContextMenu(lv);
		
		//to show context menu not only when long press but also when short press
		lv.setOnItemClickListener(new OnItemClickListener() 
		{
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) 
			{
				arg1.showContextMenu();
			}	
		});	
		
		b.setOnClickListener(new OnClickListener() 
		{	
			public void onClick(View arg0) 
			{
				Intent intent = new Intent(context, BuildPackage.class);
				context.startActivity(intent);
				context.finish();
			}
		});
				
	}
	

	//context menu methods
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) 
    {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.contextmenu, menu);
    }    
    @Override
    public boolean onContextItemSelected(MenuItem item) 
    {
        AdapterView.AdapterContextMenuInfo info;
        try 
        {
            info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        } 
        catch (ClassCastException e) 
        {
            return false;
        }
        int selectedItemPosition = (int) adapter.getItemId(info.position);
		String packageName = lv.getItemAtPosition(selectedItemPosition).toString();
		String tableName = DataBaseHelper.getTableName(context, packageName);
    	if (item.getItemId() == R.id.remove_menuItem)
    	{
			DataBaseHelper.uninstallPackage(context,tableName); 
			adapter.remove(packageName);
			adapter.notifyDataSetChanged();
    	}
    	else if (item.getItemId() == R.id.edit_menuItem)
    	{
    		Intent intent = new Intent(context, EditPackage.class);
    		intent.putExtra("tableName", tableName);
    		context.startActivity(intent);
    	}
        return true;
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
