var url1 = {"url":"/app/base/user/allTree","dataType":"text"};  //部门人员树  ----待修改
var url2 = {"url":"/app/base/user/tree","dataType":"text"};  //当前登录人局人员树  ----待修改
var url3 = {"url":"/app/base/dept/tree2","dataType":"text"};  //应用管理员部门树 ----待修改
var url4 = {"url":"/app/base/dept/tree","dataType":"text"};  //当前登录人的部门树 ----待修改
var url5 = {"url": rootPath + "/dicvocationsort/dict","dataType":"text"} //字典请假类型
var isAdministratiorUrl = {"url":"/leave/apply/acquireLoginPersonRole","dataType":"text"};  //判断是否为管理员 ----待修改
var urlDept = '';
var  urlPersons = '';
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
			pageSize:10,
			pageList: [10,20,50,100],
			striped:true,  
			//singleSelect: true,
			scrollbarSize:0,
			remoteSort:true,//是否排序数据从服务器
			rownumbers:true,
			method:'GET',
			//pageNumber:2,//初始页号
			columns:[
				[
					{field:'orgName',title:'请假部门',width:200,sortable:false,rowspan:2,align:'center',halign:'center',resizable:true},
					{field:'proposer',title:'请假人',width:100,sortable:false,rowspan:2,align:'center',halign:'center',resizable:true},
					{field:'shouldTakDays',title:'应休天数',width:100,sortable:false,rowspan:2,align:'center',halign:'center',resizable:true},
					{field:'vacationSortName',title:'请假类别',width:200,sortable:false,rowspan:2,align:'center',halign:'center',resizable:true},
					{field:'actualVocationDate',title:'休假天数',width:100,sortable:true,rowspan:2,align:'center',halign:'center',resizable:true},
					{title:'休假期间',colspan:3},
					{field:'statusName',title:'申请状态',width:100,sortable:true,rowspan:2,align:'center',halign:'center',resizable:true,formatter:function(value,data,index){
						 if(data.status == 0){
                         	return "<span class='blue'>待提交</span>";
                         }else if(data.status == 10){
                         	return "<span class='blue'>审批中</span>";
                         }else if(data.status == 30){
                         	return "<span>已通过</span>";
                         }else if(data.status == 20){
                         	return "<span class='red'>已退回</span>";
                         }
					}},
					{field:'backStatusName',title:'销假状态',width:100,sortable:true,rowspan:2,align:'center',halign:'center',resizable:true,formatter:function(value,data,index){
						if(data.backStatusId == 0){
                    		return "<span class='blue'>未销假</span>";
                    		//return "<button type='button' class='btn btn-xs btn-xj' onclick='xjfn(\""+rowdata.id+"\",\""+rowdata.qjsj+"\")'>销假</button>";
                    	}else if(data.backStatusId == 1){
                    		return "<span class='blue'>已销假</span>";
                    		//return "<span class='blue'>审批中</span>";
                        }/*else if(data.backStatusId == 2){
                        	return "<span>已通过</span>";
                        }else if(data.backStatusId == 3){
                        	return "<span class='red'>未通过</span>";
                        }*/
					}},
					{field:'caozuo',title:'操作',width:200,sortable:false,rowspan:2,align:'center',halign:'center',resizable:true,formatter:function(value,data,index){
						var view_html='<a title="查看" class="color-blueNewFa" onclick="viewfn(\''+data.id+'\',\''+data.backStatusId+'\',\''+data.status+'\')"><i class="fa fa-comment" style="color:#6699ff"></i></a>';
						return view_html
					}}
				],
				[
					{field:'planTimeStartEnd',title:'起止日期',sortable:true,width:250,align:'center',halign:'center',resizable:true},
					{field:'holidayNum',title:'法定节假日天数',sortable:true,width:150,align:'center',halign:'center',resizable:true},
					{field:'weekendNum',title:'周六日天数',sortable:true,width:150,align:'center',halign:'center',resizable:true}
				],
			],
			loadFilter:function(data){
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
		    rtl: Metronic.isRTL(),
		    orientation: "right",
		    format : "yyyy-mm-dd",
		    autoclose: true
		});

		$("input[name='documentStatus']").click(function(){
			$.uniform.update($("#documentStatusAll").attr("checked",false));
			refreshgrid();
		});

		
		$("#documentStatusAll").click(function(){
			$("input[name='documentStatus']").each(function(){
				$.uniform.update($(this).attr("checked",false));
			});
			$.uniform.update($(this).attr("checked",true));
			refreshgrid();
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
function refreshgrid(){
	var documentStatus;
	$("input[name='documentStatus']").each(function(){
		if($(this).is(":checked")){
			documentStatus = $(this).val();
		}
	});
	var keyids=["documentStatus","planTimeStart","planTimeEnd","deptid","deptname","userid","username","operateFlag","xjlb"];
	$('#gridcont').datagrid('load',getformdata(keyids));//重置第一页刷新
}