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
           
           
        </script>

</head>

<body leftmargin="2" topmargin="2"
	background='<%=path%>/images/allbg.gif'>

	<form action="<%=path%>/tongji?type=tongjiYouxiu" name="formAdd"
		method="post">
		日期范围: 开始<input name="dateinfo1" readonly="readonly" class="Wdate"
			type="text"
			onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" /> 结束<input
			name="dateinfo2" readonly="readonly" class="Wdate" type="text"
			onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" /> 
			
			 <select name="classes_id" id="classes_id" onclick="classesAll()">
									              <option value="0">请选择班级</option>
									          </select>
			
			
			 <input
			type="submit" value="分析" /> <img id="indicator" src="<%=path %>/images/loading.gif" style="display:none"/>
	</form>


	<div id="youxiuline" style="width:98%;height:400px;"></div>
	<script type="text/javascript">
		// 基于准备好的dom，初始化echarts实例
		var myChart4 = echarts.init(document.getElementById('youxiuline'));
		option4 = {
			title : {
				text : " 优秀人数曲线"
			},
			tooltip : {
				trigger : 'axis',
				axisPointer : {
					type : 'cross',
					crossStyle : {
						color : '#999'
					}
				},
				textStyle : {
					align : 'right'
				}
			},
			toolbox : {
				feature : {
					dataView : {
						show : true,
						readOnly : false
					},
					magicType : {
						show : true,
						type : [ 'line' ]
					},
					restore : {
						show : true
					},
					saveAsImage : {
						show : true
					}
				}
			},
			legend : {
				data : [ '优秀人数']
			},
			yAxis : [
				{
					type : 'value',
					name : '人数',
					axisLabel : {
						formatter : '{value} '
					}
				}
			],
			xAxis : [
				{
					type : 'category',
					axisPointer : {
						type : 'shadow'
					}
				}
			],
			series : [
				{
					name : '优秀人数',
					type : 'line',
					itemStyle : {
						normal : {
							barBorderRadius : 5
						}
					},
					label : {
						/*每个柱子上面是有数字显示的，而且数字是有千分位符号的：*/
						normal : {
							show : true,
							position : 'top'
						}
					} 
				} 
			]
		};
		// 使用刚指定的配置项和数据显示图表。
		myChart4.setOption(option4);
		myChart4.setOption({
			title: {text:  '${requestScope.dateinfo} 优秀人数曲线分析'},
           
			xAxis : [
				{
					data : ${requestScope.dateList}
				}
			],
			series : [
				{
					data : ${requestScope.youxiuList}
				} 
			]
		});
	</script>












 


</body>
</html>
