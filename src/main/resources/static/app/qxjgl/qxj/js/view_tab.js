var id = window.location.search.split("id=")[1];
var pageModule = function(){
	
	var initother = function(){
		
		$("#iframe_qjd").attr("src","qj_chakan.html?id="+id);
		
		$("#iframe_xjedit").attr("src","xj_chakan.html?id="+id);
		
		
		$("#close").click(function(){
			newbootbox.newdialogClose("View");
		})
	}
	
	return{
		//加载页面处理程序
		initControl:function(){
			initother();
		}
	}
	
}();
