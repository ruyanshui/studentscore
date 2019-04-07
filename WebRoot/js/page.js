

function PageTop(){
   var jumpurl=formpage.jumpurl.value;
   var page=parseInt(formpage.page.value);
 if(page<=1){
    alert("已至第一页");
  }else{
   	formpage.action=jumpurl+"page=1";
    formpage.submit();
  }
}
function PageLast(){
   var jumpurl=formpage.jumpurl.value;
   var page=parseInt(formpage.page.value);
  var pageCount=parseInt(formpage.pageCount.value);
  if(page>=pageCount){
    alert("已至最后一页");
  }else{
   if(formpage.pageCount.value==0){//如果总页数为0，那么最后一页为1，也就是第一页，而不是第0页
    formpage.action=jumpurl+"page=1";
    formpage.submit();
	}else{
	formpage.action=jumpurl+"page="+formpage.pageCount.value;
    	formpage.submit();
	}
  }
}

//上一页
function PagePre(){
   var jumpurl=formpage.jumpurl.value;
  var page=parseInt(formpage.page.value);
  if(page<=1){
    alert("已至第一页");
  }else{
    formpage.action=jumpurl+"page="+(page-1);
    formpage.submit();
  }
}

function PageNext(){
   var jumpurl=formpage.jumpurl.value;
  var page=parseInt(formpage.page.value);
  var pageCount=parseInt(formpage.pageCount.value);
  if(page>=pageCount){
    alert("已至最后一页");
  }else{
    formpage.action=jumpurl+"page="+(page+1);
    formpage.submit();
  }
}
function bjump(){
   var jumpurl=formpage.jumpurl.value;
  	var pageCount=parseInt(formpage.pageCount.value);
  	if( fIsNumber(formpage.busjump.value,"1234567890")!=1 ){
		alert("跳转文本框中只能输入数字!");
		formpage.busjump.select();
		formpage.busjump.focus();
		return false;
	}
	
	if(formpage.busjump.value>pageCount){//如果跳转文本框中输入的页数超过最后一页的数，则跳到最后一页
		  if(pageCount==0){	
			  formpage.action=jumpurl+"page=1";
			  formpage.submit();
		  }else{
			formpage.action=jumpurl+"?page="+pageCount;
			formpage.submit();
		  }
    }else if(formpage.busjump.value<=pageCount){
			var page=parseInt(formpage.busjump.value);
			   if(page==0){
				  page=1;//如果你输入的是0，那么就让它等于1
				  formpage.action=jumpurl+"page="+page;
				  formpage.submit();
			   }else{
				  formpage.action=jumpurl+"page="+page;
				  formpage.submit();
			   }

	  }

}


//****判断是否是Number.
function fIsNumber (sV,sR){
var sTmp;
if(sV.length==0){ return (false);}
for (var i=0; i < sV.length; i++){
sTmp= sV.substring (i, i+1);
if (sR.indexOf (sTmp, 0)==-1) {return (false);}
}
return (true);
}