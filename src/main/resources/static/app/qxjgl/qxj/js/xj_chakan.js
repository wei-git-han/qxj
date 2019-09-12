var url2 = {"url":rootPath +"/dicvocationsort/dict","dataType":"text"};     //休假类别
var url3 = {"url":rootPath +"/leaveOrBack/xjinfo","dataType":"text"}; //销假查看
var id = window.location.search.split("id=")[1];
var pageModule = function(){
	var initdatafn = function(){
		$ajax({
			url:url3,
			data:{id:id},
			async:false,
			success:initdata
		})
	}
	
	var initdata = function(data){
		setformdata(data);
	}
	
	var initxjlb = function(){
		$ajax({
			url:url2,
			success:function(data){
				initselect("xjlb",data.xjlb);
			}
		})
	}
	
	var initother = function(){
		$("#xjsjFrom").change(function(){
			$("#xjts").val(GetDateDiff($("#xjsjFrom").val(),$("#xjsjTo").val()));
		});
		$("#xjsjTo").change(function(){
			$("#xjts").val(GetDateDiff($("#xjsjFrom").val(),$("#xjsjTo").val()));
		})
		
		$(".date-picker").datepicker({
		    language:"zh-CN",
		    rtl: Metronic.isRTL(),
		    orientation: "right",
		    autoclose: true
		});

		$("#save").click(function(){
			$("#commentForm").submit();
		})
		
		$("#close").click(function(){
			window.parent.closefn();
		})
	}
	
	
	return{
		//加载页面处理程序
		initControl:function(){
			initdatafn();
			initxjlb();
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

var GetDateDiff=function(startDate,endDate){
	var startDate = new Date(Date.parse(startDate.replace(/-/g,"/"))).getTime();
	var endDate = new Date(Date.parse(endDate.replace(/-/g,"/"))).getTime();
	var dates=Math.abs((startDate-endDate))/(1000*60*60*24);
	return dates;
}
