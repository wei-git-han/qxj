var url2 = {"url":rootPath +"/dicvocationsort/dict","dataType":"text"};     //休假类别
var url3 = {"url":rootPath +"/leaveOrBack/info","dataType":"text"};  //请假查看
var url4 = {"url":rootPath +"/leaveorback/getUser","dataType":"text"}//获取登陆人
var url5 = {"url":rootPath +"/qxjapprovalflow/listAll","dataType":"text"};//获取审批人员的列表数据
var url6 = {"url":rootPath +"/leaveOrBack/getIsJuGuanLi","dataType":"text"};//当前登录用户是否是管理员
var url7 = {"url":rootPath +"/leaveOrBack/updateWeekendHolidayNum","dataType":"text"};//保存
/*var id = window.location.search.slice(1).split("id=")[1];
var status = window.location.search.slice(1).split("status=")[2];*/
var search = window.location.search.slice(1);
var id,status,codeArr,obj = {};
if(search.indexOf("&") !== -1) {
  codeArr = search.split("&");
  for(var i = 0;i < codeArr.length;i++) {
	 var ll = codeArr[i].split("=");
	 obj[ll[0]] = ll[1]
  }
}else{
  codeArr = search.split("=")
  obj[codeArr[0]] = codeArr[1];
}
id = obj.id
console.log(id)
status = obj.status;
var pageModule = function(){
	var initloginUser = function(){
		$ajax({
			url:url6,
			data:{id:id,weekendnum:$("#weekendNum").val(),holidaynum:$("#holidayNum").val(),actualVocationDate:$("#xjts").val()},
			success:function(data){
				if(data.flag=='false'){
					$("#xjts").attr("disabled",true);
					$("#holidayNum").attr("disabled",true);
					$("#weekendNum").attr("disabled",true);
				}else{
					$("#boxSave").css('display','block')
				}
			}
		})
		$("#save").click(function(){
			$ajax({
				url:url7,
				data:{id:id,weekendnum:$("#weekendNum").val(),holidaynum:$("#holidayNum").val(),actualVocationDate:$("#xjts").val()},
				success:function(data){
					if(data.result=="success"){
						newbootbox.alertInfo("修改成功！")
						newbootbox.newdialogClose("qjView");
					}else{
						newbootbox.alertInfo("修改失败！")
						newbootbox.newdialogClose("qjView");
					}
				}
			})
		})
	}
	
	
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
			url:url3,
			data:{id:id},
			//async:false,
			success:function(data){
				setformdata(data);
				//下拉框赋值
				$("#xjlb option").text(data.lb);
			}
		})
	}
	var approval_list=function(){
		var msg;
		if(status==1){
			msg="<div>审批进行中</div>";
		}
		if(status==2){
			msg="<div>审批完成</div>";
		}
		else{
			msg="";
		}
		$ajax({
			url:url5,
			data:{leaveId:id},
			success:function(data){
				var arr=data||[];
				if(arr.length>0){
					var $container=$('#newcont');
					//$container.empty();
					var html1 = "";
					for(var i=0;i<arr.length;i++){
						if(arr[i].comment==""||arr[i].comment==null){
							if(arr[i].curNum==1){
							html1 += '<div style="position:relative;margin-bottom:10px;">'+
							'	<div style="width:20px;background-color:#D7D7D7;position:absolute;top:0;left:0;bottom:0;height:100%;padding-top:30px;text-align:center;">'+(i+1)+
							'	</div>'+
							'	<div style="background-color:#F2F2F2;padding:10px 10px 10px 30px;">'+
							'		<div style="margin-bottom:10px;">'+
							'			<font style="font-size:17px;font-weight:bold;">'+arr[i].approvalName+'</font>'+
							'			<font style="padding-left:10px;">'+arr[i].modifyDate+'</font>'+
							'		</div>'+
							'		<div style="font-size:13px;height:30px;color:rgb(180, 167, 167);">审批中'+
							'		</div>'+
							'	</div>'+
							'</div>';
							}else{
								html1 += '<div style="position:relative;margin-bottom:10px;">'+
								'	<div style="width:20px;background-color:#D7D7D7;position:absolute;top:0;left:0;bottom:0;height:100%;padding-top:30px;text-align:center;">'+(i+1)+
								'	</div>'+
								'	<div style="background-color:#F2F2F2;padding:10px 10px 10px 30px;">'+
								'		<div style="margin-bottom:10px;">'+
								'			<font style="font-size:17px;font-weight:bold;">'+arr[i].approvalName+'</font>'+
								'			<font style="padding-left:10px;">'+arr[i].modifyDate+'</font>'+
								'		</div>'+
								'		<div style="font-size:13px;height:30px;color:rgb(180, 167, 167);">待提交'+
								'		</div>'+
								'	</div>'+
								'</div>';	
																
							}
						}else{
							html1 += '<div style="position:relative;margin-bottom:10px;">'+
							'	<div style="width:20px;background-color:#D7D7D7;position:absolute;top:0;left:0;bottom:0;height:100%;padding-top:30px;text-align:center;">'+(i+1)+
							'	</div>'+
							'	<div style="background-color:#F2F2F2;padding:10px 10px 10px 30px;">'+
							'		<div style="margin-bottom:10px;">'+
							'			<font style="font-size:17px;font-weight:bold;">'+arr[i].approvalName+'</font>'+
							'			<font style="padding-left:10px;">'+arr[i].modifyDate+'</font>'+
							'		</div>'+
							'		<div style="font-size:17px;height:30px;">'+arr[i].comment+
							'		</div>'+
							'	</div>'+
							'</div>';
						}
						
					}
					$container.append(html1+msg);
					
				}
			}
		})
	};
	
	var initother = function(){
			
			$("#View").click(function(){
				if($(this).text()=="查看审批过程"){
					$(this).text("收起审批过程");
					$("#approval_flow").slideDown();
					$(this).focus();
				}else{
					$(this).text("查看审批过程");
					$("#approval_flow").slideUp();
				}
			})
	}
	return{
		//加载页面处理程序
		initControl:function(){
			initloginUser();
			initxjlb();
			initdatafn();
			//approval_list();
			initother();
		}
	}
	
}();
