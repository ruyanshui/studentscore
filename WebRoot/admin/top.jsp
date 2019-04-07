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
 <title>头部</title>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9" />
<link rel="stylesheet" type="text/css" href="<%=path %>/css/top.css" />
<script type="text/javascript" src="<%=path %>/js/jquery.min.js"></script>

<script language="javascript" type="text/javascript" charset="utf-8" src="<%=path %>/js/topmenu.js"></script>

<script language="javascript" type="text/javascript">

var displayBar=true;
function switchBar(obj)
{
	if (document.all) //IE
	{
		if (displayBar)
		{
			parent.frame.cols="0,*";
			displayBar=false;
			obj.value="打开左边菜单";
		}
		else{
			parent.frame.cols="210,*";
			displayBar=true;
			obj.value="关闭左边菜单";
		}
	}
	else //Firefox 
	{  
		if (displayBar)
		{
			self.top.document.getElementById('frame').cols="0,*";
			displayBar=false;
			obj.value="打开左边菜单";
		}
		else{
			self.top.document.getElementById('frame').cols="210,*";
			displayBar=true;
			obj.value="关闭左边菜单";
		}
	}
}
</script><script type="text/javascript">
	    function logout()
		{
		   if(confirm("确定要退出本系统吗??"))
		   {
			   window.parent.location="<%=path %>/login.jsp";
		   }
		}
	</script>
</head>

<body oncontextmenu="return false" ondragstart="return false" onSelectStart="return false">
<div class="top_box">
    <div class="top_logo"><div align="center" style="color: #CCFFCC;font-size: 16pt;font-weight: bold;">高校选课管理</div></div>
    <div class="top_nav">
      <div class="top_nav_sm">
		 		 <span style="float:right; padding-right:12px">  	<a href="#" onclick="logout()">安全注销</a>    </span>
		 		你好！${sessionScope.admin.userName }&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;		</div>
          
    </div>
  
</div>
</body>
</html>


 