var id = window.location.search.split("id=")[1];
var grid = null;
var roleType = window.top.roleType;
var url1 = {
	"url": rootPath + "/qxjdicholiday/list?roleType="+roleType,
	"dataType": "text"
};
var url2 = {
	"url": rootPath + "/qxjdicholiday/delete",
	"dataType": "json"
};
console.log(roleType)
var pageModule = function(){
	var initgrid = function(){
        grid = $("#gridcont").createGrid({
            columns:[	
                         {display:"姓名",name:"username",width:"10%",align:"center",paixu:false,render:function(rowdata){
                            return rowdata.username;                                         
                         }},
                         {display:"部门",name:"deptname",width:"70%",align:"left",paixu:false,render:function(rowdata){
                            return rowdata.deptname;                                         
                         }},
                         {display:"应休假天数",name:"shouldtakdays",width:"10%",align:"center",paixu:false,render:function(rowdata){
                            return rowdata.shouldtakdays;                                        
                         }},
                         {display:"操作",name:"",width:"10%",align:"center",paixu:false,render:function(rowdata,n){
                         	return '<button type="button" class="btn btn-xs btn-icon QBlackFont" onclick="editfn(\''+rowdata.id+'\')"  title="编辑"><i class="fa fa-edit"></i></button>'+
                         		   '<button type="button" class="btn btn-xs btn-icon QBlackFont" onclick="removefn(\''+rowdata.id+'\')" title="删除"><i class="fa fa-trash-o"></i></button>';
                         }},
                     ],
            width:"100%",
            height:"100%",
            checkbox: true,
            rownumberyon:true,
            paramobj:{id:id},
            pagesize: 12,
            url: url1
       });
	}
	
	
	var initother = function(){
		$("#add").click(function(){
			newbootbox.newdialog({
				id:"dyxjts_add",
				width:800,
				height:550,
				header:true,
				title:"新增",
				url:"/app/qxjgl/ywpz/dyxjts/html/dyxjts_add.html"
			});
		});
		
		$("#plsc").click(function(){
			var datas=grid.getcheckrow();
			var ids=[];
			if(datas.length>0){
				$(datas).each(function(i){
					ids[i]=this.id;
				});
				removefn(ids.toString());
			}else{
				newbootbox.alertInfo("请选择要删除的数据！");
			}
		});
		
		$("#search").click(function(){
			var keyids=["searecBt"];
			grid.setparams(getformdata(keyids));
			grid.loadtable();
		});
		
		$(".date-picker").datepicker({
		    language:"zh-CN",
		    rtl: Metronic.isRTL(),
		    orientation: "right",
		    autoclose: true
		});
	}
	
	return{
		//加载页面处理程序
		initControl:function(){
			initgrid();
			initother();
		},
		initgrid:function(){
			grid.refresh();
		}
	}
	
}();


var closefn = function(){
	$("#viewcont").modal("hide");
}

var editfn = function(id){
	newbootbox.newdialog({
		id:"dyxjts_edit",
		width:800,
		height:550,
		header:true,
		title:"编辑",
		url:"/app/qxjgl/ywpz/dyxjts/html/dyxjts_edit.html?id="+id
	});
}


var removefn = function(id){
	 newbootbox.confirm({
     	title:"提示",
     	message: "是否要进行删除操作？",
     	callback1:function(){
     		$ajax({
				url:url2,
				type:"POST",
				data:{id:id},
				success:function(data){
					if(data.msg=="success"){
						newbootbox.alertInfo('删除成功！').done(function(){
							pageModule.initgrid();
	    				});
					}
				}
			})
     	}
     });
}
