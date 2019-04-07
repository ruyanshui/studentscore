<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<title>学生成绩管理系统</title>
</head>
<frameset rows="66,*,4" frameborder="no" border="0" framespacing="0">
	<frame src="<%=path %>/admin/top.jsp" noresize="noresize" id="topFrame" frameborder="0" name="topFrame" marginwidth="0" marginheight="0" scrolling="no">
	<frameset rows="*" cols="185,*" id="frame" framespacing="0" frameborder="no" border="0">
		<frame src="<%=path %>/admin/left.jsp" name="leftFrame" id="leftFrame" noresize="noresize" marginwidth="0" marginheight="0" frameborder="0" scrolling="yes">
		<frame src="<%=path %>/admin/main.jsp" name="main" id="main" marginwidth="8" marginheight="5" frameborder="0" scrolling="yes">
	</frameset>
	<frame src="<%=path %>/admin/down.jsp" noresize="noresize" id="bottomFrame" frameborder="0" name="bottomFrame" marginwidth="0" marginheight="0" scrolling="no">
<noframes>
	<body>当前浏览器不支持框架!</body>
</noframes>
</frameset>
</html>
