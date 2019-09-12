var url1 = {"url":"../data/dutylist.json?data=2019-01","dataType":"text"};
var url4 = {"url":"/app/qxjgl/qxjdiccalender/save","dataType":"text"};
var deleteSaveUrl = {"url":"/app/qxjgl/qxjdiccalender/deleteSave","dataType":"text"};
var zbbNav = {"url":"../data/dutylist.json","dataType":"text"};
var selectUrl = {"url":"/app/qxjgl/qxjdiccalender/queryDate","dataType":"text"};
var loginUserId = "";
var loginUser = "";
var g = "";
var id = window.location.search.split("id=")[1];
var tibanAuth = false;
var idArry=[];
var pageModule = function(){
	var shijian = new Date();
	var Year = shijian.getFullYear();
	var Month = shijian.getMonth()+1;
	var Day = shijian.getDate();
	if(Month<10){
		Month = "0"+Month;
	}
	if(Day<10){
		Day = "0"+Day;
	}
	var today = Year+"-"+Month+"-"+Day; //获取日期用于日期比较
	var getDate = function(data){
		var shijian = new Date(data);
		var Year = shijian.getFullYear();
		var Month = shijian.getMonth()+1;
		var Day = shijian.getDate();
		if(Month<10){
			Month = "0"+Month;
		}
		if(Day<10){
			Day = "0"+Day;
		}
		var today = Year+"-"+Month+"-"+Day; //获取日期用于日期比较
		return today
	}
	var initDate = function(date){
		var today  = getDate(new Date());
		if(date){
			today = getDate(new Date(date));
		}
		$("#date1").html(new Date(today).getFullYear()+'年法定公休日安排');
		var monthList = ['01','02','03','04','05','06','07','08','09','10','11','12'];
		$.each(monthList,function(index,data){
			var d = (today.split("-"))[0]+'-'+data+'-'+(today.split("-"))[2];
			fn1(d,'#content_'+data)
		})
	}
	var initother = function(){
		$("#change").click(function(){
			newbootbox.newdialog({
				id:"modifyDay",
				width:900,
				height:720,
				header:true,
				title:"编辑假期",
				url:"ywpz/fdgxr/html/table.html"
			})
		});
		$("#pre_button").click(function(){
			var date = getDate(new Date((new Date()).setFullYear(Year-0-1,(Month-1),Day)));
			
			initDate(date)
		});
		$("#nex_button").click(function(){
			var date = getDate(new Date((new Date()).setFullYear(Year-0+1,(Month-1),Day)));
			initDate(date)
		});
	}
	var fn1 = function(str,index){
		var json1 = {};
		var y = (str.split("-"))[0];
		var m = (str.split("-"))[1];
		var d = (str.split("-"))[2];
		$ajax({
			url: selectUrl,
			data:{Year:y,Month:m},
			async: false,
			success: function(obj) {
				json1 = obj;
			}
		})
//		if(1 == json1.status){
//			$("input[id='status']").val(json1.status);
//		}
	
		
		var newdate1 = new Date((new Date()).setFullYear(y,(m-1),d));
		var newdate2 = new Date((new Date()).setFullYear(y,(m-1),d));
		
		var pary = ((newdate1.format("yyyy-MM-dd")).split("-"));
		Month = pary[1];
		Year = pary[0];
		$(index+'_m').html(pary[0]+"年"+pary[1]+"月");
		newdate1.setDate(1);
		newdate2.setDate(1);
		newdate2.setMonth(newdate2.getMonth()+1);
		
		var time1 = newdate1.getTime();
		var time2 = newdate2.getTime();
		var n1 = (time2-time1)/(24*60*60*1000);
		
		var newdate3 = new Date(newdate1.getTime());
		newdate3.setDate(0);
		var n2 = newdate3.getDate();
		
		var arry1 = [];
		var day1 = newdate1.getDay();
		for(var i=0;i<day1;i++){
			newdate3.setDate(n2-(day1-1-i));
			arry1.push(newdate3.format("yyyy-MM-dd"));
		}
		for(var i=0;i<n1;i++){
			newdate1.setDate(i+1);
			arry1.push(newdate1.format("yyyy-MM-dd"));
		}
		var n3 = (7-((arry1.length)%7));
		for(var i=0;i<n3;i++){
			newdate2.setDate(i+1);
			arry1.push(newdate2.format("yyyy-MM-dd"));
		}
		var h = ($(index).height())/((arry1.length)/7);
		$(index).html("");
		
		for(var i=0;i<arry1.length;i++){
			var y1 = arry1[i].substr(0,4);
			var m1 = arry1[i].substr(5,2);
			var d1 = arry1[i].substr(8,2);
			var ddd = y1+"-"+m1+"-"+d1;
			var isSelectDay = false;
			for(var j=0;j<json1.length;j++){
				if(json1[j].calenderDate.indexOf(arry1[i])>-1){
					isSelectDay=true
				}
			}
			var html1 = "";
			var html2 = "";
			var html3 = "";
			var html4 = "";
			
			var bg = "#fff";
			var color1 = "";
			var color2 = "#000";
			var border = "1px solid #DEDEDE";
			var borderB = "none";
			var bg2 = "";
			var noborder = "";
			if(i<day1){
				bg = "#E7F8FF";
				color1 = "color:#AFB0B2";
				color2 = "color:#AFB0B2";
				html2 = "";
				noborder = "border-bottom:1px;border-right:0px"
			}else if(i<(day1+n1)){
				
				var obj1 = json1[arry1[i]];
				
				var zbid = "";
				var zbname = "";
				var hbid = "";
				var hbname = "";
				var tbid = "";
				var tbname = "";
				if(typeof(obj1)!="undefined"&&obj1!=null){
					
					zbid = obj1.zbid;
					zbname = obj1.zbname;
					hbid = obj1.hbid;
					hbname = obj1.hbname;
					tbid = obj1.tbid;
					tbname = obj1.tbname;
				}else{
					html2 = "";
				}
				
//				var html2 = '	<div style="position:absolute;bottom:5px;right:5px;">'+
//				'					<input class="checkbox1" id="'+zbid+'" data="'+arry1[i]+'" type="checkbox"/>'+
//				'				</div>';
				
				
				
				color2 = "#000";
				if((new Date()).setFullYear(y1,m1-1,d1)==(new Date()).getTime()&&isSelectDay){
					bg = "#F6FBFE;";
					color2 = "#fff";
					border = "2px solid #6699FF";
					borderB='2px solid #FFF';
					bg2 = "#6699FF;"
				}
				else if (isSelectDay){
					bg = "#6699FF";
					color2 = "#fff";
				}else if((new Date()).setFullYear(y1,m1-1,d1)==(new Date()).getTime()){
					border = "2px solid #6699FF";
					color2 = "#6699FF";
					bg = "#F6FBFE";
					borderB='2px solid #6699FF';
				}
				else{
					noborder = "border-bottom:1px;border-right:0px"
				}
				
			}

			else{
				
				bg = "#E7F8FF";
				color1 = "color:#AFB0B2";
				color2 = "color:#AFB0B2";
				html2 = "";
				noborder = "border-bottom:1px;border-right:0px"
				
			}
			if(h/16>3){
				var top="50%"
			}else{
				var top="35%"
			}
			$(index).append(
				'<div class= "no-border-right" style="overflow:hidden;background-color:'+bg+';width:14.2%;color:'+color2+';height:'+h+'px;float:left;position:relative;border-radius:2px!important;border:'+border+';'+noborder+'">'+
				'	<div style="width:100%;height:100%;position:absolute;top:0;left:0;background-color:'+bg2+'border:'+borderB+'">'+
				'		<div style="width:100%;height:100%;line-height:100%;text-align:center;padding-top:'+top+';'+color1+'">'+d1+'</div>'+
				'	</div>'+
				'</div>'
				
			);
		}
	}

	
	return{
		//加载页面处理程序
		initControl:function(){
			initother();
			initDate();
//			initSelect();
		}
	}
	
}();

