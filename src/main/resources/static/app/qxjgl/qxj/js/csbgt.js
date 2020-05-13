var userTree = {"url":"/app/base/user/allTree","dataType":"text"}; //人员选择树
var deptTree = {"url":"/app/base/dept/tree_onlyroot","dataType":"text"}; //人员选择树
var getCsbgtDefaultParamUrl = {"url":"/app/qxjgl/syncDataApi/getCsbgtDefaultParam","dataType":"text"}; //呈送办公厅获取默认参数
var syncDataToGwzbUrl = {"url":"/app/qxjgl/syncDataApi/syncDataToGwzb","dataType":"text"};//呈送办公厅操作
var id = getUrlParam("id");//主文件id
var opinionContent = getUrlParam2('opinionContent');//意见内容
var opinionType = getUrlParam('opinionType');//意见类型
var fromMsg= getUrlParam("fromMsg");
var fileFrom= getUrlParam("fileFrom");
var receiverIsMe = getUrlParam("receiverIsMe");
var flowType = getUrlParam("flowType");
var getPreStatusUrl = "/leave/apply/getPreStatus?id="+id;

var pageModule = function() {
	var inittree = function(){
        $("#undertakerName").createNewUserTree({
            url : userTree,
            width : "100%",
            plugins:'',
            success : function(data, treeobj) {},
            selectnode : function(e, data,treessname,treessid) {
                $('#undertakerId').val(data.node.id)
                $('#undertakerName').val(data.node.text)
            }
        });
        $("#undertakeDepartmentName").createNewTree({
            url : deptTree,
            width : "100%",
            plugins:'',
            success : function(data, treeobj) {},
            selectnode : function(e, data) {
                $('#undertakeDepartmentId').val(data.node.id)
                $('#undertakeDepartmentName').val(data.node.text)
            }
        });
	}
	var initParam = function(){
        $ajax({
            url: getCsbgtDefaultParamUrl,
            data: {id: id},
            type: "GET",
            async: false,
            success: function (data) {
            	setformdata(data);
            }
        });
	}
	var initother = function() {		
		$("#close").click(function(){
			newbootbox.newdialogClose("csbgtDialog");
		})
		
		//选择联系人，进行送审流程
		$("#fasong").click(function() {
			$('#fasong').attr('disabled','true');
			var elementarry = ["documentTitle","undertakerName","undertakerId","undertakeDepartmentName","undertakeDepartmentId","securityClassification","jjcdId"];
			var paramdata = getformdata(elementarry);
			paramdata.id=id;
			paramdata.opinionType=opinionType;
			paramdata.opinionContent=opinionContent;
			if(fileFrom=='qxjsp'){
                 $.ajax({
                     url:getPreStatusUrl,
                     type: "GET",
                     async:false,
                     success:function(data){
                        if (data.result == "success") {
                            $ajax({
                                url: syncDataToGwzbUrl,
                                data: paramdata,
                                type: "GET",
                                async: false,
                                success: function (data) {
                                    $('#fasong').attr('disabled','false');
                                    if (data.result == 'success') {
                                        newbootbox.alert('发送成功！').done(function(){
                                            newbootbox.newdialogClose("csbgtDialog")
                                            changToNum2(function(){
                                                if(fromMsg=='1'){
                                                    windowClose()
                                                }else if(fileFrom=='qxjsp'){
                                                    window.top.bubbleCountStatistics();
                                                    window.top.iframe1.location = '/app/qxjgl/qxj/html/qxjView.html?id='+id+'&fileFrom='+fileFrom+'&receiverIsMe='+receiverIsMe+"&flowType="+flowType;
                                                }else{
                                                    window.top.bubbleCountStatistics();
                                                    window.top.iframe1.location = '/app/qxjgl/qxj/html/table.html'
                                                }
                                            })
                                        });
                                    } else {
                                        newbootbox.alert('发送失败！').done(function(){
                                            newbootbox.newdialogClose("csbgtDialog")
                                        });
                                    }
                                }
                            });
                        }
                     }
                 })
			} else {

                $ajax({
                    url: syncDataToGwzbUrl,
                    data: paramdata,
                    type: "GET",
                    async: false,
                    success: function (data) {
                        $('#fasong').attr('disabled','false');
                        if (data.result == 'success') {
                            newbootbox.alert('发送成功！').done(function(){
                                newbootbox.newdialogClose("csbgtDialog")
                                changToNum2(function(){
                                    if(fromMsg=='1'){
                                        windowClose()
                                    }else if(fileFrom=='qxjsp'){
                                        window.top.bubbleCountStatistics();
                                        //window.top.iframe1.location = '/app/qxjgl/qxj/html/CZSP_table.html'
                                        window.top.iframe1.location = '/app/qxjgl/qxj/html/qxjView.html?id='+id+'&fileFrom='+fileFrom+'&receiverIsMe='+receiverIsMe+"&flowType="+flowType;

        //                                window.top.iframe1.location.reload();
                                    }else{
                                        window.top.bubbleCountStatistics();
                                        window.top.iframe1.location = '/app/qxjgl/qxj/html/table.html'
                                    }
                                })
                            });
                            //changToNum();
                        } else {
                            newbootbox.alert('发送失败！').done(function(){
                                newbootbox.newdialogClose("csbgtDialog")
                            });
                        }
                    }
                });
			}

        })

	}
	
	return {
		// 加载页面处理程序
		initControl : function() {
			initParam();
			inittree();
			initother();
		}
	};

}();

var v_plss = new Vue({
	el : '#vue_plss',
	data : {
        securityClassificationArr:[],
        emergencyDegreeArr:[]
	},
	created : function() {
        this.getSecurityClassificationArr()
        this.getEmergencyDegreeArr()
	},
	updated:function(){
	},
	methods : {
        getSecurityClassificationArr:function(){
            vm = this
            Vue.http.get("/app/gwcl/dictionary/info/security_classification").then(function(response){
                vm.securityClassificationArr = response.data.dictionaryValueList;
            }, function(response){
                console.log('error:getSecurityClassificationArr');
            })
        },
        //加载紧急程度
        getEmergencyDegreeArr:function(){
            vm = this
            Vue.http.get("/app/gwcl/dictionary/info/emergency_gegree").then(function(response){
                vm.emergencyDegreeArr = response.data.dictionaryValueList;
            }, function(response){
                console.log('error:getEmergencyDegreeArr');
            })
        },
	}
})