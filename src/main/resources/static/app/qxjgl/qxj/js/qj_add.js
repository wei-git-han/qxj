var url2 = {"url": rootPath + "/dicvocationsort/dict","dataType":"text"} //字典请假类型
var chooseOneJuPersons = {"url":"/leave/apply/chooseOneJuPersons","dataType":"text"};
var saveOrUpdateLeaveUrl = {"url": "/app/qxjgl/application/saveLeaveApplication","dataType":"text"}//保存或修改请假单
//var url4 = {"url": rootPath + "/leaveorback/getUser","dataType":"text"} //获取登录人
var getDefaultParamUrl = {"url": "/app/qxjgl/application/getDefaultParam","dataType":"text"} //获取登录人信息
var allUserTreeUrl = {"url":"/app/base/user/allTree","dataType":"text"};//所有人员树
var returnDate = {"url":rootPath +"/leaveorback/calculateHolidays","dataType":"text"};

var pageModule = function(){
	
	var initloginUser = function(){
		$ajax({
			url:getDefaultParamUrl,
			success:function(data){
				setformdata(data);
			}
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
	
	var initother = function(){		
		$("#xjsjFrom").datepicker({
			format:"yyyy-mm-dd",
		    language:"zh-CN",
		    rtl: Metronic.isRTL(),
		    orientation: "right",
		    startDate:new Date(),
		    autoclose: true,
		}).on('changeDate',function(end){
			$("#xjsjTo").datepicker("setStartDate",$("#xjsjFrom").val());
			var starday = new Date($("#xjsjFrom").val());
			var endday = new Date($("#xjsjTo").val());
			if(endday < starday){
				$("#xjsjTo").datepicker("setDate",$("#xjsjFrom").val());
			};
			$ajax({
				url:returnDate,
				dataType:'POST',
				data:{startDateStr:$("#xjsjFrom").val(),toDateStr:$("#xjsjTo").val()},
				success:function(data){
					setformdata(data);
				}
			});
			//$("#xjts").val(GetDateDiff($("#xjsjFrom").val(),$("#xjsjTo").val()));
		});
		
		$("#xjsjTo").datepicker({
			format:"yyyy-mm-dd",
		    language:"zh-CN",
		    rtl: Metronic.isRTL(),
		    orientation: "right",
		    startDate:new Date(),
		    autoclose: true,
		}).on('changeDate',function(end){
			$("#xjsjFrom").datepicker("setEndDate",$("#xjsjTo").val());
			$ajax({
				url:returnDate,
				dataType:'POST',
				data:{startDateStr:$("#xjsjFrom").val(),toDateStr:$("#xjsjTo").val()},
				success:function(data){
					setformdata(data);
				}
			}) 
		});
		
		$(".input-group-btn").click(function(){
			$(this).prev().focus();
		});
		/*************************/
		//所有人员树
		$("#sqr").createNewUserTree({
			url : allUserTreeUrl,
			width : "100%",
            plugins:'checkbox',
			params:{id:1},
			success : function(data, treeobj) {},
			selectnode : function(e, data,treessname,treessid) {
				$ajax({
					url:chooseOneJuPersons,
					dataType:'POST',
					data:{userIds:treessid.toString()},
					success:function(data){
						if (data.result == 'fail') {
							newbootbox.alert("请选择同一个局的人！");
							$("#save").hide();
						} else {
							$("#parentOrgId").val(data.orgId);
							$("#orgName").val(data.orgName);
							$("#save").show();
							console.log(data.orgId)
							if (data.orgId != undefined) {
								$ajax({
									url:{"url": rootPath + "/dicvocationsort/dict?orgId="+data.orgId,"dataType":"text"},
									success:function(data){
										if (data.xjlb == '') {
											initselect("xjlb", data.xjlb);
											newbootbox.alert("请联系本局管理员配置请假类别！");
											$("#xjlb").attr("disabled",true);
											$("#save").hide();
										} else {
											$("#xjlb").attr("disabled",false);
											initselect("xjlb", data.xjlb);
										}
									}
								});
							}
						}
					}
				});
				$("#sqrId").val(treessid);
				$("#sqr").val(treessname);
			}
		});
        $("#undertaker").createUserTree({
            url : allUserTreeUrl,
            width : "100%",
            data:{id:1},
            success : function(data, treeobj) {},
            selectnode : function(e, data) {
                $("#undertakerId").val(data.node.id);
                $("#undertaker").val(data.node.text);
            }
        });
        /*$("#linkMan").createUserTree({
            url : allUserTreeUrl,
            width : "100%",
            data:{id:1},
            success : function(data, treeobj) {},
            selectnode : function(e, data) {
                $("#linkManId").val(data.node.id);
                $("#linkMan").val(data.node.text);
            }
        });*/
		$("#xjts").bind('input propertychange',function(val){
			$("#holidayNum").val("")
			$("#weekendNum").val("")
		})

		 $("#save").click(function(){
			 // newbootbox.newdialogClose("qjAdd");
			 $("#commentForm").submit();
		 });
		 
	     $("#mobile").on("blur", function(){
	    	var reg = /^[1][3,4,5,7,8][0-9]{9}$/;
	    	if(!reg.test($(this).val())) {
		    	$(this).next().show();
		    	$("#mobile").focus();
	    	}else{
		    	$(this).next().hide();
	    	}
	     });
	    
	     //重置
	     $("#reset").click(function(){
	    	 removeInputData(["xjts","fdjjr","zlrts"]);
	    	 $ajax({
				url:returnDate,
				dataType:'POST',
				data:{startDateStr:$("#xjsjFrom").val(),toDateStr:$("#xjsjTo").val()},
				success:function(data){
					setformdata(data);
				}
			})
	     });
	    
		$("#commentForm").validate({

	    	submitHandler: function() {
				var elementarry = ["sqrq","sqr","sqrId","deptDuty","xjlb","syts","xjsjFrom","xjsjTo","xjts","shouldTakDays","csld",
					"csldId","csparea","qjzt","spzt","mobile","place","origin","orgId","parentOrgId","orgName","vehicle","turnOver",
					'status',"holidayNum","weekendNum","linkMan","undertaker","undertakerId","undertakerMobile"];
				var paramdata = getformdata(elementarry);
                newbootbox.newdialogClose("qjAdd");
                window.parent.frames["iframe1"].openLoading()
				$ajax({
					url:saveOrUpdateLeaveUrl,
					type: "POST",
					data:paramdata,
					success:function(data){
						if(data.result=="success"){
							window.parent.frames["iframe1"].closeLoading()
							newbootbox.alertInfo("生成请假单成功！").done(function(){
								window.top.iframe1.location.href=rootPath + '/qxj/html/qxjView.html?id='+data.id+'&showTab=1'
							});
						}else{
							newbootbox.alertInfo(data.result+"！");
						}
						newbootbox.newdialogClose("qjAdd");
					},


				}) 
	    	}
		});
		
		$("#xjlb").change(function(){
			if($("#xjlb option:selected").text()=="年假"){
				$("#njtsDiv").show();
			}else{
				$("#njtsDiv").hide();
			}
		});
		
		$("#close").click(function(){
			newbootbox.newdialogClose("qjAdd");
		})
		
		var xjtsErrorfn = function(){
			if($("#xjlb option:selected").text() == "年假"){
				if(parseInt($("#xjts").val()) > parseInt($("#syts").val())){
					$("#xjtsError").css({"display":"inline-block"})
				}else{
					$("#xjtsError").css({"display":"none"})
				}
			}
		}
	}
	
	
	return{
		//加载页面处理程序
		initControl:function(){
			initloginUser();
			initxjlb();
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
