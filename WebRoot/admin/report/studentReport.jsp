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
<link rel="stylesheet" href="<%=path%>/css/bootstrap.min.css">  
<script type="text/javascript" src="<%=path%>/My97DatePicker/WdatePicker.js"></script>
<script type='text/javascript' src='<%=path %>/js/jquery.min.js'></script>
<script type='text/javascript' src='<%=path %>/js/bootstrap.min.js'></script>
<script src="<%=path%>/js/echarts.common.min.js"></script>
<script type='text/javascript' src='<%=path %>/dwr/interface/loginService.js'></script>
<script type='text/javascript' src='<%=path %>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=path %>/dwr/util.js'></script>

<script language="javascript">
    var i=0;
    function classesAll() {
     if(i==0) {
      document.getElementById("indicator").style.display="block";
      loginService.classesAll(callback);
      i=1;
     }
    }
    function callback(data){
     document.getElementById("indicator").style.display="none";
     DWRUtil.addOptions("classes_id",data,"id","name");
    }


    var j=0;

    function stuAll(){
     if(j==0){
      document.getElementById("indicator1").style.display="block";
      loginService.stuAll(callback1);
      j=1;
     }
    }
    function callback1(data) {
     document.getElementById("indicator1").style.display="none";
     DWRUtil.addOptions("stu_id",data,"id","code");
    }
</script>
</head>
<body leftmargin="2" topmargin="2" background='<%=path%>/images/allbg.gif'>

        <form action="<%=path%>/report?type=studentReport" name="studentReport"method="post">
        日期范围:
	        开始<input name="startTime"  readonly="readonly" class="Wdate" type="text" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" /> 
	        结束<input name="endTime" readonly="readonly" class="Wdate" type="text" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
		
		<button type="button" class="btn btn-primary">查询</button>
        <img id="indicator" src="<%=path %>/images/loading.gif" style="display:none"/>
        <img id="indicator1" src="<%=path %>/images/loading.gif" style="display:none"/>
        <div>  
        	班年级
	        <input name="classesName" type="text" />
        	学生
	        <select name="stu_id" id="stu_id" onclick="stuAll()">
	        	<option value="0">请选择学生</option>
	        </select>
        </div>

        </form>
		
        <div id="gexinghuafazhan">
        <h1>个性化发展</h1>
        <div id="xuekexingqu">
        	学科兴趣
        <div></div>
        </div>
        </div>
		<h2>心理健康状态</h2>
        <div id="xinlijiangkang" align="center">
	        <div id = "feeling" style="width:500px;height:500px;float:left"></div>
	        <div id = "relationship" style="width:500px;height:500px;display:inline"></div>
	        <div id = "biaoqingshow" style="width:100%;height:500px;">
	        	<h2>表情</h2>
	        </div>
        </div>
		<h2>学业自律性分析</h2>
        <div id="xueye" align="center">
	        <div id = "learng" style="width:500px;height:500px;float:left"></div>
	        <div id = "jingshen" style="width:500px;height:500px;display:inline"></div>
	        <div id = "jingshenzhuangtai" style="width:100%;height:500px;">
	        	<h2>精神状态</h2>
	        </div>
	        <div id = "xuexizhuangtai" style="width:100%;height:500px;">
	        	<h2>学习状态</h2>
	        </div>
        </div>
		<h2>学生综合状态总结</h2>
        <div id="stuzonghezhuangtaizongjie" align="center">
        	<div id="zonghezhuangtaichart" style="width:500px;height:500px;"></div>
        </div>
		
		<h2>学业状态分析</h2>
        <div id = "xueyezhuangtaifenxi" align="center">
        	<div id="xueyezhuangtaifenxichart" style="width:100%;height:500px;"></div>
        </div>

        <script>
        var feelingdata = ${stuFeelingList};
        var happy ;
        var diluo;
        var normal ;
        for(var i = 0; i < feelingdata.length; i++) {
        var emotion_stat = feelingdata[i].student_emotion;
        var emotionData = feelingdata[i].percentage;
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

        var feelingcharts = echarts.init(document.getElementById("feeling"),
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
        });
        feelingcharts.setOption(option);

        // 人际关系
        var relationShipListData = ${stuRelationShipList};
        console.log("relationShipListData", relationShipListData)
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

        var myChart1 = echarts.init(document.getElementById("relationship"));
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

        // 学习状态
        var learningData = ${stuLearningStateList};
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

        var myChart2 = echarts.init(document.getElementById("learng"));
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
        var spiritStateList = ${stuSpiritStateList};
        console.log("spiritStateList", spiritStateList);
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

        var myChart3 = echarts.init(document.getElementById("jingshen"));
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
        // 精神状态趋势图
        var jingshenzhuangtai = ${stuMentalHistory};
        console.log("jingshenzhuangtai", jingshenzhuangtai);
        var xdata = [];
        var ydata = [];
        for(var i = 0 ; i < jingshenzhuangtai.length; i++){
            var time = jingshenzhuangtai[i].dt;
            var stu_mental_state = jingshenzhuangtai[i].student_mental_stat;
        xdata.push(time);
        ydata.push()
        }

        var jingshenzhuangtaicharts = echarts.init(document.getElementById("jingshenzhuangtai"));


        option = {
        xAxis: {
        type: 'category',
        data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
        },
        yAxis: {
        type: 'value'
        },
        series: [{
        data: [820, 932, 901, 934, 1290, 1330, 1320],
        type: 'line'
        }]
        };
        jingshenzhuangtaicharts.setOption(option);

        // 学生综合状态总结
        var stuZongheStateData = ${stuZongheState};
        console.log("stuZongheStateData: " ,stuZongheStateData);
        var stuZongheStateCharts = echarts.init(document.getElementById("zonghezhuangtaichart"));
            option = {
            title : {
            text: '预算 vs 开销（Budget vs spending）',
            subtext: '纯属虚构'
            },
            tooltip : {
            trigger: 'axis'
            },
            legend: {
            orient : 'vertical',
            x : 'right',
            y : 'bottom',
            data:['预算分配（Allocated Budget）','实际开销（Actual Spending）']
            },
            toolbox: {
            show : true,
            feature : {
            mark : {show: true},
            dataView : {show: true, readOnly: false},
            restore : {show: true},
            saveAsImage : {show: true}
            }
            },
            polar : [
            {
            indicator : [
            { text: '销售（sales）', max: 6000},
            { text: '管理（Administration）', max: 16000},
            { text: '信息技术（Information Techology）', max: 30000},
            { text: '客服（Customer Support）', max: 38000},
            { text: '研发（Development）', max: 52000},
            { text: '市场（Marketing）', max: 25000}
            ]
            }
            ],
            calculable : true,
            series : [
            {
            name: '预算 vs 开销（Budget vs spending）',
            type: 'radar',
            data : [
            {
            value : [4300, 10000, 28000, 35000, 50000, 19000],
            name : '预算分配（Allocated Budget）'
            },
            {
            value : [5000, 14000, 28000, 31000, 42000, 21000],
            name : '实际开销（Actual Spending）'
            }
            ]
            }
            ]
            };

            stuZongheStateCharts.setOption(option)

            // 学业状态
            </script>

</body>
</html>