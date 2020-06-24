var roleType = window.top.roleType;
var listUrl = {"url":"/app/qxjgl/modledept/list","dataType":"text"};
var delUrl = {"url":"/app/qxjgl/modledept/delete","dataType":"text"}; //删除
var grid = null;
var pageModule = function() {
	var initgrid = function() {
		grid = $("#gridcont").createGrid({
			columns: [{
				display: "模板名称",
				name: "modleName",
				width: "30%",
				align: "left",
				render: function(rowdata){
					 return rowdata.modleName; 
				}
			}, 
			{
				display: "模板内容",
				name: "modleValue",
				width: "30%",
				align: "left",
				render: function(rowdata){
					 return rowdata.modleValue; 
				}
			}, 
			{
				display: "部门",
				name: "deptName",
				width: "20%",
				align: "center",
				render:  function(rowdata){
					 return rowdata.deptName; 
				}
			},
			 {
				display: "更新时间",
				name: "updateTime",
				width: "20%",
				align: "center",
				render:  function(rowdata){
					 return rowdata.updateTime;
				}
			}
			],
			
			width: "100%",
			height: "100%",
			checkbox: true,
			rownumberyon: true,
			paramobj:{},
			overflowx: false,
            pagesize: 15,
			url: listUrl
			
		});

	}

	var initother = function() {
		//新增事件
		$("#add").click(function(){
			newbootbox.newdialog({
				id:"adddialog",
				width:800,
				height:550,
				header:true,
				title:"新增",
				url:"/app/qxjgl/ywpz/mbpz/html/add.html"
			});
		});
		//编辑
		$("#edit").click(function () {
			var datas=grid.getcheckrow();
			if(datas.length==1){
				newbootbox.newdialog({
					id: "editdialog",
					width: 800,
					height: 550,
					header: true,
					title: "编辑",
					url: "/app/qxjgl/ywpz/mbpz/html/edit.html?id="+datas[0].id
				});
			}else{
				newbootbox.alertInfo("请选择一条数据进行编辑！");
			}
		});
		//删除事件
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
								data: {"ids": ids.toString()},
								success: function(data) {
									if(data.msg == "success") {
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
		},
		initgrid:function(){
			grid.refresh();
		}
	}

}();
