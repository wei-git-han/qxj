var roleType = window.top.roleType;
var tableUrl = {"url":"/app/qxjgl/qxjdicusers/list?roleType="+roleType,"dataType":"text"};
var delUrl= {"url":"/app/qxjgl/qxjdicusers/delete","dataType":'json'};
var grid = null;
var pageModule = function() {
	var initgrid = function() {
		grid = $("#gridcont").createGrid({
			columns: [{
				display: "所在单位",
				name: "deptname",
				width: "50%",
				align: "left",
				render: function(rowdata){
					 return rowdata.deptname; 
				}
			}, {
				display: "姓名",
				name: "username",
				width: "30%",
				align: "center",
				render:  function(rowdata){
					 return rowdata.username; 
				}
			},
			 {
				display: "角色",
				name: "js",
				width: "20%",
				align: "center",
				render:  function(rowdata){
					 return rowdata.rolename;
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
			url: tableUrl
			
		});

	}

	var initother = function() {
		$("#add").click(function(){
			newbootbox.newdialog({
				id:"adddialog",
				width:800,
				height:550,
				header:true,
				title:"新增",
				url:"/app/qxjgl/ywpz/qxsz/html/add.html"
			});
		});
		$("#edit").click(function () {
			var datas=grid.getcheckrow();
			console.log(datas)
			var ids = [];
			var userIds = [];
			var userNames = [];
			var rolecodes = [];
			if(datas.length==1){
				$(datas).each(function(i){
					ids[i] = this.id;
					userIds[i] = this.userid;
					userNames[i] = this.username;
					rolecodes[i] = this.rolecode;
				});
				newbootbox.newdialog({
					id: "editdialog",
					width: 800,
					height: 550,
					header: true,
					title: "编辑",
					url: "/app/qxjgl/ywpz/qxsz/html/edit.html?id="+ids.toString()+"&userId="+userIds.toString()+"&userName="+userNames.toString()+"&rolecode="+rolecodes.toString()
				});
			}else{
				newbootbox.alertInfo("请选择一条数据进行编辑！");
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
		reload:function(){
			window.location.reload();
		}
	}

}();
