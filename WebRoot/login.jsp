<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>








<html>
<head>
<title>系统</title>
 <style type="text/css">
<!--
*{overflow:hidden; font-size:9pt;}
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-repeat: repeat-x;
	background-image: url(images/bg.jpg);
	background-color: #ffffff;
	/*background-color: #1d3e47;另一种颜色*/
}

-->
</style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<script type='text/javascript' src='<%=path %>/dwr/interface/loginService.js'></script>
        <script type='text/javascript' src='<%=path %>/dwr/engine.js'></script>
        <script type='text/javascript' src='<%=path %>/dwr/util.js'></script>
        
		<script language="javascript">
		function check1()
		{                                                                                         
		     if(document.ThisForm.userName.value=="")
			 {
			 	alert("请输入用户名");
				document.ThisForm.userName.focus();
				return false;
			 }
			 if(document.ThisForm.userPw.value=="")
			 {
			 	alert("请输入密码");
				document.ThisForm.userPw.focus();
				return false;
			 }
			 document.getElementById("indicator").style.display="block";
			 loginService.login(document.ThisForm.userName.value,document.ThisForm.userPw.value,document.ThisForm.role.value,callback);
		}
		
		function callback(data)
		{
		    document.getElementById("indicator").style.display="none";
		    if(data=="no")
		    {
		        alert("用户名或密码错误");
		    }
		    if(data=="yes")
		    {
		        alert("通过验证,系统登录成功");
		        window.location.href="<%=path %>/loginSuccess.jsp";
		    }
		    
		}
	    </script>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="1000" height="564" border="0" align="center" cellpadding="0" cellspacing="0" background="images/login.jpg" id="__01">
  <tr>
    <td height="193" colspan="3"><div align="center" style="color: #CCFFCC;font-size: 26pt;font-weight: bold;">高校选课管理系统</div></td>
  </tr>
  <tr>
    <td width="352" rowspan="2" valign="top">&nbsp;</td>
    <td width="211" height="152"><table width="195" border="0" align="right" cellpadding="0" cellspacing="0">
      <form name="ThisForm" action="<%=path %>/adminLogin.action" method=post>
        <tr>
          <td width="47" height="30">用户:</td>
          <td height="30" colspan="2"><input name="userName" type="text" id="userName" style="width:100px; height:16px; border:solid 1px #000000; color:#666666"></td>
        </tr>
        <tr>
          <td height="30">密码:</td>
          <td height="30" colspan="2"><input name="userPw" type="password" id="userPw" style="width:100px; height:16px; border:solid 1px #000000; color:#666666"></td>
        </tr>
        
        <tr  >
          <td height="30">权限:</td>
          <td height="30" colspan="2"><select name="role" id="role">
                                                          <option value="1">管理员</option>
                                                         <option value="2">教师</option>
                                                          <option value="3">学生</option>
                                                        </select></td>
        </tr>
		 
        <tr>
          <td height="30" colspan="3">
          
      
                                        

          <input type="button" name="button"  onClick="check1();"  value="登 陆"  style="background:url(images/button.gif) no-repeat;color:#ffffff;width:80px;height: 24px; border:0px;line-height:10px; font-size:12px;margin-right: 5px; cursor:pointer">
                
                                         <img id="indicator" src="<%=path %>/images/loading.gif" style="display:none"/>

                
            </td>
        </tr>
      </form>
    </table></td>
    <td width="437" rowspan="2">&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>
</body>
</html>



 