var url1 = {"url":rootPath +"/qxjdicholiday/save","dataType":"text"};
var url2 = {"url":"/app/base/user/tree","dataType":"text"};  //局内人员树
var url3 = {"url":"/app/base/dept/tree","dataType":"text"};  //部门树
var pageModule = function(){
	var inittree = function(){
		$("#username").createUserTree({
			url : url2,
			width : "100%",
			success : function(data, treeobj) {},
			selectnode : function(e, data) {
				$("#userid").val($("#" + data.selected).attr("id"));
				$("#username").val(data.node.text);
				var deptid = $("#usernametree2").jstree().get_parent(data.node.id);
				var deptname = $("#usernametree2").jstree().get_text(deptid)
				$("#deptid").val(deptid);
				$("#deptname").val(deptname);
			}
		});
	}	
	
	var inittreedept = function(){
		$("#deptname").createSelecttree({
			url : url3,
			width : "100%",
			success : function(data, treeobj) {},
			selectnode : function(e, data) {
				$("#deptid").val($("#" + data.selected).attr("id"));
				$("#deptname").val(data.node.text);
			}
		});
	}
	
	var inittreedept = function(){
		$("#department").createUserTree({
			url : url3,
			width : "100%",
			success : function(data, treeobj) {},
			selectnode : function(e, data) {
				$("#departmentId").val($("#" + data.selected).attr("id"));
				$("#department").val(data.node.text);
			}
		});
	}
	
	var initother = function(){
		$("#commentForm").validate({
		    submitHandler: function() {
				var elementarry =  ["username","userid","deptname","deptid","shouldtakdays"];
				var paramdata = getformdata(elementarry);
				$ajax({
					url:url1,
					type: "POST",
					data:paramdata,
					success:function(data){
						if(data.msg=="success"){
							newbootbox.newdialogClose("dyxjts_add");
							newbootbox.alertInfo("保存成功！").done(function(){
								window.top.iframe1.window.iframe2.window.pageModule.initgrid();
							});
						}else if(data.msg=="repeat"){
							newbootbox.alertInfo("保存失败！请检查此人是否已被定义过休假天数！"); 
						}else{
							newbootbox.alertInfo("保存失败！"); 
						}
					}
				}) 
		    },
		    errorPlacement: function(error, element) {
	    	 	if($(element).parent().hasClass("selecttree")){
	    	 		error.appendTo(element.parent().parent().parent()); 
	    	 	}else{
	    	 		error.appendTo(element.parent());  
	    	 	}
		     }
		});
		
		$("#save").click(function(){
			$("#commentForm").submit();
		})
		
		$("#close").click(function(){
			newbootbox.newdialogClose("dyxjts_add");
		})
	}
	
	
	return{
		//加载页面处理程序
		initControl:function(){
			inittree();
//			inittreedept();
			initother();
		}
	}
	
}();

var addDate = function(date,day){
	var d = new Date(date);
	d = d.valueOf();
	d = d +　day *　24 * 3600 * 1000;
	d = new Date(d);
	return d;
}

