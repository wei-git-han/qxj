var searchParam = window.location.search;
var id = getUrlParam('id');
var url_export_ofd= rootPath +"/leaveorback/exportofd";//导出ofd
url_export_ofd+='?id='+id;
$.getJSON(url_export_ofd, function(url) {
	document.title="请假审批单[预览]";
	openOFDFile(url, "PLUGIN_INIT_OFDDIV",$("#embedwrap").width(),$("#embedwrap").height(), "");
});
var c3 = {};
$(window).resize(function(){
	clearTimeout(c3);
	c3 = setTimeout(function(){
		//版式文件加载
		ocx.performClick('vzmode_fitwidth');//适合宽度
		//ocx.setScale(100);
	},500)
});