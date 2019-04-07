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

		String dateinfo1 = "";// ��ʼ����
		String dateinfo2 = "";// ��������

		dateinfo1 = req.getParameter("dateinfo1");
		dateinfo2 = req.getParameter("dateinfo2");

		String wheresql = "";

		String dateList = "";// �����б�
		String dengjiList = "";// �ɼ� �ȼ�

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

			// 2�ɼ��ȼ���״
			// ����ʱ�䷶Χ�ڵĳɼ�
			// ���ȼ��������,���ɱ�ͼ,
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
					dengjiList += "{value:\"" + rs.getString("youxiu") + "\",name:\"����(90������)\"},";
					dengjiList += "{value:\"" + rs.getString("lianghao") + "\",name:\"����(80������)\"},";
					dengjiList += "{value:\"" + rs.getString("zhongdeng") + "\",name:\"�е�(70������)\"},";
					dengjiList += "{value:\"" + rs.getString("jige") + "\",name:\"����(60������)\"},";
					dengjiList += "{value:\"" + rs.getString("bujige") + "\",name:\"������(60������)\"},";

				}
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			req.setAttribute("dateinfo", dateinfo1 + "��" + dateinfo2);
			dengjiList = dengjiList.substring(0, dengjiList.length() - 1);// ɾ������Ķ���
			req.setAttribute("dengjiList", "[" + dengjiList + "]");

		} else {
			req.setAttribute("dateinfo", "");
			req.setAttribute("dengjiList", "[]");

		}

		req.getRequestDispatcher("admin/tongji/tongjiDengji.jsp").forward(req, res);
	}

	public void tongjiPingjun(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		String dateinfo1 = "";// ��ʼ����
		String dateinfo2 = "";// ��������

		dateinfo1 = req.getParameter("dateinfo1");
		dateinfo2 = req.getParameter("dateinfo2");

		String wheresql = "";

		List pingjunList = new ArrayList();// ƽ���ɼ�

		String dateList = "";// �����б�

		if (dateinfo1 != "" && dateinfo2 != "") {
			wheresql = " where xuenian >'" + dateinfo1 + "' and xuenian <'" + dateinfo2 + "' ";
			if (req.getParameter("class_id") != null && !req.getParameter("class_id").equals("0")) {
				wheresql += "  and t_stu.classes_id=  '" + req.getParameter("class_id") + "'";
			}

			// 1ƽ���ɼ�
			// ����ʱ�䷶Χ�ڵĳɼ�
			// ��ʱ���������,
			// ͳ�Ƴɼ��ĸ������ܺ�,����ͬ�������ڵ�ƽ���ɼ�
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
			dateList = dateList.substring(0, dateList.length() - 1);// ɾ������Ķ���
			req.setAttribute("dateinfo", dateinfo1 + "��" + dateinfo2);
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

		String dateinfo1 = "";// ��ʼ����
		String dateinfo2 = "";// ��������

		dateinfo1 = req.getParameter("dateinfo1");
		dateinfo2 = req.getParameter("dateinfo2");

		String wheresql = "";

		List youxiuList = new ArrayList();// ��������

		String dateList = "";// �����б�

		if (dateinfo1 != null && dateinfo2 != null) {
			wheresql = " where xuenian >'" + dateinfo1 + "' and xuenian <'" + dateinfo2 + "' ";
			if (req.getParameter("class_id") != null && !req.getParameter("class_id").equals("0")) {
				wheresql += "  and t_stu.classes_id=  '" + req.getParameter("class_id") + "'";
			}

			String sql = " ";
			Object[] params = {};
			DB mydb = new DB();

			// 3��������
			// ����ʱ�䷶Χ�ڵĳɼ�
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
			// ɾ������Ķ���
			req.setAttribute("dateinfo", dateinfo1 + "��" + dateinfo2);
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

		String dateinfo = "";// ����

		dateinfo = req.getParameter("dateinfo");

		String wheresql = "";
		List zhongList = new ArrayList();// �ܳɼ�
		String stuList = "";// ѧ���б�

		if (dateinfo != "") {

			wheresql = " where xuenian like '%" + dateinfo + "%'";
			
			
			if (req.getParameter("class_id") != null && !req.getParameter("class_id").equals("0")) {
				wheresql += "  and t_stu.classes_id=  '" + req.getParameter("class_id") + "'";
			}

		 

			
			
			
			// �ܳɼ�
			// ����ʱ�� �ĳɼ�
			// ��ѧ�� ����,���ܺ�
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

			stuList = stuList.substring(0, stuList.length() - 1);// ɾ������Ķ���
			req.setAttribute("stuList", "[" + stuList + "]");
			req.setAttribute("zhongList", "[" + listToString(zhongList) + "]");

		} else {
			req.setAttribute("dateinfo", "");
			req.setAttribute("stuList", "[]");
			req.setAttribute("zhongList", "[]");

		}

		req.getRequestDispatcher("admin/tongji/tongjiZhong.jsp").forward(req, res);
	}

	// listʹ�ö��ŷ���
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
