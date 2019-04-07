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
import com.orm.Tspecialty;

public class specialty_servlet extends HttpServlet
{
	public void service(HttpServletRequest req,HttpServletResponse res)throws ServletException, IOException 
	{
        String type=req.getParameter("type");
		
		if(type.endsWith("specialtyMana"))
		{
			specialtyMana(req, res);
		}
		if(type.endsWith("specialtyAdd"))
		{
			specialtyAdd(req, res);
		}
		if(type.endsWith("specialtyDel"))
		{
			specialtyDel(req, res);
		}
		if(type.endsWith("specialtyEdit"))
		{
			specialtyEdit(req, res);
		}
	}
	
	public void specialtyAdd(HttpServletRequest req,HttpServletResponse res)
	{
		String name=req.getParameter("name");
		String jieshao=req.getParameter("jieshao");
		String del="no";
		String sql="insert into t_specialty values(?,?,?,?)";
		Object[] params={null,name,jieshao,del};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		mydb.closed();
		
		req.setAttribute("message", "操作成功");
		req.setAttribute("path", "specialty?type=specialtyMana");
		
        String targetURL = "/common/success.jsp";
		dispatch(targetURL, req, res);
	}
	

	public void specialtyMana(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException
	{
		List specialtyList=new ArrayList();
		String sql="select * from t_specialty where del='no'";
		Object[] params={};
		DB mydb=new DB();
		try
		{
			mydb.doPstm(sql, params);
			ResultSet rs=mydb.getRs();
			while(rs.next())
			{
				Tspecialty specialty=new Tspecialty();
				specialty.setId(rs.getInt("id"));
				specialty.setName(rs.getString("name"));
				specialty.setJieshao(rs.getString("jieshao"));
				specialtyList.add(specialty);
		    }
			rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		mydb.closed();
		
		req.setAttribute("specialtyList", specialtyList);
		req.getRequestDispatcher("admin/specialty/specialtyMana.jsp").forward(req, res);
	}
	
	public void specialtyEdit(HttpServletRequest req,HttpServletResponse res)
	{
		String name=req.getParameter("name");
		String jieshao=req.getParameter("jieshao");
		
		String sql="update t_specialty set name=?,jieshao=? where id="+Integer.parseInt(req.getParameter("id"));
		Object[] params={name,jieshao};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		mydb.closed();
		
		req.setAttribute("message", "操作成功");
		req.setAttribute("path", "specialty?type=specialtyMana");
		
        String targetURL = "/common/success.jsp";
		dispatch(targetURL, req, res);
	}
	
	
	public void specialtyDel(HttpServletRequest req,HttpServletResponse res)
	{
		String sql="update t_specialty set del='yes' where id="+Integer.parseInt(req.getParameter("id"));
		Object[] params={};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		mydb.closed();
		
		req.setAttribute("message", "操作成功");
		req.setAttribute("path", "specialty?type=specialtyMana");
		
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
