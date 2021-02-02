var roleType = window.top.roleType;
var url1 = {"url":"/app/base/user/allTree","dataType":"text"};  //部门人员树  ----待修改
var url2 = {"url":"/app/base/user/tree","dataType":"text"};  //当前登录人局人员树  ----待修改
var url3 = {"url":"/app/base/dept/tree2","dataType":"text"};  //应用管理员部门树 ----待修改
var url4 = {"url":"/app/base/dept/tree","dataType":"text"};  //当前登录人的部门树 ----待修改
var url5 = {"url": rootPath + "/dicvocationsort/dict","dataType":"text"} //字典请假类型
var url6 = {"url":"/app/qxjgl/leaveOrBack/deleteQxj","dataType":"text"};//管理员删除请销假
var isAdministratiorUrl = {"url":"/leave/apply/acquireLoginPersonRole","dataType":"text"};  //判断是否为管理员 ----待修改
var urlDept = '';
var  urlPersons = '';

var o= window.top.size;
if(!o){
	window.top.size = o = { }
}else if(window.top.size.documentStatus){
	$("#documentStatusAll").prop("checked",false)

	$("input[name='documentStatus']").each(function(ind){
		var val = $("input[name='documentStatus']")[ind].value;
		if(window.top.size.documentStatus == val ){
			$(this).attr("checked",true)
		}else{
			var ary = window.top.size.documentStatus.split(",");
			for(var i in ary){
				if(ary[i]==val){
					$(this).attr("checked",true)
				}
			}
		}
	 });
	
}
var pageModule = function(){
	var initxjlb = function(){
		$ajax({
			url:url5,
			success:function(data){
				data.xjlb.unshift({
                    value:'',
                    text:'请选择'
                })
                initselectNew("xjlb",data.xjlb);
//				initselect("xjlb",data.xjlb);
			}
		})
	}
	var initgrid = function(){
		$('#gridcont').datagrid({
			url:"/app/qxjgl/leaveOrBack/getQXJlist",
			//width: "auto",
			width: "auto",
			height: $('#gridcont').parent().height(),
			pagination:true,
			fitColumns: true,
			pageSize:o.pageSize||15,
			pageList: [15,20,50,100],
			striped:true,
			//singleSelect: true,
			scrollbarSize:0,
			remoteSort:true,//是否排序数据从服务器
			rownumbers:true,
            checkbox: true,
            rownumberyon: true,
            overflowx: false,
			method:'GET',
			queryParams:{documentStatus:window.top.size.documentStatus,planTimeStart:window.top.size.planTimeStart,planTimeEnd:window.top.size.planTimeEnd,deptid:window.top.size.deptid,deptname:window.top.size.deptname,userid:window.top.size.userid,username:window.top.size.username,xjlb:window.top.size.xjlb},
			pageNumber:o.num||1,//初始页号
			columns:[
				[
					{field:'orgName',title:'请假部门',width:200,sortable:false,rowspan:2,align:'center',halign:'center',resizable:true},
					{field:'proposer',title:'请假人',width:100,sortable:false,rowspan:2,align:'center',halign:'center',resizable:true,formatter:function(value,data,index){
                    		return '<span class="blue pointer" onclick="previewfn(\''+data.id+'\')" title="'+data.proposer+'">'+ data.proposer +'</span>';
					}},
					{field:'shouldTakDays',title:"<span title='应休天数'>应休天数</span>",width:110,sortable:false,rowspan:2,align:'center',halign:'center',resizable:true},
					{field:'noLeaveDays',title:"<span title='未休天数'>未休天数</span>",width:110,sortable:false,rowspan:2,align:'center',halign:'center',resizable:true},
					{field:'leavedDays',title:"<span title='已休天数'>已休天数</span>",width:110,sortable:false,rowspan:2,align:'center',halign:'center',resizable:true},
					{field:'vacationSortName',title:"<span title='请假类别'>请假类别</span>",width:150,sortable:false,rowspan:2,align:'center',halign:'center',resizable:true},
					{field:'actualVocationDate',title:"<span title='休假天数'>休假天数</span>",width:110,sortable:true,rowspan:2,align:'center',halign:'center',resizable:true},
					{title:'休假期间',colspan:3},
					{field:'place',title:'地点',width:200,sortable:true,rowspan:2,align:'center',halign:'center',resizable:true,formatter:function(value,data,index){
						return "<span title='"+data.place+"'>"+data.place+"</span>";
					}},
					{field:'origin',title:"<span title='请假事由'>请假事由</span>",width:200,sortable:true,rowspan:2,align:'center',halign:'center',resizable:true,formatter:function(value,data,index){
						return "<span title='"+data.origin+"'>"+data.origin+"</span>";
					}},
					{field:'statusName',title:"<span title='申请状态'>申请状态</span>",width:110,sortable:true,rowspan:2,align:'center',halign:'center',resizable:true,formatter:function(value,data,index){
						 if(data.status == 0){
                         	return "<span class='status-primary'>待提交</span>";
                         }else if(data.status == 10){
                         	return "<span class='status-primary'>审批中</span>";
                         }else if(data.status == 30){
                         	return "<span class='status-green'>已通过</span>";
                         }else if(data.status == 31){
                         	return "<span class='status-red'>待销假</span>";
                         }else if(data.status == 32){
                         	return "<span class='status-gray'>已销假</span>";
                         }else if(data.status == 20){
                         	return "<span class='status-red'>已退回</span>";
                         }
					}},
					/*{field:'backStatusName',title:"<span title='销假状态'>销假状态</span>",width:110,sortable:true,rowspan:2,align:'center',halign:'center',resizable:true,formatter:function(value,data,index){
						if(data.backStatusId == 0){
                    		return "<span class='blue'>未销假</span>";
                    		//return "<button type='button' class='btn btn-xs btn-xj' onclick='xjfn(\""+rowdata.id+"\",\""+rowdata.qjsj+"\")'>销假</button>";
                    	}else if(data.backStatusId == 1){
                    		return "<span style='color:#ccc'>已销假</span>";
                    		//return "<span class='blue'>审批中</span>";
                        }else if(data.backStatusId == 2){
                        	return "<span>已通过</span>";
                        }else if(data.backStatusId == 3){
                        	return "<span class='red'>未通过</span>";
                        }
					}},*/
					{field:'caozuo',title:'操作',width:200,sortable:false,rowspan:2,align:'center',halign:'center',resizable:true,formatter:function(value,data,index){
						var view_html0='<a title="查看" class="color-blueNewFa" onclick="viewfn(\''+data.id+'\',\''+data.backStatusId+'\',\''+data.status+'\')"><i class="fa fa-comment" style="color:#6699ff"></i></a>';
						var view_html1='<a title="查看" class="color-blueNewFa" onclick="viewfn(\''+data.id+'\',\'spyj\')"><i class="fa fa-book" style="color:#6699ff"></i></a>';
						if(roleType ==3 || roleType==5){
                            var view_html2='<a title="删除" class="color-blueNewFa" onclick="deletefn(\''+data.id+'\')" ><i class="fa fa-trash-o"></i></a>'
                            return view_html0+view_html1+view_html2;
						}else {
                            return view_html0+view_html1
						}
					}}
				],
				[
					{field:'planTimeStartEnd',title:"<span title='起止日期'>起止日期</span>",sortable:true,width:250,align:'center',halign:'center',resizable:true,formatter:function(value,data,index){
						return "<span title='"+data.planTimeStartEnd+"'>"+data.planTimeStartEnd+"</span>";
					}},
					{field:'holidayNum',title:"<span title='法定节假日天数'>法定节假日天数</span>",sortable:true,width:150,align:'center',halign:'center',resizable:true},
					{field:'weekendNum',title:"<span title='周六日天数'>周六日天数</span>",sortable:true,width:150,align:'center',halign:'center',resizable:true}
				],
			],
			loadFilter:function(data){
				window.top.size.num = data.currPage;
				window.top.size.pageSize = data.pageSize;
//				window.top.size.searchValue = data.list;
				return {
					total:data.totalCount,
					rows:data.list,
					page:data.currPage
				}
			}
		});
	}
	var isAdministratior = function(){
		$ajax({
			url:isAdministratiorUrl,
			type: "POST",
			// data:paramdata,
			success:function(data){
				console.log(data)
				if (data.loginPersonRole == 6 || data.loginPersonRole == 5 || data.loginPersonRole == 4) {
					urlDept = url3;
					urlPersons = url1;
				} else {
					urlDept = url4;
					urlPersons = url2;
				}
				deptfn();
				inittree();
			}
		});
	}
	
	var deptfn = function(){
		$("#deptname").createSelecttree({
			url : urlDept,
			width : "100%",
			success : function(data, treeobj) {},
			selectnode : function(e, data) {
				$("#deptid").val($("#" + data.selected).attr("id"));
				$("#deptname").val(data.node.text);
			}
		});
	}
	
	var inittree = function(){
		$("#username").createUserTree({
			url : urlPersons,
			width : "100%",
			success : function(data, treeobj) {},
			selectnode : function(e, data) {
				$("#userid").val($("#" + data.selected).attr("id"));
				$("#username").val(data.node.text);
			}
		});
	}
	
	var initother = function(){

		$("#export").click(function(){
			// window.location.href="/app/qxjgl/leaveorback/exportQXJList";
		});
		
		$(".date-picker").datepicker({
		    language:"zh-CN",
		   // rtl: Metronic.isRTL(),
		    orientation: "right",
		    format : "yyyy-mm-dd",
		    autoclose: true
		});

		$("input[name='documentStatus']").click(function(){
			var _this = this;
			$("input[name='documentStatus']").each(function(i,v){
                if(this!=_this)
               $(v).attr("checked",false)
            });
			$("#documentStatusAll").attr("checked",false)
			refreshgrid();
			resetChecked();
		});

		
		$("#documentStatusAll").click(function(){
			$("input[name='documentStatus']").each(function(){
				$(this).attr("checked",false)
			});
			$(this).attr("checked",true)
			refreshgrid();
			resetChecked();
		});
		
		$("#showSearch").click(function(){
			$("#searchwrap").toggle();
		});
		
		//筛选功能
		$("#queding").click(function(){
			 refreshgrid();
		});
		
		//重置
		$("#reset").click(function(){
			removeInputData(["planTimeStart","planTimeEnd","deptid","deptname","userid","username","xjlb"]);
		})
	}
	 
	return{
		//加载页面处理程序
		initControl:function(){
			initgrid();
			initother();
			// deptfn();
			// inittree();
			isAdministratior();
			initxjlb();
		}
	}
}();
var c1 = {};
$(window).resize(function(){
	clearTimeout(c1);
	c1 = setTimeout(function(){
		pageModule.initControl()
	},500)
});
var viewfn = function(id,xjzt,status){
	if(xjzt>0){
		//销假链接
		newbootbox.newdialog({
			id:"View",
			width:800,
			height:700,
			header:false,
			title:"",
			style:{
				"padding":"0px",
			},
			url:"/app/qxjgl/qxj/html/view_tab.html?id="+id
		});
		
	}else if(xjzt=="spyj"){
		//审批意见链接
	    newbootbox.newdialog({
	        id:"qjView",
	        width:800,
	        height:600,
	        header:true,
	        title:"审批意见",
	        style:{
	            padding:0
	        },
	        url:rootPath + "/qxj/html/opinionList.html?id="+id
	    });
		
	}else{
		//请假链接
		newbootbox.newdialog({
			id:"qjView",
			width:840,
			height:720,
			header:true,
			title:"请假查看",
			url:"/app/qxjgl/qxj/html/qj_chakan.html?id="+id+"&status="+status
		});
	}
}

function deletefn(id) {
        newbootbox.prompt({
            title:'确认删除',
            classed:'cjDialog',
            message:'删除后本条请假信息将被彻底删除且无法恢复!<br/>是否确认撤回?<br/>请输入“是”进行确认撤回操作！',
            confirm:function(value){
                if(value=='是'){
                    $ajax({
                        url:url6,
                        type:"POST",
                        data:{id:id},
                        success:function(data){
                            if(data.msg=="success"){
                                newbootbox.alertInfo('删除成功！').done(function(){
                                    refreshgrid();
                                });
                            }
                        }
                    })
                }
            }
        })
}

function refreshgrid(){
	var documentStatus;
	$("input[name='documentStatus']").each(function(){
		if($(this).is(":checked")){
			documentStatus = $(this).val();
		}
	});
	var keyids=["documentStatus","planTimeStart","planTimeEnd","deptid","deptname","userid","username","operateFlag","xjlb"];
	window.top.size = getformdata(keyids)
	$('#gridcont').datagrid('load',getformdata(keyids));//重置第一页刷新
}
//查看详情
var previewfn=function(id){
	var url=rootPath + '/qxj/html/qxjView.html?id='+id+"&filefrom=qxjsq"
	window.top.iframe1.location.href = url;
};