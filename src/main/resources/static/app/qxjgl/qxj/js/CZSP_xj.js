var url1 = {"url":rootPath +"/leaveorback/xjinfo","dataType":"text"};//销假对话框请求
var url3 = {"url":rootPath +"/leaderview/agreeback","dataType":"text"};//销假通过
var url4 = {"url":rootPath +"/leaderview/disagreeback","dataType":"text"};//销假不通过
var id = window.location.search.split("id=")[1];
var pageModule = function(){
	var initdatafn = function(){
		$ajax({
			url:url1,
			data:{id:id},
			success:initdata
		})
	}
	var initdata = function(data){
    	setformdata(data);
	}
	var initother = function(){
		$("#btn-tg").click(function(){
			$ajax({
				url:url3,
				type: "GET",
				data:{id:id,"rq":$("#sjrqFrom").val()+"#"+$("#sjrqTo").val(),"yj":$("#csspyj").val()},
				success:function(data){
					if(data.result=="success"){
						newbootbox.newdialogClose("xjCzsp");
						newbootbox.alertInfo("审批通过成功！").done(function(){
							window.top.iframe1.window.pageModule.initgrid();
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
				data:{id:id,"rq":getNowFormatDate(),"yj":$("#csspyj").val()},
				success:function(data){
					if(data.result=="success"){
						newbootbox.newdialogClose("xjCzsp");
						newbootbox.alertInfo("审批不通过成功！").done(function(){
							window.top.iframe1.window.pageModule.initgrid();
						});
					}else{
						newbootbox.alertInfo("审批失败！"); 
					}
				}
			}) 
		})
		
		$("#close").click(function(){
			newbootbox.newdialogClose("xjCzsp");
		})
	}
	
	
	return{
		//加载页面处理程序
		initControl:function(){
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
