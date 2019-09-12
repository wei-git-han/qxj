var url1 = {"url":rootPath +"/leaveorback/info","dataType":"text"};  //审批
var url2 = {"url":rootPath +"/dicvocationsort/dict","dataType":"text"};     //休假类别
var url3 = {"url":rootPath +"/leaderview/agreeleave","dataType":"text"};//请假通过
var url4 = {"url":rootPath +"/leaderview/disagreeleave","dataType":"text"};//请假不通过
var url5 = {"url":rootPath +"/qxjapprovalflow/listAll","dataType":"text"};//获取审批人员的列表数据
var url6 = {"url":rootPath +"/leaderview/tofinish","dataType":"text"};//审批完成。
var url7 = {"url":rootPath +"/leaderview/updateIsViewToZero","dataType":"json"}
var url_sendback={"url":rootPath +"/leaveorback/sendback","dataType":"text"};//退回

var id = window.location.search.split("id=")[1];
var userTree1 = {"url":rootPath +"/leaveorback/UserTree/tree?id="+id,"dataType":"text"};

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
//    	$('#csld').val('');
//    	$('#csldId').val('');
//    	$("#xjlb option").each(function(){
//    		if($(this).text() == data.lb){
//    			$(this).attr({"selected":true});
//    		}
//    	});
	}
	
	var isviewinfo = function(){
		$ajax({
			url:url7,
			data:{id:id},
			success:function(data){
				window.top.iframe1.window.pageModule.initgrid();
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
					if(arr.length>1){
						$("#approval_flow_view").show();
					};
					var $container=$('#newcont');
					$container.empty();
					var html1 = "";
					for(var i=0;i<arr.length;i++){
						if(arr[i].comment==""||arr[i].comment==null){
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
		isviewinfo()
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
		
		$("#btn_pass").click(function(){
			newbootbox.confirm({
		     	title:"提示",
		     	message: "是否需要提交其他领导审批？",
		     	callback1:function(){
		     		$("#viewcont2").modal("show");
					$("#modaltitle2").html("请选择领导");
					initldTree();
		     	},
		     	callback2:function(){
		     		var comment=$('#comment').val();
					$ajax({
						url:url6,
						type: "POST",
						data:{leaveId:id,"comment":comment},
						success:function(data){
							newbootbox.newdialogClose("qjCzsp");
							if(data.result=="success"){
								newbootbox.alertInfo("审批完成！").done(function(){
									window.top.iframe1.window.pageModule.initgrid();
								});
							}else if(data.result=='failed'){
								newbootbox.alertInfo("申请人已经撤回，您无法保存信息！").done(function(){
									window.top.iframe1.window.pageModule.initgrid();
								});
							}else{
								newbootbox.alertInfo("审批失败！");
							}
						}
					});
		     	}
		     });
			
			
			
			/**
			 * 初始化审批领导
			 */
			var initldTree = function(){	
				var comment=$('#comment').val();
				var csld=$('#csld').val();
				var csldId=$('#csldId').val();
				$ajax({
					url:userTree1,
					type:"post",
					success:function(data){
						initldData(data);
					}
				})
			}
			/**
			 * 初始化审批领导树数据
			 */
			var initldData = function (data) {
				$("#tree_2").jstree({
				    "plugins": ["wholerow", "types"],
				    "checkbox":{"keep_selected_style":false},
				    "core": {
				    "themes" : {
				        "responsive": false
				    },    
				    "data": data,
				    },
				    "types" : {
				    	"default" : {
					        "icon" : "peoples_img"
					    },
					    "file" : {
					        "icon" : "peoples_img"
					    },
					    "1" : {
					        "icon" : "people_img"
					    }
				    }
				});
				$("#tree_2").on("select_node.jstree", function(e,data) {
					if (data.node.original.type == 1) {
						$("#csldId").val($("#" + data.selected).attr("id"));
						$("#csld").val(data.node.text);
					}
				});
			}
			
			/*var comment=$('#comment').val();
			var csld=$('#csld').val();
			var csldId=$('#csldId').val();
			if(!csldId){
				newbootbox.alertInfo("请填写审批人！").done(function(){$('#csld').click();});
				return;
			}
			$ajax({
				url:url3,
				type: "POST",
				data:{leaveId:id,"comment":comment,"csld":csld,"csldId":csldId},
				success:function(data){
					newbootbox.newdialogClose("qjCzsp");
					if(data.result=="success"){
						newbootbox.alertInfo("审批通过！").done(function(){
							window.top.iframe1.window.pageModule.initgrid();
						});
					}else{
						newbootbox.alertInfo("审批失败！");
					}
				}
			}) */
		});
		
		/*$("#btn-notg").click(function(){
			$ajax({
				url:url4,
				type: "GET",
				data:{id:id,"rq":getNowFormatDate(),"yj":$("#csspyj").val()},
				success:function(data){
					newbootbox.newdialogClose("qjCzsp");
					if(data.result=="success"){
						newbootbox.alertInfo("审批不通过成功！").done(function(){
							window.top.iframe1.window.pageModule.initgrid();
						});
					}else{
						newbootbox.alertInfo("审批失败！"); 
					}
				}
			}) 
		});*/
		
		$("#saveuser2").click(function(){
			var comment=$('#comment').val();
			var csld=$('#csld').val();
			var csldId=$('#csldId').val();
			$ajax({
				url:url3,
				type: "POST",
				data:{leaveId:id,"comment":comment,"csld":csld,"csldId":csldId},
				success:function(data){
					if(data.result=="success"){
						var proper = data.proper;
						newbootbox.alertInfo(proper+"请假审批已完成！").done(function(){
							newbootbox.newdialogClose("qjCzsp");
							window.top.iframe1.window.pageModule.initgrid();
						});
					}else if(data.result=='failed'){
						newbootbox.alertInfo("申请人已经撤回，您无法保存信息！").done(function(){
							$("#viewcont2").modal("hide");
							newbootbox.newdialogClose("qjCzsp");
							window.top.iframe1.window.pageModule.initgrid();
						});
					}else{
						newbootbox.alertInfo("审批失败！");
					}
				}
			})
		})
		
		
		
		$('#btn_sendback').click(function(){
			var sqrName = $("#sqr").val();
			newbootbox.confirm({
		     	title:"提示",
		     	message: sqrName+"提交的请假申请信息不妥，确认退回？",
		     	callback1:function(){
		     		$ajax({
						url:url_sendback,
						data:{id:id,status:'3'},
						success:function(data){
							newbootbox.newdialogClose("qjCzsp");
							if(data.result=='success'){
								newbootbox.alertInfo("退回成功！").done(function(){
									//newbootbox.newdialogClose("qjCzsp");
									window.top.iframe1.window.pageModule.initgrid();
									window.parent.closefn();
								});
							}else{
								newbootbox.alertInfo("退回失败！"); 
							}
						}
					});
		     	}
		     });
		});
		$("#btn_finish").click(function(){
			var sqrName = $("#sqr").val();
			newbootbox.confirm({
		     	title:"提示",
		     	message: "是否完成本次请假审批？",
		     	callback1:function(){
					var comment=$('#comment').val();
					$ajax({
						url:url6,
						type: "POST",
						data:{leaveId:id,"comment":comment},
						success:function(data){
							newbootbox.newdialogClose("qjCzsp");
							if(data.result=="success"){
								newbootbox.alertInfo(sqrName+"请假审批已完成！").done(function(){
									window.top.iframe1.window.pageModule.initgrid();
									window.parent.closefn();
								});
							}else if(data.result=='failed'){
								newbootbox.alertInfo("申请人已经撤回，您无法保存信息！").done(function(){
									window.top.iframe1.window.pageModule.initgrid();
								});
							}
							else{
								newbootbox.alertInfo("审批失败！");
							}
						}
					});
		     	},
		     	callback2:function(){
		     		
		     	}
		     });
		});

		$("#btn_jxsp").click(function(){
			$("#viewcont2").modal("show");
			$(".modal-title").html("请选择审批人");
			initldTree();
		});
		
		 //初始化审批领导
		var initldTree = function(){	
			var comment=$('#comment').val();
			var csld=$('#csld').val();
			var csldId=$('#csldId').val();
			$ajax({
				url:userTree1,
				type:"post",
				success:function(data){
					initldData(data);
				}
			})
		}
		
		//初始化审批领导树数据
		var initldData = function (data) {
			$("#tree_2").jstree({
			    "plugins": ["wholerow", "types"],
			    "checkbox":{"keep_selected_style":false},
			    "core": {
			    "themes" : {
			        "responsive": false
			    },    
			    "data": data,
			    },
			    "types" : {
			    	"default" : {
				        "icon" : "peoples_img"
				    },
				    "file" : {
				        "icon" : "peoples_img"
				    },
				    "1" : {
				        "icon" : "people_img"
				    }
			    }
			});
			$("#tree_2").on("select_node.jstree", function(e,data) {
				if (data.node.original.type == 1) {
					$("#csldId").val($("#" + data.selected).attr("id"));
					$("#csld").val(data.node.text);
				}
			});
		}
		
		$("#close").click(function(){
			newbootbox.newdialogClose("qjCzsp");
		})
	}
	
	
	return{
		//加载页面处理程序
		initControl:function(){
			initxjlb();
			initdatafn();
			initother();
			approval_list();
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
