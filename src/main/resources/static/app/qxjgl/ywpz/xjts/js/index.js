var morenUrl={"url":"/app/qxjgl/leavecancel/info","dataType":'json'};//默认数据
var saveUrl={"url":"/app/qxjgl/leavecancel/update","dataType":'json'}; //保存
var id;
var pageModule = function() {
	var initData = function(){
		$ajax({
			url:morenUrl,
			success: function(data) {
				id=data.qxjLeaveCancel.id
				if(data.qxjLeaveCancel.type== "0"){
					$(".Tab_left").addClass("active").siblings().removeClass("active");
					$("#getVal").show();
					$(".ipt").val(data.qxjLeaveCancel.days)
				}else{
					$(".Tab_right").addClass("active").siblings().removeClass("active");
					$("#getVal").hide();
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
			if($(this)[0].id=='1'){
				$("#getVal").hide();
			}else{
				$("#getVal").show();
				$(".ipt").val(getDays)
			}
		});
		
		//保存
		$("#save").click(function(){
			var type = $(".titleTab1 .active").attr("id");
			if(type=='0'){
				if($(".ipt").val()<1){
					newbootbox.alertInfo('销假天数不能小于1天！');
					return;
				}
			}
			$ajax({
				url:saveUrl,
				data:{'id':id,'type':type,'days':type=='0'?$(".ipt").val():''},
				success: function(data) {
					if(data.msg == "success"){
						newbootbox.alertInfo('保存成功！');
					}else{
						newbootbox.alertInfo(data.message);
					}
				}
			})
		});
	}
	return {
		//加载页面处理程序
		initControl: function() {
			initData();
			initother();
		}
	}
}();
