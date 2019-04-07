package com.orm;

public class Ttea {
	private int id;
	private String teanumb;
	private String name;
	private String sex;
	private int age;
	private int classes_id;
	private String classes_name;

	public int getClasses_id() {
		return classes_id;
	}

	public void setClasses_id(int classes_id) {
		this.classes_id = classes_id;
	}


	public String getClasses_name()
	{
		return classes_name;
	}

	public void setClasses_name(String classes_name)
	{
		this.classes_name = classes_name;
	}
	
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getTeanumb() {
		return teanumb;
	}

	public void setTeanumb(String teanumb) {
		this.teanumb = teanumb;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

}
