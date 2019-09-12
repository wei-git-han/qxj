var url1 = {"url":rootPath +"/dicvocationsort/info","dataType":"text"};
var url2 = {"url":rootPath +"/dicvocationsort/update","dataType":"text"};

var id666 = (window.location.search).split("id=")[1];
var id = id666.split("&&")[0];
var pageModule = function(){
	var initdatafn = function(){
		$ajax({
			url:url1,
			data:{id:id},
			success:function(data){
				if(data.result=="success"){
					setformdata(data);
				}
			}
		})
	}
	
	var initother = function(){
		$(".date-picker").datepicker({
		    language:"zh-CN",
		    rtl: Metronic.isRTL(),
		    orientation: "right",
		    autoclose: true
		});
		$("#commentForm").validate({
			   submitHandler: function() {
			    var elementarry = ["id","fieldValue"];
				var paramdata = getformdata(elementarry);
				paramdata.id = id;
					$ajax({
						url:url2,
						type:"post",
						data: paramdata,
						success:function(data){
							if(data.result=="success"){
								newbootbox.newdialogClose("zdwh_edit");
								newbootbox.alertInfo("编辑成功！").done(function(){
									window.top.iframe1.window.iframe2.window.pageModule.initgrid();
								});
							}else if(data.result=="fail"){
								newbootbox.alertInfo("字典项已存在！");
							}else{
								newbootbox.alertInfo("编辑失败！");
							}
						}
					})
			    }
			 });
			 
		$("#save").click(function(){
			var sbarr = $("#fieldValue").val();
			if(sbarr.length > 30){
				newbootbox.alertInfo("字典值不可超过30个字！");
				return;
			}  else if (sbarr.trim().length < 1) {
				newbootbox.alertInfo("字典值不能为空！");
				return;
			}
			$("#commentForm").submit();
		})
		
		$("#close").click(function(){
			newbootbox.newdialogClose("zdwh_edit");
		})
	}
	
	
	return{
		//加载页面处理程序
		initControl:function(){
			initdatafn();
			initother();
		}
	}
	
}();

