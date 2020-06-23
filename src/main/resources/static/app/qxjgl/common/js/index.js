var url1 = { url: rootPath + '/common/data/menu.json', dataType: 'text' };
var url2 = { url: rootPath + '/common/data/decideright.json', dataType: 'text' };
var url3 = { url: '/leave/apply/acquireLoginPersonRole', dataType: 'text' }; //判断是否管理员
var url4 = { url: '/leave/apply/bubbleCountStatistics', dataType: 'text' }; //气泡显示
var url5 = { url: '/app/qxjgl/leaveOrBack/getQXJcount', dataType: 'text' }; //查询已完成但未销假条数接口
var dataName = [];
var dataitem = [];
var isAdmin = false;
window.top.roleType = '';
var pageIndex = getUrlParam("pageIndex");
var pageModule = function() {
    var initIsAdmin = function() {
        $ajax({
            url: url3,
            success: function(data) {
                roleType = data.loginPersonRole;
                if (data.loginPersonRole == 6 || data.loginPersonRole == 5 || data.loginPersonRole == 4 || data.loginPersonRole == 3) {
                    isAdmin = true
                }
                initmenu();
            }
        });

    }
    var initshouquan = function() {
        $ajax({
            url: url2,
            success: function(data) {
                for (var i in data) {
                    if (data.hasOwnProperty(i) && typeof data[i] != "function") {
                        dataName.push(i);
                    }
                }
                initIsAdmin()
            }
        });
    }
    var initmenu = function() {
        if (dataName.length == 0) {
            $("#iframe1").attr("src", "error.html")
        } else {
            $ajax({
                url: url1,
                success: function(item) {
                    $.each(item, function(i) {
                        for (var j = 0; j < dataName.length; j++) {
                            if (item[i].id == dataName[j]) {
                                if (isAdmin) {
                                    dataitem.push(item[i])
                                } else if (!item[i].isAdmin) {
                                    dataitem.push(item[i])
                                }
                            }
                        }
                    });
                    leftfn(dataitem, $("#menulist"), 1);
                }
            });
        }
    }
    var leftfn = function(data, ho, n) {
        var default_open_index = pageIndex || 0;
        /*		$.each(data,function(i){
        			var obj = data[i];
        			var child = obj.child;
        			var arrow = '';
        			var submenu = '';
        			if($.trim(obj.href)==""){
        				obj.href="javascript:;";
        			}
        			if(typeof(child)!="undefined"&&null!=child&&""!=child&&child.length>0){
        				arrow = '<span class="arrow"></span>';
        				submenu = '<ul class="sub-menu ul_'+n+"_"+i+'"></ul>';
        			}
        			ho.append(
        				'<li class="'+((n==1&&i==default_open_index)?"start open":"")+'" id='+obj.id+'>'+
        				'	<a href="'+obj.href+'" target="iframe1">'+
        				'		<span class="title">'+obj.name+'</span>'+
        				'		'+arrow+
        				'	</a>'+
        				'	'+submenu+
        				'</li>'			
        			)
        			if(typeof(child)!="undefined"&&null!=child&&""!=child&&child.length>0){
        				leftfn(child,$('.ul_'+n+"_"+i),n+1);
        			}
        			$("#iframe1").attr({"src":data[default_open_index].href})
        		});
        		
        		$("#left a").each(function(){
        			$(this).click(function(){
        				$("#left li").removeClass("open");
        				$(this).parent().addClass("open");
        			})
        		})*/
        var lis = '';
        if (data.length == 0) {
            window.location.href = "error.html";
        } else {
            $.each(data, function(i, item) {
                if (i == 0) {
                    $("#iframe1").attr("src", item.href + "?menuId=" + item.id);
                }

                lis += '<li id="' + item.id + '"><a href="' + item.href + '" target="iframe1">'+item.name+'<i id="'+item.id+'_num" style="display:none" ></i></a></li>';
            });

            $('#menulist').html(lis); //追加到页面
            $(".menuli li").eq(0).addClass("active");
            $(".menuli li").click(function() {
                $(this).siblings().removeClass("active");
                $(this).addClass("active");
            });
            bubbleCountStatistics();
            getQXJcount();
        }
    }

    return {
        //加载页面处理程序
        initControl: function() {
            initshouquan();
        }
    };
}();
var show = function(obj) {
    $("#" + obj).modal("show");
}
var hide = function(obj) {
    $("#" + obj).modal("hide");
}

//气泡
function bubbleCountStatistics(){
    $ajax({
        url:url4,
        success:function(data){
            // console.log(data.qxjsq)
            if(data.qxjsq != 0){
	            $('#QXJ_num').show()
	            $("#QXJ_num").text(data.qxjsq);
            }else{
            	  $('#QXJ_num').hide()
            	  $("#QXJ_num").text(0);
            }
            if(data.qxjsp != 0){
	            $("#CSLDSP_num").show();
	            $("#CSLDSP_num").text(data.qxjsp);
            }else{
            	 $('#CSLDSP_num').hide()
            	 $("#CSLDSP_num").text(0);
            }
        }
    });
}
//未销假条数接口
function getQXJcount(){
    $ajax({
        url:url5,
        success:function(data){
            if(data.count!=0){
            	$("#QXJCX_num").show()
                $("#QXJCX_num").text(data.count);
            }else{
            	$("#QXJCX_num").hide()
                $("#QXJCX_num").text(0);
            }
            
        }
    });
}
var openLoading = function() {
	$("#qjDialog").removeClass("none");
}

var closeLoading = function() {
	$("#qjDialog").addClass("none");
}