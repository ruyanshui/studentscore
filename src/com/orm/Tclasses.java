package com.orm;

public class Tclasses
{
	private int id;
	private String name;
	private int specialty_id;
	private String del;
	
	private String specialty_name;
	public String getDel()
	{
		return del;
	}
	public void setDel(String del)
	{
		this.del = del;
	}
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	
	public int getSpecialty_id()
	{
		return specialty_id;
	}
	public void setSpecialty_id(int specialty_id)
	{
		this.specialty_id = specialty_id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getSpecialty_name()
	{
		return specialty_name;
	}
	public void setSpecialty_name(String specialty_name)
	{
		this.specialty_name = specialty_name;
	}

}
