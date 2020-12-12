var roleType = window.top.roleType;
var url1 = {"url":rootPath +"/dicvocationsort/list?roleType="+roleType,"dataType":"text"};
var zdwh_del = {"url":rootPath +"/dicvocationsort/delete","dataType":"text"};//删除url
var previewUrl = {"url":rootPath +"/application/getQjspd","dataType":"text"};//审批预览接口ype : 0：审批单预览；1：私家车长途外出审批单预览；2：长途车审批表预览


var id = window.location.search.split("id=")[1];
var grid = null;
var pageModule = function(){
	var initgrid = function(){

		grid = $("#gridcont").createGrid({
            columns:[	
                         {display:"休假类别",name:"vacationSortId",width:"50%",align:"center",paixu:false,render:function(rowdata){
                            return rowdata.vacationSortId;
                         }},
                         {display:"是否抵扣应休假天数？",name:"flag",width:"50%",align:"center",paixu:false,render:function(rowdata,n){
                        	var checkedMark = (rowdata.deductionVacationDay==1)?"checked":""
                         	return '<div class="switch"><input class="leaveSwitch" data-clickid="'+rowdata.id+'" name="status" type="checkbox" '+checkedMark+'></div>'; 
                         }},
                     ],
            width:"100%",
            height:"100%",
            loadafter:function(){
		       	$("td").css({"white-space":"normal","vertical-align":"middle"});
		       	$('.leaveSwitch').bootstrapSwitch({
		       		onText:"开",
		       		offText:"关",
		       		onColor:"success",
		       		offColor:"danger",
		       		size:"small",
		       		animate:"false",
		       		onSwitchChange:function(event,state){
		       			console.log($(event.target),$(event.target).data("clickid"),state)
		       			$ajax({
		        			url:{"url":"/app/qxjgl/dicvocationsort/update","dataType":"text"},
		        			data:{id:$(event.target).data("clickid"),deductionVacationDay:state?"1":"0"},
		        			success:function(data){
//		        				if(data.result=="success"){
//		        					newbootbox.alertInfo('成功！').done(function(){
//		    							grid.refresh();
//		    	    				});
//		        				}else if(data.result=="fail"){
//		        					newbootbox.alertInfo("失败！");
//		        				}
		        			}
		        		});
		       		}
		       	})
            },
	        checkbox: true,
	        paramobj:{id:id,type:0},
	        url: url1,
	        rownumberyon:true,
            pagesize: 12
       });
		
	}

    var initgrid2 = function(){

        grid = $("#gridcont").createGrid({
            columns:[
                {display:"休假类别",name:"vacationSortId",width:"50%",align:"center",paixu:false,render:function(rowdata){
                        return rowdata.vacationSortId;
                    }},
                {display:"是否抵扣应休假天数？",name:"flag",width:"50%",align:"center",paixu:false,render:function(rowdata,n){
                        var checkedMark = (rowdata.deductionVacationDay==1)?"checked":""
                        return '<div class="switch"><input class="leaveSwitch" data-clickid="'+rowdata.id+'" name="status" type="checkbox" '+checkedMark+'></div>';
                    }},
            ],
            width:"100%",
            height:"100%",
            loadafter:function(){
                $("td").css({"white-space":"normal","vertical-align":"middle"});
                $('.leaveSwitch').bootstrapSwitch({
                    onText:"开",
                    offText:"关",
                    onColor:"success",
                    offColor:"danger",
                    size:"small",
                    animate:"false",
                    onSwitchChange:function(event,state){
                        console.log($(event.target),$(event.target).data("clickid"),state)
                        $ajax({
                            url:{"url":"/app/qxjgl/dicvocationsort/update","dataType":"text"},
                            data:{id:$(event.target).data("clickid"),deductionVacationDay:state?"1":"0"},
                            success:function(data){

                            }
                        });
                    }
                })
            },
            checkbox: true,
            paramobj:{id:id,type:'1'},
            url: url1,
            rownumberyon:true,
            pagesize: 12
        });

    }

    var initgrid3 = function(){

        grid = $("#gridcont").createGrid({
            columns:[
                {display:"交通工具类型",name:"vacationSortId",width:"30%",align:"center",paixu:false,render:function(rowdata){
                        return rowdata.vacationSortId;
                    }},
                {display:"是否需要审批？",name:"flag",width:"20%",align:"center",paixu:false,render:function(rowdata,n){
                        var checkedMark = (rowdata.deductionVacationDay==2)?"checked":""
                        return '<div class="switch"><input class="leaveSwitch" data-clickid="'+rowdata.id+'" name="status" type="checkbox" '+checkedMark+'></div>';
                    }},
                {display:"审批单选择",name:"flag2",width:"50%",align:"center",paixu:false,render:function(rowdata){
                		if($.trim(rowdata.deductionVacationDay) == '2'){
                			if(rowdata.flag == '0'){
                                return '<div data-id="'+rowdata.id+'"><span style="margin: 0 30px 0 46px"><input class="approvalType" type="radio" name="'+rowdata.id+'" value="0" checked/><span>私家车长途外出审批表</span></span><span><input class="approvalType" type="radio" name="'+rowdata.id+'" value="1"/><span>长途车审批表</span></span></div>';
							}else{
                                return '<div data-id="'+rowdata.id+'"><span style="margin: 0 30px 0 46px"><input class="approvalType" type="radio" name="'+rowdata.id+'" value="0"><span>私家车长途外出审批表</span></span><span><input class="approvalType" type="radio" name="'+rowdata.id+'" value="1" checked/><span>长途车审批表</span></span></div>';
							}
						}else{
                            return '<div data-id="'+rowdata.id+'"><span style="margin: 0 30px 0 46px"><input class="approvalType" type="radio" name="'+rowdata.id+'" value="0" disabled/><span style="color: #ddd">私家车长途外出审批表</span></span><span><input class="approvalType" type="radio" name="'+rowdata.id+'" value="1" disabled/><span style="color: #ddd">长途车审批表</span></span></div>';
						}

                    }},
            ],
            width:"100%",
            height:"100%",
            loadafter:function(){
                $("td").css({"white-space":"normal","vertical-align":"middle"});
                $('.leaveSwitch').bootstrapSwitch({
                    onText:"开",
                    offText:"关",
                    onColor:"success",
                    offColor:"danger",
                    size:"small",
                    animate:"false",
                    onSwitchChange:function(event,state){
                    	if(state){
                            $ajax({
                                url:{"url":"/app/qxjgl/dicvocationsort/update","dataType":"text"},
                                data:{id:$(event.target).data("clickid"),deductionVacationDay:state?"2":"3",flag:0},
                                success:function(data){

                                }
                            });
						}else{
                            $ajax({
                                url:{"url":"/app/qxjgl/dicvocationsort/update","dataType":"text"},
                                data:{id:$(event.target).data("clickid"),deductionVacationDay:state?"2":"3",flag:''},
                                success:function(data){

                                }
                            });
						}
                    }
                })
				$(document)
					//勾选交通工具审批单
					.off('change','.approvalType').on('change','.approvalType',function(){
						var _flag = $(this).val();
						var _id = $(this).parents('div').attr('data-id');
						console.log(_id)
                        $ajax({
                            url:{"url":"/app/qxjgl/dicvocationsort/update","dataType":"text"},
                            data:{id:_id,deductionVacationDay:'2',flag:_flag},
                            success:function(data){
                                grid.refresh();
                            }
                        });
					})
            },
            checkbox: true,
            paramobj:{id:id,type:'2'},
            url: url1,
            rownumberyon:true,
            pagesize: 12
        });

    }


	function getView(rowdata){
		 return '<span style="cursor:pointer;">' + rowdata.name + '</span>'; 
	}
	function getValue(rowdata){
		var content = "";
		$.each(rowdata.children, function(i, item) {
			if (item.flag == 0) {
				content += '<label style="cursor:pointer;margin-right:15px" for="'+ item.id +'"><input type="checkbox" disabled="disabled" id="'+item.id+'" value='+rowdata.type+'&'+item.id+'>&nbsp;'+item.value+ '</label>';
			} else {
				content += '<label style="cursor:pointer;margin-right:15px" for="'+ item.id +'"><input type="checkbox" id="'+item.id+'" value='+rowdata.type+'&'+item.id+'>&nbsp;'+item.value+ '</label>';
			}
		});
		return content;
	}
	
	
	var initother = function(){

		//审批单预览，私家车长途外出审批表预览，长途车审批表预览
		$('.previewBtn').click(function(){
			var $type = $(this).data('type');
            $ajax({
                url:previewUrl,
                data:{type:$type},
                success:function(data){

                }
            });
		})

		//切换请假类型/因公出差/交通工具
		$('.titleType').click(function(){
			var _type = $(this).attr('data-type');
			$(this).addClass('isSelecte')
			$(this).siblings('span').removeClass('isSelecte')
			if(_type == '0'){
				initgrid()
				$('.previewBtn').hide()
				$('.previewBtn1').show()
			}else if(_type == '1'){
                initgrid2()
                $('.previewBtn').hide()
                $('.previewBtn1').show()
			}else{
                initgrid3()
                $('.previewBtn').hide()
                $('.previewBtn3,.previewBtn2').show()
			}
		})
		
		$("#add").click(function(){
			var addType = $('.isSelecte').attr('data-type');
			newbootbox.newdialog({
				id:"zdwh_add",
				width:600,
				height:500,
				header:true,
				title:"新增字典值",
				url:"/app/qxjgl/ywpz/zdwh/html/zdwh_add.html?addType="+addType
			});
		});
		
		$("#plsc").click(function(){
//			var r = $("#gridcont_content input[type=checkbox]:checked");
			var r=grid.getcheckrow();
			if(r.length>=1){
				selectcheckboxid = [];
				$.each(r,function(i,obj){
					selectcheckboxid.push(obj.id);
				});
				removefn(selectcheckboxid.toString());
			} else if (r.length==0){
				newbootbox.alertInfo("请选择要删除的数据！");
			}
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

var editfn = function(){
//	var r = $("#gridcont_content input[type=checkbox]:checked");
	var r=grid.getcheckrow();
	if(r.length==1){
//		var selectcheckbox = r.val();
		var selectcheckboxtype = r[0].vacationSortId;//selectcheckbox.split("&")[0];
		var selectcheckboxid = r[0].id;//selectcheckbox.split("&")[1];
		newbootbox.newdialog({
			id:"zdwh_edit",
			width:600,
			height:200,
			header:true,
			title:"编辑字典值",
			url:"/app/qxjgl/ywpz/zdwh/html/zdwh_edit.html?id="+selectcheckboxid+"&&"+selectcheckboxtype
		});
	}else{
		newbootbox.alertInfo("请勾选一项字典值进行编辑！");
	}
	
}


var removefn = function(zdIds){
	 newbootbox.confirm({
     	title:"提示",
     	message: "是否要进行删除操作？",
     	callback1:function(){
     		$ajax({
    			url:zdwh_del,
    			data:{id:zdIds},
    			success:function(data){
    				if(data.result=="success"){
    					newbootbox.alertInfo('删除成功！').done(function(){
							grid.refresh();
	    				});
    				}else if(data.result=="fail"){
    					newbootbox.alertInfo("字典已引用，无法删除！");
    				}
    			}
    		});
     	}
     });
}
