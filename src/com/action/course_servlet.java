package com.action;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.DB;
import com.orm.TAdmin;
import com.orm.Tcourse;
import com.service.liuService;

public class course_servlet extends HttpServlet
{
	public void service(HttpServletRequest req,HttpServletResponse res)throws ServletException, IOException 
	{
        String type=req.getParameter("type");
		
		if(type.endsWith("courseMana"))
		{
			courseMana(req, res);
		}
		if(type.endsWith("courseAdd"))
		{
			courseAdd(req, res);
		}
		if(type.endsWith("courseDel"))
		{
			courseDel(req, res);
		}
		if(type.endsWith("courseEdit"))
		{
			courseEdit(req, res);
		}
		
		if(type.endsWith("courseAll"))
		{
			courseAll(req, res);
		}
		if(type.endsWith("courseByStu"))
		{
			courseByStu(req, res);
		}
	}
	
	
	public void courseAdd(HttpServletRequest req,HttpServletResponse res)
	{
		String name=req.getParameter("name");
		String jieshao=req.getParameter("jieshao");
		String del="no";
		String sql="insert into t_course values(?,?,?,?)";
		Object[] params={null,name,jieshao,del};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		mydb.closed();
		
		req.setAttribute("message", "操作成功");
		req.setAttribute("path", "course?type=courseMana");
		
        String targetURL = "/common/success.jsp";
		dispatch(targetURL, req, res);
	}
	
	public void courseDel(HttpServletRequest req,HttpServletResponse res)
	{
		String sql="update t_course set del='yes' where id="+Integer.parseInt(req.getParameter("id"));
		Object[] params={};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		mydb.closed();
		
		req.setAttribute("message", "操作成功");
		req.setAttribute("path", "course?type=courseMana");
		
        String targetURL = "/common/success.jsp";
		dispatch(targetURL, req, res);
	}

	public void courseMana(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException
	{
		List courseList=new ArrayList();
		String sql="select * from t_course where del='no'";
		Object[] params={};
		DB mydb=new DB();
		try
		{
			mydb.doPstm(sql, params);
			ResultSet rs=mydb.getRs();
			while(rs.next())
			{
				Tcourse course=new Tcourse();
				course.setId(rs.getInt("id"));
				course.setName(rs.getString("name"));
				course.setJieshao(rs.getString("jieshao"));
				courseList.add(course);
		    }
			rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		mydb.closed();
		
		req.setAttribute("courseList", courseList);
		req.getRequestDispatcher("admin/course/courseMana.jsp").forward(req, res);
	}
	
	
	public void courseEdit(HttpServletRequest req,HttpServletResponse res)
	{
		String name=req.getParameter("name");
		String jieshao=req.getParameter("jieshao");

		String sql="update t_course set name=?,jieshao=? where id="+Integer.parseInt(req.getParameter("id"));
		Object[] params={name,jieshao};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		mydb.closed();
		
		req.setAttribute("message", "操作成功");
		req.setAttribute("path", "course?type=courseMana");
		
        String targetURL = "/common/success.jsp";
		dispatch(targetURL, req, res);
	}
	
	
	public void courseAll(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException
	{
		List courseList=new ArrayList();
		String sql="select * from t_course where del='no'";
		Object[] params={};
		DB mydb=new DB();
		try
		{
			mydb.doPstm(sql, params);
			ResultSet rs=mydb.getRs();
			while(rs.next())
			{
				Tcourse course=new Tcourse();
				course.setId(rs.getInt("id"));
				course.setName(rs.getString("name"));
				course.setJieshao(rs.getString("jieshao"));
				courseList.add(course);
		    }
			rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		mydb.closed();
		
		req.setAttribute("courseList", courseList);
		req.setAttribute("stu_id", req.getParameter("stu_id"));
		req.getRequestDispatcher("admin/stu_course/courseAll.jsp").forward(req, res);
	}
	
	public void courseByStu(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException
	{
		int stu_id=Integer.parseInt(req.getParameter("stu_id"));
		
		List courseList=new ArrayList();
		
		String sql="select * from t_stu_course where stu_id=?";
		Object[] params={stu_id};
		DB mydb=new DB();
		try
		{
			mydb.doPstm(sql, params);
			ResultSet rs=mydb.getRs();
			while(rs.next())
			{
				Tcourse course=new Tcourse();
				course.setId(rs.getInt("course_id"));
				course.setName(liuService.getCourseName(rs.getInt("course_id")));
				courseList.add(course);
		    }
			rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		mydb.closed();
		System.out.println(courseList.size());
		req.setAttribute("courseList", courseList);
		req.setAttribute("stu_id", stu_id);
		req.getRequestDispatcher("admin/stu_course/courseByStu.jsp").forward(req, res);
	}
	
	public void dispatch(String targetURI,HttpServletRequest request,HttpServletResponse response) 
	{
		RequestDispatcher dispatch = getServletContext().getRequestDispatcher(targetURI);
		try 
		{
		    dispatch.forward(request, response);
		    return;
		} 
		catch (ServletException e) 
		{
                    e.printStackTrace();
		} 
		catch (IOException e) 
		{
			
		    e.printStackTrace();
		}
	}
	public void init(ServletConfig config) throws ServletException 
	{
		super.init(config);
	}
	
	public void destroy() 
	{
		
	}
}
