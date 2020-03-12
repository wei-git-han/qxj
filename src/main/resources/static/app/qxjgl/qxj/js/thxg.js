var userTree = {"url":"/app/base/user/tree","dataType":"text"}; //人员选择树
var goBackToLastApply = {"url":"/leave/apply/goBackToLastApply","dataType":"text"}; //人员选择树

var id = getUrlParam("id");
var opinionContent = getUrlParam2('opinionContent');//意见内容
var opinionType = getUrlParam('opinionType');//意见类型
var fileFrom = getUrlParam("fileFrom");
var fromMsg= getUrlParam("fromMsg");
var lxrId="";

var pageModule = function() {

	
	var initother = function() {
        $(".btncont1").click(function(){
            var name = $(this).attr("name");
            if(name=="tab2"){
                $(".appuser").removeClass("active");
            };
            if(name=="tab1"){
                $("#tree_2").jstree().deselect_all();
            }
            cylxrIds=[];
            lxrId="";
        });
		$("#close").click(function(){
			newbootbox.newdialogClose("thxgDialog");
		})
		
		//选择联系人，进行送审流程
		$("#fasong").click(function(){
			$('#fasong').attr('disabled','true');
			var cylxrIds = [];
			var cylxrNames = [];
			$(".appuser").each(function(i){
				if($(this).hasClass("active")){
					cylxrIds.push($(this).find("#userId").val());
					cylxrNames.push($(this).find("#userName").val());
				}
			})

			if(cylxrIds.length == 0){
				newbootbox.alertInfo("请选择联系人！");
			}else if(cylxrIds.length > 1){
				newbootbox.alertInfo("请选择单个联系人！");
			}
			$.ajax({
				url:goBackToLastApply.url,
				data:{id:id,receiverId:cylxrIds.toString(),receiverName:cylxrNames.toString(),operateFlag:"03",approveContent:opinionContent,opinionType:opinionType},
				type: "POST",
				async:false,
				success:function(data){
					$('#fasong').attr('disabled','false');
					if (data.result == 'success') {
						newbootbox.alert('退回成功！').done(function(){
                            newbootbox.newdialogClose("thxgDialog");
                            /*if(fromMsg=='1'){
                                windowClose()
                            }else{
								window.top.bubbleCountStatistics();
                                window.top.iframe1.location.reload();
                            }*/
							if(fromMsg=='1'){
								windowClose()
							}else if(fileFrom=='qxjsp'){
								console.log(fileFrom)
								window.top.bubbleCountStatistics();
								window.top.iframe1.location = '/app/qxjgl/qxj/html/CZSP_table.html'
//                                window.top.iframe1.location.reload();
							}else{
								window.top.bubbleCountStatistics();
								window.top.iframe1.location = '/app/qxjgl/qxj/html/table.html'
							}
							changToNum();
                        });
					} else {
						newbootbox.alert('退回失败！').done(function(){
                            newbootbox.newdialogClose("thxgDialog");
                        });
					}
				}
			});
		});
	
	}
	
	return {
		// 加载页面处理程序
		initControl : function() {
			initother();
		}
	};

}();

var v_plss = new Vue({
	el : '#vue_plss',
	data : {
		generalContactArr :[]
	},
	created : function() {
		this.getGeneralContactArr()
	},
	updated:function(){
		this.setGeneralContactActive();
	},
	methods : {
		getGeneralContactArr : function(){
			vm = this;
			Vue.http.get( "/leave/apply/flowAllPersons?id="+id).then(
			function(response) {
				vm.generalContactArr = response.data;
			}/*, function(response) {
			    vm.generalContactArr = [{"organName":"协办（综合处）","sex":"0","userName":"宋华健","userId":"748d2d00-ca6d-411a-a8a7-c62f8c59a5f6"},{"organName":"办公厅二局","sex":"0","userName":"局领导","userId":"f092c1b2-c974-4ae6-8a45-b5c6ca820279"},{"organName":"协办（综合处）","sex":"0","userName":"温参谋","userId":"15c79225-38d7-495b-a589-57d5f1265ab9"}]
				console.log('error:getGeneralContactArr');
			}*/)
		},
		setGeneralContactActive : function(){
			$(".appuser:eq(0)").toggleClass("active");
			$(".appuser").click(function(){
				$(this).siblings().removeClass("active");
				$(this).toggleClass("active");
			});
		}
	}
})


