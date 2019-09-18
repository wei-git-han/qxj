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

	
	

	var initother = function(){
		
		$ajax({
			url:zbbNav,
			data:{menuId:id},
			async: false,
			success:function(data){
				$.each(data,function(i,item){
					tibanAuth = true;
				});
			}
		});
		
		var newdate1 = new Date();
		fn1(newdate1.format("yyyy-MM-dd"));
		
		$("#left").click(function(){
			changeSave(newdate1.format("yyyy-MM"));
			newdate1.setMonth(newdate1.getMonth()-1);
			fn1(newdate1.format("yyyy-MM-dd"));
			initSelect();
		});
		
		$("#right").click(function(){
			changeSave(newdate1.format("yyyy-MM"));
			newdate1.setMonth(newdate1.getMonth()+1);
			fn1(newdate1.format("yyyy-MM-dd"));
			initSelect();
		});
		var c1 = {}
		$(window).resize(function(){
			fn1(newdate1.format("yyyy-MM-dd"));
		});
		
		$(".checkbox1").click(function(){
			 var array1 = $(".checkbox1:checked");
		});

		$("#reset").click(function(){
			window.top.iframe1.window.iframe2.location.reload();
			newbootbox.newdialogClose("modifyDay")
		});
		
		$("#save").click(function(){
			$(".checkbox1").each(function(){
				if($(this).attr("checked")=="checked" || $(this).attr("checked")=="checked" ){
					idArry.push($(this).attr("data"));
				}
			})
			changeSave(newdate1.format("yyyy-MM"))
			setTimeout(function(){
				window.top.iframe1.window.iframe2.location.reload();
				newbootbox.newdialogClose("modifyDay")
			},1000)
//			$ajax({
//				url: url4,
//				data:{dutyPeople:idArry.toString()},
//				async: false,
//				success:function(result){
//				if(result.msg=="success"){
//						newbootbox.newdialogClose("modifyDay")
//						window.top.iframe1.window.iframe2.location.reload();
//					}else{
//						newbootbox.alertInfo("保存失败！");
//					}
//				}
//			}); 
		});
	}

	var initDate = function(data){
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
	var initSelect = function(){
		$ajax({
			url:selectUrl,
			data:{Year:Year,Month:Month},
			async: false,
			success:function(data){
				$.each(data,function(i,val){
					tibanAuth = true;
					var splitdate = (val.calenderDate).substring(0,10);
					$("input[data="+splitdate+"]").attr("checked",true);

				});
			}
		});
		

	}
	
	var changeSave = function(objdate){
		var checkedId = [];
		$(".checkbox1").each(function(){
			if($(this).attr("checked")=="checked"){
				if(($(this).attr("data")).indexOf(objdate)!=-1){
					checkedId.push($(this).attr("data"));
				}
				
			}
		})
		var y = (objdate.split("-"))[0];
		var m = (objdate.split("-"))[1];
		$ajax({
			url: deleteSaveUrl,
			data:{dutyPeople:checkedId.toString(),year:y,month:m},
			async: false,
			success:function(result){
//				if(result.msg=="success"){
//					newbootbox.alertInfo('保存成功！').done(function(){
//						window.top.iframe1.window.iframe2.location.reload();
//					});
//				}else{
//					newbootbox.alertInfo("保存失败！");
//				}
				
			}
		});
	}
	
	var fn1 = function(str){
		var json1 = {};
		$ajax({
			url: url1,
			data:{date:str.substr(0,7)},
			async: false,
			success: function(obj) {
				json1 = obj;
			}
		})
		if(1 == json1.status){
			$("input[id='status']").val(json1.status);
		}
		
		var y = (str.split("-"))[0];
		var m = (str.split("-"))[1];
		var d = (str.split("-"))[2];
		
		var newdate1 = new Date((new Date()).setFullYear(y,(m-1),d));
		var newdate2 = new Date((new Date()).setFullYear(y,(m-1),d));
		
		var pary = ((newdate1.format("yyyy-MM-dd")).split("-"));
		Month = pary[1];
		Year = pary[0];
		$("#date1").html(pary[0]+"年"+pary[1]+"月"/*+pary[2]+"日"*/);

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
		var h = ($("#content").height())/((arry1.length)/7);
		$("#content").html("");
		
		for(var i=0;i<arry1.length;i++){
			var y1 = arry1[i].substr(0,4);
			var m1 = arry1[i].substr(5,2);
			var d1 = arry1[i].substr(8,2);
			var ddd = y1+"-"+m1+"-"+d1;

			var html1 = "";
			var html2 = "";
			var html3 = "";
			var html4 = "";
			
			var bg = "#fff";
			var color1 = "";
			var color2 = "color:#3CC7C2";
			if(i<day1){
				bg = "#E7F8FF";
				color1 = "color:#AFB0B2";
				html2 = "";
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
				
				var html2 = '	<div style="position:absolute;bottom:5px;right:5px;">'+
				'					<input class="checkbox1" id="'+zbid+'" data="'+arry1[i]+'" type="checkbox"/>'+
				'				</div>';
				
				
				
				color2 = "color:#3CC7C2";
				if((new Date()).setFullYear(y1,m1-1,d1)==(new Date()).getTime()){
					bg = "#3CC7C2;";
					color2 = "color:#fff";
				}
				else{
					bg = "#fff";
				}

				html4 = '	<div class="cell2" style="width:100%;height:100%;position:absolute;top:100%;left:0;background-color:#7DC9C6;">'+
						'		<div style="width:100%;height:100%;display:table;">'+
						'			<div style="width:100%;height:100%;display:table-cell;vertical-align: middle;text-align:center;">'+
						'			</div>'+
						'		</div>'+
						'	</div>'+
						'	<div class="cell1" style="width:96%;height:70%;position:absolute;top:2%;left:2%;cursor:pointer;">'+
						'	</div>';
				
			}

			else{
				
				bg = "#E7F8FF";
				color1 = "color:#AFB0B2";
				color2 = "color:#AFB0B2";
				html2 = "";
			}
			

			$("#content").append(
				'<div style="overflow:hidden;background-color:'+bg+';width:14.2%;color:#000;height:'+h+'px;float:left;border:1px solid #ddd;border-left:none;border-bottom:none;position:relative;">'+
				'	<div style="width:100%;height:100%;position:absolute;top:0;left:0;"><div style="width:100%;height:'+h+'px;text-align:center;line-height:'+h+'px;font-size:20px;color:#000"></div>'+
				'		<div style="position:absolute;top:5px;right:5px;font-size:28px;'+color1+'">'+d1+'</div>'+
				'		<div style="width:100%;height:100%;display:table;">'+
				'			<div style="width:100%;height:100%;display:table-cell;vertical-align: middle;text-align:center;">'+html1+
				'			</div>'+
				'		</div>'+html2+
				'	</div>'+html4+
				'</div>'
				
			);
		}
		initSelect();
	}

	
	return{
		//加载页面处理程序
		initControl:function(){
			initother();
		}
	}
	
}();

