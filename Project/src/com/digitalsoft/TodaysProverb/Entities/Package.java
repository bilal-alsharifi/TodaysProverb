package com.digitalsoft.TodaysProverb.Entities;

public class Package 
{
	private String tableName;
	private String packageName;
	private String category;
	public Package()
	{
		
	}
	public Package(String tableName, String packageName, String category)
	{
		this.tableName = tableName;
		this.packageName = packageName;
		this.category = category;
	}
	public void setTableName(String tableName)
	{
		this.tableName = tableName;
	}
	public void setPackageName(String packageName)
	{
		this.packageName = packageName;
	}
	public void setCategory(String category)
	{
		this.category = category;
	}
	public String getTableName()
	{
		return this.tableName;
	}
	public String getPackageName()
	{
		return this.packageName;
	}
	public String getCategory()
	{
		return this.category;
	}

}
