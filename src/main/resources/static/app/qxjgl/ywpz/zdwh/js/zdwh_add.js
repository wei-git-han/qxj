var url1 = {"url":rootPath +"/dicvocationsort/save","dataType":"text"};
var url2 = {"url":rootPath +"/dicvocationsort/check","dataType":"text"};
var pageModule = function(){
	var initother = function(){
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
