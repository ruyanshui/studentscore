<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9" />
<link href="<%=path%>/css/left.css" type="text/css" rel="stylesheet" />
<title>左侧菜单</title>
<script src="<%=path%>/js/jquery.min.js"></script>
<script language="javascript" type="text/javascript" charset="utf-8"
	src="<%=path%>/js/admin.js"></script>
</head>

<body oncontextmenu="return false" ondragstart="return false"
	onSelectStart="return false">

	<div class="div_bigmenu">
		<div class="div_bigmenu_nav_down" id="nav_1"
			onclick="javascript:lefttoggle(1);">系统用户管理</div>
		<ul id="ul_1">
			<li><a href="<%=path%>/admin/userinfo/userPw.jsp" target='main'>修改密码</a></li>
		</ul>
	</div>



	<c:if test="${sessionScope.role==1}">

		<div class="div_bigmenu">
			<div class="div_bigmenu_nav_down" onclick="javascript:lefttoggle(2);"
				id="nav_2">信息管理</div>
			<ul id="ul_2">
				<li><a href='<%=path%>/specialty?type=specialtyMana'
					target='main'>专业管理</a></li>
				<li><a href='<%=path%>/classes?type=classesMana' target='main'>班级管理</a>
				</li>
				<li><a href='<%=path%>/course?type=courseMana' target='main'>课程管理</a>
				</li>
			</ul>
		</div>

		<div class="div_bigmenu">
			<div class="div_bigmenu_nav_down" onclick="javascript:lefttoggle(3);"
				id="nav_3">人员管理</div>
			<ul id="ul_3">
				<li><a href='<%=path%>/tea?type=teaMana' target='main'>老师管理</a>
				</li>
				<li><a href='<%=path%>/stu?type=stuMana' target='main'>学生管理</a>
				</li>

			</ul>
		</div>

	</c:if>
		<div class="div_bigmenu">
		<div class="div_bigmenu_nav_down" onclick="javascript:lefttoggle(4);" id="nav_4">选课管理</div>
		<ul id="ul_4">
			                <li>     <a href='<%=path %>/stu?type=stuAll' target='main'>学生选课管理</a> </li>

			 
		</ul>
		</div>


	<div class="div_bigmenu">
		<div class="div_bigmenu_nav_down" onclick="javascript:lefttoggle(5);"
			id="nav_5">成绩管理</div>
		<ul id="ul_5">
			<li><a href='<%=path%>/grades?type=gradesMana' target='main'>学生成绩管理</a>
			</li>
		</ul>
	</div>

	<c:if test="${sessionScope.role==1|| sessionScope.role==2}">

	<div class="div_bigmenu">
		<div class="div_bigmenu_nav_down" onclick="javascript:lefttoggle(6);"
			id="nav_6">成绩统计</div>
		<ul id="ul_6">
			<li><a href='<%=path%>/tongji?type=tongjiPingjun' target='main'>平均成绩</a></li>
			<li><a href='<%=path%>/tongji?type=tongjiDengji' target='main'>成绩等级</a></li>
			<li><a href='<%=path%>/tongji?type=tongjiYouxiu' target='main'>优秀人数</a></li>
			<li><a href='<%=path%>/tongji?type=tongjiZhong' target='main'> 总成绩 </a></li>

		</ul>
	</div>
	</c:if>

	<div class="div_bigmenu">
		<div class="div_bigmenu_nav_down" onclick="javascript:lefttoggle(7);" id="nav_7">结果分析</div>
		<ul id="ul_7">
	    <li><a href='<%=path%>/report?type=schoolReport' target='main'>学校报表</a></li>
		<li><a href='<%=path%>/report?type=gradeReport' target='main'>年级报表</a></li>
		<li><a href='<%=path%>/report?type=classesReport' target='main'>班级报表</a></li>
		<li><a href='<%=path%>/report?type=studentReport' target='main'>学生报表</a></li>

	</ul>
	</div>

</body>
</html>




