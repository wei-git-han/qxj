var id = getUrlParam2("id");
var saveUrl = {"url":"/app/qxjgl/qxjdicusers/addDeptAdmin","dataType":"text"};  //保存
var editInfo = {"url":rootPath +"/adminset/info","dataType":"text"}; //编辑数据
var userTree ={"url":"/app/base/user/tree","dataType":"text"}; //人员选择树
//获取某人的管理员类型（0:超级管理员 ;1：部管理员；2：局管理员；3：即是部管理员又是局管理员）
var getUserAdminTypeUrl = {"url":"/app/qxjgl/qxjdicusers/getUserRoleType","dataType":"text"};
var pageModule = function(){
	var initrolefn = function(){
		$ajax({
			url:getUserAdminTypeUrl,
			async:false,
			success:function(data){
				if(data==4 ||data==5 || data==6){
					userTree = {"url":"/app/base/user/allTree","dataType":"text"}; //全部部门树
				}else{
					userTree = {"url":"/app/base/user/tree","dataType":"text"}; //局选择树
				}
			}
		})
	}
	
	var initdatafn = function(){
		if(id!="" && !!id){
			$ajax({
				url:editInfo,
				data:{id:id},
				success:function(data){
					setformdata(data);
					$("#roleType").val("局管理员");
				}
			})
		}
	}
	
	var initother = function(){
		$("#userName").createUserTree({
			url : userTree,
			width:"100%",
			success : function(data, treeobj) {},
			selectnode : function(e, data) {
				$("#userName").val(data.node.text);
				$("#userId").val(data.node.id);
			}
		});
		
		$("#quxiao,#fanhui").click(function(){
			window.location.href="/app/qxjgl/ywpz/glysz/html/index.html";
		})
		
		$("#save").click(function(){
			var userName=$("#userName").val();
			var userId=$("#userId").val();
			if(userId == ''){
				newbootbox.alertInfo("请选择用户！");
				return;
			}
			$ajax({
				url:saveUrl,
				data:{username:userName,userid:userId,type:"2"},
				type: "GET",
				success:function(data){

					if(data.result == "success") {
						newbootbox.alertInfo('保存成功！').done(function(){
							window.location.href="/app/qxjgl/ywpz/glysz/html/index.html";
						});
					}else if(data.result == "exist"){
						newbootbox.alertInfo("重复设置！");
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
			initrolefn();
			initdatafn();
			initother();
		}
	};
	
}();
