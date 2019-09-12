var url1 = {"url":rootPath +"/qxjdicholiday/info","dataType":"json"};
var url2 = {"url":rootPath +"/qxjdicholiday/update","dataType":"text"};
var url3 = {"url":rootPath +"/customuser/tree","dataType":"text"};
var id = (window.location.search).split("id=")[1];
var pageModule = function(){
	var initdatafn = function(){
		$ajax({
			url:url1,
			data:{id:id},
			type:"POST",
			success:initdata
		})
	}
	var initdata = function(data){
    	setformdata(data);
	}
	var initother = function(){
		$("#shouldtakdays").change(function(){
			jiancafn($(this).val());
		})
		$("#save").click(function(){
			var elementarry =  ["username","userid","deptname","deptid","shouldtakdays"];
			var paramdata = getformdata(elementarry);
			paramdata.id = id;
			$ajax({
				url:url2,
				data: paramdata,
				success:function(data){
					if(data.msg=="success"){
						newbootbox.newdialogClose("dyxjts_edit");
						newbootbox.alertInfo("保存成功！").done(function(){
							window.top.iframe1.window.iframe2.window.pageModule.initgrid();
						});
					}else{
						newbootbox.alertInfo("保存失败！"); 
					}
				}
			})
		})
		
		$("#close").click(function(){
			newbootbox.newdialogClose("dyxjts_edit");
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

var jiancafn = function(str){
	var re=/^\d+(\.\d+)?$/;
	if(!re.test(str)){
		newbootbox.alertInfo("请输入数字！"); 
	}
}
