var userTree = {"url":"/leave/apply/sendUserTree","dataType":"text"}; //人员选择树
var sendOrFinshApprove = {"url":"/leave/apply/sendOrFinshApprove","dataType":"text"}; //审批或者审批完成
var url_isShow_Btn= "/leave/apply/detailButtonIsShow";//显示按钮判断
var id = getUrlParam('id');//主文件id
var opinionContent = getUrlParam2('opinionContent');//意见内容
var opinionType = getUrlParam('opinionType');//意见类型
var fileFrom = getUrlParam("fileFrom");
var fromMsg= getUrlParam("fromMsg");
var deleteMark= getUrlParam("deleteMark");
var leaverIds = deleteMark.split(",");
var leaverId = leaverIds[0];
var lxrId="";
var lxrName="";
var startDate = getUrlParam("startDate");
var authrityShow = getUrlParam("authrityShow");

var pageModule = function() {
	var initBtn = function(){
		  $.ajax({
	          url : url_isShow_Btn,
	          data : {id:id},
	          type : "GET",
	          success : function(data) {
	        	  if(data.authrityShow==1){
	      			$('#sendAndAuthorize').show();
	      		  }
	          }
	     })
	}
    var inittree = function(){
        $("#tree_2").jstree("destroy");
        $ajax({
            url:userTree,
            data:{id:id,leaverId:leaverId},
            success:function(data){
                $("#tree_2").jstree({
                    "plugins": ["wholerow", "types"],
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
                $("#tree_2").on("select_node.jstree", function(e,data) {
                    if(data.node.original.type == 1){
                        lxrId = data.node.id;
                        lxrName = data.node.text;
                    }
                });
            }
        })
    }

    var initother = function() {

        $("#close").click(function(){
            newbootbox.newdialogClose("sshDialog");
        })

        //选择联系人，进行送审流程
        $("#fasong").click(function(){
        	$('#fasong').attr('disabled','true');
            var nodes2 = $("#tree_2").jstree("get_bottom_selected",true);
            var cylxrIds = [];
            var cylxrNames = [];
            $.each(nodes2, function(i,obj) {
                if(obj.original.type == 1){
                    cylxrIds.push(obj.id);
                    cylxrNames.push(obj.text);
                }
            });
            if(cylxrIds.length<1){
                newbootbox.alert('请选择人员')
                $('#fasong').attr('disabled','false');
                return;
            }
            $.ajax({
                url:sendOrFinshApprove.url,
                data:{id:id,approvalId:cylxrIds.toString(),approvalName:cylxrNames.toString(),operateFlag:"00",approveContent:opinionContent,opinionType:opinionType},
                type: "POST",
                async:false,
                success:function(data){
                	$('#fasong').attr('disabled','false');
                    if (data.result == 'success') {
                        newbootbox.alert('发送成功！').done(function(){
                        	newbootbox.newdialogClose("sshDialog")
                            if(fromMsg=='1'){
                                windowClose()
                            }else if(fileFrom=='qxjsp'){
                            	window.top.bubbleCountStatistics();
                            	window.top.iframe1.location = '/app/qxjgl/qxj/html/CZSP_table.html'
//                                window.top.iframe1.location.reload();
                            }else{
                            	window.top.bubbleCountStatistics();
                            	window.top.iframe1.location = '/app/qxjgl/qxj/html/table.html'
                            }
                        });
                        changToNum();
                    } else {
                    	newbootbox.alert('发送失败！').done(function(){
                        	newbootbox.newdialogClose("sshDialog")
                        });
                    }
                }
            });
        })
        
        //选择联系人，进行送审流程
        $("#sendAndAuthorize").click(function(){
            var nodes2 = $("#tree_2").jstree("get_bottom_selected",true);
            var cylxrIds = [];
            var cylxrNames = [];
            $.each(nodes2, function(i,obj) {
                if(obj.original.type == 1){
                    cylxrIds.push(obj.id);
                    cylxrNames.push(obj.text);
                }
            });
            if(cylxrIds.length<1){
                newbootbox.alert('请选择人员')
                return;
            }
            
            $.ajax({
                url:sendOrFinshApprove.url,
                data:{id:id,approvalId:cylxrIds.toString(),approvalName:cylxrNames.toString(),operateFlag:"00",approveContent:opinionContent,opinionType:opinionType},
                type: "POST",
                async:false,
                success:function(data){
                    if (data.result == 'success') {
                    	newbootbox.alert('发送成功！').done(function(){
                    		newbootbox.newdialogClose("sshDialog");
                            if(fromMsg=='1'){
                              windowClose()
                            }else{
                              window.top.iframe1.v_edit.sendSH2();
                            }
                        });
                    	changToNum();
                    } else {
                        newbootbox.alert('发送失败！').done(function(){
                            newbootbox.newdialogClose("sshDialog");
                        });
                    }
                }
            });
        });

    }

    return {
        // 加载页面处理程序
        initControl : function() {
        	initBtn();
            inittree();
            initother();
        }
    };

}();


