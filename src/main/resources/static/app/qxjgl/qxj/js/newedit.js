var id = window.opener.id;
var fileId = window.opener.v_edit.activeFile.id;
var url_get_stream_file = '/app/qxjgl/documentfile/getStreamFileUrl'; //最新的流式url
var pageModule = function() {

	var initfile = function(){
		$.ajax({
			url:url_get_stream_file,
			data:{id:fileId},
			async:false,
			type: "GET",
			success:function(data){
				fileUrl = data.url
				cssOffice = new CssOffice();
				cssOffice.init("cssOffice", "100%", "100%");
			}
		});
	}
	var initother = function() {

		$("#save").click(function(){
			opinionSaveServlet();
			/*newbootbox.alert("正在生成版式文件...",false);
			setTimeout(function(){
				suwellTransferPdf();
			},500)*/
		});
		
		$("#close").click(function(){
			window.close();
		});
		
	}
	return {
		// 加载页面处理程序
		initControl : function() {
			initfile();
			initother();
		}
	};
}();

//正文的保存
function opinionSaveServlet(){
	var formatFileName = '';
	var officeType = window.opener.officeType;
	var suffix = '.docx'
	var url=location.protocol+"//"+location.host+"/app/qxjgl/servlet/streamFile/save";
	if(officeType == 2){
		suffix = '.wps';
	}
	$.ajax({
		url:"/app/base/user/getToken",
		type: "GET",
		async:false,
		success:function(data){
			tokenAccess = data.result;			
			url+="?streamOrFormatFileType=stream&windowsFlag=1";
			url+="&fileId=" + fileId;
			url+="&access_token=" + tokenAccess;
			//文件名为起草公文id
			cssOffice.saveURL(url,fileId+suffix);
            window.opener.location.reload();
            window.close();
		}
	});
	
}

function onCssOfficeLoadEnd(obj) {
	//wps插件初始化返回对象
	if(typeof obj !== "undefined" && typeof obj === "object"){
		cssOffice = obj;
	};
	if(fileUrl != null && typeof(fileUrl) != "undefined" && fileUrl != ""){
		cssOffice.openFile(fileUrl, false);
	}
}

