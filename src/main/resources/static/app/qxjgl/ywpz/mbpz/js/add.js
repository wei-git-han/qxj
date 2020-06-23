var saveUrl = {"url":"/app/qxjgl/qxjdicusers/save","dataType":"text"};
var userTree = {"url":"/app/base/dept/tree_onlyroot","dataType":"text"}; //人员选择树

var pageModule = function(){
	var initother = function(){
		//部门选择
		$("#deptName").createUserTree({
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
	
	return{
		//加载页面处理程序
		initControl:function(){
			initother();
		}
	};
	
}();
