package com.action;

import java.io.IOException;
import java.sql.ResultSet;
import java.text.DecimalFormat;
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
import com.orm.TTongji;
import com.orm.Tgrades;
import com.service.liuService;

public class tongji_servlet extends HttpServlet {
	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String type = req.getParameter("type");

		if (type.endsWith("tongjiDengji")) {
			tongjiDengji(req, res);
		}
		if (type.endsWith("tongjiPingjun")) {
			tongjiPingjun(req, res);
		}
		if (type.endsWith("tongjiYouxiu")) {
			tongjiYouxiu(req, res);
		}
		if (type.endsWith("tongjiZhong")) {
			tongjiZhong(req, res);
		}

	}

	public void tongjiDengji(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		String dateinfo1 = "";// 开始日期
		String dateinfo2 = "";// 结束日期

		dateinfo1 = req.getParameter("dateinfo1");
		dateinfo2 = req.getParameter("dateinfo2");

		String wheresql = "";

		String dateList = "";// 日期列表
		String dengjiList = "";// 成绩 等级

		if (dateinfo1 != null && dateinfo2 != null) {
			wheresql = " where xuenian >'" + dateinfo1 + "' and xuenian <'" + dateinfo2 + "' ";
			if (req.getParameter("class_id") != null && !req.getParameter("class_id").equals("0")) {
				wheresql += "  and t_stu.classes_id=  '" + req.getParameter("class_id") + "'";
			}

			if (req.getParameter("course_id") != null && !req.getParameter("course_id").equals("0")) {
				wheresql += "  and t_grades.course_id=  '" + req.getParameter("course_id") + "'";
			}
			String sql = "  ";
			Object[] params = {};
			DB mydb = new DB();

			// 2成绩等级饼状
			// 搜索时间范围内的成绩
			// 按等级计算个数,生成饼图,
			sql = "    SELECT sum( if( grades > 90, 1, 0)) as youxiu ,sum( if( grades < 90 && grades>80, 1, 0)) as lianghao "
					+ ",sum( if( grades < 80 && grades>70, 1, 0)) as zhongdeng "
					+ ",sum( if( grades < 70 && grades>60, 1, 0)) as jige "
					+ ",sum( if( grades < 60  , 1, 0)) as bujige"
					+ " FROM `t_grades` left join t_course on t_course.id=t_grades.course_id "
					+ " left join t_stu on t_stu.id=t_grades.stu_id " + "" + " " + wheresql;

			try {
				mydb.doPstm(sql, params);
				ResultSet rs = mydb.getRs();
				while (rs.next()) {
					dengjiList += "{value:\"" + rs.getString("youxiu") + "\",name:\"优秀(90分以上)\"},";
					dengjiList += "{value:\"" + rs.getString("lianghao") + "\",name:\"良好(80分以上)\"},";
					dengjiList += "{value:\"" + rs.getString("zhongdeng") + "\",name:\"中等(70分以上)\"},";
					dengjiList += "{value:\"" + rs.getString("jige") + "\",name:\"及格(60分以上)\"},";
					dengjiList += "{value:\"" + rs.getString("bujige") + "\",name:\"不及格(60分以下)\"},";

				}
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			req.setAttribute("dateinfo", dateinfo1 + "到" + dateinfo2);
			dengjiList = dengjiList.substring(0, dengjiList.length() - 1);// 删除多余的逗号
			req.setAttribute("dengjiList", "[" + dengjiList + "]");

		} else {
			req.setAttribute("dateinfo", "");
			req.setAttribute("dengjiList", "[]");

		}

		req.getRequestDispatcher("admin/tongji/tongjiDengji.jsp").forward(req, res);
	}

	public void tongjiPingjun(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		String dateinfo1 = "";// 开始日期
		String dateinfo2 = "";// 结束日期

		dateinfo1 = req.getParameter("dateinfo1");
		dateinfo2 = req.getParameter("dateinfo2");

		String wheresql = "";

		List pingjunList = new ArrayList();// 平均成绩

		String dateList = "";// 日期列表

		if (dateinfo1 != "" && dateinfo2 != "") {
			wheresql = " where xuenian >'" + dateinfo1 + "' and xuenian <'" + dateinfo2 + "' ";
			if (req.getParameter("class_id") != null && !req.getParameter("class_id").equals("0")) {
				wheresql += "  and t_stu.classes_id=  '" + req.getParameter("class_id") + "'";
			}

			// 1平均成绩
			// 搜索时间范围内的成绩
			// 以时间分组排序,
			// 统计成绩的个数和总和,计算同相日期内的平均成绩
			String sql = "";

			sql = "SELECT * from "
					+ " (SELECT count(t_grades.id),sum(t_grades.grades),sum(grades)/count(t_grades.id) as punjun,t_course.name FROM `t_grades`   "
					+ "left join t_course on t_course.id=t_grades.course_id "
					+ "left join t_stu on t_stu.id=t_grades.stu_id "

					+ wheresql + "		 group by  t_grades.grades)  " + " as aa  " + " ORDER BY  name asc ";

			Object[] params = {};
			DB mydb = new DB();
			try {
				mydb.doPstm(sql, params);
				ResultSet rs = mydb.getRs();
				while (rs.next()) {
					dateList += "\"" + rs.getString("name") + "\"" + ",";
					pingjunList.add(rs.getString("punjun"));

				}
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			mydb.closed();

		}

		if (dateList != "") {
			dateList = dateList.substring(0, dateList.length() - 1);// 删除多余的逗号
			req.setAttribute("dateinfo", dateinfo1 + "到" + dateinfo2);
			req.setAttribute("dateList", "[" + dateList + "]");
			req.setAttribute("pingjunList", "[" + listToString(pingjunList) + "]");

		} else {
			req.setAttribute("dateinfo", "");
			req.setAttribute("dateList", "[]");
			req.setAttribute("pingjunList", "[]");

		}

		req.getRequestDispatcher("admin/tongji/tongjiPingjun.jsp").forward(req, res);
	}

	public void tongjiYouxiu(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		String dateinfo1 = "";// 开始日期
		String dateinfo2 = "";// 结束日期

		dateinfo1 = req.getParameter("dateinfo1");
		dateinfo2 = req.getParameter("dateinfo2");

		String wheresql = "";

		List youxiuList = new ArrayList();// 优秀人数

		String dateList = "";// 日期列表

		if (dateinfo1 != null && dateinfo2 != null) {
			wheresql = " where xuenian >'" + dateinfo1 + "' and xuenian <'" + dateinfo2 + "' ";
			if (req.getParameter("class_id") != null && !req.getParameter("class_id").equals("0")) {
				wheresql += "  and t_stu.classes_id=  '" + req.getParameter("class_id") + "'";
			}

			String sql = " ";
			Object[] params = {};
			DB mydb = new DB();

			// 3优秀人数
			// 搜索时间范围内的成绩
			sql = "SELECT * from ("
					+ "SELECT sum( if( grades > 90, 1, 0)) as youxiu ,t_course.name FROM `t_grades`   	"
					+ "left join t_course on t_course.id=t_grades.course_id "
					+ "left join t_stu on t_stu.id=t_grades.stu_id " + wheresql
					+ "			 group by  t_grades.grades) as aa " + " ORDER BY name asc ";

			 

			try {
				mydb.doPstm(sql, params);
				ResultSet rs = mydb.getRs();
				while (rs.next()) {
					// dateList += "\"" + rs.getString("xuenian") + "\"" + ",";
					youxiuList.add(rs.getString("youxiu"));

				}
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			mydb.closed();

			// dateList = dateList.substring(0, dateList.length() - 1);//
			// 删除多余的逗号
			req.setAttribute("dateinfo", dateinfo1 + "到" + dateinfo2);
			req.setAttribute("dateList", "[" + dateList + "]");
			req.setAttribute("youxiuList", "[" + listToString(youxiuList) + "]");

		} else {
			req.setAttribute("dateinfo", "");
			req.setAttribute("youxiuList", "[]");
			req.setAttribute("dateList", "[]");

		}

		req.getRequestDispatcher("admin/tongji/tongjiYouxiu.jsp").forward(req, res);
	}

	public void tongjiZhong(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		String dateinfo = "";// 日期

		dateinfo = req.getParameter("dateinfo");

		String wheresql = "";
		List zhongList = new ArrayList();// 总成绩
		String stuList = "";// 学生列表

		if (dateinfo != "") {

			wheresql = " where xuenian like '%" + dateinfo + "%'";
			
			
			if (req.getParameter("class_id") != null && !req.getParameter("class_id").equals("0")) {
				wheresql += "  and t_stu.classes_id=  '" + req.getParameter("class_id") + "'";
			}

		 

			
			
			
			// 总成绩
			// 搜索时间 的成绩
			// 按学生 分组,求总和
			String sql = "SELECT name1,sum(  grades ) as grades ,xuenian FROM `t_grades`   	"
					+ "left join t_stu on t_stu.id=t_grades.stu_id" + "	" + wheresql + " group by  stu_id ";
			DB mydb = new DB();
			Object[] params = {};
			try {
				mydb.doPstm(sql, params);
				ResultSet rs = mydb.getRs();
				while (rs.next()) {
					// dateList += "\"" + rs.getString("xuenian") + "\"" + ",";

					stuList += "\"" + rs.getString("name1") + "\"" + ",";
					zhongList.add(rs.getString("grades"));

				}
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			mydb.closed();

		}

		if (stuList != "") {
			req.setAttribute("dateinfo", dateinfo);

			stuList = stuList.substring(0, stuList.length() - 1);// 删除多余的逗号
			req.setAttribute("stuList", "[" + stuList + "]");
			req.setAttribute("zhongList", "[" + listToString(zhongList) + "]");

		} else {
			req.setAttribute("dateinfo", "");
			req.setAttribute("stuList", "[]");
			req.setAttribute("zhongList", "[]");

		}

		req.getRequestDispatcher("admin/tongji/tongjiZhong.jsp").forward(req, res);
	}

	// list使用逗号分切
	public static String listToString(List<String> stringList) {
		if (stringList == null) {
			return null;
		}
		StringBuilder result = new StringBuilder();
		boolean flag = false;
		for (String string : stringList) {
			if (flag) {
				result.append(",");
			} else {
				flag = true;
			}
			result.append(string);
		}
		return result.toString();
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
