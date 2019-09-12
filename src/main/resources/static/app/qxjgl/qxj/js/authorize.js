var authorizeCheckUrl = {"url":"/leave/apply/authorizeCheck","dataType":"text"};
var authorizeUrl = {"url":"/leave/apply/authorize","dataType":"text"};
var userTree = {"url":"/sysuser/tree","dataType":"text"}; //人员选择树
var userId = getUrlParam("userId")||""; //主流程id	
var startDate = getUrlParam("startDate")||""; //请假起始时间
var deleteMark = getUrlParam("deleteMark")||""; //请假人的ID
var fileFrom = getUrlParam('fileFrom')||'';//主文件id
var fromMsg= getUrlParam("fromMsg");//是否来自消息
var pageModule = function(){
	//加载人员树
	var inittree = function(){
		$ajax({
			url:userTree,
			success:function(data){
				$("#tree1").jstree({
				    "plugins": ["wholerow", "types" ],
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
				$("#tree1").on("select_node.jstree", function(e,data) { 
					if(data.node.original.type == 1){
						$("#replaceUser").val(data.node.text);
						$("#replaceUserId").val(data.node.id)
					}else{
						$("#replaceUser").val("");
						$("#replaceUserId").val("");
					}
				});
			}
		})
		
	}
	
	var initother = function(){
		
//		$("#startDate").datetimepicker({
//		    language:"zh-CN",
//		    rtl: Metronic.isRTL(),
//		    orientation: "left",
//		    format: "yyyy-mm-dd hh:ii",
//		    startDate:(new Date()).format("yyyy-MM-dd hh:mm"),
//		    autoclose: true,
//		    pickerPosition: (Metronic.isRTL() ? "bottom-right" : "bottom-left")
//		}).on("changeDate",function(){
//			$("#endDate").datetimepicker("setStartDate",$("#startDate").val())
//		});
//		
//		$("#endDate").datetimepicker({
//		    language:"zh-CN",
//		    rtl: Metronic.isRTL(),
//		    orientation: "left",
//		    format: "yyyy-mm-dd hh:ii",
//		    startDate:($("#startDate").val() == "" ? (new Date()).format("yyyy-MM-dd hh:mm") : $("#startDate").val()),
//		    autoclose: true,
//		    pickerPosition: (Metronic.isRTL() ? "bottom-right" : "bottom-left")
//		}).on("changeDate",function(){
//			$("#startDate").datetimepicker("setEndDate",$("#endDate").val())
//		});
		
		$("#quxiao").click(function(){
			newbootbox.newdialogClose("authorizeDialog");
		})
		
		$("#commentForm").validate({
			    submitHandler: function() {
					var replaceUser=$("#replaceUser").val();
					var replaceUserId=$("#replaceUserId").val();
					if(replaceUserId == userId){
						newbootbox.alertInfo("不可以选择自己为代理人！");
						return;
					}
					if(replaceUserId == ''){
						newbootbox.alertInfo("请选择接替人员！");
						return;
					}
					var authType=$("#authorizationType option:selected").text();
				    var elementarry =["replaceUser","replaceUserId","authorizationType"];
					var paramdata = getformdata(elementarry);
					paramdata.type="persional";
					paramdata.startDate=startDate + "00:00"; 
					paramdata.leaveUserId = deleteMark;
					$ajax({
						url:authorizeCheckUrl,
						data:paramdata,
						type:'GET',
						success:function(data){
							if (data.result == "repeat") {
								newbootbox.alertInfo('用户已在授权期内，暂无法授权！');
								return;
							} else {
								newbootbox.oconfirm({
								 	title:"提示",
								 	message:"移交后，被移交人可以被"+authType+"，确认移交权限给"+replaceUser+"吗？",
								 	callback1:function(){
								 		$ajax({
											url:authorizeUrl,
											data:paramdata,
											type:'GET',
											success:function(data){
												if (data.result == "success") {
													newbootbox.newdialogClose("authorizeDialog");
													newbootbox.alert('保存成功！').done(function(){
														if(fromMsg=='1'){
															windowClose()
														}else{
															window.top.bubbleCountStatistics();
															if(fileFrom=='qxjsp'){
																$("#iframe1",window.top.document).attr("src","/app/qxjgl/qxj/html/CZSP_table.html");
											                }else{
											                	$("#iframe1",window.top.document).attr("src","/app/qxjgl/qxj/html/table.html");
											                }
														}
//														$("#iframe1",window.top.document).attr("src","/app/qxjgl/qxj/html/table.html");
//														window.top.iframe1.reload();
													});
												} else {
													newbootbox.alertInfo("保存失败！");
												}
											}
										})
								 	}
								});
							}
						}
					})
			    }
			  });
		
		$("#save").click(function(){
			$("#commentForm").submit();
		})
		
	}
	
	var getdatefn = function(date){
		if(date!=""&&date!=null&&typeof(date)!=undefined){
			return date.substr(0,10);
		}
		return "";
	}
	
	return{
		//加载页面处理程序
		initControl:function(){
			inittree();
			initother();
		}
	};
	
}();
