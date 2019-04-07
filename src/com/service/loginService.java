package com.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

import com.dao.DB;
import com.orm.TAdmin;
import com.orm.Tclasses;
import com.orm.Tcourse;
import com.orm.Tstu;
import com.orm.Tspecialty;

public class loginService {
	public String login(String userName, String userPw, String role
	// ,String yanzhengma
	) {
		System.out.println("userType" + role);

		try {
			Thread.sleep(700);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String result = "no";

		/*
		 * if(role.equals("1"))//系统管理员登陆 {
		 */
		WebContext ctx = WebContextFactory.get();
		HttpSession session = ctx.getSession();
		System.out.println("123");
		System.out.println(userName + userPw);
		String sql = "select * from t_admin where userName=? and userPw=? and role=?";
		Object[] params = { userName, userPw, role };
		DB mydb = new DB();
		mydb.doPstm(sql, params);
		try {
			ResultSet rs = mydb.getRs();
			boolean mark = (rs == null || !rs.next() ? false : true);
			if (mark == false) {
				result = "no";
			} else {
				result = "yes";
				TAdmin admin = new TAdmin();
				admin.setUserId(rs.getInt("userId"));
				admin.setUserName(rs.getString("userName"));
				admin.setUserPw(rs.getString("userPw"));
				session.setAttribute("role", role);
				session.setAttribute("admin", admin);
			}
			System.out.println(result);
			rs.close();
		} catch (SQLException e) {
			System.out.println("登录失败！");
			e.printStackTrace();
		} finally {
			mydb.closed();
		}

		/* } */

		/*
		 * if(userType==1) { } if(userType==2) {
		 * 
		 * }
		 */
		return result;
	}

	public String adminPwEdit(String userPwNew) {
		System.out.println("DDDD");
		try {
			Thread.sleep(700);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebContext ctx = WebContextFactory.get();
		HttpSession session = ctx.getSession();
		TAdmin admin = (TAdmin) session.getAttribute("admin");

		String sql = "update t_admin set userPw=? where userId=?";
		Object[] params = { userPwNew, admin.getUserId() };
		DB mydb = new DB();
		mydb.doPstm(sql, params);

		return "yes";
	}

	public List specialtyAll() {
		try {
			Thread.sleep(700);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List specialtyList = new ArrayList();
		String sql = "select * from t_specialty where del='no'";
		Object[] params = {};
		DB mydb = new DB();
		try {
			mydb.doPstm(sql, params);
			ResultSet rs = mydb.getRs();
			while (rs.next()) {
				Tspecialty specialty = new Tspecialty();
				specialty.setId(rs.getInt("id"));
				specialty.setName(rs.getString("name"));
				specialty.setJieshao(rs.getString("jieshao"));
				specialtyList.add(specialty);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		mydb.closed();
		return specialtyList;
	}

	public List classesAll() {
		try {
			Thread.sleep(700);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List classesList = new ArrayList();
		String sql = "select * from t_classes where del='no'";
		Object[] params = {};
		DB mydb = new DB();
		try {
			mydb.doPstm(sql, params);
			ResultSet rs = mydb.getRs();
			while (rs.next()) {
				Tclasses classes = new Tclasses();
				classes.setId(rs.getInt("id"));
				classes.setName(rs.getString("name"));
				classesList.add(classes);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		mydb.closed();
		return classesList;
	}

	public List stuAll() {
		try {
			Thread.sleep(700);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 判断是否老师登录

		WebContext ctx = WebContextFactory.get();
		HttpSession session = ctx.getSession();
		TAdmin admin = (TAdmin) session.getAttribute("admin");
		String role = (String) session.getAttribute("role");

		int userid = admin.getUserId();

		String username = admin.getUserName();

		String whereSQL="";
		if (role.equals("2")) {
			// 获得老师的班级
			String classes_id = "";
			String sql33 = "select classes_id from t_tea where del='no' and teanumb='" + username + "'";
			Object[] params = {};
			DB mydb = new DB();
			try {
				mydb.doPstm(sql33, params);
				ResultSet rs = mydb.getRs();
				while (rs.next()) {

					classes_id = (rs.getString("classes_id"));

				}
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			mydb.closed();
			if (classes_id != "") {
				// 老师登录只显示他负责的班级的学生 
				whereSQL += "    and    classes_id='" + classes_id + "'";
				
			}
		}

		List stuList = new ArrayList();
		String sql = "select * from t_stu where del='no' "+whereSQL;
		Object[] params = {};
		DB mydb = new DB();
		try {
			mydb.doPstm(sql, params);
			ResultSet rs = mydb.getRs();
			while (rs.next()) {
				Tstu stu = new Tstu();
				stu.setId(rs.getInt("id"));
				stu.setCode(rs.getString("code") + rs.getString("name1"));
				stuList.add(stu);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		mydb.closed();
		return stuList;
	}

	public List courseAll() {
		try {
			Thread.sleep(700);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List courseList = new ArrayList();
		String sql = "select * from t_course where del='no'";
		Object[] params = {};
		DB mydb = new DB();
		try {
			mydb.doPstm(sql, params);
			ResultSet rs = mydb.getRs();
			while (rs.next()) {
				Tcourse course = new Tcourse();
				course.setId(rs.getInt("id"));
				course.setName(rs.getString("name"));
				courseList.add(course);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		mydb.closed();
		return courseList;
	}

	public String xuankeAdd(int stu_id, int course_id) {
		try {
			Thread.sleep(700);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String result = "no";
		String sql = "select * from t_stu_course where stu_id=? and course_id=?";
		Object[] params = { stu_id, course_id };
		DB mydb = new DB();
		try {
			mydb.doPstm(sql, params);
			ResultSet rs = mydb.getRs();
			if (rs.next() == true) {
				result = "no";
			} else {
				result = "yes";
				String sql1 = "insert into t_stu_course values(?,?,?)";
				Object[] params1 = { null, stu_id, course_id };
				mydb.doPstm(sql1, params1);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		mydb.closed();
		return result;
	}

	public String xuankeDel(int stu_id, int course_id) {
		try {
			Thread.sleep(700);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String result = "no";
		String sql = "delete from t_stu_course where stu_id=? and course_id=?";
		Object[] params = { stu_id, course_id };
		DB mydb = new DB();
		try {
			mydb.doPstm(sql, params);
			int ii = mydb.getCount();
			if (ii == 1) {
				result = "yes";
			} else {
				result = "no";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mydb.closed();
		return result;
	}

}
