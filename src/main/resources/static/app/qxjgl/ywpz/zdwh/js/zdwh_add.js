var url1 = {"url":rootPath +"/dicvocationsort/save","dataType":"text"};
var url2 = {"url":rootPath +"/dicvocationsort/check","dataType":"text"};
var addType = window.location.href.split('?')[1].split('=')[1]; //新增类型（0：请假 1：因公出差 2：交通工具）
deductionVacationDay = "0";
var pageModule = function(){
	var initother = function(){
        if(addType == '0'){
            deductionVacationDay = '1';
            $('.leaveSwitch').prop('checked',true);
        }else if(addType == '2'){
            deductionVacationDay = '3';
            $('#isNeedInfo').html('是否需要<br/>审批：')
			$('#xjlb').html('交通工具类型')
		}
		//初始化开关按钮
		$('.leaveSwitch').bootstrapSwitch({
       		onText:"ON",
       		offText:"OFF",
       		onColor:"success",
       		offColor:"danger",
//       		size:"small",
       		labelWidth:"20px",
       		handleWidth:"20px",
       		animate:"false",
       		onSwitchChange:function(event,state){
                if(addType == '2'){
                	if(state){
                        deductionVacationDay = '2';
                        //$('#approvalForm').show();
					}else{
                        deductionVacationDay = '3';
                        //$('#approvalForm').hide();
					}
				}else{
                    deductionVacationDay = state?"1":"0"
				}
       		}
       	})
       	$('.leaveSwitch').css("visibility","visible");

		$(".date-picker").datepicker({
		    language:"zh-CN",
		    rtl: Metronic.isRTL(),
		    orientation: "right",
		    autoclose: true
		});
		$("#commentForm").validate({
		    submitHandler: function() {
				var elementarry = ["textitem"];	
				var paramdata = getformdata(elementarry);
				paramdata.deductionVacationDay = deductionVacationDay
                paramdata.type = addType
                if(addType == '2' && $('.leaveSwitch').prop('checked')){  //如果是交通工具
                    if($('input[type=radio]:checked').length != 0){
                        //flag   0：私家车长途 1：长途车审批表'
                        paramdata.flag = $('input[type=radio]:checked').val();
                    }
                }
				$ajax({
					url:url2,
					type: "POST",
					data:paramdata,
					success:function(data){
					if(data.result=="success"){
						$ajax({
							url:url1,
							type: "POST",
							data:paramdata,
							success:function(data){
								if(data.result=="success"){
									newbootbox.newdialogClose("zdwh_add");
									newbootbox.alertInfo("保存成功！").done(function(){
										window.top.iframe1.window.iframe2.window.pageModule.initgrid();
									});
								}else{
									newbootbox.alertInfo("保存失败！"); 
								}
							}
						}) 
					}else{
						newbootbox.alertInfo("字典值已存在！"); 
					}
				}
				})
					
		    }

		});
		
		$("#save").click(function(){
			// if(addType == '2' && $('.leaveSwitch').prop('checked')){  //如果是交通工具
			// 	if($('input[type=radio]:checked').length == 0){
             //        newbootbox.alertInfo("请选择审批表！");
             //        return;
			// 	}
			// }
			var sbarr = $("#textitem").val();
			var strs = sbarr.split("\n");
			for(var i=0;i<strs.length;i++){		
				if(strs[i].trim().length>30){
					newbootbox.alertInfo("字典值不能超过30个字！");
					return;
				} else if (strs[i].trim().length < 1) {
					newbootbox.alertInfo("字典值不能为空！");
					return;
				}
			}
			$("#commentForm").submit();
		})
		
		
		$("#close").click(function(){
			newbootbox.newdialogClose("zdwh_add");
		})
		
	}
	return{
		//加载页面处理程序
		initControl:function(){
			initother();
		}
	}

}();
