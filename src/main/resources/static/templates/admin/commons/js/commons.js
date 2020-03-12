var css={
		filter_html:function(html){
			/*1、本方法的功能：去除HTML标签、空格（&nbsp;）、换行（\n）*/
			/*var regex=/<[^>]+>/g;*/
			var regex=/<[^>]*>/g;
			var txt=html.replace(regex,'');
			txt=txt.replace(/&nbsp;/g,'');
			txt=txt.replace(/\n/g,'');
			return txt;
		},
		limit_rich_text:function(){
			/*1、本方法的功能：限制富文本框的最大字符长度（去除掉：HTML标签、空格（&nbsp;）、换行（\n））*/
			var _obj=css;
			$('textarea.limit').each(function(){
				var $this=$(this);
				var id=$this.attr('id');
				var editor=CKEDITOR.instances[id];
				editor.on('change',function(event){
					var data_html=editor.getData();
					var txt=_obj.filter_html(data_html);
					var len=txt.length;
					$this.val(txt).blur();
				});
				
				var cancel=setInterval(function(){
					var $cke=$('#cke_'+id);
					if($cke.size()>0){
						clearInterval(cancel);
						$('#cke_'+id).after($this);
					}
				},300);
			});
		},
		get_checkbox_val:function(name,split){
			/*获取指定name的已被选择的复选框的所有值*/
			var vals='';
			split=split||',';
			$('input[name='+name+']:checked').each(function(){
				var $this=$(this);
				vals+=split+$this.val();
			});
			return vals.substring(split.length);
		},
		get_query_val:function(name){
			/*1、获取当前url中，指定参数的值*/
			var reg=new RegExp('(^|&)'+name+'=([^&]*)(&|$)');//构建一个含有目标参数的正则表达式对象
			var r=window.location.search.substr(1).match(reg);//匹配目标参数
			if(!!r){
				return unescape(r[2]);
			}else{
				return '';
			}
			
		},
		cut:function(selector){
			val=$(selector).val();
			val=val||'';
			$(selector).val(val.substring(0,10));
		},
	      /*
                             会议名称-限制输入特殊字符
          * */
		filter:function(str){
			var pattern=/[`~^@#$%&￥!！……××*]*/g;
			return str.replace(pattern,"");
		}
};

jQuery(document).ready(function() {    
    Metronic.init(); // init metronic core componets
    Layout.init(); // init layout
    QuickSidebar.init(); // init quick sidebar
    Demo.init(); // init demo features
    initcomonother();
    initinclude();
    
    var pagedate = new Date();
	var month = pagedate.getMonth()+1;
	if(month<10){
		month = "0"+month;
	}
	var day = pagedate.getDate();
	if(day<10){
		day = "0"+day;
	}
	
	var hour = pagedate.getHours();
	var minute = pagedate.getMinutes();
	
	var year = pagedate.getFullYear();
	$(".datee").val(year+"-"+month+"-"+day);
	$(".nowdate").val(year+"-"+month+"-"+day+" "+hour+":"+minute);
	
});    
    

/*start设置bootstrap滚动条自适应浏览器高度*/
var c2 = {};
$(window).resize(function(){
	clearTimeout(c2);
	c2 = setTimeout(function(){
		/*
        <div style="height:100%;">高度通过此处来设置,scroller外面需要包一层容器
        	<div  style="height:100%;" class="scroller">
        	</div>
        </div>
		 */
        $(".slimScrollDiv,.scroller").each(function(){
            $(this).css({"height":"100%"});
        });
		//控制弹出框上下垂直居中
		var $dialog = $("div.modal-dialog");
		var tops = document.documentElement.clientHeight;
		var top = (tops-$dialog.height())/2;
		var lefts = document.documentElement.clientWidth;
		var left = (lefts-$dialog.width())/2;
		if($dialog.parent().hasClass("cjDialog")){
			$dialog.parent().css({"top":+top+"px","padding":"0 0 1px","left":left+"px"});
		}else{
			$dialog.css({"margin":+top+"px auto 0"});
		}
		
	},500)
});
/*end*/

function initselect_DOC(id,arry){
	var html = "";
	$.each(arry,function(i){
		html+='<option value='+arry[i].id+'>'+arry[i].dictionaryValue+'</option>';
	});
	$("#"+id).append(html);
}
var changToNumUrl = {"url":"/api/qxjgl/todo"}; 
//更新桌面代办数量
function changToNum(){
		$ajax({
			url:changToNumUrl,
			success:function(data){
				if(navigator.userAgent.indexOf('OfficeBrowser')>=0){	
					window.top.__set_todo_count__(data.count);
			    }
			}
		});
}
function setformdata(data){
	for(key in data){
		if($("[name="+key+"]").attr("type")=="checkbox"){
			var checkbox = document.getElementsByName(key);
			for(var j = 0;j<checkbox.length;j++){
				if(checkbox[j].value==data[key]){
					checkbox[j].checked=true;
					$(checkbox[j]).parent().addClass("checked");
				}
			};
		}else if($("[name="+key+"]").attr("type")=="radio"){
			var radio = document.getElementsByName(key);
			for(var j = 0;j<radio.length;j++){
				if(radio[j].value==data[key]){
					radio[j].checked=true;
					$(radio[j]).parent().addClass("checked");
				}else{
					radio[j].checked=false;
					$(radio[j]).parent().removeClass("checked");
				}
			};
		}else{
			$("#"+key).val(data[key]);
			//alert(data[key])
		}
	}
	
}
    
/*	var elementarry = ["username","email","phone","password1","password2","ifduty","role"];
	返回表单值{username:"username",...}
	var paramdata = getformdata(elementarry);
*/

function getformdata(arry){
	var paramdata = {};
	$.each(arry,function(i){
		if($("[name="+arry[i]+"]").attr("type")=="checkbox"){
			var valuearry = [];
			var checkboxvalue = "";
			var checkbox = document.getElementsByName(arry[i]);
			for(var j = 0;j<checkbox.length;j++){
				if(checkbox[j].checked==true){
					valuearry.push(checkbox[j].value);
				}
			};
			checkboxvalue = valuearry.join(",");
			paramdata[arry[i]] = checkboxvalue;
		}else if($("[name="+arry[i]+"]").attr("type")=="radio"){
			var radiovalue = "";
			var radio = document.getElementsByName(arry[i]);
			for(var j = 0;j<radio.length;j++){
				if(radio[j].checked==true){
					radiovalue = radio[j].value;
				}
			};
			paramdata[arry[i]] = radiovalue;
		}else{
			paramdata[arry[i]] = $("#"+arry[i]).val();
		}
	})
	return paramdata;
}



/*	var jsonarry =  [{
						text:"是否转维修",
						value:"01"
					},{
						text:"已转",
						value:"02"
					},{
						text:"未转",
						value:"03"
					}]
	用来对select进行初始化
	initselect("selectelement",jsonarry);
	*/
function initselect(id,arry){
	$("#"+id).html("");
	var html = "";
	$.each(arry,function(i){
		if(($.trim(arry[i].text)).indexOf("请选择")==-1){
			html+='<option value='+arry[i].value+'>'+arry[i].text+'</option>';
		}
	});
	$("#"+id).append(html);
}
	
function $ajax(obj){
	var urlobj = obj.url;
	if(urlobj==null||typeof(urlobj)=="undefined"){
		return;
	}
	var url = urlobj.url;
	var async = obj.async;
	var dataType = urlobj.dataType;
	var data = obj.data;
	var success = obj.success;
	var error = obj.error;
	var type = obj.type;
	if(url==null||typeof(url)=="undefined"){
		return
	}
	if(async==null||typeof(async)=="undefined"){
		async = true;
	}
	if(dataType==null||typeof(dataType)=="undefined"){
		dataType = 'json';
	}
	if(data==null||typeof(data)=="undefined"){
		data = {};
	}
	if(success==null||typeof(success)=="undefined"){
		success = function(data){
			//alert();
		}
	}
	if(error==null||typeof(error)=="undefined"){
		error = function(msg) {
			//alert("系统故障!");
		}
	}
	if(type==null||typeof(type)=="undefined"){
		type = "GET";
	}else{
		if(dataType=='text'){
			type = "GET";
		}
	}
	$.ajax({
		url : url,
		dataType : dataType,
		type:type,
		async: async,
		data:data,
		success:function(data){
			if(dataType=="text"){
				data = eval("("+data+")");
			}
			return success(data);
		},
		error : error
	});
}
	
function getUrlParam(name){
	var reg=new RegExp("(^|&)"+name+"=([^&]*)(&|$)");
	var r=window.location.search.substr(1).match(reg);
	if(r!=null)return unescape(r[2]);
	return null;
}
/*升级版，可以带中文自动解码*/
function getUrlParam2(name){
	var reg=new RegExp("(^|&)"+name+"=([^&]*)(&|$)","i");
	var r=window.location.search.substr(1).match(reg);
	//if(r!=null)return unescape(r[2]);
	if(r!=null)return decodeURI(r[2]);
	return null;
}

function initcomonother(){
	$(".menu2_list2").click(function(){
		$(".menu2_list2").removeClass("active");
		$(this).addClass("active");
	})
}
	
function initinclude(){
	var include = $("[include]");
	if(include!=null&&typeof(include)!="undefined"){
		include.each(function(i){
			var obj = $(include[i]);
			var url = obj.attr("include");
			$ajax({
				url:{"url":url,"dataType":"html"},
				async:false,
				success:function(html){
					obj.append(html);
				}
			})
		})
	}
}

//用来日期格式化
Date.prototype.format =function(format) {
	var o = {
		"M+" : this.getMonth()+1, //month
		"d+" : this.getDate(), //day
		"h+" : this.getHours(), //hour
		"m+" : this.getMinutes(), //minute
		"s+" : this.getSeconds(), //second
		"q+" : Math.floor((this.getMonth()+3)/3), //quarter
		"S" : this.getMilliseconds() //millisecond
	}
	if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
	(this.getFullYear()+"").substr(4- RegExp.$1.length));
	for(var k in o)if(new RegExp("("+ k +")").test(format))
	format = format.replace(RegExp.$1,
	RegExp.$1.length==1? o[k] :
	("00"+ o[k]).substr((""+ o[k]).length));
	return format;
}

jQuery.fn.extend({
	createSelecttree: function(obj) {
		obj.target = $(this).attr("id");
		var gridobj = new createSelecttree(obj);
		return gridobj;
	}
});

jQuery.fn.extend({
	createcheckboxtree: function(obj) {
		obj.target = $(this).attr("id");
		var gridobj = new createcheckboxtree(obj);
		return gridobj;
	}
});


function createSelecttree(obj){
	var create = function(){
		$(".selecttree").css({
			width:"100%",
			height:"100%",
			overflow:"visible"
		});
		$("#"+obj.target).css({
			width:"100%",
			height:"100%",
			"padding-left":"10px",
			border:"none"
		});
		$("#"+obj.target)[0].readOnly=true;
		
		var width = obj.width;
		if(width==null||typeof(width)=="undefined"){
			width = "";
		}else{
			width = "width:"+obj.width;
		}
		var data = obj.data;
		if(data==null||typeof(data)=="undefined"){
			data = {};
		}
		$("#"+obj.target).parent().append(
			'<div class="'+obj.target+'tree1" style="max-height:300px;overflow: auto;display:none;background:#ffffff;border:1px solid #cccccc;'+width+';padding:10px;position:relative;z-index: 100;">'+
    		'	<div id="'+obj.target+'tree2" class="tree-demo" style="width:100%;">'+
			'	</div>'+
    		'</div>'
		);
		$("#"+obj.target).click(function(){
			$("."+obj.target+"tree1").slideToggle(50);
			return false;
		})
/*		$("body").click(function(){
			$("."+obj.target+"tree1").slideUp(50)
		})*/
		//增加判断，当点击展开和收起加减号时不隐藏树。
		$("body").click(function(e){
			if($(e.target).hasClass("jstree-ocl")){
				return;
			}
			$("."+obj.target+"tree1").slideUp(50);
		})
		$ajax({
			url:obj.url,
			async:false,
			success:function(data){
				
				$("#"+obj.target+"tree2").jstree({
				    "plugins": ["wholerow", "types"],
				    "core": {
				    "themes" : {
				        "responsive": false
				    },    
				    "data": data,
				    },
				    "types" : {
				    	 "default" : {
						        "icon" : "fa fa-folder icon-state-warning icon-lg"
						    },
						    "file" : {
						        "icon" : "fa fa-file icon-state-warning icon-lg"
						    },
						    "1" : {
						        "icon" : "fa fa-user icon-state-warning icon-lg"
						    }
				    }
				});
				$("#"+obj.target+"tree2").on("ready.jstree", function(e,o) {
					obj.success(data,$("#"+obj.target+"tree2"));
				});
				$("#"+obj.target+"tree2").on("select_node.jstree", function(e,data) {
					var id = $("#" + data.selected).attr("id");
					//liuhq:当同一页面中有两个或多个相同树时赋值有重复问题，
					//$("#"+obj.target).val($("#"+id+">a").text());
					$("#"+obj.target).val($("#"+obj.target+"tree2").find("#"+id+">a").eq(0).text());
				    obj.selectnode(e,data);
				});
				
			}
		})
		
	}
	create();
}



function createcheckboxtree(obj){
	var create = function(){
		$(".selecttree").css({
			width:"100%",
			height:"100%",
			overflow:"visible"
		});
		$("#"+obj.target).css({
			width:"100%",
			height:"100%",
			"padding-left":"10px",
			border:"none"
		});
		$("#"+obj.target)[0].readOnly=true;
		
		var width = obj.width;
		if(width==null||typeof(width)=="undefined"){
			width = "";
		}else{
			width = "width:"+obj.width;
		}
		var data = obj.data;
		if(data==null||typeof(data)=="undefined"){
			data = {};
		}
		$("#"+obj.target).parent().append(
			'<div class="'+obj.target+'tree1" style="max-height:300px;overflow-y: auto;display:none;background:#ffffff;border:1px solid #cccccc;'+width+';padding:10px;position:relative;z-index: 100;">'+
    		'	<div id="'+obj.target+'tree2" class="tree-demo" style="width:100%;">'+
			'	</div>'+
    		'</div>'
		);
		$("#"+obj.target).click(function(){
			$("."+obj.target+"tree1").slideToggle(50);
			return false;
		})
		/*在选择树的框范围内点击树不消失*/
		$("."+obj.target+"tree1").click(function(){
			return false;
		})
		//增加判断，当点击展开和收起加减号时不隐藏树。
		$("body").click(function(e){
			if($(e.target).hasClass("jstree-ocl")){
				return;
			}
			$("."+obj.target+"tree1").slideUp(50);
		})
		$ajax({
			url:obj.url,
			async:false,
			success:function(data){
				
				$("#"+obj.target+"tree2").jstree({
				    "plugins": ["wholerow", "types","checkbox"],
				    "core": {
				    "themes" : {
				        "responsive": false
				    },    
				    "data": data,
				    },
				    "types" : {
				    	 "default" : {
						        "icon" : "fa fa-folder icon-state-warning icon-lg"
						    },
						    "file" : {
						        "icon" : "fa fa-file icon-state-warning icon-lg"
						    },
						    "1" : {
						        "icon" : "fa fa-user icon-state-warning icon-lg"
						    }
				    }
				});
				$("#"+obj.target+"tree2").on("ready.jstree", function(e,o) {
					obj.success(data,$("#"+obj.target+"tree2"));
				});
				$("#"+obj.target+"tree2").on("select_node.jstree", function(e,data) {
					var id = $("#" + data.selected).attr("id");
					
					var nodes2 = $("#"+obj.target+"tree2").jstree("get_bottom_checked",true);
			
					var treessid = [];
					var treessname = [];
					$.each(nodes2, function(i,obj) {
						treessid.push(obj.id)
						treessname.push(obj.text)
					});
					//liuhq:当同一页面中有两个或多个相同树时赋值有重复问题，
					//$("#"+obj.target).val($("#"+id+">a").text());
					//$("#"+obj.target).val($("#"+obj.target+"tree2").find("#"+id+">a").eq(0).text());
					$("#bm").val(treessname);
					$("#bmId").val(treessid);
				    //obj.selectnode(e,data);
				});
				
			}
		})
		
	}
	create();
}

jQuery.fn.extend({
	createUserTree: function(obj) {
		obj.target = $(this).attr("id");
		var gridobj = new createUserTree(obj);
		return gridobj;
	}
});

function createUserTree(obj){
	var create = function(){
		$(".selecttree").css({
			width:"100%",
			height:"100%",
			overflow:"visible"
		});
		$("#"+obj.target).css({
			width:"100%",
			height:"100%",
			"padding-left":"10px",
			border:"none"
		});
		$("#"+obj.target)[0].readOnly=true;
		
		var width = obj.width;
		if(width==null||typeof(width)=="undefined"){
			width = "";
		}else{
			width = "width:"+obj.width;
		}
		var data = obj.data;
		if(data==null||typeof(data)=="undefined"){
			data = {};
		};
		var piugins = obj.piugins;
		if(piugins==null||typeof(piugins)=="undefined"){
			piugins = "";
		};
		$("#"+obj.target).parent().append(
			'<div class="'+obj.target+'tree1" style="max-height:300px;overflow: auto;display:none;background:#ffffff;border:1px solid #cccccc;'+width+';padding:10px;position:relative;z-index: 100;">'+
    		'	<div id="'+obj.target+'tree2" class="tree-demo" style="width:100%;">'+
			'	</div>'+
    		'</div>'
		);
		$("#"+obj.target).click(function(){
			$("."+obj.target+"tree1").slideToggle(50);
			return false;
		})
		/*在选择树的框范围内点击树不消失*/
		$("."+obj.target+"tree1").click(function(){
			return false;
		})
		//增加判断，当点击展开和收起加减号时不隐藏树。
		$("body").click(function(e){
			if($(e.target).hasClass("jstree-ocl")){
				return;
			}
			$("."+obj.target+"tree1").slideUp(50);
		})
		
		$ajax({
			url:obj.url,
			async:false,
			success:function(data){
				
				$("#"+obj.target+"tree2").jstree({
				    "plugins": ["wholerow", "types",piugins],
				    "core": {
				    "themes" : {
				        "responsive": false
				    },    
				    "data": data,
				    },
				    "types" : {
				    	"default" : {
				    		"icon" : "fa fa-folder icon-state-warning icon-lg"
				    	},
				    	"file" : {
				    		"icon" : "fa fa-file icon-state-warning icon-lg"
				    	},
				    	"1" : {
				    		"icon" : "fa fa-user icon-state-warning icon-lg"
				    	}
				    }
				});
				$("#"+obj.target+"tree2").on("ready.jstree", function(e,o) {
					obj.success(data,$("#"+obj.target+"tree2"));
				});
				$("#"+obj.target+"tree2").on("select_node.jstree", function(e,data) {
					var nodes2 = $("#"+obj.target+"tree2").jstree("get_bottom_selected",true);
					
					var treessid = [];
					var treessname = [];
					$.each(nodes2, function(i,obj) {
						if (obj.type == 1) {
							treessid.push(obj.id);
							treessname.push(obj.text);
						}
					});
				    obj.selectnode(e,data,treessname,treessid);
				});
				
				$("#"+obj.target+"tree2").on("deselect_node.jstree", function(e,data) {
					var nodes2 = $("#"+obj.target+"tree2").jstree("get_bottom_selected",true);
					var treessid = [];
					var treessname = [];
					$.each(nodes2, function(i,obj) {
						if (obj.type == 1) {
							treessid.push(obj.id);
							treessname.push(obj.text);
						}
					});
				    obj.selectnode(e,data,treessname,treessid);
				});
				
			}
		})
		
	}
	create();
}



/**
 * 判断OS，如果是Window系统并且是IE浏览器，则需要初始化；如果是Linux系统并且是FireFox浏览器，则需要初始化；
 */
function getWebEquipmentOS(){
	// 判断OS
	var sUserAgent = navigator.userAgent;
	var isWin = (navigator.platform == 'Win32') || (navigator.platform == 'Windows');
	var isLinux = (String(navigator.platform).indexOf("Linux") > -1);
	var isUnix = (navigator.platform == 'X11') && !isWin;
	// 判断浏览器
	var explorere = navigator.userAgent.toLowerCase();
	var isFireFox = (explorere.indexOf("firefox") > 0);
	var isIE = (explorere.indexOf("msie") > 0);
	if ((isLinux || isUnix) && isFireFox) {
		return 'firefox';
	} else if (isWin && isIE) {
		return 'ie';
	} else {
		return 'no_init';
	}
	
}

var path = "/gwgl/";
var newbootbox = {
	confirm:function(obj){
		window.top.bootbox.dialog({
	        title: obj.title,
	        message: obj.message,
	        buttons: {
	          success: {
	            label: "确定",
	            className: "btn-primary",
	            callback: function() {
					obj.callback1();	
	            }
	          },
	          danger: {
	            label: "取消",
	            className: "btn-default",
	            callback: function() {
	            	//obj.callback2();
	            }
	          },
	        }
	    });
	},
	//插件的确认框
	oconfirm:function(obj){
		window.top.bootbox.dialog({
	        title: obj.title,
	        message: obj.message,
            className:"cjDialog",
	        buttons: {
	          success: {
	            label: "确定",
	            className: "btn-primary",
	            callback: function() {
					obj.callback1();	
	            }
	          },
	          danger: {
	            label: "取消",
	            className: "btn-default",
	            callback: function() {
	            	//obj.callback2();
	            }
	          },
	        }
	    });
	},
	confirm100:function(obj){
		window.top.bootbox.dialog({
	        title: obj.title,
	        message: obj.message,
            className:"cjDialog",
	        buttons: {
	          success: {
	            label: "保存",
	            className: "btn-primary",
	            callback: function() {
					obj.callback1();	
	            }
	          },
	          danger1: {
	            label: "不保存",
	            className: "btn-primary",
	            callback: function() {
	            	obj.callback2();
	            }
	          },
	          danger: {
	            label: "取消",
	            className: "btn-default",
	            callback: function() {
	            	//obj.callback3();
	            }
	          },
	        }
	    });
	},
	//插件的提示框
	alert:function(text,shi){
		var dtd=$.Deferred();
        window.top.bootbox.dialog({
	            message: text,
	            title: "提示",
	            className:"cjDialog"
	        });
    	var $alert=window.top.$(".cjDialog");
        var cancel=setTimeout(function(){
        	 if(shi || shi == null || typeof(shi)=="undefined"){
        		 window.top.$(".newclose").click();
        	 }
        	 dtd.resolve();
		},2000);
		$alert.on("hidden.bs.modal",function(e){
			clearTimeout(cancel);
			dtd.resolve();
		});
		return dtd;
	},
	//非插件的提示框
	alertInfo:function(text){
		var dtd=$.Deferred();
        window.top.bootbox.dialog({
	            message: text,
	            title: "提示",
	            className:"alertInfo"
	        });
        
        var $alert=window.top.$(".alertInfo");
        var cancel=setTimeout(function(){
			window.top.$(".alertInfo").find(".newclose").click();
			dtd.resolve();
		},2000);
		$alert.on("hidden.bs.modal",function(e){
			clearTimeout(cancel);
			dtd.resolve();
		});
		return dtd;
	},
	newdialog:function(obj){
		var id = obj.id;
		var width = obj.width+"px";
		var height = obj.height+"px";
		var title = obj.title;
		var header = obj.header;
		var style = obj.style;
		var url = obj.url;
		$(window.top.document.body).find(".modal").remove();
		var html="";
		var styleHtml="";
		if(!header){
			html = "style='display:none'";
		}
		if(style!=null||typeof(style)!="undefined"){
			for(key in style){
				styleHtml+=";"+key+":"+style[key]
			}
		};
		$(window.top.document.body).append(
			'<div class="modal fade in newmodal" id="'+obj.id+'" tabindex="-1" aria-hidden="true">'+
			'    <div class="modal-dialog" style="width:'+width+'">'+
			'        <div class="modal-content">'+
			'            <div class="modal-header"'+html+'>'+
			'                <div class="newclose" data-dismiss="modal" aria-hidden="true"><i class="fa fa-times"></i></div>'+
			'                <h4 class="modal-title">'+title+'</h4>'+
			'            </div>'+
			'            <div class="modal-body" style="height:'+height+styleHtml+'">'+
			'				<iframe src="'+url+'" style="width:100%;height:100%;" frameborder="0" marginheight="0px" marginwidth="0px"  height="100%" width="100%"></iframe>'+
			'            </div>'+
			'        </div>'+
			'    </div>'+
			'</div>'
		);
		window.top.show(obj.id);
	},
	newdialogClose:function(obj){
		window.top.hide(obj);
	}
}

function GetRootPath(){
	var pathName = window.location.pathname.substring(1);
	var webName = pathName == '' ? '' : pathName.substring(0, pathName.lastIndexOf('/'));
	//rootPath = webName == '' ? '/' : '/' + webName;
	rootPath = "/"+webName.split('/')[0]+"/"+webName.split('/')[1];
	return rootPath;
}

var rootPath = GetRootPath();

//这个方法是解决浏览器中input框焦点丢失问题的调用方法
//建议是在所有的input框中都调用这个方法
function fixForcesBug(){
	try{
	  var obj = document.getElementById("fixfocusbug");
	  obj.ResetFocus();
	}catch(e){
		console.log("error:ResetFocus");
	}
}
