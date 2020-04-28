var applicationListUrl = {"url":"/app/qxjgl/application/applicationList?filefrom=qxjsp","dataType":"text"};//请销假审批列表
var withdrawUrl = {"url":"/leave/apply/withdrawToLastApply","dataType":"text"};//撤回操作
var userTree = {"url":"/app/base/user/tree","dataType":"text"};  //人员树（高级筛选）
var dicvocationUrl = {"url":rootPath +"/dicvocationsort/dict","dataType":"text"}; //休假类别（高级筛选）
var xjzt ={"url":"/app/qxjgl/qxj/data/xjzt.json","dataType":"text"}//销假类别（高级筛选）
var grid = null;
var o  = window.top.param||{
	page:1
}
var shSearchFormdata = window.top.shSearchFormdata||{}
var pageModule = function(){
	var initgrid = function(){
        grid = $("#gridconts").createGrid({
            columns:[	
            	{display:"申请状态",name:"status",width:"10%",align:"center",paixu:false,render:function(rowdata){
					var statusName="";
					var color="status-red";
					if(rowdata.status == 0){
						statusName="待提交";
						color="status-red";
					}else if(rowdata.status == 10){
						statusName="待审批";
						color="status-red";
						if(1 != rowdata.receiverIsMe){
							if(rowdata.flowType==10){
								statusName=rowdata.dealUserName+"审批中";
							}else if(rowdata.flowType == 11){
								statusName=rowdata.dealUserName+"核文中";
							}else if(rowdata.flowType == 12){
								statusName="送"+rowdata.dealUserName+"审批";
							}else if(rowdata.flowType == 13){
								statusName=rowdata.dealUserName+"部长审批完成";
							}else if(rowdata.flowType == 14){
								statusName="送"+rowdata.dealUserName+"审批";
							}
							color="status-primary";
						}
					}else if(rowdata.status == 20){
						statusName="已退回";
						color="status-red";
						if(1 != rowdata.receiverIsMe){
							statusName="已退回"+rowdata.dealUserName;
							color="status-primary";
						}
					}else if(rowdata.status == 30){
						statusName="已通过";
						color="status-success";
					}else if(rowdata.status == 40){
						statusName="已作废";
						color="status-gray";
					}
					return '<span style="cursor: pointer;" class="status-btn '+color+'" onclick="previewfn(\''+rowdata.id+'\',\''+rowdata.receiverIsMe+'\',\''+rowdata.flowType+'\',\''+rowdata.backStatusId+'\')">'+statusName+'</span>';
				}},
                 {display:"请假人",name:"status",width:"10%",align:"center",paixu:false,render:function(rowdata){
                	 return '<span class="pointer" title="'+rowdata.proposer+'" onclick="previewfn(\''+rowdata.id+'\',\''+rowdata.receiverIsMe+'\',\''+rowdata.flowType+'\')">'+rowdata.proposer+'</span>';
	             }},
	             {display:"应休天数",name:"yxday",width:"6%",align:"center",paixu:false,render:function(rowdata){
	                 return '<span class="pointer" title="'+rowdata.offDays+'" onclick="previewfn(\''+rowdata.id+'\',\''+rowdata.receiverIsMe+'\',\''+rowdata.flowType+'\')">'+rowdata.offDays+'</span>';
	             }},
                 {display:"已休天数",name:"numday",width:"6%",align:"center",paixu:false,render:function(rowdata){
	                  return '<span class="pointer" title="'+rowdata.leavedDays+'" onclick="previewfn(\''+rowdata.id+'\',\''+rowdata.receiverIsMe+'\',\''+rowdata.flowType+'\')">'+rowdata.leavedDays+'</span>';
	             }},
                 {display:"未休天数",name:"wxday",width:"6%",align:"center",paixu:false,render:function(rowdata){
                     return '<span class="pointer" title="'+rowdata.noLeaveDays+'" onclick="previewfn(\''+rowdata.id+'\',\''+rowdata.receiverIsMe+'\',\''+rowdata.flowType+'\')">'+rowdata.noLeaveDays+'</span>';
	             }},
                 {display:"请假类别",name:"lb",width:"10%",align:"center",paixu:false,render:function(rowdata){
                    return '<span class="pointer" onclick="previewfn(\''+rowdata.id+'\',\''+rowdata.receiverIsMe+'\',\''+rowdata.flowType+'\')">'+rowdata.vacationSortName+'</span>';
                 }},
                 {display:"起止日期",name:"qjsj",width:"15%",align:"center",paixu:false,render:function(rowdata){
                    return '<span class="pointer" onclick="previewfn(\''+rowdata.id+'\',\''+rowdata.receiverIsMe+'\',\''+rowdata.flowType+'\')">'+rowdata.planTimeStartEnd+'</span>';
                 }},
                 {display:"请假天数",name:"sjqjsj",width:"6%",align:"center",paixu:false,render:function(rowdata){
	            	 if(rowdata.noLeaveMinDays < rowdata.actualVocationDate){
	            		 return '<span class="pointer" style="color:red" onclick="previewfn(\''+rowdata.id+'\',\''+rowdata.receiverIsMe+'\',\''+rowdata.flowType+'\')">'+rowdata.actualVocationDate+'</span>';
	            	 }else{
	            		 return '<span class="pointer" onclick="previewfn(\''+rowdata.id+'\',\''+rowdata.receiverIsMe+'\',\''+rowdata.flowType+'\')">'+rowdata.actualVocationDate+'</span>';
	            	 }                                     
                   }},
                {display:"申请日期",name:"sqrq",width:"10%",align:"center",paixu:false,render:function(rowdata,n){
              	     var applicationDate="";
	              	 if(rowdata.applicationDate){
	              		 applicationDate=rowdata.applicationDate.substring(0,10);
	              	 }
	                  return '<span class="pointer" onclick="previewfn(\''+rowdata.id+'\',\''+rowdata.receiverIsMe+'\',\''+rowdata.flowType+'\')">'+applicationDate+'</span>';
                }},
                 {display:"销假状态",name:"xjzt",width:"10%",align:"center",paixu:false,render:function(rowdata){
                	 if(rowdata.backStatusId == 0){
                 		return '<span class="blue pointer" onclick="previewfn(\''+rowdata.id+'\',\''+rowdata.receiverIsMe+'\',\''+rowdata.flowType+'\')">未销假</span>';
                 	}else if(rowdata.backStatusId == 1){
                 		return '<span class="blue pointer" onclick="previewfn(\''+rowdata.id+'\',\''+rowdata.receiverIsMe+'\',\''+rowdata.flowType+'\')">已销假</span>';
                     }else if(rowdata.backStatusId == 2){
                     	return '<span class="pointer" onclick="previewfn(\''+rowdata.id+'\',\''+rowdata.receiverIsMe+'\',\''+rowdata.flowType+'\')">已通过</span>';
                     }else if(rowdata.backStatusId == 3){
                     	return '<span class="red pointer" onclick="previewfn(\''+rowdata.id+'\',\''+rowdata.receiverIsMe+'\',\''+rowdata.flowType+'\')">未通过</span>';
                     }
                 }},
                 {display:"操作",name:"",width:"11%",align:"center",paixu:false,render:function(rowdata,n){
                	 //var check_html='<a title="审批" class="color-blueNewFa" onclick="editspfn(\''+rowdata.id+'\',\''+rowdata.xjzt+'\')"><i class="fa fa-file-text-o"></i></a>'
                	 //var export_html='<a title="下载" class="color-blueNewFa" onclick="exportfn(\''+rowdata.id+'\',\''+rowdata.xjzt+'\')"><i class="fa fa-download"></i></a>';
                	// var preview_html='<a title="预览" class="color-blueNewFa" onclick="previewfn(\''+rowdata.id+'\',\''+rowdata.xjzt+'\')"><i class="fa fa-search-plus"></i></a>';
                	 var view_html='<a title="查看" class="color-blueNewFa" onclick="viewfn(\''+rowdata.id+'\')"><i class="fa fa-comment"></i></a>';
                	 var withdraw_html='<a title="撤回" class="color-blueNewFa" onclick="withdrawfn(\''+rowdata.id+'\')"><i class="fa fa-mail-reply"></i></a>';
                	 var html=view_html
                	 if(rowdata.withdrawFlag == 1){
                		 html+=withdraw_html
                	 }
                	 return html;
                   }}
              ],
            width:"100%",
            height:"100%",
            checkbox: false,
            rownumberyon:true,
            paramobj:{documentStatus:shSearchFormdata.documentStatus,
            	planTimeStart:shSearchFormdata.planTimeStart,
            	planTimeEnd:shSearchFormdata.planTimeEnd,
            	xjzt:shSearchFormdata.xjzt,
            	xjlb:shSearchFormdata.xjlb,
            	sqrqFrom:shSearchFormdata.sqrqFrom,
            	sqrqTo:shSearchFormdata.sqrqTo,
            	username:shSearchFormdata.username,
            	userid:shSearchFormdata.userid},
            overflowx:true,
            pagesize: 15,
            newpage:o.page,
            url: applicationListUrl,
			loadafter:function(data){
				window.top.param = {
					page:data.currPage
				}
				getSearchData()
				$.each(data.clist,function(i,v){
					console.log(data.clist)
					$('#num_'+i).text(v)
				})
			}
       });
	}
	
	function getValue(rowdata){
		if(rowdata.sqzt == 2){
			if(rowdata.xjzt == 2){
				return '<span style="color:#187fff;cursor:pointer;" onclick="viewfn(\''+rowdata.id+'\',\''+rowdata.xjzt+'\')" style="display:block;cursor:pointer">'+rowdata.sqr+'</span>';
			}else if(rowdata.xjzt == 3){
				return '<span style="color:#187fff;cursor:pointer;" onclick="viewfn(\''+rowdata.id+'\',\''+rowdata.xjzt+'\')" style="display:block;cursor:pointer">'+rowdata.sqr+'</span>';
			}else if(rowdata.xjzt == 1){
				return '<span style="color:#187fff;cursor:pointer;" onclick="editspfn(\''+rowdata.id+'\',\''+rowdata.sqzt+'\')" style="display:block;cursor:pointer">'+rowdata.sqr+'</span>';
			}else{
				return '<span style="color:#187fff;cursor:pointer;" onclick="viewfn(\''+rowdata.id+'\',\''+rowdata.xjzt+'\')" style="display:block;cursor:pointer">'+rowdata.sqr+'</span>';
			}
		}else{
			if(rowdata.sqzt == 3){
				return '<span style="color:#187fff;cursor:pointer;" onclick="viewfn(\''+rowdata.id+'\',\''+rowdata.xjzt+'\')" style="display:block;cursor:pointer">'+rowdata.sqr+'</span>';
			}else{
				return '<span style="color:#187fff;cursor:pointer;" onclick="editspfn(\''+rowdata.id+'\',\''+rowdata.sqzt+'\')" style="display:block;cursor:pointer">'+rowdata.sqr+'</span>';
			}
		}
	}

	var initxjlb = function(){
		$ajax({
			url:dicvocationUrl,
			success:function(data){
				data.xjlb.unshift({
					value:'',
					text:'请选择'
				})
				initselectNew("xjlb",data.xjlb);
			}
		})
	}
	var initxjzt = function(){
		$ajax({
			url:xjzt,
			success:function(data){
				data.xjlb.unshift({
					value:'',
					text:'请选择'
				})
				initselectNew("xjzt",data.xjlb);
			}
		})
	}

	var initother = function(){
		$("#search").click(function(){
			var searchsqr = $("#sqr").val();
			var searchsqrqFrom = $("#sqrqFrom").val();
			var searchsqrqTo = $("#sqrqTo").val();
			var searchxjlb = $("#xjlb").val();
			if((searchsqr==undefined || searchsqr==""||searchsqr==null)&&(searchsqrqFrom==undefined || searchsqrqFrom==""||searchsqrqFrom==null)&&(searchsqrqTo==undefined || searchsqrqTo==""||searchsqrqTo==null)&&(searchxjlb==undefined || searchxjlb==""||searchxjlb==null)){
				newbootbox.alertInfo("请输入查询项！");
			}else{
				var keyids=["sqr","sqrqFrom","sqrqTo","xjlb"];
				grid.setparams(getformdata(keyids));
				grid.loadtable();
			}	
		});



		$("input[name='documentStatus']").click(function(){
			var _this = this;
			$("input[name='documentStatus']").each(function(i,v){
				if(this!=_this)
					$.uniform.update($(v).attr("checked",false));
			});
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

		
		$(".date-picker").datepicker({
			language:"zh-CN",
		    rtl: Metronic.isRTL(),
		    orientation: "right",
		    format : "yyyy-mm-dd",
		    autoclose: true
		});
		
		$(".input-group-btn").click(function(){
			$(this).prev().focus();
		});

		$("#showSearch").click(function(){
			$("#searchwrap").toggle();
		});
		//筛选功能
		$("#queding").click(function(){
			refreshgrid();
			$ajax({
				url:url_count_list,
				data:{xjzt:$("#xjzt").val(),sqrqFrom:$("#sqrqFrom").val(),sqrqTo:$("#sqrqTo").val(),xjlb:$("#xjlb").val(),planTimeStart:$("#planTimeStart").val(),planTimeEnd:$("#planTimeEnd").val(),username:$("#username").val()},
				success:function(data){
						$("#total").html(data[0]);
						$("#yth").html(data[3]);
						$("#spz").html(data[2]);
						$("#ytg").html(data[4]);
					}
			});
		});

		//重置
		$("#hiddenSearch").click(function(){
			removeInputData(["planTimeStart","planTimeEnd","deptid","deptname","userid","username"]);
			$("#searchwrap").hide();
			refreshgrid();
		})
        //重置
        $("#reset").click(function(){
            removeInputData(["planTimeStart","planTimeEnd","xjzt","xjlb","sqrqFrom","sqrqTo","username","userid"]);
            //$("#searchwrap").hide();
            refreshgrid();
        })

	}
	var inittree = function(){
		$("#username").createUserTree({
			url : userTree,
			width : "100%",
			success : function(data, treeobj) {},
			selectnode : function(e, data) {
				$("#userid").val($("#" + data.selected).attr("id"));
				$("#username").val(data.node.text);
			}
		});
	}
	var setSearchData = function(){
	    setformdata(shSearchFormdata);
	    if(!shSearchFormdata.documentStatus){
	    	$.uniform.update($('#documentStatusAll').attr("checked",true));
	    }else{
	    	$.uniform.update($('#documentStatusAll').attr("checked",false));
	    }
	    $("input[name='documentStatus']").each(function(i,v){
		    if($(v).val()==shSearchFormdata.documentStatus){
		        $.uniform.update($(v).attr("checked",true));
		    }else{
		    	$.uniform.update($(v).attr("checked",false));
		    }
	    });
	}
	return{
		//加载页面处理程序
		initControl:function(){			
			initxjlb();
			initxjzt();
			initother();
			inittree();
			setSearchData();
			initgrid();
		},
		initgrid:function(){
			grid.refresh();
		}
	}
	
}();
//撤回
var withdrawfn = function(id){
	newbootbox.confirm({
		title:"提示",
		message: "是否要进行撤回操作？",
		sure:"确认",
		cancel:"取消",
		callback1:function(){
			$ajax({
				url:withdrawUrl,
				data:{id:id},
				success:function(data){
					if(data.result=="success"){
						newbootbox.alertInfo('撤回成功！').done(function(){
						    changToNum2(function(){
                                grid.refresh();
                                window.top.bubbleCountStatistics();
						    })
						});
						//changToNum();
					}else{
						newbootbox.alertInfo("撤回失败！");
					}
				}
			})
		}
	});
}
//意见查看
var viewfn = function(id){
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
}

var closefn = function(){
	$("#viewcont").modal("hide");
}

var getSearchData = function(){
	var keyids=["documentStatus","planTimeStart","planTimeEnd","sqrqFrom","sqrqTo","xjzt","xjlb","userid","username"];
    window.top.shSearchFormdata = shSearchFormdata = getformdata(keyids);//重置第一页刷新
}
var getNowFormatDate = function(){
	var date = new Date();
	var seperator = "-";
	var month = date.getMonth()+1;
	var strDate = date.getDate();
	if(month >=1 && month<=9){
		month ="0"+month;
	};
	if(strDate >=1 && strDate<=9){
		strDate ="0"+strDate;
	}
	var currentDate = date.getFullYear()+seperator+month+seperator+strDate;
	return currentDate;
}

//点击查看详情,因请销假审批添加上一篇下一篇功能添加参数  receiverIsMe、flowType
var previewfn=function(id,receiverIsMe,flowType){
	var url=rootPath + '/qxj/html/qxjView.html?id='+id+'&fileFrom=qxjsp&receiverIsMe='+receiverIsMe+"&flowType="+flowType;
	window.top.iframe1.location.href = url;
};
var resetTable = function(){
	$("#allform")[0].reset();
	pageModule.initControl()
}
// 刷新
function refreshgrid(){
	var documentStatus;
	$("input[name='documentStatus']").each(function(){
		if($(this).is(":checked")){
			documentStatus = $(this).val();
		}
	});
	var keyids=["documentStatus","planTimeStart","planTimeEnd","xjzt","xjlb","sqrqFrom","sqrqTo","username","userid"];
	grid.setparams(getformdata(keyids));//重置第一页刷新
	grid.loadtable();
}
$('#menulist li a',window.top.document).click(function(){
	window.top.shSearchFormdata = {}
})