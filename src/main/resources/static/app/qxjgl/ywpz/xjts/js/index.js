var morenUrl={"url":"/app/gwcl/audit/getworkstatus","dataType":'json'};//默认数据
var saveUrl={"url":"/app/gwcl/audit/setworkstatus","dataType":'json'}; //保存
var pageModule = function() {
	/*var initData = function(){
		$ajax({
			url:morenUrl,
			success: function(data) {
				console.log(data)
				if(data.result== "是"){
					$(".Tab_left").addClass("active").siblings().removeClass("active");
				}else{
					$(".Tab_right").addClass("active").siblings().removeClass("active");
				}
				
			}
		})
	}
	var initother = function() {
		$(".titleTab1").delegate("div","click",function(e){
			if($(this).hasClass("active")){
				return;
			}
			$(this).addClass("active").siblings().removeClass("active");
		});
		
		//保存
		$("#save").click(function(){
			var id = $(".titleTab1 .active").attr("id");
			$ajax({
				url:saveUrl,
				data:{type:id},
				success: function(data) {
					if(data.result == "success"){
						newbootbox.alertInfo('保存成功！');
					}else{
						newbootbox.alertInfo(data.message);
					}
				}
			})
		});
	}*/
	return {
		//加载页面处理程序
		initControl: function() {
			initData();
			initother();
		}
	}
}();
