<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false" %> 
<%
String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />

		<link rel="stylesheet" type="text/css" href="<%=path %>/css/base.css" />
		<script type="text/javascript" src="<%=path %>/js/popup.js"></script>
		
        <script language="javascript">
           function courseAll(stu_id)
           {
                var url="<%=path %>/course?type=courseAll&stu_id="+stu_id;
                var pop=new Popup({ contentType:1,isReloadOnClose:false,width:500,height:300});
	            pop.setContent("contentUrl",url);
	            pop.setContent("title","课程选择");
	            pop.build();
	            pop.show();
           }
           
           function courseByStu(stu_id)
           {
                var url="<%=path %>/course?type=courseByStu&stu_id="+stu_id;
                var pop=new Popup({ contentType:1,isReloadOnClose:false,width:500,height:300});
	            pop.setContent("contentUrl",url);
	            pop.setContent("title","选课管理");
	            pop.build();
	            pop.show();
           }
           
       </script>
	</head>

	<body leftmargin="2" topmargin="2" background='<%=path %>/images/allbg.gif'>
			<table width="98%" border="0" cellpadding="2" cellspacing="1" bgcolor="#D9E9FF" align="center" style="margin-top:8px">
				<tr bgcolor="#E7E7E7">
					<td height="14" colspan="7" background="<%=path %>/images/tbg.gif">&nbsp;学生选课管理&nbsp;</td>
				</tr>
				<tr align="center" bgcolor="#87BBEF" height="22">
					<td width="11%">学号</td>
					<td width="11%">姓名</td>
					<td width="11%">性别</td>
					<td width="11%">年龄</td>
					<td width="11%">班级</td>
					<td width="12%">入学年份</td>
					<td width="11%">操作</td>
		        </tr>	
				<c:forEach items="${requestScope.stuList}" var="stu">
		<c:if test="${(sessionScope.role==1||sessionScope.role==2)||(stu.code==sessionScope.admin.userName)}">
<tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='red';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22">
					<td bgcolor="#FFFFFF" align="center">
						${stu.code}
					</td>
					<td bgcolor="#FFFFFF" align="center">
						${stu.name1}
					</td>
					<td bgcolor="#FFFFFF" align="center">
					    ${stu.sex}
					</td>
					<td bgcolor="#FFFFFF" align="center">
						${stu.age}
					</td>
					<td bgcolor="#FFFFFF" align="center">
						${stu.classes_name}
					</td>
					<td bgcolor="#FFFFFF" align="center">
					    ${stu.indate}
					</td>
					<td bgcolor="#FFFFFF" align="center">
						<a href="#" style="color: red" onclick="courseByStu(${stu.id})" class="pn-loperator">查看选课</a>
						<c:if test="${sessionScope.role==1||sessionScope.role==2}">
&nbsp;&nbsp;&nbsp;
						<a href="#" style="color: red" onclick="courseAll(${stu.id})" class="pn-loperator">选课添加</a>
                        </c:if>
					</td>
				</tr>
                </c:if>
				</c:forEach>
			</table>
	</body>
</html>
