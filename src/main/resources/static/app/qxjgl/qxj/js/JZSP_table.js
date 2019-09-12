var url1 = {"url":rootPath +"/chairmanview/list","dataType":"text"};  //列表
var url2 = {"url":rootPath +"/dicvocationsort/dict","dataType":"text"};     //休假类别
var url3 = {"url":rootPath +"/chairmanview/agreeleave","dataType":"text"};//通过按钮
var url4 = {"url":rootPath +"/chairmanview/disagreeleave","dataType":"text"};//不通过按钮

var id = window.location.search.split("id=")[1];
var grid = null;

var pageModule = function(){
	var initgrid = function(){
        grid = $("#gridconts").createGrid({
	             columns:[	
	    			 {display:"申请人",name:"sqr",width:"12%",align:"center",paixu:true,render:getValue},
	                 {display:"申请日期",name:"sqrq",width:"10%",align:"center",paixu:true,render:function(rowdata){
	                    return rowdata.sqrq;                                         
	                 }},
	                 {display:"类别",name:"lb",width:"10%",align:"center",paixu:false,render:function(rowdata){
	                    return rowdata.lb;                                         
	                 }},
	                 {display:"开始时间",name:"kssj",width:"20%",align:"center",paixu:false,render:function(rowdata){
	                    return rowdata.kssj;                                        
	                 }},
	                 {display:"结束时间",name:"jssj",width:"20%",align:"center",paixu:false,render:function(rowdata){
	                    return rowdata.jssj;                                        
	                 }},
	                 {display:"请假天数",name:"qjts",width:"10%",align:"center",paixu:false,render:function(rowdata){
	                    return rowdata.qjts;                                      
	                 }},
	                 {display:"状态",name:"zt",width:"18%",align:"center",paixu:false,render:function(rowdata){
	                    //return rowdata.sqzt;
	                    if(rowdata.zt != 2){
	                    	return "<button type='button' class='btn btn-xs btn-xj' onclick='tgfn(\""+rowdata.id+"\")'>通过</button>"+
	                    		   "<button type='button' class='btn btn-xs btn-xj' onclick='notgfn(\""+rowdata.id+"\")'>不通过</button>";
	                    }else if(rowdata.zt == 2){
	                    	return "<span>已通过</span>";
	                    }
	                 }}
	             ],
	        width:"100%",
	        height:"100%",
	        checkbox: false,
	        rownumberyon:true,
	        paramobj:{id:id},
	        overflowx:true,
	        pagesize: 12,
	        url: url1
	   });
	}
	
	function getValue(rowdata){
		 if(rowdata.zt != 2){
			 return '<span style="color:#187fff;cursor:pointer;" onclick="editspfn(\''+rowdata.id+'\')" style="display:block;cursor:pointer">'+rowdata.sqr+'</span>';
         }else if(rowdata.zt == 2){
        	 return '<span>'+rowdata.sqr+'</span>';
         }
	}
	
	var initxjlb = function(){
		$ajax({
			url:url2,
			success:function(data){
				initselect("xjlb",data.xjlb);
			}
		})
	}
	
	var initother = function(){
		$("#search").click(function(){
			var keyids=["sqr","sqrqFrom","sqrqTo","xjlb"];
			grid.setparams(getformdata(keyids));
			grid.loadtable();
		});
		
		$(".date-picker").datepicker({
			language:"zh-CN",
		    rtl: Metronic.isRTL(),
		    orientation: "right",
		    format : "yyyy-mm-dd",
		    autoclose: true
		})

		
	}
	
	return{
		//加载页面处理程序
		initControl:function(){
			initgrid();
			initxjlb();
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

var editspfn = function(id){
	newbootbox.newdialog({
		id:"qjJzsp",
		width:800,
		height:700,
		header:true,
		title:"请假审批",
		url:rootPath + "/qxj/html/JZSP_qj.html?id="+id
	});
}

var tgfn = function(id){
	$ajax({
		url:url3,
		data:{id:id,"rq":getNowFormatDate(),"yj":"同意"},
		success:function(data){
			if(data.result=="success"){
				newbootbox.alertInfo("审批通过成功！").done(function(){
					pageModule.initgrid();
				});
			}
		}
	})
}
var notgfn = function(id){
	$ajax({
		url:url4,
		data:{id:id,"rq":getNowFormatDate(),"yj":"不同意"},
		success:function(data){
			if(data.result=="success"){
				newbootbox.alertInfo("审批不通过成功！").done(function(){
					pageModule.initgrid();
				});
			}
		}
	})
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
