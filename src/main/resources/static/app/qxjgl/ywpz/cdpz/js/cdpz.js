var url1 = {"url":rootPath +"/orguser/showuser","dataType":"text"};
var url2 = {"url":rootPath +"/authority/orgauthority","dataType":"text"};
var url3 = {"url":"/sysorgan/tree","dataType":"text"};

var id="root";
var treeid="";
var treetext="";
var grid = null;
var pageModule = function(){
	
	var inittree = function(){
		$ajax({
			url:url3,
			success:function(data){	
				$("#tree_2").jstree({
				    "plugins": ["wholerow", "types"],
				    "checkbox":{"keep_selected_style":false},
				    "core": {
					    "themes" : {
					        "responsive": false
					    },    
					    "data": data,
				    },
				    "types" : {
				    	"default" : {
					        "icon" : "peoples_img"
					    },
					    "file" : {
					        "icon" : "peoples_img"
					    },
					    "1" : {
					        "icon" : "people_img"
					    }
				    }
				});
				$("#currentOrgId").val(data.currentOrgId);
				var obj = {};
				obj.id = data.currentOrgId;
				grid.setparams(obj);
				grid.loadtable();
				
				$("#tree_2").on("select_node.jstree", function(e,data) { 
				    var id = $("#" + data.selected).attr("id");
					var obj = {};
					treeid=data.node.id;
					obj.id=id;
					grid.setparams(obj);
					grid.loadtable();
				});	
				
				
			}
		})
	}

	var initgrid = function(){
		
        grid = $("#gridcont").createGrid({
        	columns : [
                         {display:"姓名",name:"name",width:"20%",align:"left",paixu:false,render:getValue},
                         {display:"联系方式",name:"tel",width:"20%",align:"center",paixu:false,render:getValue},
                         {display:"权限",name:"qx",width:"40%",align:"left",paixu:false,render:getValue},
                         {display:"操作",name:"",width:"20%",align:"center",paixu:false,render:function(rowdata){
                        	 return '<span><button class="btn btn-sm btn-success OnButton" onclick="sqfn(this,\''+rowdata.id+'\',\''+rowdata.qxid+'\')" style="cursor:pointer" title="授权"><i class="fa fa-lock"></i></button>';
                         }}
                    ],
                    width:"100%",
                    height:"100%",
                    rownumberyon:true,
                    paramobj:{id:id},
                    overflowx:false,
                    pagesize: 18,
                    url: url1,
                    
               });
               
		initother();
	
	}
	
	function getValue(rowdata,index,val){
		if(val==null){
			return "";
		}
		return val;
	}
	
	var initother = function(){
 
	}
	
	return{
		//加载页面处理程序
		initControl:function(){
			initgrid();
			inittree();
		},
		initgrid:function(){
			grid.refresh();
		}
	}
	
}();


var closefn = function(){
	$("#viewcont").modal("hide");
}

var dwsqfn=function(){
	if (treeid == "") {
		newbootbox.alertInfo("请选择部门！");
		return;
	}
	newbootbox.newdialog({
		id:"cdpz_sq",
		width:500,
		height:300,
		header:true,
		title:"授权",
		url:"/app/qxjgl/ywpz/cdpz/html/cdpz_sq.html?id="+treeid+"&flag=0"
	});
}

var sqfn=function(thisd,id,qxid){
	var flag=1;
	newbootbox.newdialog({
		id:"cdpz_sq",
		width:500,
		height:300,
		header:true,
		title:"授权",
		url:"/app/qxjgl/ywpz/cdpz/html/cdpz_sq.html?id="+id+"&flag=1&qxid="+qxid
	});
}

