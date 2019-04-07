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
import com.orm.Tclasses;
import com.orm.Tspecialty;
import com.service.liuService;

public class classes_servlet extends HttpServlet
{
	public void service(HttpServletRequest req,HttpServletResponse res)throws ServletException, IOException 
	{
        String type=req.getParameter("type");
		
		if(type.endsWith("classesMana"))
		{
			classesMana(req, res);
		}
		if(type.endsWith("classesAdd"))
		{
			classesAdd(req, res);
		}
		if(type.endsWith("classesDel"))
		{
			classesDel(req, res);
		}
		if(type.endsWith("classesEdit"))
		{
			classesEdit(req, res);
		}
	}
	
	
	public void classesAdd(HttpServletRequest req,HttpServletResponse res)
	{
		String name=req.getParameter("name");
		String specialty_id=req.getParameter("specialty_id");
		String del="no";
		String sql="insert into t_classes values(?,?,?,?)";
		Object[] params={null,name,specialty_id,del};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		mydb.closed();
		
		req.setAttribute("message", "操作成功");
		req.setAttribute("path", "classes?type=classesMana");
		
        String targetURL = "/common/success.jsp";
		dispatch(targetURL, req, res);
	}
	
	public void classesDel(HttpServletRequest req,HttpServletResponse res)
	{
		String sql="update t_classes set del='yes' where id="+Integer.parseInt(req.getParameter("id"));
		Object[] params={};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		mydb.closed();
		
		req.setAttribute("message", "操作成功");
		req.setAttribute("path", "classes?type=classesMana");
		
        String targetURL = "/common/success.jsp";
		dispatch(targetURL, req, res);
	}

	public void classesMana(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException
	{
		List classesList=new ArrayList();
		String sql="select * from t_classes where del='no' order by specialty_id";
		Object[] params={};
		DB mydb=new DB();
		try
		{
			mydb.doPstm(sql, params);
			ResultSet rs=mydb.getRs();
			while(rs.next())
			{
				Tclasses classes=new Tclasses();
				classes.setId(rs.getInt("id"));
				classes.setName(rs.getString("name"));
				classes.setSpecialty_id(rs.getInt("specialty_id"));
				classes.setSpecialty_name(liuService.getSpecialtyName(rs.getInt("specialty_id")));
				classesList.add(classes);
		    }
			rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		mydb.closed();
		
		req.setAttribute("classesList", classesList);
		req.getRequestDispatcher("admin/classes/classesMana.jsp").forward(req, res);
	}
	
	public void classesEdit(HttpServletRequest req,HttpServletResponse res)
	{
		String name=req.getParameter("name");
		String specialty_id=req.getParameter("specialty_id");
		
		
		String sql="update t_classes set name=?,specialty_id=? where id="+Integer.parseInt(req.getParameter("id"));
		Object[] params={name,specialty_id};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		mydb.closed();
		
		req.setAttribute("message", "操作成功");
		req.setAttribute("path", "classes?type=classesMana");
		
        String targetURL = "/common/success.jsp";
		dispatch(targetURL, req, res);
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
