var saveUrl = {"url":"/app/qxjgl/modledept/save","dataType":"text"};
var userTree = {"url":"/app/base/dept/tree_onlyroot","dataType":"text"}; //人员选择树

var pageModule = function(){
	var initother = function(){
		//部门选择
		$("#deptName").createDeptTree({
			url : userTree,
			plugins:"checkbox",
			width:"100%",
			success : function(data, treeobj) {},
			selectnode : function(e, data,treessname,treessid) {
				$("#deptName").val(treessname);
				$("#deptId").val(treessid);
			}
		});
	}
	//保存
	$("#save").click(function(){
		var modleName=$("#modleName").val();
		var modleValue=$("#modleValue").val();
		var deptName=$("#deptName").val();
		var deptId=$("#deptId").val();
		if(modleName == ''){
			newbootbox.alertInfo("请输入模板名称！");
			return;
		}
		if(modleValue == ''){
			newbootbox.alertInfo("请输入模板内容！");
			return;
		}
		if(deptName == ''){
			newbootbox.alertInfo("请选择局领导！");
			return;
		}
		$ajax({
			url:saveUrl,
			data:{modleName:modleName,modleValue:modleValue,deptName:deptName,deptId:deptId},
			type: "post",
			success:function(data){
				newbootbox.newdialogClose("adddialog");
				if(data.msg == "success") {
					newbootbox.alertInfo('保存成功！').done(function(){
						window.top.iframe1.window.iframe2.window.pageModule.reload();
					});
				}else{
					newbootbox.alertInfo('保存失败！').done(function(){
					});
				}
			}
		});
		
	})
	$("#quxiao").click(function(){
		newbootbox.newdialogClose("adddialog");
	})
	return{
		//加载页面处理程序
		initControl:function(){
			initother();
		}
	};
	
}();
