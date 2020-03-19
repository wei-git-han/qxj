var leaverIds = getUrlParam('leaverIds');
var url1 = {"url":"/app/qxjgl/application/editInfo","dataType":"text"};  //编辑
var url2 = {"url":rootPath +"/dicvocationsort/dict?leaverIds="+leaverIds,"dataType":"text"};     //休假类别
var url3 = {"url":"/app/qxjgl/application/saveLeaveApplication","dataType":"text"};  //编辑保存成功
var url4 = {"url":rootPath +"/customuser/tree","dataType":"text"};  //编辑保存成功
var url5 = {"url":rootPath +"/leaveorback/getUser","dataType":"text"}//获取登陆人
var returnDate = {"url":rootPath +"/leaveOrBack/calculateHolidays","dataType":"text"};
var allUserTreeUrl = {"url":"/app/base/user/tree","dataType":"text"};//所有人员树
var id = getUrlParam('id');
var tishi="";
var userTree2 = {"url":rootPath +"/orguser/chairmantree","dataType":"text"};
var fileFrom = getUrlParam("fileFrom");
var fromMsg= getUrlParam("fromMsg");
var pageModule = function(){
	var initloginUser = function(){
		$ajax({
			url:url5,
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
		});
	};
	
	var initdatafn = function(){
		$ajax({
			url:url1,
			data:{id:id},
			success:initdata
		})
	}
	
	var initdata = function(data){
    	setformdata(data);
		initxjlb();
		setTimeout(function () {
			$("#xjlb option").each(function(){
				if($(this).text() == data.lb){
					$(this).attr({"selected": true});
					// this.selected=true;
				}
			});
		},50)
	};

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
	var initother = function(){
		
		$("#xjlb").change(function(){
			if($("#xjlb option:selected").text() == "年假"){
				$("#njtsDiv").show();
			}else{
				$("#njtsDiv").hide();
			}
		})
		
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
		/*	$("#xjts").val(GetDateDiff($("#xjsjFrom").val(),$("#xjsjTo").val()));*/
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
			});
			/*$("#xjts").val(GetDateDiff($("#xjsjFrom").val(),$("#xjsjTo").val()));*/
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
		$(".input-group-btn").click(function(){
			$(this).prev().focus();
		});
		
		$("#ghjhgz_edit_form").validate({
			    submitHandler: function() {
					var elementarry = ["sqrq","sqr","sqrId","deptDuty","xjlb","syts","xjsjFrom","xjsjTo","xjts","shouldTakDays","csld",
						"csldId","csparea","qjzt","spzt","mobile","place","origin","orgId","parentOrgId","orgName","vehicle","turnOver",
						'status',"holidayNum","weekendNum","linkMan","undertaker","undertakerId","undertakerMobile"];
					var paramdata = getformdata(elementarry);
					paramdata.id=id;
					$("#qjDialog").removeClass("none");
					$ajax({
						url:url3,
						type: "POST",
						data:paramdata,
						success:function(data){
                            $("#qjDialog").addClass("none")
							if(data.result=="success"){
								newbootbox.newdialogClose("qjEdit");
								var tstext = "保存成功！"
								newbootbox.alert(tstext).done(function(){
//									window.top.iframe1.setParams({'showTab':1});
									if(fromMsg=='1'){
										setParams({'showTab':1});
									}else{
										window.top.iframe1.setParams({'showTab':1});
									}
								});
							}else{
								newbootbox.alert("保存失败！");
							}
						}
					}) 
			      
			    }
		});
		
		 $("#save").click(function(){
		 	$("#ghjhgz_edit_form").submit();
		 });
		$("#close").click(function(){
			newbootbox.newdialogClose("qjEdit");
		})
	}
	
	
	return{
		//加载页面处理程序
		initControl:function(){
			// initloginUser();
			// initxjlb();
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

var xjtsErrorfn = function(){
	if($("#xjlb option:selected").text() == "年假"){
		if(parseInt($("#xjts").val()) > parseInt($("#syts").val())){
			$("#xjtsError").css({"display":"inline-block"})
		}else{
			$("#xjtsError").css({"display":"none"})
		}
	}
}
//修改页面参数并刷新
var setParams = function(obj){//{key:'val'}
	if(!obj){
		obj = {}
	}
	obj.isReload = true;
    var url = window.top.location.href.toString();
    for(i in obj){
        var re = eval('/('+i+'=)([^&]*)/gi');
        if(re.test(url)){
            url = url.replace(re,i+'='+obj[i])
        }else{
            if(url.indexOf('?')>-1){
                url +='&'+i+'='+obj[i]
            }else{
                url +='?'+i+'='+obj[i]
            }
        }
    }
    window.top.location.href = url;
};