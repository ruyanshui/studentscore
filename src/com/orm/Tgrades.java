
package com.orm;

public class Tgrades
{
	private int id;
	private int stu_id;
	private int course_id;
    private int grades;
	private String xuenian;
	private String stu_code;
	private String stu_codename;
	private String course_name;
	
	public int getGrades()
	{
		return grades;
	}
	public void setGrades(int grades)
	{
		this.grades = grades;
	}
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public int getStu_id()
	{
		return stu_id;
	}
	public void setStu_id(int stu_id)
	{
		this.stu_id = stu_id;
	}
	public String getXuenian()
	{
		return xuenian;
	}
	public void setXuenian(String xuenian)
	{
		this.xuenian = xuenian;
	}
	
	public String getCourse_name()
	{
		return course_name;
	}
	public void setCourse_name(String course_name)
	{
		this.course_name = course_name;
	}
	public int getCourse_id()
	{
		return course_id;
	}
	public void setCourse_id(int course_id)
	{
		this.course_id = course_id;
	}
	public String getStu_code()
	{
		return stu_code;
	}
	public void setStu_code(String stu_code)
	{
		this.stu_code = stu_code;
	}
	
	public String getStu_codename()
	{
		return stu_codename;
	}
	public void setStu_codename(String stu_codename)
	{
		this.stu_codename = stu_codename;
	}
	
}
