var infoUrl = {"url":"/app/qxjgl/modledept/info","dataType":"text"};//详情
var userTree = {"url":"/app/base/dept/tree_onlyroot","dataType":"text"}; //人员选择树
var saveUrl = {"url":"/app/qxjgl/modledept/update","dataType":"text"};//修改
var id = (window.location.search).split("id=")[1];
var pageModule = function(){
	var initother = function(){
		//部门选择
		$ajax({
			url:infoUrl,
			data:{id:id},
			type: "post",
			success:function(data){
				if(data.qxjModleDept.modleName){
					$("#modleName").val(data.qxjModleDept.modleName)
				}
				if(data.qxjModleDept.modleValue){
					$("#modleValue").val(data.qxjModleDept.modleValue)
				}
				if(data.qxjModleDept.deptName){
					$("#deptName").val(data.qxjModleDept.deptName)
					$("#deptId").val(data.qxjModleDept.deptId)
				}
			}
		});
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
				if(data.msg == "success") {
					newbootbox.alertInfo('保存成功！').done(function(){
						newbootbox.newdialogClose("editdialog");
						window.top.iframe1.window.iframe2.window.pageModule.initControl();
					});
				}else{
					newbootbox.alertInfo('保存失败！').done(function(){
						newbootbox.newdialogClose("editdialog");
					});
				}
			}
		});
		
	})
	$("#quxiao").click(function(){
		newbootbox.newdialogClose("editdialog");
	})
	return{
		//加载页面处理程序
		initControl:function(){
			initother();
		}
	};
	
}();
