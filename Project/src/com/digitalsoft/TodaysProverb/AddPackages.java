package com.digitalsoft.TodaysProverb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.xml.sax.SAXException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Toast;

import com.digitalsoft.TodaysProverb.Entities.Package;
import com.digitalsoft.TodaysProverb.Helpers.Config;
import com.digitalsoft.TodaysProverb.Helpers.DataBaseHelper;
import com.digitalsoft.TodaysProverb.Helpers.OptionsMenuHelper;
import com.digitalsoft.TodaysProverb.Helpers.XMLHelper;

public class AddPackages extends Activity
{
	private Activity context;
	private ExpandableListView elv;
	private ProgressDialog pd;
	private ArrayList<Package> packages = null;
	private ArrayList<Package> filterdPackages = null;
	private SimpleExpandableListAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addpackages);		
		
		context = this;
    	elv = (ExpandableListView) findViewById(R.id.expandableListView1);  
    	
		loadPackages();
		
		elv.setOnChildClickListener(new OnChildClickListener() 
		{	
			public boolean onChildClick(ExpandableListView arg0, View arg1, int arg2,int arg3, long arg4) 
			{
				Integer groupPosition = arg2;
				Integer childPosition = arg3;
				String childName = adapter.getChild(groupPosition, childPosition).toString();
				//clear the package name, because the child name will be like "{Sub Item=packageName}"
				//so we only want to get the packageName
				Integer indexOfEqualSign = childName.indexOf("=");
				String packageName = childName.substring(indexOfEqualSign + 1, childName.length() - 1);
				loadTexts(packageName);
				return false;
			}
		});

	}        
	
	private void setAdapter()
	{
		//filter packages to show only not installed packages
		filterdPackages = DataBaseHelper.filterNotInstalledPacakges(context, packages);	
		
		//create list of categories
	    ArrayList<String> categories = new ArrayList<String>();
	    for(Package p : filterdPackages)
	    {
	  		if (!categories.contains(p.getCategory()))
	  		{
	  			categories.add(p.getCategory());
	  		}
	    }
	    Collections.sort(categories);
	    
	    //create the groupList by converting the categories list to list of hashmap because the adapter doesn't accept list of string
	    ArrayList<HashMap<String, String>> groupList = new ArrayList<HashMap<String, String>>();
	    for(String c : categories)
	    {
	  		HashMap<String, String> m = new HashMap<String, String>();
	  	    m.put("Group Item", c); // the key and it's value.
	  		groupList.add(m); 	
	    }
	    
	    //create the childList
		ArrayList<ArrayList<HashMap<String, String>>> childList = new ArrayList<ArrayList<HashMap<String, String>>>();
	    for(int i = 0; i < categories.size(); i++)
	    {  
	    	childList.add(new ArrayList<HashMap<String, String>>());
	    }
		for(Package p : filterdPackages)
	    {
			HashMap<String, String> child = new HashMap<String, String>();
    		child.put("Sub Item", p.getPackageName()); 
			int index = categories.indexOf(p.getCategory());
			childList.get(index).add(child);
	    } 	 
		
	    //create the adapter
		adapter = new SimpleExpandableListAdapter
		(
				context,
				groupList, 				// group List.
				R.layout.expandable_list_view_group_row,				// Group item layout XML.			
				new String[] { "Group Item" },	// the key of group item.
				new int[] { R.id.groupRow_textView },	// ID of each group item.-Data under the key goes into this TextView.					
				childList,				// childData describes second-level entries.
				R.layout.expandable_list_view_child_row,				// Layout for sub-level entries(second level).
				new String[] {"Sub Item"},		// Keys in childData maps to display.
				new int[] { R.id.childRow_textView}		// Data under the keys above go into these TextViews.
        );
    	elv.setAdapter(adapter);		// setting the adapter in the list.    
	}
	
	private void loadPackages()
    {
		class Worker extends AsyncTask<Void, Void, Void> 
	    {
	        private Boolean connetionValid = true;
	        private Boolean xmlValid = true;
	        protected void onPreExecute() 
	        {
	        	pd = new ProgressDialog(context);
	            pd.setMessage(getResources().getText(R.string.waitMessage));
	            pd.setCanceledOnTouchOutside(false);
	            pd.show();
	        }
	        protected Void doInBackground(Void... args) 
	        {
		        try 
		        {
		        	packages = XMLHelper.getPackages(Config.url + "packages.xml");	
				} 
		        catch (IOException e) 
				{
		            connetionValid = false;
				}
		        catch (SAXException e) 
		        {
					xmlValid = false;
				}
		        return null;
	        }
	        protected void onPostExecute(Void unused) 
	        {
	            pd.dismiss();        
	            if (connetionValid & xmlValid)
	            {
	            	if (packages != null)
	            	{
	                	setAdapter();      		
	            	}
	            }
	            else if (!connetionValid)
	            {
	            	Toast.makeText(context, context.getResources().getText(R.string.noConnectionMessage), Toast.LENGTH_SHORT).show();
	            }
	            else if (!xmlValid)
	            {
	            	Toast.makeText(context, context.getResources().getString(R.string.invalidXmlMassage), Toast.LENGTH_SHORT).show();
	            }
	        }  
	    }
		new Worker().execute();
    }  
	
	private void loadTexts(final String packageName)
    {
		class Worker extends AsyncTask<Void, Void, Void> 
	    {
	        private Boolean connetionValid = true;
	        private Boolean xmlValid = true;
	        private ArrayList<String> texts = null;
	        private String tableName;
	        protected void onPreExecute() 
	        {
				tableName = XMLHelper.getTableName(filterdPackages, packageName);
	        	pd = new ProgressDialog(context);
	            pd.setMessage(getResources().getText(R.string.waitMessage));
	            pd.setCanceledOnTouchOutside(false);
	            pd.show();
	        }
	        protected Void doInBackground(Void... args) 
	        {
		        try 
		        {
		        	texts = XMLHelper.getElementsByTagName(Config.url + tableName + ".xml", "text");	
		        	String category = XMLHelper.getCategory(filterdPackages, packageName);
            		DataBaseHelper.installPackage(context, tableName, packageName, category, texts);
				} 
		        catch (IOException e) 
				{
		            connetionValid = false;
				}
		        catch (SAXException e) 
		        {
					xmlValid = false;
				}
		        return null;
	        }
	        protected void onPostExecute(Void unused) 
	        {
	            pd.dismiss();        
	            if (connetionValid & xmlValid)
	            {
	            	if (texts != null)
	            	{
	            		setAdapter();
	    				Toast.makeText(context, context.getResources().getString(R.string.packageInstalledSuccessfullyMessage), Toast.LENGTH_SHORT).show();
	            	}
	            }
	            else if (!connetionValid)
	            {
	            	Toast.makeText(context, context.getResources().getText(R.string.noConnectionMessage), Toast.LENGTH_SHORT).show();
	            }
	            else if (!xmlValid)
	            {
	            	Toast.makeText(context, context.getResources().getString(R.string.invalidXmlMassage), Toast.LENGTH_SHORT).show();
	            }
	        }  
	    }
		new Worker().execute();
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
    

	// to prevent a force close problem when the screen rotate during progress dialog working
	@Override
    protected void onDestroy()
    {
        super.onDestroy();
        pd.dismiss();
    }
}
