<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>
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

<script language="javascript">
	function gradesDel(id) {
		if (confirm('您确定删除吗？')) {
			window.location.href = "<%=path %>/grades?type=gradesDel&id=" + id;
		}
	}

	function gradesAdd() {
		var url = "<%=path %>/admin/grades/gradesAdd.jsp";
		//var n="";
		//var w="480px";
		//var h="500px";
		//var s="resizable:no;help:no;status:no;scroll:yes";
		//openWin(url,n,w,h,s);
		window.location.href = url;
	}
</script>
</head>

<body leftmargin="2" topmargin="2"
	background='<%=path%>/images/allbg.gif'>
	<table width='98%' border='0' align="center">
		<tr>
			<td>
				<form action="<%=path%>/grades?type=gradesMana" name="formAdd"
					method="post">
					<c:if test="${sessionScope.role==1||sessionScope.role==2}">
						<input type="button" value="添加" style="width: 80px;"
							onclick="gradesAdd()" />
					</c:if>

					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="text" name="name"
						size="20"
						value="<%if (request.getParameter("name") != null) {
				out.print(request.getParameter("name"));
			}%>" />
					<input type="submit" value="搜索" style="width:40px;" /> 支持学号 姓名 课程
					考试日期的模糊搜索
				</form>

			</td>


		</tr>
	</table>


	<table width="98%" border="0" cellpadding="2" cellspacing="1"
		bgcolor="#D9E9FF" align="center" style="margin-top:8px">
		<tr align="center" bgcolor="#87BBEF" height="22">
			<td width="20%">学生学号</td>
			<td width="20%">课程</td>
			<td width="20%">成绩</td>
			<td width="20%">考试日期</td>
			<td width="20%">操作</td>
		</tr>
		<c:forEach items="${requestScope.gradesList}" var="grades">
			 
				<tr align='center' bgcolor="#FFFFFF"
					onMouseMove="javascript:this.bgColor='red';"
					onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22">
					<td bgcolor="#FFFFFF" align="center">${grades.stu_codename}</td>
					<td bgcolor="#FFFFFF" align="center">${grades.course_name}</td>
					<td bgcolor="#FFFFFF" align="center">${grades.grades}</td>
					<td bgcolor="#FFFFFF" align="center">${grades.xuenian}</td>
					<td bgcolor="#FFFFFF" align="center"><c:if
							test="${sessionScope.role==1||sessionScope.role==2}">
							<input type="button" value="删除" onclick="gradesDel(${grades.id})" />
						</c:if></td>
				</tr>
			 
		</c:forEach>
	</table>
	<table width='98%' border='0' style="margin-top:8px;margin-left: 5px;">
		<TR align="right">
			<TD><form action="" method="post" name="formpage">
					<input type="hidden" name="pageCount"
						value="${requestScope.pagecount}" />
					<!--//用于给上面javascript传值-->
					<input type="hidden" name="page" value="${requestScope.page}" />
					<!--//用于给上面javascript传值-->
					<input type="hidden" name="jumpurl"
						value="<%=path%>/grades?type=gradesMana&<%if (request.getParameter("name") != null) {
				out.print("name=" + request.getParameter("name") + "&");
			}%>" />
					<!--//用于给上面javascript传值-->
					<a href="#" onClick="PageTop()"><strong>首页</strong></a>&nbsp;&nbsp;&nbsp;
					<a href="#" onClick='PagePre()'><strong>上一页</strong></a>&nbsp;&nbsp;&nbsp;
					共${requestScope.cou}条记录, 共计${requestScope.pagecount}页,
					当前第${requestScope.page}页&nbsp;&nbsp;&nbsp; <a href="#"
						onClick="PageNext()"><strong>下一页</strong></a>&nbsp;&nbsp;&nbsp; <a
						href="#" onClick="PageLast()"><strong>尾页</strong></a> 第 <input
						name="busjump" type="text" size="3" value="${requestScope.page}"
						style=" width:15px" /> 页<a href="#" onClick="bjump()"><strong>跳转</strong></a>&nbsp;&nbsp;&nbsp;
				</form> <script type="text/javascript" src="<%=path%>/js/page.js"></script></TD>
		</TR>
	</table>

</body>
</html>
