package com.service;

import java.sql.ResultSet;

import com.dao.DB;
import com.orm.Tspecialty;

public class liuService
{
	public static String getSpecialtyName(int id)
	{
		String specialty_name="";
		
		String sql="select * from t_specialty where id="+id;
		Object[] params={};
		DB mydb=new DB();
		try
		{
			mydb.doPstm(sql, params);
			ResultSet rs=mydb.getRs();
			rs.next();
			specialty_name=rs.getString("name");
			rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		mydb.closed();
		return specialty_name;
	}
	
	public static String getClassesName(int id)
	{
		String name="";
		
		String sql="select * from t_classes where id="+id;
		Object[] params={};
		DB mydb=new DB();
		try
		{
			mydb.doPstm(sql, params);
			ResultSet rs=mydb.getRs();
			rs.next();
			name=rs.getString("name");
			rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		mydb.closed();
		return name;
	}
	
	public static String getStuCodeName(int id)
	{
		String code="";
		
		String sql="select * from t_stu where id="+id;
		Object[] params={};
		DB mydb=new DB();
		try
		{
			mydb.doPstm(sql, params);
			ResultSet rs=mydb.getRs();
			rs.next();
			code=rs.getString("code")+"("+rs.getString("name1")+")";
			rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		mydb.closed();
		return code;
	}
	public static String getJgnumb(String stu_id,String xuenian)
	{
		 
		String numb="0";
		String sql="select count(*) as numb from t_grades where stu_id='"+stu_id+"' and xuenian='"+xuenian+"' and grades>60";
		Object[] params={};
		DB mydb=new DB();
		try
		{
			mydb.doPstm(sql, params);
			ResultSet rs=mydb.getRs();
			rs.next();
			 numb=rs.getString("numb");
			rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		mydb.closed();
		return numb;
	}	
	public static String getBjgnumb(String stu_id,String xuenian)
	{
		 
		String numb="0";
		String sql="select count(*) as numb from t_grades where stu_id='"+stu_id+"' and xuenian='"+xuenian+"' and grades<60";
		Object[] params={};
		DB mydb=new DB();
		try
		{
			mydb.doPstm(sql, params);
			ResultSet rs=mydb.getRs();
			rs.next();
			 numb=rs.getString("numb");
			rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		mydb.closed();
		return numb;
	}	
	
	
	public static String getStuCode(int id)
	{
		String code="";
		
		String sql="select * from t_stu where id="+id;
		Object[] params={};
		DB mydb=new DB();
		try
		{
			mydb.doPstm(sql, params);
			ResultSet rs=mydb.getRs();
			rs.next();
			code=rs.getString("code") ;
			rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		mydb.closed();
		return code;
	}
	
	public static String getCourseName(int id)
	{
		String name="";
		
		String sql="select * from t_course where id="+id;
		Object[] params={};
		DB mydb=new DB();
		try
		{
			mydb.doPstm(sql, params);
			ResultSet rs=mydb.getRs();
			rs.next();
			name=rs.getString("name");
			rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		mydb.closed();
		return name;
	}

}
