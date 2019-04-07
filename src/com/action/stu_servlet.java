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
import com.orm.Tstu;
import com.service.liuService;

public class stu_servlet extends HttpServlet
{
	public void service(HttpServletRequest req,HttpServletResponse res)throws ServletException, IOException 
	{
        String type=req.getParameter("type");
		
		if(type.endsWith("stuAdd"))
		{
			stuAdd(req, res);
		}
		if(type.endsWith("stuDel"))
		{
			stuDel(req, res);
		}
		if(type.endsWith("stuMana"))
		{
			stuMana(req, res);
		}
		if(type.endsWith("stuAll"))
		{
			stuAll(req, res);
		}
	}
	
	
	public void stuAdd(HttpServletRequest req,HttpServletResponse res)
	{
		String code=req.getParameter("code");
		String name1=req.getParameter("name1");
		String sex=req.getParameter("sex");
		int age=Integer.parseInt(req.getParameter("age"));
		int classes_id=Integer.parseInt(req.getParameter("classes_id"));
		String indate=req.getParameter("indate");
		String del="no";
		
		
		
		String userName=req.getParameter("code");
		String userPw=req.getParameter("code");
		String  role="3";

		String sql="insert into t_stu values(?,?,?,?,?,?,?,?)";
				String sql1="insert into t_admin values(?,?,?,?)";

		Object[] params={null,code,name1,sex,age,classes_id,indate,del};
			Object[] params1={null,userName,userPw,role};
	DB mydb=new DB();
		mydb.doPstm(sql, params);
			mydb.doPstm(sql1, params1);
	mydb.closed();
		
		req.setAttribute("message", "操作成功");
		req.setAttribute("path", "stu?type=stuMana");
		
        String targetURL = "/common/success.jsp";
		dispatch(targetURL, req, res);
	}
	
	public void stuDel(HttpServletRequest req,HttpServletResponse res)
	{
		String sql="update t_stu set del='yes' where id="+Integer.parseInt(req.getParameter("id"));
		Object[] params={};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		mydb.closed();
		
		req.setAttribute("message", "操作成功");
		req.setAttribute("path", "stu?type=stuMana");
		
        String targetURL = "/common/success.jsp";
		dispatch(targetURL, req, res);
	}

	public void stuMana(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException
	{
		List stuList=new ArrayList();
		String sql="select * from t_stu where del='no'";
		Object[] params={};
		DB mydb=new DB();
		try
		{
			mydb.doPstm(sql, params);
			ResultSet rs=mydb.getRs();
			while(rs.next())
			{
				Tstu stu=new Tstu();
				stu.setId(rs.getInt("id"));
				stu.setCode(rs.getString("code"));
				stu.setName1(rs.getString("name1"));
				stu.setSex(rs.getString("sex"));
				stu.setAge(rs.getInt("age"));
				stu.setClasses_id(rs.getInt("classes_id"));
				stu.setIndate(rs.getString("indate"));
				stu.setClasses_name(liuService.getClassesName(rs.getInt("classes_id")));
				stuList.add(stu);
		    }
			rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		mydb.closed();
		
		req.setAttribute("stuList", stuList);
		req.getRequestDispatcher("admin/stu/stuMana.jsp").forward(req, res);
	}
	
	public void stuAll(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException
	{
		List stuList=new ArrayList();
		String sql="select * from t_stu where del='no'";
		Object[] params={};
		DB mydb=new DB();
		try
		{
			mydb.doPstm(sql, params);
			ResultSet rs=mydb.getRs();
			while(rs.next())
			{
				Tstu stu=new Tstu();
				stu.setId(rs.getInt("id"));
				stu.setCode(rs.getString("code"));
				stu.setName1(rs.getString("name1"));
				stu.setSex(rs.getString("sex"));
				stu.setAge(rs.getInt("age"));
				stu.setClasses_id(rs.getInt("classes_id"));
				stu.setIndate(rs.getString("indate"));
				stu.setClasses_name(liuService.getClassesName(rs.getInt("classes_id")));
	
				stuList.add(stu);
		    }
			rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		mydb.closed();
		
		req.setAttribute("stuList", stuList);
		req.getRequestDispatcher("admin/stu_course/stuAll.jsp").forward(req, res);
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
