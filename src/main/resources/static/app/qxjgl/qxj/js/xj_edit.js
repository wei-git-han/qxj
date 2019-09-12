var url1 = {"url":rootPath +"/leaveorback/xjinfo","dataType":"text"};//销假编辑
var url2 = {"url":rootPath +"/leaveorback/commitback","dataType":"text"};//销假提交
var id = window.location.search.split("id=")[1];
var pageModule = function(){
	var initdatafn = function(){
		$ajax({
			url:url1,
			data:{id:id},
			success:initdata
		})
	}
	var sjrqFromVal = "";
	var sjrqToVal = "";
	var initdata = function(data){
    	setformdata(data);
    	sjrqFromVal = data.sjrqFrom;
    	sjrqToVal = data.sjrqTo;
	}
	var initother = function(){
		
		$("#sjrqFrom").datepicker({
			format:"yyyy-mm-dd",
		    language:"zh-CN",
		    rtl: Metronic.isRTL(),
		    orientation: "right",
		    startDate:new Date(),
		    autoclose: true,
		}).on('changeDate',function(end){
			$("#sjrqTo").datepicker("setStartDate",$("#sjrqFrom").val());
			var starday = new Date($("#sjrqFrom").val());
			var endday = new Date($("#sjrqTo").val());
			if(endday < starday){
				$("#sjrqTo").datepicker("setDate",$("#sjrqFrom").val());
			};
			$("#sjqjts").val(GetDateDiff($("#sjrqFrom").val(),$("#sjrqTo").val()));
		});
		
		$("#sjrqTo").datepicker({
			format:"yyyy-mm-dd",
		    language:"zh-CN",
		    rtl: Metronic.isRTL(),
		    orientation: "right",
		    startDate:new Date(),
		    autoclose: true,
		}).on('changeDate',function(end){
			$("#sjrqFrom").datepicker("setEndDate",$("#sjrqTo").val());
			$("#sjqjts").val(GetDateDiff($("#sjrqFrom").val(),$("#sjrqTo").val()));
		});
		
		$("#commentForm").validate({
		    submitHandler: function() {
				var elementarry = ["sjrqFrom","sjrqTo","sjqjts","bz"];
				var paramdata = getformdata(elementarry);
				paramdata.id = id;
				$ajax({
					url:url2,
					type: "GET",
					data:paramdata,
					success:function(data){
						if(data.result=="success"){
							newbootbox.newdialogClose("Edit");
							newbootbox.alertInfo("保存并上报成功！").done(function(){
								window.top.iframe1.window.pageModule.initgrid();
							});
						}else{
							newbootbox.alertInfo("上报失败！"); 
						}
					}
				}) 
		    }
		});
		
		$("#save").click(function(){
			$("#commentForm").submit();
		});
		
		$("#close").click(function(){
			newbootbox.newdialogClose("Edit");
		});
	}
	
	
	return{
		//加载页面处理程序
		initControl:function(){
			initdatafn();
			initother();
		}
	}
	
}();

var GetDateDiff=function(startDate,endDate){
	var startDate = new Date(Date.parse(startDate.replace(/-/g,"/"))).getTime();
	var endDate = new Date(Date.parse(endDate.replace(/-/g,"/"))).getTime();
	var dates=Math.abs((startDate-endDate))/(1000*60*60*24);
	return dates+1;
}
