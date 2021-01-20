var userTree = {"url":"/app/base/user/allTree","dataType":"text"};  //人员树
var dicvocationUrl = {"url":rootPath +"/dicvocationsort/dict","dataType":"text"}; //休假类别
var applicationListUrl = {"url":"/app/qxjgl/application/applicationList?filefrom=qxjsq","dataType":"text"};//请销假申请列表
var queryPersonConfigUrl = {"url":"/app/qxjgl/personconfig/getPersonConfig","dataType":"text"};//个人应休假天数获取
var url_withdraw={"url":"/leave/apply/withdrawToLastApply","dataType":"text"};//撤回
var url3 = {"url":"/app/qxjgl/application/deleteInfo","dataType":"text"};//删除
var xjzt ={"url":"/app/qxjgl/qxj/data/xjzt.json","dataType":"text"}
var countXiuJiaDaysUrl = {"url":"/leave/apply/countXiuJiaDays","dataType":"text"};
var dicvocationsortTypeUrl = {"url": rootPath + "/dicvocationsort/type","dataType":"text"}
var grid = null;
var o  = window.top.params||{
	page:1
}
var searchFormdata = window.top.searchFormdata||{}
var pageModule = function(){
	var initgrid = function(){
        grid = $("#gridcont").createGrid({
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
							}else if(rowdata.status == 31){
								statusName="待销假";
								color="status-red";
							}else if(rowdata.status == 32){
								statusName="已销假";
								color="status-gray";
							}else if(rowdata.status == 40){
								statusName="已作废";
								color="status-gray";
							}else if(rowdata.status == 50){
								statusName="审批未通过";
								color="status-gray";
							}
							return '<span class="status-btn pointer '+color+'" title="'+statusName+'" onclick="previewfn(\''+rowdata.id+'\')">'+statusName+'</span>';
						}},
		                 {display:"请假人",name:"status",width:"20%",align:"center",paixu:false,render:function(rowdata){
                            return '<span class="pointer" onclick="previewfn(\''+rowdata.id+'\')">'+rowdata.proposer+'</span>';
			             }},
                         {display:"申请日期",name:"sqrq",width:"15%",align:"center",paixu:false,render:function(rowdata,n){
                        	 var applicationDate="";
                        	 if(rowdata.applicationDate){
                        		 applicationDate=rowdata.applicationDate.substring(0,10);
                        	 }
                            return '<span class="pointer" onclick="previewfn(\''+rowdata.id+'\')">'+applicationDate+'</span>';
                         }},

                         {display:"请假类别",name:"lb",width:"15%",align:"center",paixu:false,render:function(rowdata){
                            return '<span class="pointer" onclick="previewfn(\''+rowdata.id+'\')">'+rowdata.vacationSortName+'</span>';
                         }},
                         {display:"起止日期",name:"qjsj",width:"20%",align:"center",paixu:false,render:function(rowdata){
                            return '<span class="pointer" onclick="previewfn(\''+rowdata.id+'\')">'+rowdata.planTimeStartEnd+'</span>';
                         }},

                        {display:"请假天数",name:"sjqjsj",width:"10%",align:"center",paixu:false,render:function(rowdata){
                            return '<span class="pointer" onclick="previewfn(\''+rowdata.id+'\')">'+rowdata.actualVocationDate+'</span>';
                         }},
                         /*{display:"销假状态",name:"xjzt",width:"10%",align:"center",paixu:false,render:function(rowdata){
                            	if(rowdata.backStatusId == 0){
                            		return '<span class="pointer" style="color:orange" onclick="previewfn(\''+rowdata.id+'\')">未销假</span>';
                            	}else if(rowdata.backStatusId == 1){
                            		return '<span class="blue pointer" onclick="previewfn(\''+rowdata.id+'\')">已销假</span>';
                                }else if(rowdata.backStatusId == 2){
                                	return '<span class="pointer" onclick="previewfn(\''+rowdata.id+'\')">已通过</span>';
                                }else if(rowdata.backStatusId == 3){
                                	return '<span class="red pointer" onclick="previewfn(\''+rowdata.id+'\')">未通过</span>';
                                }
                         }},*/
                         {display:"操作",name:"",width:"10%",align:"center",paixu:false,render:function(rowdata,n){
                        	 // var edit_html='<a title=" 编辑" class="color-blueNewFa" onclick="editfn(\''+rowdata.id+'\',\''+rowdata.backStatusId+'\',\''+rowdata.status+'\')"><i class="fa fa-edit"></i></a>';
                        	 var view_html='<a title="查看" class="color-blueNewFa" onclick="viewfn(\''+rowdata.id+'\')"><i class="fa fa-comment"></i></a>';
                        	// var export_html='<a title="下载" class="color-blueNewFa" onclick="exportfn(\''+rowdata.id+'\',\''+rowdata.backStatusId+'\')"><i class="fa fa-download"></i></a>';
                        	 //var preview_html='<a title="预览" class="color-blueNewFa" onclick="previewfn(\''+rowdata.id+'\',\''+rowdata.backStatusId+'\')"><i class="fa fa-search-plus"></i></a>';
                        	 var delete_html='<a title="删除" class="color-blueNewFa" onclick="removefn(\''+rowdata.id+'\')"><i class="fa fa-trash-o"></i></a>';
                        	 var withdraw_html='<a title="撤回" class="color-blueNewFa" onclick="withdrawfn(\''+rowdata.id+'\')"><i class="fa fa-mail-reply"></i></a>';
                        	 var html='';
                        	 if(rowdata.status == 0){
                        		 html+=delete_html
                        	 }else if(rowdata.status == 20){
                        		 html+=(view_html+delete_html)
                        	 }else {
                        		 html+=view_html
                        	 }
                        	 if(rowdata.withdrawFlag == 1){
                        		 html+=withdraw_html
                        	 }
                         	return html;
                         }}
                     ],
            width:"100%",
            height:"100%",
            checkbox: true,
            rownumberyon:true,
            paramobj:{
            	documentStatus:searchFormdata.documentStatus,
            	planTimeStart:searchFormdata.planTimeStart,
            	planTimeEnd:searchFormdata.planTimeEnd,
            	xjzt:searchFormdata.xjzt,
            	xjlb:searchFormdata.xjlb,
            	sqrqFrom:searchFormdata.sqrqFrom,
            	sqrqTo:searchFormdata.sqrqTo,
            	username:searchFormdata.username,
            	userid:searchFormdata.userid},
            overflowx:false,
            pagesize: 15,
            newpage:o.page,
            url: applicationListUrl,
			loadafter:function(data){
				window.top.params = {
						page:data.currPage,
				};
				getSearchData()
				$.each(data.clist,function(i,v){
					$('#num_'+i).text(v)
				})
			}

       });
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
	var initother = function(){
        $("#add").click(function(){
        	$ajax({
				url:dicvocationsortTypeUrl,
				data:{type:0},
				success:function(data){
					if(data.result=='success'){
						var str = ''
						data.list.forEach(function (e) {
							str+=`<a class="qjTypeChild" data-type="0" onclick="qjAdd('0','${e.flag}','${e.id}','${e.text}')">
<!--                                <img src="../image/feiji.svg">-->
                                <span>${e.text}</span>
                           </a>`
						})
						$('#ysqj').html(str)
					}
				}
			})
			$ajax({
				url:dicvocationsortTypeUrl,
				data:{type:1},
				success:function(data){
					if(data.result=='success'){
						var str = ''
						data.list.forEach(function (e) {
							str+=`<a class="qjTypeChild" data-type="1" onclick="qjAdd('1','${e.flag}','${e.id}','${e.text}')">
<!--                                <img src="../image/feiji.svg">-->
                                <span>${e.text}</span>
                           </a>`
						})
						$('#ygqj').html(str)
					}
				}
			})
            $("#qjTypeBox").modal("show");
        });
		$('.qjType').click(function () {
			// $('.qjType').removeClass('select')
			// $(this).addClass('select')
		})
		$("#xjsq").click(function(){
			var datas=grid.getcheckrow();
			var ids=[];
			var statuses=[];
			var backStatusIds=[];
			if(datas.length === 1){
				$(datas).each(function(i){
					ids[i]=this.id;
					statuses[i]=this.status
					backStatusIds[i]=this.backStatusId
				});
				console.log(statuses.toString())
				if (statuses.toString() < 30) {
					newbootbox.alertInfo("请假申请没有审批通过，不能申请销假！");
				}else if (statuses.toString() == 32 && backStatusIds.toString()  == '1') {
					newbootbox.alertInfo("已销假，不允许重复发起销假申请！");
				} else if (statuses.toString() == 31 || statuses.toString() == 30) {
					newbootbox.newdialog({
						id: "xjsqadd",
						width: 580,
						height: 400,
						header: true,
						title: "销假申请",
						url: rootPath + "/qxj/html/xj_add.html?id=" + ids.join(",")
					});
				}
			}else{
				newbootbox.alertInfo("请选择一条要销假的数据！");
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
		    //rtl: Metronic.isRTL(),
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
			var text=$("#xjzt").val()
            refreshgrid();
			$ajax({
				url:url_count_list,
				data:{xjzt:$("#xjzt").val(),sqrqFrom:$("#sqrqFrom").val(),sqrqTo:$("#sqrqTo").val(),xjlb:$("#xjlb").val(),planTimeStart:$("#planTimeStart").val(),planTimeEnd:$("#planTimeEnd").val(),username:$("#username").val()},
				success:function(data){
					$("#all_num").html(data[0]);
					$("#wait_submit_num").html(data[1]);
					$("#turn_back_num").html(data[3]);
					$("#checking_on_num").html(data[2]);
					$("#already_passed_num").html(data[4]);
				}
			});
        });

        //重置
        $("#hiddenSearch").click(function(){
            removeInputData(["planTimeStart","planTimeEnd","xjzt","xjlb","sqrqFrom","sqrqTo","username","userid"]);
            $("#searchwrap").hide();
            refreshgrid();
			countList();
        })
        //重置
        $("#reset").click(function(){
            removeInputData(["planTimeStart","planTimeEnd","xjzt","xjlb","sqrqFrom","sqrqTo","username","userid"]);
            //$("#searchwrap").hide();
            refreshgrid();
        })

    }
	var countXiuJiaDays = function(){
		$ajax({
			url:countXiuJiaDaysUrl,
			success:function(data){
				$(".qjcountNew").show();
				$("#totalDays").text(data.totalDays);
				$("#xiuJiaDays").text(data.xiuJiaDays);
				$("#ygDays").text(data.ygDays);
				$("#ysDays").text(data.ysDays);
				$("#xiujiaWcl").text(data.wcl ? data.wcl +'%' : '0%');
			}
		});
	}
	var setSearchData = function(){
	    setformdata(searchFormdata);
	    $('#xjzt').val(searchFormdata.xjzt)
	    $('#xjlb').val(searchFormdata.xjlb)
	    if(!searchFormdata.documentStatus){
	    	$.uniform.update($('#documentStatusAll').attr("checked",true));
	    }else{
	    	$.uniform.update($('#documentStatusAll').attr("checked",false));
	    }
	    $("input[name='documentStatus']").each(function(i,v){
		    if($(v).val()==searchFormdata.documentStatus){
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
            inittree();
            setSearchData();
            initgrid();
			initother();
			countXiuJiaDays();
		},
		initgrid:function(){
			grid.refresh();
		},
		countXiuJiaDaysUpdate:function(){
			countXiuJiaDays();
		}
	}

}();

var xjfn = function(id,times){
	newbootbox.newdialog({
		id:"xjAdd",
		width:800,
		height:500,
		header:true,
		title:"销假申请",
		url:rootPath + "/qxj/html/table_xj.html?id="+id+"&times="+encodeURI(times)
	});
}

var getSearchData = function(){
	var keyids=["documentStatus","planTimeStart","planTimeEnd","sqrqFrom","sqrqTo","xjzt","xjlb","userid","username"];
    window.top.searchFormdata = searchFormdata =  getformdata(keyids);//重置第一页刷新
}

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

var removefn = function(id){
	 newbootbox.confirm({
     	title:"提示",
     	message: "删除请假单后本次请假取消，是否确认操作？",
     	callback1:function(){
     		$ajax({
				url:url3,
				data:{leaveId:id},
				success:function(data){
					if(data.msg=="success"){
						newbootbox.alertInfo('删除成功！').done(function(){
							grid.refresh();
	    				});
					}
				}
			})
     	}
     });
}
var withdrawfn = function(id){
	newbootbox.confirm({
		title:"提示",
		message: "是否要进行撤回操作？",
		sure:"确认",
		cancel:"取消",
		callback1:function(){
			$ajax({
				url:url_withdraw,
				data:{id:id},
				success:function(data){
					if(data.result=="success"){
						newbootbox.alertInfo('撤回成功！').done(function(){
							grid.refresh();
							window.top.bubbleCountStatistics();
						});
					}else{
						newbootbox.alertInfo("撤回失败！");
					}
				}
			})
		}
	});
}
//查看详情
var previewfn=function(id){
	var url=rootPath + '/qxj/html/qxjView.html?id='+id+"&filefrom=qxjsq&qxjFlag=1"
	window.top.iframe1.location.href = url;
};
// 刷新
function refreshgrid(){
    var documentStatus;
    $("input[name='documentStatus']").each(function(){
        if($(this).is(":checked")){
            documentStatus = $(this).val();
        }
    });
	var keyids=["documentStatus","planTimeStart","planTimeEnd","sqrqFrom","sqrqTo","xjzt","xjlb","userid","username"];
    grid.setparams(getformdata(keyids));//重置第一页刷新
    grid.loadtable();
}

//var openLoading = function() {
//	$("#qjDialog").removeClass("none");
//}
//
//var closeLoading = function() {
//	$("#qjDialog").addClass("none");
//}
$('#menulist li a',window.top.document).click(function(){
	window.top.searchFormdata = {}
})

function qjAdd(qjType,qjFlag,id,qjText) {
	$("#qjTypeBox").modal("hide");
	$ajax({
		url:queryPersonConfigUrl,
		success:function(data){
			if(data.perConfig){
				newbootbox.newdialog({
					id:"qjAdd",
					width:950,
					height:600,
					header:true,
					title:"请假申请",
					url:rootPath + "/qxj/html/qj_add.html?loginUserId="+data.perConfig.userid+"&qjType="+qjType+"&qjFlag="+qjFlag+"&qjTypeId="+id+"&qjText="+escape(qjText)
				})
			}else{
				newbootbox.alertInfo("请在个人配置先维护个人应休假天数！",true);
			}
		}
	})
}
