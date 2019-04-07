<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />

<link rel="stylesheet" type="text/css" href="<%=path%>/css/base.css" />
<link rel="stylesheet" href="<%=path%>/css/bootstrap.min.css">  
<script type="text/javascript"
    src="<%=path%>/My97DatePicker/WdatePicker.js"></script>
<script src="<%=path%>/js/echarts.common.min.js"></script>
<script type='text/javascript' src='<%=path %>/js/jquery.min.js'></script>
<script type='text/javascript' src='<%=path %>/dwr/interface/loginService.js'></script>
<script type='text/javascript' src='<%=path %>/js/bootstrap.min.js'></script>
<script type='text/javascript' src='<%=path %>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=path %>/dwr/util.js'></script>
<script language="javascript">
   var i=0;
   function classesAll(){
      if(i==0){
          document.getElementById("indicator").style.display="block";
          loginService.classesAll(callback);
          i=1;
      }
   }
   function callback(data) {
      document.getElementById("indicator").style.display="none";
      DWRUtil.addOptions("classes_id",data,"id","name");
   }   
       
</script>
</head>
<body leftmargin="2" topmargin="2" background='<%=path%>/images/allbg.gif'>
    <form action="<%=path%>/report?type=classesReport" name="classesReport"
    method="post">
    日期<input name="dateinfo1" readonly="readonly" class="Wdate"
    type="text" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
    班年级：
    <select name="classes_id" id="classes_id" onclick="classesAll()">
    <option value="0">请选择班级</option>
    </select>


    <input type="submit" value="查询" />
    <img id="indicator" src="<%=path %>/images/loading.gif" style="display:none"/>
    <img id="indicator1" src="<%=path %>/images/loading.gif" style="display:none"/>
    </form>
	
	<div>
	  <h2>今日考勤</h2>
	  <table class="table table-striped" style="width:100%;">
		<thead>
			<tr>
				<th>时间</th>
				<th>科目</th>
				<th>缺勤人数</th>
				<th>名字</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>1</td>
				<td>Anna</td>
				<td>1</td>
				<td>Anna</td>
			</tr>
			<tr>
				<td>2</td>
				<td>Debbie</td>
				<td>2</td>
				<td>Debbie</td>
			</tr>
			<tr>
				<td>3</td>
				<td>John</td>
				<td>3</td>
				<td>John</td>
			</tr>
		</tbody>
	  </table>
	</div>
	
	<h2>今日心里健康状态分布</h2>
    <div id="health" align="center">
	    <div id="feeling" style="width:500px;height:500px;float:left"></div>
	    <div id="relationShip" style="width:500px;height:500px;display:inline"></div>
    </div>
    
    <h2>今日学业自律性分布</h2>
    <div id="learningStatus" align="center">
	    <div id="learning"  style="width:500px;height:500px;float:left"></div>
	    <div id="spirts" style="width:500px;height:500px;display:inline"></div>
    </div>
	
	<h2>学科兴趣分布</h2>
    <div id="subjectInterest1"  >
    	<div id = "subjectInterest" style="width:100%;height:500px;"></div>
    </div>
    
    <h2>校园安全预警</h2>
    <div id="xiaoyuananquanyujing">    
    	<div ></div>
    </div>
	
	<h2>    历史状态信息</h2>
    <div id = "historyInfo">
	    <div id="hisqingxuzhuangtai" style="width:100%;height:400px;"></div>
	    <div id="hisjingshenzhuangtai" style="width:100%;height:400px;"></div>
	    <div id="histxuexizhuangtai" style="width:100%;height:400px;"></div>
    </div>

    <script type='text/javascript'>
    // 考勤表格
    var resultAttendenceData = ${classesResultList}
    console.log("resultAttendenceData: " ,resultAttendenceData)

    var feelingData = ${classesFeelingList}
    var happy ;
    var diluo;
    var normal ;
    for(var i = 0; i < feelingData.length; i++) {
    var emotion_stat = feelingData[i].student_emotion;
    var emotionData = feelingData[i].percentage;
    if (emotion_stat == 2) {
    diluo = emotionData;
    }else if (emotion_stat == 1) {
    happy = emotionData;
    }else {
    normal = emotionData;
    }

    }

    var ydata = [];
    var happyres = {value: '', name :'开心'};
    happyres.value = happy;
    var shiluores = {value: '', name :'低落'};
    shiluores.value = diluo;
    var normalres = {value: '', name :'正常'};
    normalres.value = normal;
    ydata.push(happyres);
    ydata.push(shiluores);
    ydata.push(normalres);

    var myChart = echarts.init(document.getElementById("feeling"));
    option = {
    title : {
	    text: '情绪状态',
	    x:'center'
    },
    tooltip : {
    trigger: 'item',
    formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
    orient: 'vertical',
    left: 'left',
    data: ['低落','正常','开心']
    },
    series : [
    {
    name: '访问来源',
    type: 'pie',
    radius : '55%',
    center: ['50%', '60%'],
    data:ydata,
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
    myChart.setOption(option);

    // 人际关系
    var relationShipListData = ${classesRelationShipList};
    var alone ;
    var verygood;
    var good;
    var normal1;
    for(var i = 0; i < relationShipListData.length; i++) {
    var relationship_stat = relationShipListData[i].student_relationship;
    var relationPercentageData = relationShipListData[i].percentage;

    if (relationship_stat == 0) {
    alone = relationPercentageData;
    }else if (relationship_stat == 1) {
    verygood = relationPercentageData;
    }else if (relationship_stat == 2){
    normal1 = relationPercentageData;
    }else {
    good = relationPercentageData;
    }

    }

    var ydata1 = [];
    var aloneres = {value: '', name :'孤僻'};
    aloneres.value = alone;
    var verygoodres = {value: '', name :'非常好'};
    verygoodres.value = verygood;
    var goodres = {value: '', name :'良好'};
    goodres.value = good;
    var normal1res = {value: '', name : '正常'};
    normal1res.value = normal1;

    ydata1.push(aloneres);
    ydata1.push(verygoodres);
    ydata1.push(goodres);
    ydata1.push(normal1res);

    var myChart1 = echarts.init(document.getElementById("relationShip"));
    option = {
    title : {
    text: '人际关系',
    x:'center'
    },
    tooltip : {
    trigger: 'item',
    formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
    orient: 'vertical',
    left: 'left',
    data: ['孤僻','非常好','良好','正常']
    },
    series : [
    {
    name: '访问来源',
    type: 'pie',
    radius : '55%',
    center: ['50%', '60%'],
    data:ydata1,
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
    myChart1.setOption(option);

    var learningData = ${classesLearningStateList};
    console.log("learningData", learningData);

    var studybujia;
    var studyverygood;
    var studygood;
    var studynormal1;
    for(var i = 0; i < learningData.length; i++) {
    var study_stat = learningData[i].student_study_stat;
    var studyPercentageData = learningData[i].percentage;

    if (study_stat == 1) {
    studyverygood = studyPercentageData;
    }else if (study_stat == 2) {
    studygood = studyPercentageData;
    }else if (study_stat == 3){
    studynormal1 = studyPercentageData;
    }else {
    studybujia = studyPercentageData;
    }

    }

    var ydata2 = [];
    var studybujiares = {value: '', name :'不佳'};
    studybujiares.value = studybujia;
    var studyverygoodres = {value: '', name :'非常好'};
    studyverygoodres.value = studyverygood;
    var studygoodres = {value: '', name :'良好'};
    studygoodres.value = studygood;
    var studynormal1res = {value: '', name : '正常'};
    studynormal1res.value = studynormal1;

    ydata2.push(studybujiares);
    ydata2.push(studyverygoodres);
    ydata2.push(studygoodres);
    ydata2.push(studynormal1res);

    var myChart2 = echarts.init(document.getElementById("learning"));
    option = {
    title : {
    text: '学习状态',
    x:'center'
    },
    tooltip : {
    trigger: 'item',
    formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
    orient: 'vertical',
    left: 'left',
    data: ['不佳','非常好','良好','正常']
    },
    series : [
    {
    name: '访问来源',
    type: 'pie',
    radius : '55%',
    center: ['50%', '60%'],
    data:ydata2,
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
    myChart2.setOption(option);
    // 精神
    var spiritStateList = ${classesSpiritStateList};

    var spiritpibei;
    var spiritgood;
    var spiritnormal1;
    for(var i = 0; i < spiritStateList.length; i++) {
    var spirit_stat = spiritStateList[i].student_mental_stat;
    var spiritPercentageData = spiritStateList[i].percentage;

    if (spirit_stat == 0) {
    spiritpibei = spiritPercentageData;
    }else if (spirit_stat == 1) {
    spiritgood = spiritPercentageData;
    }else if (spirit_stat == 2){
    spiritnormal1 = spiritPercentageData;
    }

    }

    var ydata3 = [];
    var spiritpibeires = {value: '', name :'疲惫'};
    spiritpibeires.value = spiritpibei;
    var spiritgoodres = {value: '', name :'积极'};
    spiritgoodres.value = spiritgood;
    var spiritnormal1res = {value: '', name : '正常'};
    spiritnormal1res.value = spiritnormal1;

    ydata3.push(spiritpibeires);
    ydata3.push(spiritgoodres);
    ydata3.push(spiritnormal1res);

    var myChart3 = echarts.init(document.getElementById("spirts"));
    option = {
    title : {
    text: '精神状态',
    x:'center'
    },
    tooltip : {
    trigger: 'item',
    formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
    orient: 'vertical',
    left: 'left',
    data: ['疲惫','积极','正常']
    },
    series : [
    {
    name: '访问来源',
    type: 'pie',
    radius : '55%',
    center: ['50%', '60%'],
    data:ydata3,
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
    myChart3.setOption(option);

    var subjectInterestData = ${classesSubjectInterestList}
    console.log("subjectInterestData ;",subjectInterestData)
    var xdata = [];
    var ydata = [];
    for(var j = 0; j <  subjectInterestData.length; j ++) {
    var subjectname = subjectInterestData[j].student_interest;
    var subjectNum = subjectInterestData[j].total;
    xdata.push(subjectname);
    ydata.push(parseInt(subjectNum));
    }
    console.log("xdata",xdata)
    console.log("ydata",ydata)
    mycharts6 = echarts.init(document.getElementById("subjectInterest"),
    option = {
    color: ['#3398DB'],
    tooltip : {
    trigger: 'axis',
    axisPointer : {            // 坐标轴指示器，坐标轴触发有效
    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
    }
    },
    grid: {
    left: '3%',
    right: '4%',
    bottom: '3%',
    containLabel: true
    },
    xAxis : [
    {
    type : 'category',
    data : xdata,
    axisTick: {
    alignWithLabel: true
    }
    }
    ],
    yAxis : [
    {
    type : 'value'
    }
    ],
    series : [
    {
    name:'直接访问',
    type:'bar',
    barWidth: '60%',
    data:ydata,
    }
    ]
    });
    mycharts6.setOption(option)

    // 情绪不佳
    var qingxubujiaData = ${classesqingxubujia};
    var xuexizhuangtaibujiaData = ${claasesjingshenbujia};
    var jingshenzhuangtaibujiaData = ${classesXuexibujia};
    console.log("qingxubujiaData ：",qingxubujiaData)
    console.log("xuexizhuangtaibujiaData ：",xuexizhuangtaibujiaData)
    console.log("jingshenzhuangtaibujiaData ：",jingshenzhuangtaibujiaData)


    // 历史状态信息
    // 情绪状态
    var qingxuhistoryData = ${classeshistoryqingxu}
    console.log("qingxuhistoryData: ", qingxuhistoryData)
    var mychartshistoryqingxu  = echarts.init(document.getElementById("hisqingxuzhuangtai"))
    option = {
    title: {
    text: '情绪状态'
    },
    tooltip: {
    trigger: 'axis'
    },
    legend: {
    data:['邮件营销','联盟广告','视频广告','直接访问','搜索引擎']
    },
    grid: {
    left: '3%',
    right: '4%',
    bottom: '3%',
    containLabel: true
    },
    toolbox: {
    feature: {
    saveAsImage: {}
    }
    },
    xAxis: {
    type: 'category',
    boundaryGap: false,
    data: ['周一','周二','周三','周四','周五','周六','周日']
    },
    yAxis: {
    type: 'value'
    },
    series: [
    {
    name:'邮件营销',
    type:'line',
    stack: '总量',
    data:[120, 132, 101, 134, 90, 230, 210]
    },
    {
    name:'联盟广告',
    type:'line',
    stack: '总量',
    data:[220, 182, 191, 234, 290, 330, 310]
    },
    {
    name:'视频广告',
    type:'line',
    stack: '总量',
    data:[150, 232, 201, 154, 190, 330, 410]
    },
    {
    name:'直接访问',
    type:'line',
    stack: '总量',
    data:[320, 332, 301, 334, 390, 330, 320]
    },
    {
    name:'搜索引擎',
    type:'line',
    stack: '总量',
    data:[820, 932, 901, 934, 1290, 1330, 1320]
    }
    ]
    };
    mychartshistoryqingxu.setOption(option);
    // 历史-精神状态
    var jingshenhistoryData = ${classeshistoryjingshen}
    console.log("jingshenhistoryData: ", jingshenhistoryData)
    var mychartshistoryjingshen  = echarts.init(document.getElementById("hisjingshenzhuangtai"))
    option = {
    title: {
    text: '精神状态'
    },
    tooltip: {
    trigger: 'axis'
    },
    legend: {
    data:['邮件营销','联盟广告','视频广告','直接访问','搜索引擎']
    },
    grid: {
    left: '3%',
    right: '4%',
    bottom: '3%',
    containLabel: true
    },
    toolbox: {
    feature: {
    saveAsImage: {}
    }
    },
    xAxis: {
    type: 'category',
    boundaryGap: false,
    data: ['周一','周二','周三','周四','周五','周六','周日']
    },
    yAxis: {
    type: 'value'
    },
    series: [
    {
    name:'邮件营销',
    type:'line',
    stack: '总量',
    data:[120, 132, 101, 134, 90, 230, 210]
    },
    {
    name:'联盟广告',
    type:'line',
    stack: '总量',
    data:[220, 182, 191, 234, 290, 330, 310]
    },
    {
    name:'视频广告',
    type:'line',
    stack: '总量',
    data:[150, 232, 201, 154, 190, 330, 410]
    },
    {
    name:'直接访问',
    type:'line',
    stack: '总量',
    data:[320, 332, 301, 334, 390, 330, 320]
    },
    {
    name:'搜索引擎',
    type:'line',
    stack: '总量',
    data:[820, 932, 901, 934, 1290, 1330, 1320]
    }
    ]
    };
    mychartshistoryjingshen.setOption(option);
    // 历史-学习

    var xuexihistoryData = ${classeshistoryXuexi}
    console.log("xuexihistoryData: ", xuexihistoryData)
    var mychartshistoryjxuexi  = echarts.init(document.getElementById("histxuexizhuangtai"))
    option = {
    title: {
    text: '学习状态'
    },
    tooltip: {
    trigger: 'axis'
    },
    legend: {
    data:['邮件营销','联盟广告','视频广告','直接访问','搜索引擎']
    },
    grid: {
    left: '3%',
    right: '4%',
    bottom: '3%',
    containLabel: true
    },
    toolbox: {
    feature: {
    saveAsImage: {}
    }
    },
    xAxis: {
    type: 'category',
    boundaryGap: false,
    data: ['周一','周二','周三','周四','周五','周六','周日']
    },
    yAxis: {
    type: 'value'
    },
    series: [
    {
    name:'邮件营销',
    type:'line',
    stack: '总量',
    data:[120, 132, 101, 134, 90, 230, 210]
    },
    {
    name:'联盟广告',
    type:'line',
    stack: '总量',
    data:[220, 182, 191, 234, 290, 330, 310]
    },
    {
    name:'视频广告',
    type:'line',
    stack: '总量',
    data:[150, 232, 201, 154, 190, 330, 410]
    },
    {
    name:'直接访问',
    type:'line',
    stack: '总量',
    data:[320, 332, 301, 334, 390, 330, 320]
    },
    {
    name:'搜索引擎',
    type:'line',
    stack: '总量',
    data:[820, 932, 901, 934, 1290, 1330, 1320]
    }
    ]
    };
    mychartshistoryjxuexi.setOption(option);
    </script>

    </body>
</html>