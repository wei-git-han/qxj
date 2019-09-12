var grid = null;
var tableUrl = {"url":"/app/qxjgl/qxjdicusers/selectDeptAdminBu","dataType":"text"};
var delUrl = {"url":"/app/qxjgl/qxjdicusers/deleteDeptAdmin","dataType":"text"};
var departType = getUrlParam2("departType");

var pageModule = function() {
	var initgrid = function() {
		grid = $("#gridcont").createGrid({
			columns: [
				  {display: "单位名称",name:"orgName",width: "25%",align: "center",render: function(rowdata,n){
					  return rowdata.orgName;
				  }},
				  {display: "部门",name:"deptName",width: "25%",align: "center",render: function(rowdata,n){
					  return rowdata.deptname;
				  }},

				  {display: "姓名",name: "userName",width: "25%",align: "center",render: function(rowdata,n){
					  return rowdata.username;
				  }}, 
				  
				  {display: "管理员类型",name: "adminType",width: "25%",align: "center",render: function(rowdata,n){
					  return "部管理员"
				  }}

				 ],
			width: "100%",
			height: "100%",
			checkbox: true,
			rownumberyon: true,
			paramobj:{type:'3'},
			overflowx: false,
            pagesize: 15,
			url: tableUrl
			
		});
	}

	var initother = function() {
		$("#add").click(function() {
			window.location.href="edit.html";
		});
		
		$("#edit").click(function() {
			var datas = grid.getcheckrow();
			var ids=[];
			if(datas.length < 1 || datas.length > 1) {
				newbootbox.alertInfo("请选择一条数据进行编辑！");
			} else {
				$(datas).each(function(i){
					ids[i]=this.id;
				});
				window.location.href="edit.html?id="+ids[0];
			}
			
		});

		$("#plsc").click(function() {
			var datas = grid.getcheckrow();
			var ids=[];
			if(datas.length < 1) {
				newbootbox.alertInfo("请选择要删除的数据！");
			} else {
				$(datas).each(function(i){
					ids[i]=this.id;
				});
				newbootbox.confirm({
					 title: "提示",
				     message: "是否要进行删除操作？",
				     callback1:function(){
				    	 $ajax({
								url: delUrl,
								type: "GET",
								data: {"ids": ids.toString(),"type":3},
								success: function(data) {
									if(data.result == "success") {
										newbootbox.alertInfo('删除成功！').done(function(){
											grid.refresh();
										});
									}else{
										newbootbox.alertInfo("删除失败！");
									}
								}
							})
				     }
				});
			}
		});
		
	}

	return {
		//加载页面处理程序
		initControl: function() {
			initgrid();
			initother();
		}
	}

}();

//编辑
var editfn = function(id){
	window.location.href="edit.html?id="+id;
}