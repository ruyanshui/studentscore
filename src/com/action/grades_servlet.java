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
import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

import com.dao.DB;
import com.orm.TAdmin;
import com.orm.Tgrades;
import com.service.liuService;

public class grades_servlet extends HttpServlet {
	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String type = req.getParameter("type");

		if (type.endsWith("gradesMana")) {
			gradesMana(req, res);
		}
		if (type.endsWith("gradesAdd")) {
			gradesAdd(req, res);
		}
		if (type.endsWith("gradesDel")) {
			gradesDel(req, res);
		}
	}

	public void gradesAdd(HttpServletRequest req, HttpServletResponse res) {
		int stu_id = Integer.parseInt(req.getParameter("stu_id"));
		int course_id = Integer.parseInt(req.getParameter("course_id"));
		int grades = Integer.parseInt(req.getParameter("grades"));
		String xuenian = req.getParameter("xuenian");
		String del = "no";
		String sql = "insert into t_grades values(?,?,?,?,?,?)";
		Object[] params = { null, stu_id, course_id, grades, xuenian, del };
		DB mydb = new DB();
		mydb.doPstm(sql, params);
		mydb.closed();

		req.setAttribute("message", "操作成功");
		req.setAttribute("path", "grades?type=gradesMana");

		String targetURL = "/common/success.jsp";
		dispatch(targetURL, req, res);
	}

	public void gradesDel(HttpServletRequest req, HttpServletResponse res) {
		String sql = "update t_grades set del='yes' where id=" + Integer.parseInt(req.getParameter("id"));
		Object[] params = {};
		DB mydb = new DB();
		mydb.doPstm(sql, params);
		mydb.closed();

		req.setAttribute("message", "操作成功");
		req.setAttribute("path", "grades?type=gradesMana");

		String targetURL = "/common/success.jsp";
		dispatch(targetURL, req, res);
	}

	public void gradesMana(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String page1 = req.getParameter("page");
		if (page1 == null) {
			page1 = "1";
		}
		// 分页设置
		int EVERYPAGENUM = 10;// 每页条数
		int page = Integer.parseInt(page1); // 传递过来的当前页
		int cou = 1;// 得到信息总数
		int pagecount = 1; // 总页数
		//分页统计 个数
		String sql1 = "select count(*) as cou from t_grades" + " left join  t_stu on t_stu.id=t_grades.stu_id"
				+ " left join t_course on t_course.id=t_grades.course_id" + " "
						 
						+ "where t_grades.del='no' ";
		if (req.getParameter("name") != null)
			sql1 += " and( t_stu.code like '%" + req.getParameter("name").trim() + "%' " + " or t_stu.name1 like '%"
					+ req.getParameter("name").trim() + "%'   " + " or t_course.name like '%"
					+ req.getParameter("name").trim() + "%'  " + " or xuenian like '%" + req.getParameter("name").trim()
					+ "%'" +

			")";

		
		
		//页面显示使用
		String sql = "select * from t_grades" + " left join  t_stu on t_stu.id=t_grades.stu_id"
				+ " left join t_course on t_course.id=t_grades.course_id" +
 		" where t_grades.del='no' ";
	
		
		
		
		
		// 判断是否老师登录

		HttpSession session = req.getSession();
		TAdmin admin = (TAdmin) session.getAttribute("admin");
		String role = (String) session.getAttribute("role");
	 
		int userid = admin.getUserId();

		String username = admin.getUserName();

		if (role. equals( "2")) {
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
				// 老师登录只显示他负责的班级的学生的成绩
				sql1 += "    AND t_stu.classes_id='"+classes_id+"'";
				sql += "    AND t_stu.classes_id='"+classes_id+"'";
				}
		}

		if (role. equals( "3")) {
			  
			System.out.println(role);
			//	System.out.println(userid);
			//	System.out.println(username);
		// 学生登录只显示他 的成绩
			sql1 += "    AND t_stu.code='"+username+"'";
			sql += "    AND t_stu.code='"+username+"'";
			 
		}
	
		Object[] params1 = {};
		DB mydb1 = new DB();
		try {
			mydb1.doPstm(sql1, params1);
			ResultSet rs = mydb1.getRs();
			while (rs.next()) {
				cou = rs.getInt("cou");
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		mydb1.closed();

		if (cou % EVERYPAGENUM == 0) {
			pagecount = cou / EVERYPAGENUM;
		} else {
			pagecount = cou / EVERYPAGENUM + 1;
		}

		req.setAttribute("EVERYPAGENUM", EVERYPAGENUM);
		req.setAttribute("page", page);
		req.setAttribute("cou", cou);
		req.setAttribute("pagecount", pagecount);

		List gradesList = new ArrayList();

		if (req.getParameter("name") != null)
			sql += " and( t_stu.code like '%" + req.getParameter("name").trim() + "%' " + " or t_stu.name1 like '%"
					+ req.getParameter("name").trim() + "%'   " + " or t_course.name like '%"
					+ req.getParameter("name").trim() + "%'  " + " or xuenian like '%" + req.getParameter("name").trim()
					+ "%'" +

			")";
		sql += " order by t_grades.id desc";
		System.out.println(sql);
		Object[] params = {};
		DB mydb = new DB();
		try {
			mydb.doPstm(sql, params);
			ResultSet rs = mydb.getRs();
			for (int i = 0; i < (page - 1) * EVERYPAGENUM; i++) {
				rs.next();
			}
			for (int t = 0; t < EVERYPAGENUM; t++) {
				if (rs.next()) {
					Tgrades grades = new Tgrades();
					grades.setId(rs.getInt("id"));
					grades.setStu_id(rs.getInt("stu_id"));
					grades.setCourse_id(rs.getInt("course_id"));
					grades.setGrades(rs.getInt("grades"));
					grades.setXuenian(rs.getString("xuenian"));
					grades.setStu_code(liuService.getStuCode(rs.getInt("stu_id")));
					grades.setStu_codename(liuService.getStuCodeName(rs.getInt("stu_id")));
					grades.setCourse_name(liuService.getCourseName(rs.getInt("course_id")));
					gradesList.add(grades);
				} else {
					break; // 减少空循环的时间
				}
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		mydb.closed();

		req.setAttribute("gradesList", gradesList);
		req.getRequestDispatcher("admin/grades/gradesMana.jsp").forward(req, res);
	}

	public void dispatch(String targetURI, HttpServletRequest request, HttpServletResponse response) {
		RequestDispatcher dispatch = getServletContext().getRequestDispatcher(targetURI);
		try {
			dispatch.forward(request, response);
			return;
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	public void destroy() {

	}
}
