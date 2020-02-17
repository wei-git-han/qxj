var roleType = window.top.roleType;
var url1 = {"url":rootPath +"/dicvocationsort/list?roleType="+roleType,"dataType":"text"};
var zdwh_del = {"url":rootPath +"/dicvocationsort/delete","dataType":"text"};//删除url
var id = window.location.search.split("id=")[1];
var grid = null;
var pageModule = function(){
	var initgrid = function(){
//        grid = $("#gridcont").createGrid({
//        	columns : [
//						{
//							display : "字典项",
//							name : "name",
//							width : "10%",
//							align : "center",
//							render : getView
//						},
//						{
//							display : "字典值",
//							name : "children",
//							width : "90%",
//							align : "left",
//							render :getValue
//						} ],
//                    width:"100%",
//                    height:"100%",
//                   loadafter:function(){
//                   	$("td").css({"white-space":"normal","vertical-align":"middle"});
//                   },
//                    checkbox: false,
//                    rownumberyon:false,
//                    paramobj:{id:id},
//                    overflowx:false,
//                    pagesize: 1,
//                    pageyno:false,
//                    url: url1
//               });
		grid = $("#gridcont").createGrid({
            columns:[	
                         {display:"休假类别",name:"value",width:"50%",align:"center",paixu:false,render:function(rowdata){
                            return rowdata.value;                                         
                         }},
                         {display:"是否抵扣应休假天数？",name:"flag",width:"50%",align:"center",paixu:false,render:function(rowdata,n){
                        	var checkedMark = (rowdata.deductionVacationDay==1)?"":"checked"
                         	return '<div class="switch"><input class="leaveSwitch" data-clickid="'+rowdata.id+'" name="status" type="checkbox" '+checkedMark+'></div>'; 
                         }},
                     ],
            width:"100%",
            height:"100%",
            loadafter:function(){
		       	$("td").css({"white-space":"normal","vertical-align":"middle"});
		       	$('.leaveSwitch').bootstrapSwitch({
		       		onText:"ON",
		       		offText:"OFF",
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
	        paramobj:{id:id},
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
		
		$("#add").click(function(){
			newbootbox.newdialog({
				id:"zdwh_add",
				width:600,
				height:450,
				header:true,
				title:"新增字典值",
				url:"/app/qxjgl/ywpz/zdwh/html/zdwh_add.html"
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
		var selectcheckboxtype = r[0].value;//selectcheckbox.split("&")[0];
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
