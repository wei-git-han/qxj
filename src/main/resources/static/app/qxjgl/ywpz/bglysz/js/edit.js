var id = getUrlParam2("id");
var saveUrl = {"url":"/app/qxjgl/qxjdicusers/addDeptAdmin","dataType":"text"};  //保存
var editInfo = {"url":"/adminset/info","dataType":"text"}; //编辑数据
var userTree ={"url":"/app/base/user/allTree","dataType":"text"}; //人员选择树
var shouZhangTree ={"url":"/app/db/qxjdicusers/allShouZhang","dataType":"text"}; //人员选择树
var pageModule = function(){
	var initdatafn = function(){
		$ajax({
			url:editInfo,
			data:{id:id},
			success:function(data){
				setformdata(data);
				$("#roleType").val("部管理员");
			}
		})
	}

	var initother = function(){
/*		$("#leaderName").createUserTree({
			url : leaderTreeUrl,
			width : "100%",
			success : function(data, treeobj) {},
			selectnode : function(e, data,treessname,treessid) {
				$("#leaderId").val(treessid);
				$("#leaderName").val(treessname);
			},
			deselectnode:function(e,data,treessname,treessid){
				$("#leaderId").val(treessid);
				$("#leaderName").val(treessname);
		   }
		});*/
		$("#userName").createUserTree({
			url : userTree,
			width:"100%",
			success : function(data, treeobj) {},
			selectnode : function(e, data) {
				$("#userName").val(data.node.text);
				$("#userId").val(data.node.id);
			}
		});
 
//		//选择首长       seniorOfficial
//		$("#seniorOfficial").createUserTree({
//			url : shouZhangTree,
//			width:"100%",
//			success : function(data, treeobj) {},
//			selectnode : function(e, data) {
//				$("#seniorOfficial").val(data.node.text);
//				$("#seniorOfficialId").val(data.node.id);
//			}
//		});
//		
		$("#quxiao,#fanhui").click(function(){
			window.location.href="/app/qxjgl/ywpz/bglysz/html/index.html";
		})
		
		$("#save").click(function(){
			var userName=$("#userName").val();
			var userId=$("#userId").val();
			var seniorOfficial=$("#seniorOfficial").val();
			var seniorOfficialId=$("#seniorOfficialId").val();
			if(userId == ''){
				newbootbox.alertInfo("请选择用户！");
				return;
			}
			$ajax({
				url:saveUrl,
				data:{username:userName,userid:userId,type:"3"},
				type: "GET",
				success:function(data){
					if(data.result == "success") {
						newbootbox.alertInfo('保存成功！').done(function(){
							window.location.href="/app/qxjgl/ywpz/bglysz/html/index.html";
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
			initdatafn();
			initother();
		}
	}
}();
