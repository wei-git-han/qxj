var url2 = {"url": "/app/qxjgl/application/saveLeaveApplication","dataType":"text"}//保存或修改请假单
var recallAuthorizeUrl = {"url":"/leave/apply/recallAuthorize","dataType":"text"};//撤销公文的授权
var url_getLeaveInfo={"url": "/app/qxjgl/application/getLeaveInfo","dataType":"text"};//获取请假详情
var id = getUrlParam("id");
var flag = getUrlParam("flag");
var fromMsg = getUrlParam("fromMsg");
var fileFrom = getUrlParam("fileFrom");
var deleteMark = "";//请假人的ID

var pageModule = function(){
	var initother = function(){
		$("#sjrqFrom").datepicker({
		    language:"zh-CN",
		    rtl: Metronic.isRTL(),
		    orientation: "left",
		    format: "yyyy-mm-dd",
		    autoclose: true,
		}).on("change",function(){
				$("#sjrqTo").datepicker("setStartDate",$("#sjrqFrom").val());
			}
		);
			
		$("#sjrqTo").datepicker({
		    language:"zh-CN",
		    rtl: Metronic.isRTL(),
		    orientation: "left",
		    format: "yyyy-mm-dd",
		    autoclose: true,
		}).on("change",function(){
				$("#sjrqFrom").datepicker("setEndDate",$("#sjrqTo").val())
			}
		);
		$(".input-group-btn").click(function(){
			$(this).prev().focus();
		});
		
		$("#commentForm").validate({
		    submitHandler: function() {
				var that = this;
				var elementarry = ["sjrqFrom","sjrqTo","sjqjts","xjzt"];
				var paramdata = getformdata(elementarry);
				paramdata.id = id;
				paramdata.flag = flag;
				$ajax({
					url:url2,
					type: "GET",
					data:paramdata,
					success:function(data){
						if(data.result=="success"){
							newbootbox.newdialogClose("xjsqadd");
							
							//window.top.iframe1.window.pageModule.initControl();
							if (flag == 1) {
								newbootbox.alert("保存成功！").done(function(){
//										window.top.iframe1.location.reload();
                                    changToNum2(function(){
                                        if(fromMsg=='1'){
                                            windowClose()
                                        }else if(fileFrom=='qxjsp'){
                                            window.top.bubbleCountStatistics();
                                            window.top.iframe1.location = '/app/qxjgl/qxj/html/CZSP_table.html'
    //			                                window.top.iframe1.location.reload();
                                        }else{
                                            window.top.bubbleCountStatistics();
                                            window.top.iframe1.location = '/app/qxjgl/search/html/index.html'
                                        }
                                    })
								});
							} else {
								newbootbox.alert("保存成功！").done(function(){
								     changToNum2(function(){
                                        window.top.iframe1.window.pageModule.countXiuJiaDaysUpdate();
                                         window.top.iframe1.window.pageModule.initgrid();
								     })
//									window.top.iframe1.window.pageModule.initgrid();
								});
							}
							//changToNum();
							//撤销公文的权限
							$ajax({
								url:recallAuthorizeUrl,
								type: "POST",
								data:{recallUserId:deleteMark},
								success:function(data){
									
								}
							});
						}else{
							newbootbox.alertInfo("保存失败！"); 
						}
					}
				}) 
		    },
	        rules: {
	        	sjrqFrom: {
	        			required: true
	            },
	            sjrqTo: {
		              required: true
		        }
	        },
	        messages: {
	        	sjrqFrom: {
	        			required: "请输入实际开始日期"
	            },
	            sjrqTo: {
		              	required: "请输入实际结束日期"
		        }
	        },
	        errorPlacement: function(error, element) {  
	            error.appendTo(element.parent().parent());  
	        }
		});
		
		$("#save").click(function(){
			$("#xjzt").val("1");
			$("#commentForm").submit();
		});
		
		$("#close").click(function(){
			newbootbox.newdialogClose("xjsqadd");
		});
		
		var sjqjtsErrorfn = function(){
			if($("#xjlb option:selected").text() == "年假"){
				if(parseInt($("#sjqjts").val()) > parseInt($("#syts").val())){
					$("#sjqjtsError").css({"display":"inline-block"})
				}else{
					$("#sjqjtsError").css({"display":"none"})
				}
			}
		}
	}
	var getInfo = function(){
		$ajax({
			url:url_getLeaveInfo,
			data:{id:id},
			type:"POST",
			success:function(res){
				deleteMark = res.deleteMark;
				$("#sjrqFrom").val(res.planTimeStart)
				$("#sjrqTo").val(res.planTimeEnd)
			}
		});
	}
	
	return{
		//加载页面处理程序
		initControl:function(){
			initother();
			getInfo();
		}
	}
	
}();
