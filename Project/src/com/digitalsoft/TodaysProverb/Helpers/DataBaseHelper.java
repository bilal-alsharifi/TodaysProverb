package com.digitalsoft.TodaysProverb.Helpers;


import java.util.ArrayList;

import com.digitalsoft.TodaysProverb.R;
import com.digitalsoft.TodaysProverb.Entities.Package;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;

public class DataBaseHelper 
{
	private static SQLiteDatabase db;
	private static String dbName = "database";
	private static String packagesTableName = "packages";
	private static int mode = Context.MODE_PRIVATE;
	public static void addPackage(Context context, String tableName, String packageName, String category)
	{
		db = context.openOrCreateDatabase(dbName, mode, null);
		db.execSQL("CREATE TABLE IF NOT EXISTS " + tableName + " (_id INTEGER PRIMARY KEY, text TEXT NOT NULL);");
		db.execSQL("CREATE TABLE IF NOT EXISTS " + packagesTableName + " (_table TEXT PRIMARY KEY, package TEXT NOT NULL, category TEXT NOT NULL);");
		ContentValues values = new ContentValues();
        values.put("_table", tableName);
        values.put("package", packageName);
        values.put("category", category);
        db.insert(packagesTableName, null, values);
		db.close();
	}
	public static void addText(Context context, String table, String text)
	{
		db = context.openOrCreateDatabase(dbName, mode, null);
        ContentValues values = new ContentValues();
        values.put("text", text);
        db.insert(table, null, values);
        db.close();       
	}
	public static void installPackage(Context context, String tableName, String packageName, String category, ArrayList<String> texts)
	{
		addPackage(context, tableName, packageName, category);
		for(String text : texts)
		{
			addText(context, tableName, text);
		}		
	}
	public static void uninstallPackage(Context context, String tableName)
	{
		db = context.openOrCreateDatabase(dbName, mode, null);	 
		db.execSQL("DROP TABLE " + tableName + ";");
		db.delete(packagesTableName, "_table = ?", new String[] { tableName });
		db.close();
	}
	public static Boolean tableExists(Context context, String tableName)
	{
		Boolean result = false;
		tableName =  "'" + tableName + "'";
		db = context.openOrCreateDatabase(dbName, mode, null);
	    Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=" + tableName + ";", null);  
	    if(cursor.getCount() > 0)
	    {
	    	result = true; 
	    }
	    cursor.close();
	    db.close();
	    return result;   
	}
	public static ArrayList<Package> filterNotInstalledPacakges(Context context, ArrayList<Package> packages)
	{
		ArrayList<Package> result = new ArrayList<Package>();
		for(Package pkg : packages)
		{
			String tableName = pkg.getTableName();
			if (!tableExists(context, tableName))
			{
				result.add(pkg);
			}		
		}	
		return result;
	}
	public static ArrayList<String> getAllStrings(Context context, String attribute, String tableName)
	{
		ArrayList<String> result = new ArrayList<String>();
		if(tableExists(context, tableName))
		{
			db = context.openOrCreateDatabase(dbName, mode, null); 
			Cursor cursor = db.rawQuery("SELECT * FROM " + tableName + ";", null);  
		    if(cursor.getCount() > 0)
		    {
		    	cursor.moveToFirst();
		 	    do
		 	    {
		 	    	result.add(cursor.getString(cursor.getColumnIndex(attribute)));
		 	    }
		 	    while(cursor.moveToNext());  
		    }
		    cursor.close();
		}
	    db.close();
	    return result;
	}
	public static ArrayList<String> getAllTexts(Context context, String tableName)
	{
	    return getAllStrings(context, "text", tableName);
	}
	public static ArrayList<String> getAllPackages(Context context)
	{
	    return getAllStrings(context, "package", packagesTableName);
	}
	public static String getRandomText(Context context, String tableName)
	{
		String result;
		db = context.openOrCreateDatabase(dbName, mode, null);
	    Cursor cursor = db.rawQuery("SELECT * FROM " + tableName + " ORDER BY RANDOM() LIMIT 1;", null);
	    if(cursor.getCount() > 0)
	    {
	    	cursor.moveToFirst();
		    result = cursor.getString(cursor.getColumnIndex("text")); 
	    }
	    else
	    {
	    	result = context.getResources().getString(R.string.emptyPackage);
	    }    
	    cursor.close();
	    db.close();
	    return result;
	}
	public static String getTableName(Context context, String packageName)
	{
		String result;
		packageName = "'" + packageName + "'";
		db = context.openOrCreateDatabase(dbName, mode, null);
	    Cursor cursor = db.rawQuery("SELECT * FROM " + packagesTableName + " WHERE package=" + packageName + ";", null);
	    if(cursor.getCount() > 0)
	    {
	    	cursor.moveToFirst();
		    result = cursor.getString(cursor.getColumnIndex("_table")); 
	    }
	    else
	    {
	    	result = null;
	    }    
	    cursor.close();
	    db.close();
	    return result;
	}
	public static String getPackageName(Context context, String tableName)
	{
		String result;
		tableName = "'" + tableName + "'";
		db = context.openOrCreateDatabase(dbName, mode, null);
	    Cursor cursor = db.rawQuery("SELECT * FROM " + packagesTableName + " WHERE _table=" + tableName + ";", null);
	    if(cursor.getCount() > 0)
	    {
	    	cursor.moveToFirst();
		    result = cursor.getString(cursor.getColumnIndex("package")); 
	    }
	    else
	    {
	    	result = null;
	    }    
	    cursor.close();
	    db.close();
	    return result;
	}
	public static Integer getTextID(Context context, String tableName, String text)
	{
		Integer result;
		text = "'" + text + "'";
		db = context.openOrCreateDatabase(dbName, mode, null);
	    Cursor cursor = db.rawQuery("SELECT * FROM " + tableName + " WHERE text=" + text + ";", null);
	    if(cursor.getCount() > 0)
	    {
	    	cursor.moveToFirst();
		    result = cursor.getInt(cursor.getColumnIndex("_id")); 
	    }
	    else
	    {
	    	result = null;
	    }    
	    cursor.close();
	    db.close();
	    return result;
	}
	public static String getText(Context context, String tableName, Integer id)
	{
		String result;
		db = context.openOrCreateDatabase(dbName, mode, null);
	    Cursor cursor = db.rawQuery("SELECT * FROM " + tableName + " WHERE _id=" + id + ";", null);
	    if(cursor.getCount() > 0)
	    {
	    	cursor.moveToFirst();
		    result = cursor.getString(cursor.getColumnIndex("text")); 
	    }
	    else
	    {
	    	result = null;
	    }    
	    cursor.close();
	    db.close();
	    return result;
	}
	public static void removeText(Context context, String tableName, Integer textID)
	{
		db = context.openOrCreateDatabase(dbName, mode, null);	 
		db.delete(tableName, "_id = ?", new String[] { textID.toString() });
		db.close();
	}
	public static void removeText(Context context, String tableName, String text)
	{
		db = context.openOrCreateDatabase(dbName, mode, null);	 
		db.delete(tableName, "text = ?", new String[] { text });
		db.close();
	}
	public static void editText(Context context, String tableName, Integer textID, String newText)
	{
		db = context.openOrCreateDatabase(dbName, mode, null);
	    ContentValues values = new ContentValues();
	    values.put("text", newText);
		db.update(tableName, values, "_id = ?", new String[] { textID.toString() });
		db.close();
	}
}
