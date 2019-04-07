<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<link rel="stylesheet" href="<%=path%>/images/StyleSheet.css" type="text/css" />
</head>
<body>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<table width="100%" height="184" border="1" align="center" cellpadding="3" cellspacing="1" bordercolor="#D9E9FF" style="border-collapse:collapse">
  <TBODY>
    <TR 
  align=middle bgColor=#ffffff>
      <td height="37" colspan="2" bgColor=#87BBEF><strong>高校选课管理</strong></td>
    </TR>
    <TR   align=middle 
  bgColor=#ffffff>
      <TD width="10%" align="left" >系统作者：</TD>
      <TD width="41%"  align="left" ><font class="t4">xxxxxx</font></TD>
    </TR>
    <TR   align=middle 
  bgColor=#ffffff>
      <TD align="left"  >指导老师：</TD>
      <TD  align="left" >xxxxxxx</TD>
    </TR>
    <TR   align=middle 
  bgColor=#ffffff>
      <TD align="left"  >联系方式：</TD>
      <TD  align="left" ><font class="t41">xxxxxxxxxxxxxxx</font></TD>
    </TR>
  </TBODY>
</TABLE>
<p></p>
</BODY>
</HTML>
