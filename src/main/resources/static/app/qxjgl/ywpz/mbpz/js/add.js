var saveUrl = {"url":"/app/qxjgl/qxjdicusers/save","dataType":"text"};
var userTree = {"url":"/app/base/user/tree","dataType":"text"}; //人员选择树

var pageModule = function(){
	var initother = function(){
		
		$("#username").createUserTree({
			url : userTree,
			width : "100%",
			success : function(data, treeobj) {},
			selectnode : function(e, data) {
				$("#username").val(data.node.text);
				$("#userid").val(data.node.id);
				var deptid = $("#usernametree2").jstree().get_parent(data.node.id);
				$("#deptid").val(deptid);
			}
		});
		
		$("#quxiao").click(function(){
			newbootbox.newdialogClose("adddialog");
		})
		
		$("#save").click(function(){
			var username=$("#username").val();
			var userid=$("#userid").val();
			var deptid=$("#deptid").val();
			if(userid == ''){
				newbootbox.alertInfo("请选择用户！");
				return;
			}
			$ajax({
				url:saveUrl,
				data:{username:username,userid:userid,deptid:deptid,rolecode:$("#rolecode").val(),rolename:$("#rolecode").find("option:selected").text()},
				type: "GET",
				success:function(data){
					newbootbox.newdialogClose("adddialog");
					if(data.msg == "success") {
						newbootbox.alertInfo('保存成功！').done(function(){
							window.top.iframe1.window.iframe2.window.pageModule.reload();
						});
					}else if(data.msg == "repeat") {
						newbootbox.alertInfo('该用户已定义角色,如想定义新角色请先删除原有角色!').done(function(){
						});
					}else{
						newbootbox.alertInfo("保存失败！");
					}
				}
			});
			
		})
	}
	
	return{
		//加载页面处理程序
		initControl:function(){
			initother();
		}
	};
	
}();
