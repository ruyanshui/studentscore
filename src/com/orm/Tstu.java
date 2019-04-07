
package com.orm;

public class Tstu
{
	private int id;
	private String code;
	private String name1;
	private String sex;
	private int age;
    private int classes_id;
    private String indate;
    
    private String classes_name;

	public int getAge()
	{
		return age;
	}

	public void setAge(int age)
	{
		this.age = age;
	}


	public String getClasses_name()
	{
		return classes_name;
	}

	public void setClasses_name(String classes_name)
	{
		this.classes_name = classes_name;
	}

	public int getClasses_id()
	{
		return classes_id;
	}

	public void setClasses_id(int classes_id)
	{
		this.classes_id = classes_id;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName1()
	{
		return name1;
	}

	public void setName1(String name1)
	{
		this.name1 = name1;
	}

	public String getIndate()
	{
		return indate;
	}

	public void setIndate(String indate)
	{
		this.indate = indate;
	}

	public String getSex()
	{
		return sex;
	}

	public void setSex(String sex)
	{
		this.sex = sex;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}
    
}
