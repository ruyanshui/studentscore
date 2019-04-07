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
		<script type='text/javascript' src='<%=path %>/dwr/interface/loginService.js'></script>
        <script type='text/javascript' src='<%=path %>/dwr/engine.js'></script>
        <script type='text/javascript' src='<%=path %>/dwr/util.js'></script>
        <script language="javascript">
           var s=0;
           function xuankeDel(stu_id,course_id)
           {
               s=course_id;
               document.getElementById("indicator"+s).style.display="block";
               loginService.xuankeDel(stu_id,course_id,callback);
           }
          
             function callback(data)
			 {
			    document.getElementById("indicator"+s).style.display="none";
			    if(data=="no")
			    {
			        alert("发生错误");
			    }
			    if(data=="yes")
			    {
			        alert("删除成功");
			        window.location.reload();
			    }
			 }
       </script>
	</head>

	<body leftmargin="2" topmargin="2" background='<%=path %>/images/allbg.gif'>
			<table width="98%" border="0" cellpadding="2" cellspacing="1" bgcolor="#D9E9FF" align="center" style="margin-top:8px">
				<tr align="center" bgcolor="#87BBEF" height="22">
					<td width="33%">名称</td>
					<c:if test="${sessionScope.role==1||sessionScope.role==2}"><td width="33%">操作</td> </c:if>
		        </tr>	
				<c:forEach items="${requestScope.courseList}" var="course">
				<tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='red';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22">
					<td bgcolor="#FFFFFF" align="center">
						${course.name}
					</td>
					<c:if test="${sessionScope.role==1||sessionScope.role==2}"><td bgcolor="#FFFFFF" align="center">
						<a style="color: red" href="#" onclick="xuankeDel(${requestScope.stu_id },${course.id})" class="pn-loperator">删除</a>
				        <img id="indicator${course.id}" src="<%=path %>/images/loading.gif" style="display:none"/> </c:if>
					</td>
				</tr>
				</c:forEach>
			</table>
	</body>
</html>
