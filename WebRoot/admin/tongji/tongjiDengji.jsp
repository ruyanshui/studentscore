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

<link rel="stylesheet" type="text/css" href="<%=path%>/css/base.css" />
<script type="text/javascript"
	src="<%=path%>/My97DatePicker/WdatePicker.js"></script>
<script src="<%=path%>/js/echarts.common.min.js"></script>


<script type='text/javascript' src='<%=path %>/dwr/interface/loginService.js'></script>
		<script type='text/javascript' src='<%=path %>/dwr/engine.js'></script>
		<script type='text/javascript' src='<%=path %>/dwr/util.js'></script>
        <script language="javascript">
           var i=0;
           function classesAll()
           {
               if(i==0)
               {
                   document.getElementById("indicator").style.display="block";
                   loginService.classesAll(callback);
                   i=1;
               }
               
           }
           function callback(data)
           {
               document.getElementById("indicator").style.display="none";
               DWRUtil.addOptions("classes_id",data,"id","name");
           }
         
         
            var j=0;
         
           function courseAll()
           {
               if(j==0)
               {
                   document.getElementById("indicator1").style.display="block";
                   loginService.courseAll(callback1);
                   j=1;
               }
               
           }
           function callback1(data)
           {
               document.getElementById("indicator1").style.display="none";
               DWRUtil.addOptions("course_id",data,"id","name");
           }
           
           
          /* function check()
           {
               if(document.formAdd.stu_id.value==0)
               { 
                   alert("请选择学生");
                   return false;
               }
               if(document.formAdd.course_id.value==0)
               { 
                   alert("请选择课程");
                   return false;
               }
               return true;
           }
           */
        </script>

</head>

<body leftmargin="2" topmargin="2"
	background='<%=path%>/images/allbg.gif'>

	<form action="<%=path%>/tongji?type=tongjiDengji" name="formAdd"
		method="post">
		日期范围: 开始<input name="dateinfo1" readonly="readonly" class="Wdate"
			type="text"
			onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" /> 结束<input
			name="dateinfo2" readonly="readonly" class="Wdate" type="text"
			onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
			
			
			 <select name="classes_id" id="classes_id" onclick="classesAll()">
									              <option value="0">请选择班级</option>
									          </select>
			<select name="course_id" id="course_id" onclick="courseAll()">
									              <option value="0">请选择课程</option>
									          </select>
							               
							                  
			
			 <input
			type="submit" value="分析" />
			 <img id="indicator" src="<%=path %>/images/loading.gif" style="display:none"/>
			<img id="indicator1" src="<%=path %>/images/loading.gif" style="display:none"/>
	</form>





	<div id="pie" style="width:98%;height:400px;"></div>


	<script type="text/javascript">
		// 基于准备好的dom，初始化echarts实例
		var myChart3 = echarts.init(document.getElementById('pie'));
		option3 = {
			title : {
				text : " 成绩等级饼图"
			},
			tooltip : {
				trigger : 'item',
				/*鼠标划过标签*/
            /* formatter: "{b} : {c} ({d}%)"B对应name c对应 value  d对应百分比 */
            formatter: function (num) {
                //console.log(num);
                return num.name + ":" +  (num.value ) + "(" + num.percent + "%)";
            }
			},
			series: [
            {
                type: 'pie',
                radius: '60%',
                center: ['50%', '60%'],
                label: {
                    normal: {
                        /*浮动标签样式*/
                        /* formatter: "{b} : {c} ({d}%)"B对应name c对应 value  d对应百分比 */
                        formatter: function (num) {
                            //console.log(num);
                            return num.name + ":" +  (num.value ) + "(" + num.percent + "%)";
                        }
                    }
                },
                data: [],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
		};
		// 使用刚指定的配置项和数据显示图表。
		myChart3.setOption(option3);
		myChart3.setOption({
			
            
            title: {text:  '${requestScope.dateinfo} 成绩等级饼图'},
           series: [
                {
                    data: ${requestScope.dengjiList}
                }
            ] 
		});
	</script>










 


</body>
</html>
