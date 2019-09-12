var url1 = {"url":rootPath +"/leaveorback/commitback","dataType":"text"};

var idStr = window.location.search.split("id=")[1];
var id = idStr.split("&")[0];
var times = decodeURI(window.location.search.split("times=")[1]);

var pageModule = function(){
	
	var initother = function(){
		
		var sjrqStr = times.split("(")[0];
		var sjrqFromTime = sjrqStr.split("至")[0];
		var sjrqToTime = sjrqStr.split("至")[1];
		
		$("#sjrqFrom").val(sjrqFromTime);
		$("#sjrqTo").val(sjrqToTime);
		$("#sjqjts").val(GetDateDiff($("#sjrqFrom").val(),$("#sjrqTo").val()));
		
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
					url:url1,
					type: "GET",
					data:paramdata,
					success:function(data){
						if(data.result=="success"){
							newbootbox.newdialogClose("xjAdd");
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
		})
		
		$("#close").click(function(){
			newbootbox.newdialogClose("xjAdd");
		})
	}
	
	
	return{
		//加载页面处理程序
		initControl:function(){
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
