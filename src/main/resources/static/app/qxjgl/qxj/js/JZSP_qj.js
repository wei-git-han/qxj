var url1 = {"url":rootPath +"/leaveorback/info","dataType":"text"};  //审批
var url2 = {"url":rootPath +"/dicvocationsort/dict","dataType":"text"};     //休假类别
var url3 = {"url":rootPath +"/chairmanview/agreeleave","dataType":"text"};//通过
var url4 ={"url":rootPath +"/chairmanview/disagreeleave","dataType":"text"};//不通过
var id = window.location.search.split("id=")[1];
var pageModule = function(){
	var initxjlb = function(){
		$ajax({
			url:url2,
			success:function(data){
				initselect("xjlb",data.xjlb);
			}
		})
	}
	var initdatafn = function(){
		$ajax({
			url:url1,
			data:{id:id},
			success:initdata
		})
	}
	var initdata = function(data){
    	setformdata(data);
    	$("#xjlb option").each(function(){
    		if($(this).text() == data.lb){
    			$(this).attr({"selected":true});
    		}
    	});
	}
	var initother = function(){
		$("#btn-tg").click(function(){
			$ajax({
				url:url3,
				type: "GET",
				data:{id:id,"rq":getNowFormatDate(),"yj":$("#jspyj").val()},
				success:function(data){
					if(data.result=="success"){
						newbootbox.newdialogClose("qjJzsp");
						newbootbox.alertInfo("审批通过成功！").done(function(){
							//changToNum();
							changToNum2(function(){
                                window.top.iframe1.window.pageModule.initgrid();
							})
						});
					}else{
						newbootbox.alertInfo("审批失败！"); 
					}
				}
			}) 
		})
		$("#btn-notg").click(function(){
			$ajax({
				url:url4,
				type: "GET",
				data:{id:id,"rq":getNowFormatDate(),"yj":$("#jspyj").val()},
				success:function(data){
					window.parent.closefn();
					if(data.result=="success"){
						newbootbox.alertInfo("审批不通过成功！").done(function(){
							//changToNum();
							changToNum2(function(){
							window.top.iframe1.window.pageModule.initgrid();
							});
						});
					}else{
						newbootbox.alertInfo("审批失败！"); 
					}
				}
			}) 
		})
		
		$("#close").click(function(){
			newbootbox.newdialogClose("qjJzsp");
		})
	}
	
	
	return{
		//加载页面处理程序
		initControl:function(){
			initxjlb();
			initdatafn();
			initother();
		}
	}
	
}();

var getNowFormatDate = function(){
	var date = new Date();
	var seperator = "-";
	var month = date.getMonth()+1;
	var strDate = date.getDate();
	if(month >=1 && month<=9){
		month ="0"+month;
	};
	if(strDate >=1 && strDate<=9){
		strDate ="0"+strDate;
	}
	var currentDate = date.getFullYear()+seperator+month+seperator+strDate;
	return currentDate;
}
