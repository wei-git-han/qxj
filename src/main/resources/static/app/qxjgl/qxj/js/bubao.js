var saveUrl = {"url":"/leave/apply/qxjBuBao","dataType":"json"};
var userTree = {"url":"/app/base/user/tree","dataType":"text"}; //人员选择树

var dFlowId = getUrlParam("id"); //主流程id
var leaderId = getUrlParam("leaderId")||""; //领导id
var fileFrom = getUrlParam("fileFrom")||"";
var checkArry=[];
var pageModule = function(){
	var initAllUser = function(){
		$ajax({
			url:userTree,
			async:false,
			success:function(data){
				$("#tree_2").jstree("destroy");
				$("#tree_2").jstree({
				    "plugins": ["wholerow", "types" ,"checkbox" ],
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
				$("#tree_2").on("load_node.jstree", function(e,data) {
					for(var i=0;i<checkArry.length;i++){
						$("#tree_2").jstree("select_node",checkArry[i],true);
					}
				});
				$("#tree_2").on("select_node.jstree", function(e,data) {
					$("#alreadyUser").html("");
					var nodes2 = $("#tree_2").jstree("get_bottom_selected",true);
					AddUser(nodes2);
				});
				$("#tree_2").on("deselect_node.jstree", function(e,data) {
					$("#alreadyUser").html("");
					var nodes2 = $("#tree_2").jstree("get_bottom_selected",true);
					AddUser(nodes2);
				});
			}
		})
	}
	
	var initOther = function(){
		$("#save").click(function(){
			var selectUsers = '';
			//补报人员   id,name;
			$("#alreadyUser li").each(function(i,item){
				selectUsers += $(this).attr("id")+','+$(this).attr("name")+';';
			})
			if (selectUsers.length < 1) {
			    newbootbox.alert('请选择补报人员！');
			    return ;
			}
			$ajax({
				url:saveUrl,
				data:{
				    id:dFlowId,
				    approvalIds:selectUsers,
				    approveContent:'',
                    opinionType:'',
                    operateFlag:''
				},
				type: "post",
				success:function(data){
					if(data.result == "success"){
						newbootbox.newdialogClose("bubaoDialog");
						newbootbox.alert('补报成功！').done(function(){
						    if ( fileFrom == 'qxjsq') {
                               //$("#iframe1",window.top.document).attr("src","/app/qxjgl/qxj/html/table.html");
                               window.top.iframe1.v_edit.getLeaveInfo();
						    }
						});
					}else{
						newbootbox.alert('补报失败！');
					}
				}
			});
		});
		
		$("#quit").click(function(){
			$("#alreadyUser").html("");
			checkArry=[];
			initAllUser();
		});
	}

	return{
		//加载页面处理程序
		initControl:function(){
			//initComUser();
			initAllUser();
			initOther();
		}
	};
	
}();

function AddUser(nodes2){
	$.each(nodes2, function(i,obj) {
		if(obj.original.type == 1){
			$("#alreadyUser").append(
				'<li id='+obj.id+' name='+obj.text+'>'+
				'	<a href="javascript:;">'+
				'		<span>'+obj.text+'</span>'+
				'		<span style="float:right">'+
				'			<i class="fa fa-trash-o del" onclick="deletefn(\''+obj.id+'\',this);"></i>'+
				'			<i class="fa fa-arrow-up up" onclick="upfn(this);"></i>'+
				'			<i class="fa fa-arrow-down down" onclick="downfn(this)"></i>'+
				'		</span>'+
				'	</a>'+
				'</li>'
			);
		}
	});
	iShow();
}

function loadUser(nodes2){
	$.each(nodes2, function(i,obj) {
		checkArry.push(obj.contacterId);
	});
}

function iShow(){
	$("#alreadyUser li").each(function(i,item){
		if(i==0){
			$(this).find(".up").hide();
		};
		if(i==$("#alreadyUser li").length-1){
			$(this).find(".down").hide();
		};
	})
}

function deletefn(id,obj){
	$(obj).parents("li").remove();
	$("#tree_2").jstree().uncheck_node(id);
}

function upfn(obj){
	var currentId = $(obj).parents("li").attr("id");
	var prevId = $(obj).parents("li").prev().attr("id");
	var currentText = $(obj).parent().prev().text();
	var prevText = $(obj).parents("li").prev().find("span:eq(0)").text();
	$(obj).parent().prev().text(prevText);
	$(obj).parents("li").attr("id",prevId);
	$(obj).parents("li").attr("name",prevText);
	$(obj).parents("li").prev().find("span:eq(0)").text(currentText);
	$(obj).parents("li").prev().attr("id",currentId);
	$(obj).parents("li").prev().attr("name",currentText);
	iShow();
}
function downfn(obj){
	var currentId = $(obj).parents("li").attr("id");
	var nextId = $(obj).parents("li").next().attr("id");
	var currentText = $(obj).parent().prev().text();
	var nextText = $(obj).parents("li").next().find("span:eq(0)").text();
	$(obj).parent().prev().text(nextText);
	$(obj).parents("li").attr("id",nextId);
	$(obj).parents("li").attr("name",nextText);
	$(obj).parents("li").next().find("span:eq(0)").text(currentText);
	$(obj).parents("li").next().attr("id",currentId);
	$(obj).parents("li").next().attr("name",currentText);
	iShow();
}
