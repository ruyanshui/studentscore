package com.action;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.DB;

public class report_servlet extends HttpServlet{
    
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String type = req.getParameter("type");

        if (type.endsWith("schoolReport")) {
            schoolReport(req, res);
        }
        if (type.endsWith("gradeReport")) {
            gradeReport(req, res);
        }
        if (type.endsWith("classesReport")) {
            classesReport(req, res);
        }
        if (type.endsWith("studentReport")) {
            studentReport(req, res);
        }
    }

    public void studentReport(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // 考勤
        String resultList = "";
        // 情绪状态
        String feelingList = "";
        // 人际关系状态
        String relationShipList = "";
        // 学习状态
        String learningStateList = "";
        // 精神状态
        String spiritStateList = "";
        // 学科兴趣
        String subjectName = "";
        // 体艺发展
        String Interest = "";
        // 情绪状态历史趋势图
        String stuEmotionHistory = "";
        // 精神状态历史趋势图
        String stuMentalHistory = "";
        // 学习状态历史趋势图
        String stuStudyHistory = "";
        // 学生综合状态
        String stuZongheState = "";
        // 学生状态分析
        String stuFenxiState = "";
        
        
        // 获取时间参数
        String startTime = req.getParameter("startTime");
        String endTime = req.getParameter("endTime");
        String stuCode = req.getParameter("stu_id");
        String className = req.getParameter("classes_id");
        if (startTime != null && endTime != null && stuCode != null) {
            //学科兴趣
            subjectName = getStulikeSubjectName(startTime, endTime, stuCode);
            // 体艺发展
            Interest = getStuLikeInterestName(startTime, endTime, stuCode);
            // 情绪
            feelingList = getStudentFeelingList(startTime, endTime, stuCode);
            relationShipList = getStudentRelationShip(startTime, endTime, stuCode);
            learningStateList  = getStudentLearningState(startTime,endTime, stuCode);
            spiritStateList = getStudentspiritState(startTime, endTime, stuCode);
            stuEmotionHistory = getStuEmotionHistory(startTime, endTime, stuCode);
            stuMentalHistory = getStuMentalHistory(startTime, endTime, stuCode);
            stuStudyHistory = getStuStudyHistory(startTime, endTime, stuCode);
            stuZongheState = getStuZongheState(startTime, endTime, stuCode);
            stuFenxiState = getStuFenxiState(startTime, endTime, stuCode);
            
            
            req.setAttribute("stuDateinfo", startTime + ", " + endTime);
            req.setAttribute("stuResultList", "[" + resultList + "]");
            req.setAttribute("stuFeelingList", "[" + feelingList +"]");
            req.setAttribute("stuRelationShipList", "[" + relationShipList + "]");
            req.setAttribute("stuLearningStateList", "[" + learningStateList + "]");
            req.setAttribute("stuSpiritStateList", "[" + spiritStateList + "]");
            req.setAttribute("stuEmotionHistory", "[" + stuEmotionHistory + "]");
            req.setAttribute("stuMentalHistory", "[" + stuMentalHistory + "]");
            req.setAttribute("stuStudyHistory", "[" + stuStudyHistory + "]");
            req.setAttribute("stuZongheState", "[" + stuZongheState + "]");
            req.setAttribute("stuFenxiState", "[" + stuFenxiState + "]");
            req.setAttribute("subjectName", "[" + subjectName + "]");
            req.setAttribute("Interest", "[" + Interest + "]");
            //resultList = resultList.substring(0, resultList.length() - 1);// 
          
        } else {
            req.setAttribute("stuDateinfo", "");
            req.setAttribute("stuResultList", "[]");
            req.setAttribute("stuFeelingList", "[]");
            req.setAttribute("stuRelationShipList", "[]");
            req.setAttribute("stuLearningStateList", "[]");
            req.setAttribute("stuSpiritStateList", "[]");
            req.setAttribute("stuEmotionHistory", "[]");
            req.setAttribute("stuMentalHistory", "[]");
            req.setAttribute("stuStudyHistory", "[]");
            req.setAttribute("stuZongheState", "[]");
            req.setAttribute("stuFenxiState", "[]");
            req.setAttribute("subjectName", "[]");
            req.setAttribute("Interest", "[]");
        }
        
        // 重定向转发到schoolReport.jsp
        req.getRequestDispatcher("admin/report/studentReport.jsp").forward(req, res);
        
    }
    
    /**
     * 
     * @Title: getStuFenxiState
     * @Description: 分析学生状态
     * @param startTime
     * @param endTime
     * @param stuNum
     * @return
     * @throws
     */
    private String getStuFenxiState(String startTime, String endTime, String stuNum) {   
        DB mydb = new DB();
        Object[] params = {};
        String result = "";
        String sql = " SELECT" + 
                "        course_name, grade_level, study_level" + 
                "    FROM student_mental_status_grade_study_daily" + 
                "    WHERE student_number = '" + stuNum + "' AND dt >= '" + startTime + "' AND dt <= '" + endTime + "'" + 
                "    ORDER BY course_name ASC;";
        
        try {
            mydb.doPstm(sql, params);
            ResultSet rs = mydb.getRs();
            while (rs.next()) {       
                result += "{course_name:\"" + rs.getString("course_name") + "\"," + "grade_level:\"" + rs.getString("grade_level") +"\"," +  "study_level:\"" + rs.getString("study_level")+"\"},";
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
       
    }
    /**
     * 
     * @Title: getStuZongheState
     * @Description: 获取学识综合状态
     * @param startTime
     * @param endTime
     * @param stuNum
     * @return
     * @throws
     */
    private String getStuZongheState(String startTime, String endTime, String stuNum) {
        DB mydb = new DB();
        Object[] params = {};
        String result = "";
        String sql = " SELECT" + 
                "        ROUND((1.0 * IFNULL(t5.emotion_count, 0)) / t1.total, 2) AS emotion, ROUND((1.0 * IFNULL(t2.study_count, 0)) / t1.total, 2) AS study, ROUND((1.0 * IFNULL(t3.mental_count, 0)) / t1.total, 2) AS mental, ROUND((1.0 * IFNULL(t4.relationship_count, 0)) / t1.total, 2) AS relationship" + 
                "    FROM" + 
                "    (" + 
                "        SELECT" + 
                "            COUNT(*) AS total" + 
                "        FROM student_mental_status_ld" + 
                "        WHERE student_number = '" + stuNum +"' AND dt >= '" + startTime +"' AND dt <= '" + endTime + "'" + 
                "    ) t1 JOIN" + 
                "    (" + 
                "        SELECT" + 
                "            COUNT(*) AS study_count" + 
                "        FROM student_mental_status_ld" + 
                "        WHERE student_number = '" + stuNum +"' AND dt >= '" + startTime +"' AND dt <= '" + endTime + "' AND student_study_stat != '3'" + 
                "    ) t2 JOIN" + 
                "    (" + 
                "        SELECT" + 
                "            COUNT(*) AS mental_count" + 
                "        FROM student_mental_status_ld" + 
                "        WHERE student_number = '" + stuNum +"' AND dt >= '" + startTime + "' AND dt <= '" + endTime + "' AND student_mental_stat != '2'" + 
                "    ) t3 JOIN" + 
                "    (" + 
                "        SELECT" + 
                "            COUNT(*) AS relationship_count" + 
                "        FROM student_mental_status_ld" + 
                "        WHERE student_number = '" + stuNum +"' AND dt >= '" + startTime + "' AND dt <= '" + endTime + "' AND student_relationship != '3' AND student_relationship != ''" + 
                "    ) t4 JOIN" + 
                "    (" + 
                "        SELECT" + 
                "            COUNT(*) AS emotion_count" + 
                "        FROM student_mental_status_ld" + 
                "        WHERE student_number = '" + stuNum +"' AND dt >= '" + startTime + "' AND dt <= '" + endTime + "' AND student_emotion != '2'" + 
                "    ) t5";
        
        try {
            mydb.doPstm(sql, params);
            ResultSet rs = mydb.getRs();
            while (rs.next()) {       
                result += "{emotion:\"" + rs.getString("emotion") + "\"," + "study:\"" + rs.getString("study") +"\"," +  "mental:\"" + rs.getString("mental") + "\"," +  "relationship:\"" + rs.getString("relationship") + "\"},";
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 
     * @Title: getStuStudyHistory
     * @Description: 学习状态历史趋势图
     * @param startTime
     * @param endTime
     * @param stuNum
     * @return
     * @throws
     */
    private String getStuStudyHistory(String startTime, String endTime, String stuNum) {
        DB mydb = new DB();
        Object[] params = {};
        String result = "";
        String sql = " SELECT" + 
                "        dt, student_study_stat" + 
                "    FROM student_mental_status_ld" + 
                "    WHERE student_number = '" + stuNum + "' AND dt >= '" + startTime + "' AND dt <= '"+ endTime + "'" + 
                "    ORDER BY dt ASC;";
        
        try {
            mydb.doPstm(sql, params);
            ResultSet rs = mydb.getRs();
            while (rs.next()) {       
                result += "{dt:\"" + rs.getString("dt") + "\"," + "student_study_stat:\"" + rs.getString("student_study_stat") +"\"},";
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 
     * @Title: getStuMentalHistory
     * @Description: 精神状态历史趋势图
     * @param startTime
     * @param endTime
     * @param stuNum
     * @return
     * @throws
     */
    private String getStuMentalHistory(String startTime, String endTime, String stuNum) {
        DB mydb = new DB();
        Object[] params = {};
        String result = "";
        String sql = "SELECT" + 
                "        dt, student_mental_stat" + 
                "    FROM student_mental_status_ld" + 
                "    WHERE student_number = '" + stuNum +"' AND dt >= '" + startTime + "' AND dt <= '" + endTime + "'" + 
                "    ORDER BY dt ASC";
        
        try {
            mydb.doPstm(sql, params);
            ResultSet rs = mydb.getRs();
            while (rs.next()) {       
                result += "{dt:\"" + rs.getString("dt") + "\"," + "student_mental_stat:\"" + rs.getString("student_mental_stat") + "\"},";
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
       
    }

    /**
     * 
     * @Title: getStuEmotionHistory
     * @Description: 情绪状态历史趋势图
     * @param startTime
     * @param endTime
     * @param stuNum
     * @return
     * @throws
     */
    private String getStuEmotionHistory(String startTime, String endTime, String stuNum) {
        DB mydb = new DB();
        Object[] params = {};
        String result = "";
        String sql = "SELECT" + 
                "        dt, student_emotion" + 
                "    FROM student_mental_status_ld" + 
                "    WHERE student_number = '" + stuNum +"' AND dt >= '" + startTime + "' AND dt <= '" + endTime +"'" + 
                "    ORDER BY dt ASC";
        
        try {
            mydb.doPstm(sql, params);
            ResultSet rs = mydb.getRs();
            while (rs.next()) {       
                result += "{dt:\"" + rs.getString("dt") + "\"," + "student_emotion:\"" + rs.getString("student_emotion") + "\"},";
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
       
    }

    /**
     * 
     * @Title: getStudentsubjectInterest
     * @Description: 
     * @param startTime
     * @param endTime
     * @param stuNum
     * @return
     * @throws
     */
    private String getStudentsubjectInterest(String startTime, String endTime, String stuNum) {
        DB mydb = new DB();
        Object[] params = {};
        String result = "";
        String sql = "SELECT" + 
                "        GROUP_CONCAT(DISTINCT student_interest) AS student_interest" + 
                "    FROM student_mental_status_interest_daily" + 
                "    WHERE student_number = '" + stuNum +"' AND dt >= '" + startTime + "' AND dt <= '" + endTime + "'";
        
        try {
            mydb.doPstm(sql, params);
            ResultSet rs = mydb.getRs();
            while (rs.next()) {       
                result += "{student_interest:\"" + rs.getString("student_interest")  + "\"},";
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
       
    }

    /**
     * 
     * @Title: getStudentspiritState
     * @Description: 精神状态
     * @param startTime
     * @param endTime
     * @param stuNum
     * @return
     * @throws
     */
    private String getStudentspiritState(String startTime, String endTime, String stuNum) {
        DB mydb = new DB();
        Object[] params = {};
        String result = "";
        String sql = " SELECT" + 
                "        t1.student_mental_stat, ROUND((1.0 * t1.num) / t2.total * 100, 2) AS percentage" + 
                "    FROM" + 
                "    (" + 
                "        SELECT" + 
                "            student_mental_stat, COUNT(*) AS num" + 
                "        FROM student_mental_status_ld" + 
                "        WHERE student_number = '" + stuNum +"' AND dt >= '" + startTime + "' AND dt <= '" + endTime + "'" + 
                "        GROUP BY student_mental_stat" + 
                "    ) t1 JOIN" + 
                "    (" + 
                "        SELECT" + 
                "            COUNT(*) AS total" + 
                "        FROM student_mental_status_ld" + 
                "        WHERE student_number = '" + stuNum +"' AND dt >= '" + startTime +"' AND dt <= '" + endTime + "'"  + 
                "    ) t2";
        
        try {
            mydb.doPstm(sql, params);
            ResultSet rs = mydb.getRs();
            while (rs.next()) {       
                result += "{student_mental_stat:\"" + rs.getString("student_mental_stat")  + "\"," + "percentage:\"" + rs.getString("percentage") + "\"},";
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 
     * @Title: getStudentLearningState
     * @Description: 学习状态
     * @param startTime
     * @param endTime
     * @param stuNum
     * @return
     * @throws
     */
    private String getStudentLearningState(String startTime, String endTime, String stuNum) {
        DB mydb = new DB();
        Object[] params = {};
        String result = "";
        String sql = " SELECT" + 
                "        t1.student_study_stat, ROUND((1.0 * t1.num) / t2.total * 100, 2) AS percentage" + 
                "    FROM" + 
                "    (" + 
                "        SELECT" + 
                "            student_study_stat, COUNT(*) AS num" + 
                "        FROM student_mental_status_ld" + 
                "        WHERE student_number = '" + stuNum +"' AND dt >= '" + startTime +"' AND dt <= '" + endTime +"'" + 
                "        GROUP BY student_study_stat" + 
                "    ) t1 JOIN" + 
                "    (" + 
                "        SELECT" + 
                "            COUNT(*) AS total" + 
                "        FROM student_mental_status_ld" + 
                "        WHERE student_number = '" + stuNum +"' AND dt >= '" + startTime +"' AND dt <= '" + endTime + "'" + 
                "    ) t2";
        
        try {
            mydb.doPstm(sql, params);
            ResultSet rs = mydb.getRs();
            while (rs.next()) {       
                result += "{student_study_stat:\"" + rs.getString("student_study_stat")  + "\"," + "percentage:\"" + rs.getString("percentage") + "\"},";
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * 
     * @Title: getStudentRelationShip
     * @Description: 人际关系
     * @param startTime
     * @param endTime
     * @param stuNum
     * @return
     * @throws
     */
    private String getStudentRelationShip(String startTime, String endTime, String stuNum) {
        DB mydb = new DB();
        Object[] params = {};
        String result = "";
        String sql = "SELECT" + 
                "        t.student_relationship" + 
                "    FROM" + 
                "    (" + 
                "        SELECT" + 
                "            student_relationship, COUNT(*) AS total" + 
                "        FROM student_mental_status_ld" + 
                "        WHERE student_number = '" + stuNum + "'  AND student_relationship != '' AND dt >= '" + startTime + "' AND dt <= '"+ endTime +"'" + 
                "        GROUP BY student_relationship" + 
                "    ) t" + 
                "    ORDER BY t.student_relationship ASC, t.total DESC" + 
                "    LIMIT 0, 1";
        
        try {
            mydb.doPstm(sql, params);
            ResultSet rs = mydb.getRs();
            while (rs.next()) {       
                result += "{student_relationship:\"" + rs.getString("student_study_stat") + "\"},";
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 
     * @Title: getStudentFeelingList
     * @Description: 情绪状态
     * @param startTime
     * @param endTime
     * @param stuNum
     * @return
     * @throws
     */
    private String getStudentFeelingList(String startTime, String endTime, String stuNum) {
        DB mydb = new DB();
        Object[] params = {};
        String result = "";
        String sql = " SELECT" + 
                "        t1.student_emotion, ROUND((1.0 * t1.num) / t2.total * 100, 2) AS percentage" + 
                "    FROM" + 
                "    (" + 
                "        SELECT" + 
                "            student_emotion, COUNT(*) AS num" + 
                "        FROM student_mental_status_ld" + 
                "        WHERE student_number = '" + stuNum +"' AND dt >= '" + startTime +"' AND dt <= '" + endTime +"'" + 
                "        GROUP BY student_emotion" + 
                "    ) t1 JOIN" + 
                "    (" + 
                "        SELECT" + 
                "            COUNT(*) AS total" + 
                "        FROM student_mental_status_ld" + 
                "        WHERE student_number = '" + stuNum +"' AND dt >= '" + startTime +"' AND dt <= '" + endTime +"'" + 
                "    ) t2";
        
        try {
            mydb.doPstm(sql, params);
            ResultSet rs = mydb.getRs();
            while (rs.next()) {       
                result += "{student_emotion:\"" + rs.getString("student_emotion") +  "\"," + "percentage:\"" + rs.getString("percentage") + "\"},";
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

   
    /**
     * 
     * @Title: getStuLikeInterestName
     * @Description:体艺发展 
     * @param startTime
     * @param endTime
     * @param stuNum
     * @return
     * @throws
     */
    private String getStuLikeInterestName(String startTime, String endTime, String stuNum) {
        DB mydb = new DB();
        Object[] params = {};
        String result = "";
        String sql = "SELECT" + 
                "        GROUP_CONCAT(DISTINCT award_type) AS award_type" + 
                "    FROM school_award_info" + 
                "    WHERE student_number = '" + stuNum +"' AND dt >= '" + startTime +"' AND dt <= '" + endTime +"'";
        
        try {
            mydb.doPstm(sql, params);
            ResultSet rs = mydb.getRs();
            while (rs.next()) {       
                result += "{award_type:\"" + rs.getString("award_type")  + "\"},";
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 
     * @Title: getStulikeSubjectName
     * @Description: 学科兴趣
     * @param startTime
     * @param endTime
     * @param stuNum
     * @return
     * @throws
     */
    private String getStulikeSubjectName(String startTime, String endTime, String stuNum) {
        DB mydb = new DB();
        Object[] params = {};
        String result = "";
        String sql = "SELECT" + 
                "        GROUP_CONCAT(DISTINCT student_interest) AS student_interest" + 
                "    FROM student_mental_status_interest_daily" + 
                "    WHERE student_number = '" + stuNum +"' AND dt >= '" + startTime + "' AND dt <= '" + endTime + "'";
        
        try {
            mydb.doPstm(sql, params);
            ResultSet rs = mydb.getRs();
            while (rs.next()) {       
                result += "{student_interest:\"" + rs.getString("student_interest")  + "\"},";
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

   

    /**
     * 班级
     * @param req
     * @param res
     * @throws IOException 
     * @throws ServletException 
     */
    public void classesReport(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
         // 考勤
         String resultList = "";
         // 情绪状态
         String feelingList = "";
         // 人际关系状态
         String relationShipList = "";
         // 学习状态
         String learningStateList = "";
         // 精神状态
         String spiritStateList = "";
         // 学科兴趣分布
         String subjectInterestList = "";
         // 历史状态信息-qingxu 
         String classeshistoryqingxu = "";
         String classeshistoryjingshen = "";
         String classeshistoryXuexi = "";
         // 校园预警 --
         String classesqingxubujia = "";
         String qingxubujiastuList = "";
         String claasesjingshenbujia = "";
         String jingshenbujiastuList = "";
         String classesXuexibujia = "";
         String xuexibujiaStuList = "";
         // 获取时间参数
         String dateInfo = req.getParameter("dateinfo1");
         // 获取班级参数
         String classesName = req.getParameter("classes_id");
         String gradeName = req.getParameter("grade_name");
         if (dateInfo != null && classesName != null && gradeName != null ) {
             resultList = getClassesAttendenctDataByTime(dateInfo, classesName, gradeName);
             feelingList = getClassesFeelingListByTime(dateInfo, classesName, gradeName);
             relationShipList = getClassesRelationShipByTime(dateInfo, classesName, gradeName);
             learningStateList  = getClassesLearningStateByTime(dateInfo, classesName, gradeName);
             spiritStateList = getClassesspiritStateByTime(dateInfo, classesName,gradeName);
             subjectInterestList = getClassessubjectInterestByTime(dateInfo, classesName, gradeName);
             classeshistoryqingxu = getclassesqingxu(dateInfo,classesName, gradeName);
             classeshistoryjingshen = getclassesjingshen(dateInfo, classesName, gradeName);
             classeshistoryXuexi = getclassesXuexi(dateInfo, classesName, gradeName);
             classesqingxubujia = getclassesqingxubujia(dateInfo, classesName ,gradeName);
             claasesjingshenbujia = getclaasesjingshenbujia(dateInfo, classesName, gradeName);
             classesXuexibujia = getclassesXuexibujia(dateInfo, classesName, gradeName);
             qingxubujiastuList = getqingxubujiastuList(dateInfo, classesName, gradeName);
             jingshenbujiastuList = getjingshenbujiastuList(dateInfo, classesName, gradeName);
             xuexibujiaStuList = getxuexibujiaStuList(dateInfo, classesName, gradeName);
             
             
             req.setAttribute("classesDateinfo", dateInfo);
             req.setAttribute("classesResultList", "[" + resultList + "]");
             req.setAttribute("classesFeelingList", "[" + feelingList +"]");
             req.setAttribute("classesRelationShipList", "[" + relationShipList + "]");
             req.setAttribute("classesLearningStateList", "[" + learningStateList + "]");
             req.setAttribute("classesSpiritStateList", "[" + spiritStateList + "]");
             req.setAttribute("classesSubjectInterestList", "[" + subjectInterestList + "]");
             req.setAttribute("classeshistoryqingxu", "[" + classeshistoryqingxu + "]");
             req.setAttribute("classeshistoryjingshen", "[" + classeshistoryjingshen + "]");
             req.setAttribute("classeshistoryXuexi", "[" + classeshistoryXuexi + "]");
             req.setAttribute("classesqingxubujia", "[" + classesqingxubujia + "]");
             req.setAttribute("claasesjingshenbujia", "[" + claasesjingshenbujia + "]");
             req.setAttribute("classesXuexibujia", "[" + classesXuexibujia + "]");
             req.setAttribute("qingxubujiastuList", "[" + qingxubujiastuList + "]");
             req.setAttribute("jingshenbujiastuList", "[" + jingshenbujiastuList + "]");
             req.setAttribute("xuexibujiaStuList", "[" + xuexibujiaStuList + "]");
             //resultList = resultList.substring(0, resultList.length() - 1);// 
           
         } else {
             req.setAttribute("classesDateinfo", "");
             req.setAttribute("classesResultList", "[]");
             req.setAttribute("classesFeelingList", "[]");
             req.setAttribute("classesRelationShipList", "[]");
             req.setAttribute("classesLearningStateList", "[]");
             req.setAttribute("classesSpiritStateList", "[]");
             req.setAttribute("classesSubjectInterestList", "[]");
             req.setAttribute("classeshistoryqingxu", "[]");
             req.setAttribute("classesjingshen", "[]");
             req.setAttribute("classesXuexi", "[]");
             req.setAttribute("classesqingxubujia", "[]");
             req.setAttribute("claasesjingshenbujia", "[]");
             req.setAttribute("classesXuexibujia", "[]");
             req.setAttribute("qingxubujiastuList", "[]");
             req.setAttribute("jingshenbujiastuList", "[]");
             req.setAttribute("xuexibujiaStuList", "[]");
         }
         
         // 重定向转发到schoolReport.jsp
         req.getRequestDispatcher("admin/report/classesReport.jsp").forward(req, res);
         
         
        
    }
    private String getxuexibujiaStuList(String dateInfo, String classesName, String gradeName) {
        DB mydb = new DB();
        Object[] params = {};
        String resultList ="";
        String attendeceSql = " SELECT" + 
                "        DISTINCT student_number, student_name" + 
                "    FROM student_mental_status_ld" + 
                "    WHERE grade_name = 'param' and class_name = 'param' AND dt = selected_time_param AND student_study_stat = 'param';";

        try {
          mydb.doPstm(attendeceSql, params);
          ResultSet rs = mydb.getRs();
          while (rs.next()) {  
              resultList += "{GROUP_CONCAT(student_name separator '/,'):\"" + rs.getString("GROUP_CONCAT(student_name separator ',')")  +  "\"},";           
          }
          rs.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
              
        return resultList;
    }

    private String getjingshenbujiastuList(String dateInfo, String classesName, String gradeName) {
        DB mydb = new DB();
        Object[] params = {};
        String resultList ="";
        String attendeceSql = "SELECT GROUP_CONCAT(student_name separator ',')" + 
                "    FROM (" + 
                "        SELECT student_number, student_name, count(*) as num" + 
                "        FROM student_mental_status_ld" + 
                "        WHERE dt>=date_format(date_add('"+dateInfo+"', interval -15 day), '%Y%m%d') AND dt<='"+dateInfo+"' AND student_study_stat='3' AND grade_name='"+gradeName+"' AND class_name='"+classesName+"'" + 
                "        GROUP BY student_number, student_name" + 
                "        HAVING num>=6" + 
                "    )t1 ";

       

          try {
            mydb.doPstm(attendeceSql, params);
            ResultSet rs = mydb.getRs();
            while (rs.next()) {  
                resultList += "{GROUP_CONCAT(student_name separator '/,'):\"" + rs.getString("GROUP_CONCAT(student_name separator ',')")  +  "\"},";           
            }
            rs.close();
          } catch (Exception e) {
            e.printStackTrace();
          }
                
             
        return resultList;
    }

    private String getqingxubujiastuList(String dateInfo, String classesName, String gradeName) {
        // TODO Auto-generated method stub
        return null;
    }

    private String getclassesXuexibujia(String dateInfo, String classesName, String gradeName) {
        DB mydb = new DB();
        Object[] params = {};
        String resultList ="";
        String attendeceSql = "SELECT GROUP_CONCAT(student_name separator ',') as name" + 
                "    FROM (" + 
                "        SELECT student_number, student_name, count(*) as num" + 
                "        FROM student_mental_status_ld" + 
                "        WHERE dt>=date_format(date_add('"+dateInfo+"', interval -15 day), '%Y%m%d') AND dt<='"+dateInfo+"' AND student_study_stat='3' AND grade_name='"+gradeName+"' AND class_name='"+classesName+"'" + 
                "        GROUP BY student_number, student_name" + 
                "        HAVING num>=6" + 
                "    )t1 ";

        try {
          mydb.doPstm(attendeceSql, params);
          ResultSet rs = mydb.getRs();
          while (rs.next()) {  
              resultList += "{GROUP_CONCAT(student_name separator '/,'):\"" + rs.getString("name")  +  "\"},";           
          }
          rs.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
              
        return resultList;
    }

    private String getclaasesjingshenbujia(String dateInfo, String classesName, String gradeName) {
        DB mydb = new DB();
        Object[] params = {};
        String resultList ="";
        String attendeceSql = "SELECT GROUP_CONCAT(student_name separator ',') as name" + 
                "    FROM (" + 
                "        SELECT student_number, student_name, count(*) as num" + 
                "        FROM student_mental_status_ld" + 
                "        WHERE dt>=date_format(date_add('"+dateInfo+"', interval -15 day), '%Y%m%d') AND dt<='"+dateInfo+"' AND student_mental_stat='2' AND grade_name='"+gradeName+"' AND class_name='"+classesName+"'"+ 
                "        GROUP BY student_number, student_name" + 
                "        HAVING num>=6" + 
                "    )t1 ";

        try {
          mydb.doPstm(attendeceSql, params);
          ResultSet rs = mydb.getRs();
          while (rs.next()) {  
              resultList += "{GROUP_CONCAT(student_name separator '/,'):\"" + rs.getString("name") +  "\"},";           
          }
          rs.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
              
        return resultList;
    }

    private String getclassesqingxubujia(String dateInfo, String classesName, String gradeName) {
        DB mydb = new DB();
        Object[] params = {};
        String resultList ="";
        String attendeceSql = " SELECT GROUP_CONCAT(student_name separator ',') as name" + 
                "    FROM (" + 
                "        SELECT student_number, student_name, count(*) as num" + 
                "        FROM student_mental_status_ld" + 
                "        WHERE dt>=date_format(date_add('"+dateInfo+"', interval -15 day), '%Y%m%d') AND dt<='"+dateInfo+"' AND student_emotion='2' AND grade_name='"+gradeName+"' AND class_name='"+classesName+"'"+ 
                "        GROUP BY student_number, student_name" + 
                "        HAVING num>=6" + 
                "    )t1 ";

        try {
          mydb.doPstm(attendeceSql, params);
          ResultSet rs = mydb.getRs();
          while (rs.next()) {  
              resultList += "{GROUP_CONCAT(student_name separator ','):\"" + rs.getString("name") +  "\"}";                  
          }
          rs.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
              
        return resultList;
    }

    private String getclassesXuexi(String dateInfo, String classesName, String gradeName) {
        DB mydb = new DB();
        Object[] params = {};
        String resultList ="";
        String attendeceSql = "SELECT" + 
                "        t1.dt, t1.student_study_stat, ROUND((1.0 * t1.num) / t2.total * 100, 2) AS percentage" + 
                "    FROM" + 
                "    (" + 
                "        SELECT" + 
                "            dt, student_study_stat, COUNT(*) AS num" + 
                "        FROM student_mental_status_ld" + 
                "        WHERE grade_name = '" + gradeName + "' and class_name = '" +classesName+"' AND dt >= '"+dateInfo+"' AND dt <= '" + dateInfo +"'" + 
                "        GROUP BY dt, student_study_stat" + 
                "    ) t1 JOIN" + 
                "    (" + 
                "        SELECT" + 
                "            dt, COUNT(*) AS total" + 
                "        FROM student_mental_status_ld" + 
                "        WHERE grade_name = '" + gradeName + "' and class_name = '" +classesName+"' AND dt >= '" + dateInfo + "' AND dt <= '" + dateInfo+"'" + 
                "        GROUP BY dt" + 
                "    ) t2 ON t1.dt = t2.dt" + 
                "    ORDER BY dt ASC";
                

        try {
          mydb.doPstm(attendeceSql, params);
          ResultSet rs = mydb.getRs();
          while (rs.next()) {  
              resultList += "{dt:\"" + rs.getString("dt") + "\"," + " student_study_stat:\"" + rs.getString(" student_study_stat") + "\"," + " percentage:\"" + rs.getString(" percentage")+ "\"},";           
          }
          rs.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
              
        return resultList;
    }

    private String getclassesjingshen(String dateInfo, String classesName, String gradeName) {
        DB mydb = new DB();
        Object[] params = {};
        String resultList ="";
        String attendeceSql = " SELECT" + 
                "        t1.dt, t1.student_mental_stat, ROUND((1.0 * t1.num) / t2.total * 100, 2) AS percentage" + 
                "    FROM" + 
                "    (" + 
                "        SELECT" + 
                "            dt, student_mental_stat, COUNT(*) AS num" + 
                "        FROM student_mental_status_ld" + 
                "        WHERE grade_name = '" + gradeName+ "' and class_name = '" + classesName +"' AND dt >= '" + dateInfo +"' AND dt <= '" + dateInfo +"'" + 
                "        GROUP BY dt, student_mental_stat" + 
                "    ) t1 JOIN" + 
                "    (" + 
                "        SELECT" + 
                "            dt, COUNT(*) AS total" + 
                "        FROM student_mental_status_ld" + 
                "        WHERE grade_name = '" + gradeName+ "' and class_name = '" + classesName +"' AND dt >= '" + dateInfo +"' AND dt <= '" + dateInfo +"'" + 
                "        GROUP BY dt" + 
                "    ) t2 ON t1.dt = t2.dt" + 
                "    ORDER BY dt ASC";
                

        try {
          mydb.doPstm(attendeceSql, params);
          ResultSet rs = mydb.getRs();
          while (rs.next()) {  
              resultList += "{student_mental_stat:\"" + rs.getString("student_mental_stat") + "\","+ " percentage" + rs.getString(" percentage") +  "\"},";           
          }
          rs.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
              
        return resultList;
    }

    private String getclassesqingxu(String dateInfo, String classesName, String gradeName) {
        DB mydb = new DB();
        Object[] params = {};
        String resultList ="";
        String attendeceSql = " SELECT" + 
                "        t1.dt, t1.student_emotion, ROUND((1.0 * t1.num) / t2.total * 100, 2) AS percentage" + 
                "    FROM" + 
                "    (" + 
                "        SELECT" + 
                "            dt, student_emotion, COUNT(*) AS num" + 
                "        FROM student_mental_status_ld" + 
                "        WHERE grade_name = '" + gradeName +"' and class_name = '" + classesName +"' AND dt >= '" +dateInfo +"' AND dt <= '" + dateInfo +"'"+ 
                "        GROUP BY dt, student_emotion" + 
                "    ) t1 JOIN" + 
                "    (" + 
                "        SELECT" + 
                "            dt, COUNT(*) AS total" + 
                "        FROM student_mental_status_ld" + 
                "        WHERE grade_name = '" + gradeName +"' and class_name = '" + classesName +"' AND dt >= '" +dateInfo +"' AND dt <= '" + dateInfo +"'" + 
                "        GROUP BY dt" + 
                "    ) t2 ON t1.dt = t2.dt" + 
                "    ORDER BY dt ASC";
                

        try {
          mydb.doPstm(attendeceSql, params);
          ResultSet rs = mydb.getRs();
          while (rs.next()) {  
              resultList += "{student_emotion:\"" + rs.getString("student_emotion") + "\","+ " percentage:\"" + rs.getString(" percentage") +  "\"},";           
          }
          rs.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
              
        return resultList;
    }

    private String getClassessubjectInterestByTime(String dateInfo, String classesName, String gradeName) {
        DB mydb = new DB();
        Object[] params = {};
        String subjectInterestList = "";
        String sql = "SELECT" + 
                "        student_interest, COUNT(*) AS total" + 
                "    FROM student_mental_status_interest_daily" + 
                "    WHERE grade_name = '" + gradeName +"' AND class_name = '" + classesName +"' AND dt = '" + dateInfo + "'" + 
                "    GROUP BY student_interest" + 
                "    ORDER BY student_interest ASC";
                
        
        try {
            mydb.doPstm(sql, params);
            ResultSet rs = mydb.getRs();
            while (rs.next()) {       
                subjectInterestList += "{student_interest:\"" + rs.getString("student_interest") + "\"," + "total:\"" + rs.getString("total") + "\"},";
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
       
        return subjectInterestList;
	}

	private String getClassesspiritStateByTime(String dateInfo, String classesName, String gradeName) {
	    DB mydb = new DB();
        Object[] params = {};
        String spiritStateList = "";
        String sql = " SELECT" + 
                "        t1.student_mental_stat, ROUND((1.0 * t1.num) / t2.total * 100, 2) AS percentage" + 
                "    FROM" + 
                "    (" + 
                "        SELECT" + 
                "            student_mental_stat, COUNT(*) AS num" + 
                "        FROM student_mental_status_ld" + 
                "        WHERE grade_name = '" + gradeName +"' and class_name = '" + classesName +"' AND dt = '"+ dateInfo +"'" + 
                "        GROUP BY student_mental_stat" + 
                "    ) t1 JOIN" + 
                "    (" + 
                "        SELECT" + 
                "            COUNT(*) AS total" + 
                "        FROM student_mental_status_ld" + 
                "        WHERE grade_name = '" + gradeName +"' and class_name = ' " +classesName +"' AND dt = '" + dateInfo +"'" + 
                "    ) t2";
        
        
        try {
            mydb.doPstm(sql, params);
            ResultSet rs = mydb.getRs();
            while (rs.next()) {       
                spiritStateList += "{student_mental_stat:\"" + rs.getString("student_mental_stat") + "\"," + "percentage:\"" + rs.getString("percentage") + "\"},";
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
       
        return spiritStateList;
	}

	private String getClassesLearningStateByTime(String dateInfo, String classesName, String gradeName) {
	    DB mydb = new DB();
        Object[] params = {};
        String learningStateList = "";
        String sql = " SELECT" + 
                "        t1.student_study_stat, ROUND((1.0 * t1.num) / t2.total * 100, 2) AS percentage" + 
                "    FROM" + 
                "    (" + 
                "        SELECT" + 
                "            student_study_stat, COUNT(*) AS num" + 
                "        FROM student_mental_status_ld" + 
                "        WHERE grade_name = '" + gradeName + "' and class_name = '" + classesName +"' AND dt = '" + dateInfo +"'" + 
                "        GROUP BY student_study_stat" + 
                "    ) t1 JOIN" + 
                "    (" + 
                "        SELECT" + 
                "            COUNT(*) AS total" + 
                "        FROM student_mental_status_ld" + 
                "        WHERE grade_name = '" + gradeName + "' and class_name = '" + classesName + "' AND dt = '" + dateInfo +"'" + 
                "    ) t2;";
        
        
        try {
            mydb.doPstm(sql, params);
            ResultSet rs = mydb.getRs();
            while (rs.next()) {       
                learningStateList += "{student_study_stat:\"" + rs.getString("student_study_stat") + "\"," + "percentage:\"" + rs.getString("percentage") + "\"},";
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
       
        return learningStateList;
    }
	

	private String getClassesRelationShipByTime(String dateInfo, String classesName, String gradeName) {
	    DB mydb = new DB();
        Object[] params = {};
        String relationShipList = "";
        // 情绪状态
        String sql = " SELECT" + 
                "        t1.student_relationship, ROUND((1.0 * t1.num) / t2.total * 100, 2) AS percentage" + 
                "    FROM" + 
                "    (" + 
                "        SELECT" + 
                "            student_relationship, COUNT(*) AS num" + 
                "        FROM student_mental_status_ld" + 
                "        WHERE grade_name = '" + gradeName + "' and class_name = '" + classesName + "' AND dt = '" + dateInfo +"'" + 
                "        GROUP BY student_relationship" + 
                "    ) t1 JOIN" + 
                "    (" + 
                "        SELECT" + 
                "            COUNT(*) AS total" + 
                "        FROM student_mental_status_ld" + 
                "        WHERE grade_name = '" + gradeName + "' and class_name = '" + classesName + "' AND dt = '" + dateInfo + "'" + 
                "    ) t2";
        try {
            mydb.doPstm(sql, params);
            ResultSet rs = mydb.getRs();
            while (rs.next()) {       
                relationShipList += "{student_relationship:\"" + rs.getString("student_relationship") + "\"," + "percentage:\"" + rs.getString("percentage") + "\"},";
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
       
        return relationShipList;
	}

	/**
     * 
     * @param dateInfo
	 * @param classesName 
	 * @param gradeName 
     * @return
     */
    private String getClassesFeelingListByTime(String dateInfo, String classesName, String gradeName) {
    	 DB mydb = new DB();
         Object[] params = {};
         String spiritStateList = "";
         String sql = " SELECT" + 
                 "        t1.student_emotion, ROUND((1.0 * t1.num) / t2.total * 100, 2) AS percentage" + 
                 "    FROM" + 
                 "    (" + 
                 "        SELECT" + 
                 "            student_emotion, COUNT(*) AS num" + 
                 "        FROM student_mental_status_ld" + 
                 "        WHERE grade_name = '" + gradeName + "' and class_name = '" + classesName + "' AND dt = '" + dateInfo + "'" + 
                 "        GROUP BY student_emotion" + 
                 "    ) t1 JOIN" + 
                 "    (" + 
                 "        SELECT" + 
                 "            COUNT(*) AS total" + 
                 "        FROM student_mental_status_ld" + 
                 "        WHERE grade_name = '" + gradeName + "' and class_name = '" + classesName + "' AND dt = '" + dateInfo + "'" + 
                 "    ) t2";
         
         try {
             mydb.doPstm(sql, params);
             ResultSet rs = mydb.getRs();
             while (rs.next()) {       
                 spiritStateList += "{student_mental_stat:\"" + rs.getString("student_mental_stat") + "\"}," + "{percentage:\"" + rs.getString("percentage") + "\"},";
             }
             rs.close();
         } catch (Exception e) {
             e.printStackTrace();
         }
        
         return spiritStateList;
	}

	private String getClassesAttendenctDataByTime(String dateInfo, String classesName, String gradeName) {
	    DB mydb = new DB();
        Object[] params = {};
        String resultList ="";
      
        String attendeceSql = " SELECT t1.time_gap, t1.course_name, IF(t2.num IS NULL, 0, t2.num), IF(t2.name IS NULL, '', t2.name)" + 
                "    FROM (" + 
                "        SELECT CONCAT(from_unixtime(start_time,'%H:%i'),'_',from_unixtime(end_time,'%H:%i')) as time_gap, course_name" + 
                "        FROM school_course_info" + 
                "        WHERE dt='" + dateInfo + "' AND grade_name= '" + gradeName + "' AND class_name= '" + classesName + "'" + 
                "    )t1 LEFT JOIN (" + 
                "        SELECT CONCAT(from_unixtime(start_time,'%H:%i'),'_',from_unixtime(end_time,'%H:%i')) as time_gap, course_name, GROUP_CONCAT(student_name separator ',') as name, count(*) AS num" + 
                "        FROM school_student_attendance_info" + 
                "        WHERE dt='" + dateInfo + "' AND grade_name='" + gradeName + "'} AND class_name='" + classesName + "'" + 
                "        GROUP BY start_time,end_time,course_name" + 
                "    )t2 ON t1.time_gap=t2.time_gap AND t1.course_name=t2.course_name";

        try {
          mydb.doPstm(attendeceSql, params);
          ResultSet rs = mydb.getRs();
          while (rs.next()) {  
              resultList += "{time_gap:\"" + rs.getString("time_gap") + "\","+ "course_name:\"" + rs.getString("course_name") + "\"," + "num1:\"" + rs.getString("IF(t2.num IS NULL, 0, t2.num)") + "num2:\"" + rs.getString("IF(t2.name IS NULL, '', t2.name)") + "\"},";           
          }
          rs.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
              
        return resultList;
	}

	public void gradeReport(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	        // 考勤
	        String resultList = "";
	        // 情绪状态
	        String feelingList = "";
	        // 人际关系状态
	        String relationShipList = "";
	        // 学习状态
	        String learningStateList = "";
	        // 精神状态
	        String spiritStateList = "";
	        // 学科兴趣分布
	        String subjectInterestList = "";
	        // 情绪状态不佳
	        String subjectQingxuzhuangtaibujia = "";
	        // 精神状态不佳
	        String subjectJingshenZhuantaibujia = "";
	        // 学习状态不佳
	        String subjectXuexiZhuangtaibujia = "";
	        // 获取时间参数
	        String dateInfo = req.getParameter("dateinfo");
	        // 获取班级参数
	        String gradeName = req.getParameter("grade_name");
	        if (dateInfo != null && "".equals(dateInfo)) {
	            resultList = getGradeAttendenctDataByTime(dateInfo, gradeName);
	            feelingList = getGradeFeelingListByTime(dateInfo, gradeName);
	            relationShipList = getGradeRelationShipByTime(dateInfo, gradeName);
	            learningStateList  = getGradeLearningStateByTime(dateInfo,gradeName);
	            spiritStateList = getGradespiritStateByTime(dateInfo, gradeName);
	            subjectInterestList = getGradesubjectInterestByTime(dateInfo, gradeName);
	            subjectQingxuzhuangtaibujia = getGradeSubjectQingxubujia(dateInfo, gradeName);
	            subjectJingshenZhuantaibujia = getGradeSubjectJingshenbujia(dateInfo, gradeName);
	            subjectXuexiZhuangtaibujia = getGradeSubjectXuexibujia(dateInfo, gradeName);
	            
	            
	            req.setAttribute("gradeDateinfo", dateInfo);
	            req.setAttribute("gradeResultList", "[" + resultList + "]");
	            req.setAttribute("gradeFeelingList", "[" + feelingList +"]");
	            req.setAttribute("gradeRelationShipList", "[" + relationShipList + "]");
	            req.setAttribute("gradeLearningStateList", "[" + learningStateList + "]");
	            req.setAttribute("gradeSpiritStateList", "[" + spiritStateList + "]");
	            req.setAttribute("gradeSubjectInterestList", "[" + subjectInterestList + "]");
	            req.setAttribute("gradesubjectQingxuzhuangtaibujia", "[" + subjectQingxuzhuangtaibujia + "]");
	            req.setAttribute("gradesubjectJingshenZhuantaibujia", "[" + subjectJingshenZhuantaibujia + "]");
	            req.setAttribute("gradesubjectXuexiZhuangtaibujia", "[" + subjectXuexiZhuangtaibujia + "]");
	            //resultList = resultList.substring(0, resultList.length() - 1);// 
	          
	        } else {
	            req.setAttribute("gradeDateinfo", "");
	            req.setAttribute("gradeResultList", "[]");
	            req.setAttribute("gradeFeelingList", "[]");
	            req.setAttribute("gradeRelationShipList", "[]");
	            req.setAttribute("gradeLearningStateList", "[]");
	            req.setAttribute("gradeSpiritStateList", "[]");
	            req.setAttribute("subjectQingxuzhuangtaibujia", "[]");
	            req.setAttribute("subjectJingshenZhuantaibujia", "[]");
	            req.setAttribute("subjectXuexiZhuangtaibujia", "[]");
	        }
	        
	        // 重定向转发到schoolReport.jsp
	        req.getRequestDispatcher("admin/report/gradeReport.jsp").forward(req, res);
        
    }

    private String getGradeSubjectXuexibujia(String dateInfo, String gradeName) {
        DB mydb = new DB();
        Object[] params = {};
        String resultList ="";
        String attendeceSql = " SELECT class_name, GROUP_CONCAT(student_name separator ',')" + 
                "    FROM (" + 
                "        SELECT student_number, student_name, class_name, count(*) as num" + 
                "        FROM student_mental_status_ld" + 
                "        WHERE dt>=date_format(date_add('"+dateInfo + "', interval -15 day), '%Y%m%d') AND dt<='"+dateInfo+"' AND student_study_stat='3' AND grade_name='" + gradeName +"' "+ 
                "        GROUP BY student_number, student_name, class_name" + 
                "        HAVING num>=6" + 
                "    )t1 " + 
                "    GROUP BY class_name";

        try {
          mydb.doPstm(attendeceSql, params);
          ResultSet rs = mydb.getRs();
          while (rs.next()) {  
              resultList += "{class_name:\"" + rs.getString("class_name") + "\","+ " GROUP_CONCAT(student_name separator ','):\"" + rs.getString(" GROUP_CONCAT(student_name separator ',')") +  "\"},";           
          }
          rs.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
              
        return resultList;
    }

    private String getGradeSubjectJingshenbujia(String dateInfo, String gradeName) {
        DB mydb = new DB();
        Object[] params = {};
        String resultList ="";
        String attendeceSql = " SELECT class_name, GROUP_CONCAT(student_name separator ',')" + 
                "    FROM (" + 
                "        SELECT student_number, student_name, class_name, count(*) as num" + 
                "        FROM student_mental_status_ld\r\n" + 
                "        WHERE dt>=date_format(date_add('" + dateInfo+"', interval -15 day), '%Y%m%d') AND dt<='" + dateInfo+"' AND student_mental_stat='2' AND grade_name='" + gradeName+"'" + 
                "        GROUP BY student_number, student_name, class_name" + 
                "        HAVING num>=6" + 
                "    )t1 " + 
                "    GROUP BY class_name";

        try {
          mydb.doPstm(attendeceSql, params);
          ResultSet rs = mydb.getRs();
          while (rs.next()) {  
              resultList += "{class_name:\"" + rs.getString("class_name") + "\","+ " GROUP_CONCAT(student_name separator ','):\"" + rs.getString(" GROUP_CONCAT(student_name separator ',')") +  "\"},";           
          }
          rs.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
              
        return resultList;
    }

    private String getGradeSubjectQingxubujia(String dateInfo, String gradeName) {
        DB mydb = new DB();
        Object[] params = {};
        String resultList ="";
        String attendeceSql = "SELECT class_name, GROUP_CONCAT(student_name separator ',')" + 
                "    FROM (" + 
                "        SELECT student_number, student_name, class_name, count(*) as num" + 
                "        FROM student_mental_status_ld" + 
                "        WHERE dt>=date_format(date_add('"+dateInfo + "', interval -15 day), '%Y%m%d') AND dt<='" + dateInfo +"' AND student_emotion='2' AND grade_name='" + gradeName + " '"+ 
                "        GROUP BY student_number, student_name, class_name" + 
                "        HAVING num>=6" + 
                "    )t1 " + 
                "    GROUP BY class_name" ;
                

        try {
          mydb.doPstm(attendeceSql, params);
          ResultSet rs = mydb.getRs();
          while (rs.next()) {  
              resultList += "{class_name:\"" + rs.getString("class_name") + "\","+ " GROUP_CONCAT(student_name separator ','):\"" + rs.getString(" GROUP_CONCAT(student_name separator ',')") +  "\"},";           
          }
          rs.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
              
        return resultList;
    }

    private String getGradeAttendenctDataByTime(String dateInfo, String gradeName) {
        DB mydb = new DB();
        Object[] params = {};
        String resultList ="";
        String attendeceSql = " SELECT t1.time_gap, t1.class_name, IF(t2.num IS NULL, 0, t2.num)" + 
                "    FROM (" + 
                "        SELECT CONCAT(from_unixtime(start_time,'%H:%i'),'_',from_unixtime(end_time,'%H:%i')) as time_gap,class_name" + 
                "        FROM school_course_info" + 
                "        WHERE dt= '" + dateInfo +"' AND grade_name= '" + gradeName+ "'" +
                "        GROUP BY start_time,end_time,class_name" + 
                "    )t1 LEFT JOIN (" + 
                "        SELECT CONCAT(from_unixtime(start_time,'%H:%i'),'_',from_unixtime(end_time,'%H:%i')) as time_gap, class_name, count(*) AS num" + 
                "        FROM school_student_attendance_info" + 
                "        WHERE dt= '" + dateInfo + "' AND grade_name= '" + gradeName +"'" +  
                "        GROUP BY start_time,end_time,class_name" + 
                "    )t2 ON t1.time_gap=t2.time_gap AND t1.class_name=t2.class_name";

        try {
          mydb.doPstm(attendeceSql, params);
          ResultSet rs = mydb.getRs();
          while (rs.next()) {  
              resultList += "{time_gap:\"" + rs.getString("time_gap") + "\","+ "grade_name:\"" + rs.getString("grade_name") + "\"," + "num:\"" + rs.getString("IF(t2.num IS NULL, 0, t2.num)") + "\"},";           
          }
          rs.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
              
        return resultList;
	}

	private String getGradesubjectInterestByTime(String dateInfo, String gradeName) {
	    DB mydb = new DB();
        Object[] params = {};
        String subjectInterestList = "";
        String sql = " SELECT" + 
                "        student_interest, COUNT(*) AS total" + 
                "    FROM student_mental_status_interest_daily" + 
                "    WHERE grade_name = '" + gradeName +"' AND dt = '" +  dateInfo +"'"+ 
                "    GROUP BY student_interest" + 
                "    ORDER BY student_interest ASC";
        
        try {
            mydb.doPstm(sql, params);
            ResultSet rs = mydb.getRs();
            while (rs.next()) {       
                subjectInterestList += "{student_interest:\"" + rs.getString("student_interest") + "\"," + "total:\"" + rs.getString("total") + "\"},";
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
       
        return subjectInterestList;
	}

	private String getGradespiritStateByTime(String dateInfo, String gradeName) {
	    DB mydb = new DB();
        Object[] params = {};
        String spiritStateList = "";
        String sql = " SELECT" + 
                "        t1.student_mental_stat, ROUND(1.0 * t1.num / t2.total * 100, 2) AS percentage" + 
                "    FROM" + 
                "    (" + 
                "        SELECT" + 
                "            student_mental_stat, COUNT(*) AS num" + 
                "        FROM student_mental_status_ld" + 
                "        WHERE grade_name = '" + gradeName + "' AND dt = '" + dateInfo + "'" + 
                "        GROUP BY student_mental_stat" + 
                "    ) t1 JOIN" + 
                "    (" + 
                "        SELECT" + 
                "            COUNT(*) AS total" + 
                "        FROM student_mental_status_ld" + 
                "        WHERE grade_name = '" + gradeName + "' AND dt = '" + dateInfo + "'" + 
                "    ) t2;";
        
        
        try {
            mydb.doPstm(sql, params);
            ResultSet rs = mydb.getRs();
            while (rs.next()) {       
                spiritStateList += "{student_mental_stat:\"" + rs.getString("student_mental_stat") + "\"," + "percentage:\"" + rs.getString("percentage") + "\"},";
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
       
        return spiritStateList;
	}

	private String getGradeLearningStateByTime(String dateInfo, String gradeName) {
	    DB mydb = new DB();
        Object[] params = {};
        String learningStateList = "";
        String sql = " SELECT" + 
                "        t1.student_study_stat, ROUND(1.0 * t1.num / t2.total * 100, 2) AS percentage" + 
                "    FROM" + 
                "    (" + 
                "        SELECT" + 
                "            student_study_stat, COUNT(*) AS num" + 
                "        FROM student_mental_status_ld" + 
                "        WHERE grade_name = '" + gradeName + "' AND dt = '" + dateInfo + "'" + 
                "        GROUP BY student_study_stat" + 
                "    ) t1 JOIN" + 
                "    (" + 
                "        SELECT" + 
                "            COUNT(*) AS total" + 
                "        FROM student_mental_status_ld" + 
                "        WHERE grade_name = '" + gradeName + "' AND dt = ' " + dateInfo + "'" +
                "    ) t2";
        
        
        try {
            mydb.doPstm(sql, params);
            ResultSet rs = mydb.getRs();
            while (rs.next()) {       
                learningStateList += "{student_study_stat:\"" + rs.getString("student_study_stat") + "\"," + "percentage:\"" + rs.getString("percentage") + "\"},";
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
       
        return learningStateList;
	}

	private String getGradeRelationShipByTime(String dateInfo, String gradeName) {
	    DB mydb = new DB();
        Object[] params = {};
        String relationShipList = "";
        // 情绪状态
        String sql = "SELECT" + 
                "        t1.student_relationship, ROUND(1.0 * t1.num / t2.total * 100, 2) AS percentage" + 
                "    FROM" + 
                "    (" + 
                "        SELECT" + 
                "            student_relationship, COUNT(*) AS num" + 
                "        FROM student_mental_status_ld" + 
                "        WHERE grade_name = '" + gradeName +"' AND dt = '" + dateInfo +"' " +
                "        GROUP BY student_relationship" + 
                "    ) t1 JOIN" + 
                "    (" + 
                "        SELECT" + 
                "            COUNT(*) AS total" + 
                "        FROM student_mental_status_ld" + 
                "        WHERE grade_name = '" + gradeName + "' AND dt = '" + dateInfo +"' " +
                "    ) t2";
        try {
            mydb.doPstm(sql, params);
            ResultSet rs = mydb.getRs();
            while (rs.next()) {       
                relationShipList += "{student_relationship:\"" + rs.getString("student_relationship") + "\"," + "percentage:\"" + rs.getString("percentage") + "\"},";
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
       
        return relationShipList;
	}

	private String getGradeFeelingListByTime(String dateInfo, String gradeName) {
		     DB mydb = new DB();
	        Object[] params = {};
	        String feelingList = "";
	        // 情绪状态
	        String feelingSql = " SELECT" + 
	        		"        t1.student_emotion, ROUND(1.0 * t1.num / t2.total * 100, 2) AS percentage" + 
	        		"    FROM" + 
	        		"    (" + 
	        		"        SELECT" + 
	        		"            student_emotion, COUNT(*) AS num" + 
	        		"        FROM student_mental_status_ld" + 
	        		"        WHERE grade_name = '" + gradeName + "' AND dt = '" + dateInfo +"'" + 
	        		"        GROUP BY student_emotion" + 
	        		"    ) t1 JOIN" + 
	        		"    (" + 
	        		"        SELECT" + 
	        		"            COUNT(*) AS total" + 
	        		"        FROM student_mental_status_ld" + 
	        		"        WHERE grade_name = '" + gradeName + "' AND dt = '" + dateInfo +"'" + 
	        		"    ) t2";
	        
	        try {
	            mydb.doPstm(feelingSql, params);
	            ResultSet rs = mydb.getRs();
	            while (rs.next()) {       
	                feelingList += "{student_emotion:\"" + rs.getString("student_emotion") + "\","+ "percentage:\"" + rs.getString("percentage") + "\"},";
	            }
	            rs.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	       
	        return feelingList;
	}

	/**
     * @throws IOException 
     * @throws ServletException 
     * 
     * @Title: schoolReport
     * @Description: 学校报表统计
     * @param req
     * @param res
     * @throws
     */
    public void schoolReport(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        
        // 考勤
        String resultList = "";
        // 情绪状态
        String feelingList = "";
        // 人际关系状态
        String relationShipList = "";
        // 学习状态
        String learningStateList = "";
        // 精神状态
        String spiritStateList = "";
        // 学科兴趣分布
        String subjectInterestList = "";
        // 校园安全预警 -- 情绪不佳
        String qingxubujia = "";
        String jingshenzhuangtaibujia = "";
        String xuexizhuangtaibujia = "";
        
        // 获取时间参数
        String dateInfo = req.getParameter("dateinfo");
        // 获取班级参数
        String gradeName = req.getParameter("grade_name");
        if (dateInfo != null ) {
            resultList = getAttendenctDataByTime(dateInfo);
            feelingList = getFeelingListByTime(dateInfo);
            relationShipList = getRelationShipByTime(dateInfo);
            learningStateList  = getLearningStateByTime(dateInfo);
            spiritStateList = getspiritStateByTime(dateInfo);
            subjectInterestList = getsubjectInterestByTime(dateInfo);
            qingxubujia = getQingxubujia(dateInfo);
            jingshenzhuangtaibujia = getjingshenzhuangtaibujia(dateInfo);
            xuexizhuangtaibujia = getxuexizhuangtaibujia(dateInfo);
            req.setAttribute("dateinfo", dateInfo);
            req.setAttribute("resultList", "[" + resultList + "]");
            req.setAttribute("feelingList", "[" + feelingList +"]");
            req.setAttribute("relationShipList", "[" + relationShipList + "]");
            req.setAttribute("learningStateList", "[" + learningStateList + "]");
            req.setAttribute("spiritStateList", "[" + spiritStateList + "]");
            req.setAttribute("subjectInterestList", "[" + subjectInterestList + "]");
            req.setAttribute("qingxubujia", "[" + qingxubujia + "]");
            req.setAttribute("xuexizhuangtaibujia", "[" + xuexizhuangtaibujia + "]");
            req.setAttribute("jingshenzhuangtaibujia", "[" + jingshenzhuangtaibujia + "]");
            //resultList = resultList.substring(0, resultList.length() - 1);// 
          
        } else {
            req.setAttribute("dateinfo", "");
            req.setAttribute("resultList", "[]");
            req.setAttribute("feelingList", "[]");
            req.setAttribute("relationShipList", "[]");
            req.setAttribute("learningStateList", "[]");
            req.setAttribute("spiritStateList", "[]");
            req.setAttribute("subjectInterestList", "[]");
            req.setAttribute("qingxubujia", "[]");
            req.setAttribute("xuexizhuangtaibujia", "[]");
            req.setAttribute("jingshenzhuangtaibujia", "[]");
        }
        
        // 重定向转发到schoolReport.jsp
        req.getRequestDispatcher("admin/report/schoolReport.jsp").forward(req, res);
    }

    
    private String getxuexizhuangtaibujia(String dateInfo) {
        DB mydb = new DB();
        Object[] params = {};
        String resultList ="";
        String attendeceSql = " SELECT grade_name, GROUP_CONCAT(student_name separator ',')" + 
                "    FROM (" + 
                "        SELECT student_number, student_name, count(*) as num" + 
                "        FROM student_mental_status_ld" + 
                "        WHERE dt>=date_format(date_add('" + dateInfo + "', interval -15 day), '%Y%m%d') AND dt<='" + dateInfo + "' AND student_study_stat='3'" + 
                "        GROUP BY student_number, student_name, grade_name" + 
                "        HAVING num>=6" + 
                "    )t1 " + 
                "    GROUP BY grade_name";
        
        try {
          mydb.doPstm(attendeceSql, params);
          ResultSet rs = mydb.getRs();
          while (rs.next()) {  
              resultList += "{grade_name:\"" + rs.getString("grade_name") + "\","+ "GROUP_CONCAT(student_name separator ','):\"" + rs.getString("GROUP_CONCAT(student_name separator ',')") + "\"," +  "\"},";           
          }
          rs.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
              
        return resultList;
    }

    private String getjingshenzhuangtaibujia(String dateInfo) {
        DB mydb = new DB();
        Object[] params = {};
        String resultList ="";
        String attendeceSql = " SELECT grade_name, GROUP_CONCAT(student_name separator ',')" + 
                "    FROM (" + 
                "        SELECT student_number, student_name, grade_name, count(*) as num" + 
                "        FROM student_mental_status_ld" + 
                "        WHERE dt>=date_format(date_add('"+dateInfo+"', interval -15 day), '%Y%m%d') AND dt<='"+dateInfo+"' AND student_mental_stat='2'" + 
                "        GROUP BY student_number, student_name, grade_name" + 
                "        HAVING num>=6" + 
                "    )t1 " + 
                "    GROUP BY grade_name";

        try {
          mydb.doPstm(attendeceSql, params);
          ResultSet rs = mydb.getRs();
          while (rs.next()) {  
              resultList += "{grade_name:\"" + rs.getString("grade_name") + "\","+ "GROUP_CONCAT(student_name separator ','):\"" + rs.getString("GROUP_CONCAT(student_name separator ',')") +  "\"},";           
          }
          rs.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
              
        return resultList;
    }

    private String getQingxubujia(String dateInfo) {
        DB mydb = new DB();
        Object[] params = {};
        String resultList ="";
        String attendeceSql = " SELECT grade_name, GROUP_CONCAT(student_name separator ',')" + 
                "    FROM (" + 
                "        SELECT student_number, student_name, grade_name, count(*) as num" + 
                "        FROM student_mental_status_ld" + 
                "        WHERE dt>=date_format(date_add('"+dateInfo+"', interval -15 day), '%Y%m%d') AND dt<='"+dateInfo+"' AND student_emotion='2'" + 
                "        GROUP BY student_number, student_name, grade_name" + 
                "        HAVING num>=6" + 
                "    )t1 " + 
                "    GROUP BY grade_name";

        try {
          mydb.doPstm(attendeceSql, params);
          ResultSet rs = mydb.getRs();
          while (rs.next()) {  
              resultList += "{grade_name:\"" + rs.getString("grade_name") + "\","+ " GROUP_CONCAT(student_name separator ','):\"" + rs.getString(" GROUP_CONCAT(student_name separator ',')") +  "\"},";           
          }
          rs.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
              
        return resultList;
    }

    // 考勤数据
    public String getAttendenctDataByTime(String dateInfo) {
        DB mydb = new DB();
        Object[] params = {};
        String resultList ="";
        String attendeceSql = " SELECT t1.time_gap, t1.grade_name, IF(t2.num IS NULL, 0, t2.num)" + 
                "  FROM (" + 
                " SELECT CONCAT(from_unixtime(start_time,'%H:%i'),'_',from_unixtime(end_time,'%H:%i')) as time_gap,grade_name" + 
                "  FROM school_course_info" + 
                " WHERE dt= '" + dateInfo + "'" +
                " GROUP BY start_time,end_time,grade_name" + 
                " )t1 LEFT JOIN (" + 
                " SELECT CONCAT(from_unixtime(start_time,'%H:%i'),'_',from_unixtime(end_time,'%H:%i')) as time_gap, grade_name, count(*) AS num" + 
                "  FROM school_student_attendance_info" + 
                " WHERE dt='"+ dateInfo + "'" + 
                " GROUP BY start_time,end_time,grade_name" + 
                " )t2 ON t1.time_gap=t2.time_gap AND t1.grade_name=t2.grade_name";

        try {
          mydb.doPstm(attendeceSql, params);
          ResultSet rs = mydb.getRs();
          while (rs.next()) {  
              resultList += "{time_gap:\"" + rs.getString("time_gap") + "\","+ "grade_name:\"" + rs.getString("grade_name") + "\"," + "num:\"" + rs.getString("IF(t2.num IS NULL, 0, t2.num)") + "\"},";           
          }
          rs.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
              
        return resultList;
    }
    

    // 学业自律性分析--精神状态
    public String getspiritStateByTime(String dateInfo) {
        DB mydb = new DB();
        Object[] params = {};
        String spiritStateList = "";
        String sql ="SELECT" + 
                "        t1.student_mental_stat, ROUND(1.0 * t1.num / t2.total * 100, 2) AS percentage" + 
                "    FROM" + 
                "    (" + 
                "        SELECT" + 
                "            student_mental_stat, COUNT(*) AS num" + 
                "        FROM student_mental_status_ld" + 
                "        WHERE dt = '" + dateInfo + "'" +
                "        GROUP BY student_mental_stat" + 
                "    ) t1 JOIN" + 
                "    (" + 
                "        SELECT" + 
                "            COUNT(*) AS total" + 
                "        FROM student_mental_status_ld" + 
                "        WHERE dt = '" + dateInfo + "'" + 
                "    ) t2";
        
        
        try {
            mydb.doPstm(sql, params);
            ResultSet rs = mydb.getRs();
            while (rs.next()) {       
                spiritStateList += "{student_mental_stat:\"" + rs.getString("student_mental_stat") + "\"," + "percentage:\"" + rs.getString("percentage") + "\"},";
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
       
        return spiritStateList;
    }
    
    
    // 学科兴趣分布
    public String getsubjectInterestByTime(String dateInfo) {
        DB mydb = new DB();
        Object[] params = {};
        String subjectInterestList = "";
        String sql =" SELECT" + 
                "        student_interest, COUNT(*) AS total" + 
                "    FROM student_mental_status_interest_daily" + 
                "    WHERE dt = '" + dateInfo +"'" +
                "    GROUP BY student_interest" + 
                "    ORDER BY student_interest ASC";
        
        try {
            mydb.doPstm(sql, params);
            ResultSet rs = mydb.getRs();
            while (rs.next()) {       
                subjectInterestList += "{student_interest:\"" + rs.getString("student_interest") + "\"," + "total:\"" + rs.getString("total") + "\"},";
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
       
        return subjectInterestList;
        
    }
    
    
    // 学业自律性分析--学习状态
    public String getLearningStateByTime(String dateInfo) {
        DB mydb = new DB();
        Object[] params = {};
        String learningStateList = "";
        String sql = "SELECT" + 
                "        t1.student_study_stat, ROUND(1.0 * t1.num / t2.total * 100, 2) AS percentage" + 
                "    FROM" + 
                "    (" + 
                "        SELECT" + 
                "            student_study_stat, COUNT(*) AS num" + 
                "        FROM student_mental_status_ld" + 
                "        WHERE dt = '" + dateInfo + "'" +
                "        GROUP BY student_study_stat" + 
                "    ) t1 JOIN" + 
                "    (" + 
                "        SELECT" + 
                "            COUNT(*) AS total" + 
                "        FROM student_mental_status_ld" + 
                "        WHERE dt = '" + dateInfo + "'" +
                "    ) t2";
        
        
        try {
            mydb.doPstm(sql, params);
            ResultSet rs = mydb.getRs();
            while (rs.next()) {       
                learningStateList += "{student_study_stat:\"" + rs.getString("student_study_stat") + "\"," + "percentage:\"" + rs.getString("percentage") + "\"},";
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
       
        return learningStateList;
        
    }
    
    
    // 人际关系
    public String getRelationShipByTime(String dateInfo) {
        DB mydb = new DB();
        Object[] params = {};
        String relationShipList = "";
        String sql = " SELECT" + 
                "        t1.student_relationship, ROUND(1.0 * t1.num / t2.total * 100, 2) AS percentage" + 
                "    FROM" + 
                "    (" + 
                "        SELECT" + 
                "            student_relationship, COUNT(*) AS num" + 
                "        FROM student_mental_status_ld" + 
                "        WHERE dt = '" + dateInfo + "'" +
                "        GROUP BY student_relationship" + 
                "    ) t1 JOIN" + 
                "    (" + 
                "        SELECT" + 
                "            COUNT(*) AS total" + 
                "        FROM student_mental_status_ld" + 
                "        WHERE dt = '" + dateInfo + "'" +
                "    ) t2";
        
        
        try {
            mydb.doPstm(sql, params);
            ResultSet rs = mydb.getRs();
            while (rs.next()) {       
                relationShipList += "{student_relationship:\"" + rs.getString("student_relationship") + "\"," + "percentage:\"" + rs.getString("percentage") + "\"},";
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
       
        return relationShipList;
        
    }
    
    
    // 情绪状态
    public String getFeelingListByTime(String dateInfo) {
        
        DB mydb = new DB();
        Object[] params = {};
        String feelingList = "";
        // 情绪状态
        String feelingSql = " SELECT" + 
                " t1.student_emotion, ROUND(1.0 * t1.num / t2.total * 100, 2) AS percentage" + 
                "    FROM" + 
                "    (" + 
                "        SELECT" + 
                "            student_emotion, COUNT(*) AS num" + 
                "        FROM student_mental_status_ld" + 
                "        WHERE dt = '" + dateInfo + "'"+ 
                "        GROUP BY student_emotion" + 
                "    ) t1 JOIN" + 
                "    (" + 
                "        SELECT" + 
                "            COUNT(*) AS total" + 
                "        FROM student_mental_status_ld" + 
                "        WHERE dt = '"+ dateInfo + "'" + 
                "    ) t2";
        
        try {
            mydb.doPstm(feelingSql, params);
            ResultSet rs = mydb.getRs();
            while (rs.next()) {       
                feelingList += "{student_emotion:\"" + rs.getString("student_emotion") + "\","+ "percentage:\"" + rs.getString("percentage") + "\"},";
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
       
        return feelingList;
    }
    
}
