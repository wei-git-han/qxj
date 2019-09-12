var url1 = {"url":rootPath +"/authority/orgauthority","dataType":"text"};//单位id
var url2 = {"url":rootPath +"/authority/authorization","dataType":"text"};//人员 idauthority

var idStr = (window.location.search).split("id=")[1];
var id = idStr.split("&flag=")[0];
var flag = (window.location.search).split("&flag=")[1];
var qxid = (window.location.search).split("&qxid=")[1];

var pageModule = function(){
	var initother = function(){
		$("input[name=radio1]").click(function(){
			var obj = this;
			$("[name=radio1]").each(function(i){
				if(obj!=this){
					$(this).removeAttr("checked");
					$(this).parent().removeClass("checked");
				}
			});
		});
		
		if(qxid && qxid != "null" && qxid != ""){
			$("#commentForm input").each(function(){
				if(qxid.indexOf($(this).val())>=0){
					$(this).attr("checked",true);
				}
			});
		}
		
			$("#commentForm").validate({
			    submitHandler: function() {
			    	var elementarry=[];
					$("#commentForm input").each(function(){
						if($(this).is(":checked")){
							elementarry.push($(this).attr("id"));
						}
					})
					var paramdata = getformdata(elementarry);
					paramdata.id = id;
					if(flag==0){
						var sjurl = url1;
					}else{
						var sjurl = url2;
					}
					console.log(sjurl);
					$ajax({
						url:sjurl,
						type: "POST",
						data : paramdata,
						success:function(data){
							if(data.result=="success"){
								newbootbox.newdialogClose("cdpz_sq");
								newbootbox.alertInfo("授权成功！").done(function(){
									window.top.iframe1.window.iframe2.window.grid.refresh();
								});
							}else{
								newbootbox.alertInfo("授权失败！");
							}
						}
					}) 
			    }
			});
		
		$("#save").click(function(){
			$("#commentForm").submit();
		})
		
		$("#close").click(function(){
			newbootbox.newdialogClose("cdpz_sq");
		})
		
	}
	
	return{
		//加载页面处理程序
		initControl:function(){
			initother();
		}
	}
	
}();

var showfn=function(thisd){
	if($("#SPDiv").is(":hidden")){
		$("#SPDiv").show();
		$(thisd).find("i").removeClass("fa-plus-square-o").addClass("fa-minus-square-o");
	}else{
		$("#SPDiv").hide();
		$(thisd).find("i").removeClass("fa-minus-square-o").addClass("fa-plus-square-o");
	}
	
}

