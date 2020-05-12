var id = getUrlParam('id');//主文件id
var filefrom1 = getUrlParam('filefrom')||'';//菜单标识
var fileFrom = filefrom1?filefrom1:(getUrlParam('fileFrom')||'');//菜单标识
var authorizeStartDate = "";//授权使用
var deleteMark = "";//请假人的ID
var isDealUser = "0";//1:说明当前登录人为当前处理人
var showTab = getUrlParam('showTab')||'2'//选中选项卡用
var fromMsg= getUrlParam("fromMsg");//是否来自消息
var isView = '/leave/apply/isView'; //更新是否已读状态
var url_isShow_Btn= "/leave/apply/detailButtonIsShow";//显示按钮判断
var url_delete_info = '/app/qxjgl/application/deleteInfo'; //删除主文件
var getCurrentDealUserUrl = "/leave/apply/getCurrentDealUser" //获取当前处理人
var saveTempOpinionUrl = {"url":"/app/qxjgl/application/saveTempOpinion","dataType":"text"} //提交草稿保存
var sendOrFinshApprove = {"url":"/leave/apply/sendOrFinshApprove","dataType":"text"}; //审批或者审批完成
var url_getLeaveInfo="/app/qxjgl/application/getLeaveInfo";//获取请假详情
var url_getAllPublicOpinion='/app/qxjgl/application/getAllPublicOpinion';//获取意见
var url_getCurrentOpinion = 'app/qxjgl/application/getCurrentOpinion'; //单个获取意见地址（意见编辑）
/*------------文件相关的----------------*/
var uploadFileUrl= "/app/qxjgl/documentfile/uploadFile?leaveId="+id;//上传附件
var url_file_downFormatLoad= "/app/qxjgl/documentfile/getDownloadFile";//下载
var url_file_list= rootPath +"/documentfile/fileList";//详情页右侧文件列表
var url_tab_info= rootPath +"/documentfile/tabList";//详情页文件页签列表
var url_file_info= rootPath +"/documentfile/getTabFile"//获取单个选中页签的文件
var url_file_up= rootPath +"/documentfile/upSort"//文件上移
var url_file_down= rootPath +"/documentfile/downSort"//文件下移
var url_file_detele= rootPath +"/documentfile/deleteById";//删除附件
var url_isOrNotFormatFile = '/app/qxjgl/documentfile/isOrNotFormatFile'; //编辑附件时判断是否有流式文件
var url_get_stream_file = '/app/qxjgl/documentfile/getStreamFileUrl'; //最新的流式url(附件编辑使用)
var SavePenUrl = '/app/qxjgl/documentset/save';  //保存笔的路径
var getPenUrl = '/app/qxjgl/documentset/findPenByUserId';   //公文笔查询
var c3 = {};
var receiverIsMe = getUrlParam('receiverIsMe');     //与上下篇的显示有关
var flowType = getUrlParam('flowType');
var getNextPageUrl = "/app/qxjgl/application/getNextPage?id="+id;
$(window).resize(function(){
    clearTimeout(c3);
    c3 = setTimeout(function(){
        //版式文件加载
        ocx.performClick('vzmode_fitwidth');//适合宽度
    },500)
});
if(id){
    $('#id').val(id)
}
Vue.component('file-message',fileMessage);
Vue.component('opinion-message',opinionMessage);
var v_edit = new Vue({
    el:"#v_edit",
    filters: {
        timeFilter(time) {
            return time.substring(0,10)
        }
    },
    data(){
        return {
            activeTab:showTab=='1'?'file':'opinion',
            FileList:[{
                fileTitle:'文件',
                child:[]
            }],
            activeFile:{},
            reload:true,
            opinionArr:[],
            writeType:'pointer',
            menuArr:[],
            showWrite:false,
            opinionPicture:'',
            opinionContent:'',
            opinionType:'0',
            editShow:false,
            penNum:'0.5',
            showSetPen:false,
            defaultPenWidth:'signpen_05mm',
            showOpinion:true,
            showDownLoadTab:false,
            penIndex:1,
            fileFrom:fileFrom,
            next:false,
            prev:false,
            prevId:'',
            nextId:'',
            flowType:'',    //flowType、status   用于判断版式文件、意见是否进行保存
            status:''
        }
    },
    created(){
    	this.getDealUser();
        this.isShowBtn();
        this.getFileList();
        this.getMenu()
        // this.viewFile();
        this.getOpinion();
        this.isView();
        if(this.writeType=='pointer'&&this.activeTab=='opinion'){
            this.initWrite()
        }
        this.getLeaveInfo();
        this.initUserInfo();
        this.initmemory();
    },
    computed:{

    },
    methods:{
        //显示按钮判断
        isShowBtn(){
            vm = this;
            request({
                url: url_isShow_Btn,
                method: 'get',
                params: {id:id}
            }).then(function (res){
                var sendShow = res.sendShow;
                var editShow = res.editShow;
                var sendBgtShow = res.sendBgtShow;
                var sendAgainShow = res.sendAgainShow;
                var finishShow  = res.finishShow;
                var returnShow  = res.returnShow;
                var opinionShow = res.opinionShow;
                var xjapply = res.xjapply;
                vm.editShow = editShow=='1' ?true:false
                if (opinionShow == '1') {
                    vm.showWrite = true;
                    vm.showOpinion=true
                    vm.initWrite();
                }else {
                    vm.showWrite = false;
                    vm.initWrite();
                }
                if (sendShow == "1") {
                    $("#songshen").show();
                }
                if (editShow == "1") {
                    $("#bianji").show();
                }
                if (sendBgtShow == "1") {
                    $("#cengsongbgt").show();
                }
                if (sendAgainShow == "1") {
                    $("#jixusp").show();
                }
                if (finishShow == "1") {
                    $("#spfinish").show();
                }
                if (returnShow == "1") {
                    $("#tuihui").show();
                }
                if (xjapply === "1" && filefrom1 === 'qxjsq') {
                    $("#xjapply").show();
                }
            })
        },
        getDealUser(){
            vm = this
            request({
                url: getCurrentDealUserUrl,
                method: 'get',
                params: {id:id}
            }).then(function (res){
            	if(res.isDealUser=='1'){
            		isDealUser="1";
            	}
            })
        },
        isView(){
            vm = this
            request({
                url: isView,
                method: 'get',
                params: {id:id}
            }).then(function (res){
                //点击详及时情刷新气泡
                window.top.bubbleCountStatistics();
            })
        },
        initPen(){
        	$('#btnGroups').show()
        	vm = this
			//读取版式文件保存的签字笔
			if(vm.penIndex!=null && typeof(vm.penIndex)!="undefined"&& vm.penIndex!=""){
	    		if(vm.penIndex==7){
	    			ocx.performClick('t_tablet/signpen_05mm');
	    		}else if(vm.penIndex==8){
	    			ocx.performClick('t_tablet/signpen_1mm');
	    		}else if(vm.penIndex==9){
	    			ocx.performClick('t_tablet/softpen_1mm');
	    		}else if(vm.penIndex==10){
	    			ocx.performClick('t_tablet/softpen_3mm');
	    		}
	    	};
			ocx.setConfigInfo("tablet.fullsign.usercolor","#000000");
			ocx.registListener("f_open","openPerformed",true);
			ocx.registListener("f_saveurl","savePerformed",true);
			if($(".spybIcon2_1").hasClass("active")){
                 $(".spybIcon2_1").addClass("active");
                 $(".spybIcon_"+vm.penIndex).click();
            }else{
                ocx.performClick('t_handtool');//阅读模式
                $(".spybIcon2").removeClass("active");

            }
    		$(".spybIcon i").click(function(){
    			$(this).parents("ul").find("i").removeClass("active");
    			$(this).addClass("active");
    		})
    		//原始大小
    		$(".spybIcon_1").click(function(){
    			ocx.performClick('vzmode_original');
    		})
    		//适合页面
    		$(".spybIcon_2").click(function(){
    			ocx.performClick('vzmode_fitpage');
    		})
    		//适合宽度
    		$(".spybIcon_3").click(function(){
    			ocx.performClick('vzmode_fitwidth');//适合宽度
    		})
    		//适合高度
    		$(".spybIcon_4").click(function(){
    			ocx.performClick('vzmode_fitheight');//适合高度
    		})
    			
    		$(".spybIcon2_1").unbind("click").click(function(){//0201
    			if($(this).hasClass("active")){
    				ocx.performClick('t_handtool');//阅读模式
    				$(".spybIcon2").removeClass("active");
    			}else{
    				$(this).addClass("active");
    				$(".spybIcon_"+vm.penIndex).click();
    			}
    		});
    		
    		//手写签批细
    		$(".spybIcon_7").click(function(){
    			$(".spybIcon2").removeClass("active");
    			$(".spybIcon2_1").addClass("active");
    			$(this).parent().addClass("active");
    			ocx.performClick('t_tablet/signpen_05mm');
    			ocx.setConfigInfo("tablet.fullsign.usercolor","#000000");
    			vm.penIndex = 7;
    			vm.savePen(vm.penIndex);
    		})
    		$(".spybIcon_8").click(function(){
    			$(".spybIcon2").removeClass("active");
    			$(".spybIcon2_1").addClass("active");
    			$(this).parent().addClass("active");
    			ocx.performClick('t_tablet/signpen_1mm');
    			ocx.setConfigInfo("tablet.fullsign.usercolor","#000000");
    			vm.penIndex = 8;
    			vm.savePen(vm.penIndex);
    		})
    		$(".spybIcon_9").click(function(){
    			$(".spybIcon2").removeClass("active");
    			$(".spybIcon2_1").addClass("active");
    			$(this).parent().addClass("active");
    			ocx.performClick('t_tablet/softpen_1mm');
    			ocx.setConfigInfo("tablet.fullsign.usercolor","#000000");
    			vm.penIndex = 9;
    			vm.savePen(vm.penIndex);
    		})
    		$(".spybIcon_10").click(function(){
    			$(".spybIcon2").removeClass("active");
    			$(".spybIcon2_1").addClass("active");
    			$(this).parent().addClass("active");
    			ocx.performClick('t_tablet/softpen_3mm');
    			ocx.setConfigInfo("tablet.fullsign.usercolor","#000000");
    			vm.penIndex = 10;
    			vm.savePen(vm.penIndex);
    		})
        },
        back(){
            opinionSaveServlet(function(){	
                if(fileFrom=='qxjsp'){
                    location.href="/app/qxjgl/qxj/html/CZSP_table.html"
                }else{
                	history.back()
//                    location.href="/app/qxjgl/qxj/html/table.html"
                }
            	
            })
        },
//        back(){
//            opinionSaveServlet(function(){
//                if(fileFrom=='qxjsp'){
//                    location.href="/app/qxjgl/qxj/html/CZSP_table.html"
//                }else{
//                    location.href="/app/qxjgl/qxj/html/table.html"
//                }
//            })
//        },
        editOpinion(item){
            vm = this
            request({
                url: url_getCurrentOpinion,
                method: 'get',
                params: {id:item.id}
            }).then(function (res){
                vm.opinionType = res.opinionType
                if(res.opinionType=='1'){
                    vm.writeType = 'pointer'
                    openPng(res.opinion)
                }else{
                    vm.writeType = 'text'
                    vm.opinionContent=res.opinion
                }

            })
        },
        selectTab(tab){
            this.activeTab = tab;
            if(tab=='opinion'&&this.writeType=='pointer'){
                this.initWrite()
            }
//            if(tab=='info'){
//            	this.getLeaveInfo()
//            }
        },
        selectFile(file){
            vm= this
            opinionSaveServlet(function(){
                vm.activeFile = file;
                vm.viewFile()
            })
        },
        // 获取文件列表
        getFileList(type){
            vm = this
            request({
                url: url_file_list,
                method: 'get',
                params: {id:id}
            }).then(function (res){
                vm.FileList = res;
                if(!type){
                    if(res[0].child.length>0){
                        vm.activeFile = res[0].child[0]
                        vm.viewFile();
                    }else if(res[1].child.length>0){
                        vm.activeFile = res[1].child[0]
                        vm.viewFile();
                    }else{
                    	if(fileFrom=='qxjsp'){
                    		openOFDFile(null, "PLUGIN_INIT_OFDDIV",$("#embedwrap").width(),$("#embedwrap").height(), "yes");
                    		if(isDealUser =='1'){
                    			vm.initPen()
                    		}
                    	}else{
                    		openOFDFile(null, "PLUGIN_INIT_OFDDIV",$("#embedwrap").width(),$("#embedwrap").height(), "");
                    	}
                    }
                }else{
                	vm.viewFile()
                }
                vm.$nextTick(()=>{
                    vm.reload = false;
                });
            })
        },
        // 顶部页签
        getMenu(){
            request({
                url: url_tab_info,
                method: 'get',
                params: {id:id}
            }).then(function (res){
                if(res){
                    vm.menuArr = res
                    vm.$nextTick(()=>{
                        countWidth()
                    });
                }
            })
        },
        // 获取单个文件
        viewFile(){
            vm = this
            request({
                url: url_file_info,
                method: 'get',
                params: {id:vm.activeFile.id},
            }).then(function (res) {
            	if(fileFrom=='qxjsp'){
            		openOFDFile(res.downFormatIdUrl, "PLUGIN_INIT_OFDDIV",$("#embedwrap").width(),$("#embedwrap").height(), "yes");
            		if(isDealUser =='1'){
            			vm.initPen()
            		}
            	}else{
            		openOFDFile(res.downFormatIdUrl, "PLUGIN_INIT_OFDDIV",$("#embedwrap").width(),$("#embedwrap").height(), "");
            	}
               
            })
            vm.reload = true;
            vm.$nextTick(()=>{
                vm.reload = false;
            });
        },
        //手写签批保存
        saveWrite(type){
            vm = this;
            if(type=='test'){
                //临时意见
                if(this.writeType=='pointer'){
                    if(checkIsModified()){
                        vm.opinionType="1"; //图片
                        $.ajax({
                            url : "/app/base/user/getToken",
                            type : "GET",
                            async : false,
                            success : function(data) {//插件读取文件
                                end();	//签批确定
                                var surl = location.protocol+ "//"+ location.host+ "/app/qxjgl/saveCommentPicture?access_token="+data.result;
                                document.getElementById("signtool").SetUploadURL(surl);
                                var result = document.getElementById("signtool").UploadImageStream();
                                if(result && result!="" && result!=null){
                                    imgFileId = result.replace(/^\"|\"$/g,'');
                                    vm.opinionPicture = imgFileId;
                                    $ajax({
                                        url:saveTempOpinionUrl,
                                        async:false,
                                        data:{id:id,opinionContent:vm.opinionPicture,tempType:"0",opinionType:vm.opinionType},
                                        success:function(){
                                            tablet()
                                            vm.getOpinion()
                                            return true
                                        }
                                    })
                                }else{
                                    vm.opinionType="0"; //文字\
                                    return true
                                }
                            }
                        })
                    }
                }else{
                    $ajax({
                        url:saveTempOpinionUrl,
                        data:{id:id,opinionContent:vm.opinionContent,tempType:"0",opinionType:vm.opinionType},
                        async : false,
                        success:function(data){
                            if(data.result="success"){
                                vm.getOpinion()
                                return true
                            }else{
                                return true
                            }
                        }
                    })
                }

            }else{
                // 实际意见
                if(this.writeType=='pointer'){
                    if(vm.opinionPicture){
                        return true
                    }
                    if(checkIsModified()){
                        vm.opinionType="1"; //图片
                        $.ajax({
                            url : "/app/base/user/getToken",
                            type : "GET",
                            async : false,
                            success : function(data) {//插件读取文件
                                end();	//签批确定
                                var surl = location.protocol+ "//"+ location.host+ "/app/qxjgl/saveCommentPicture?access_token="+data.result;
                                document.getElementById("signtool").SetUploadURL(surl);
                                var result = document.getElementById("signtool").UploadImageStream();
                                if(result && result!="" && result!=null){
                                	tablet()
                                    imgFileId = result.replace(/^\"|\"$/g,'');
                                    vm.opinionPicture = imgFileId;
                                    return true
                                }else{
                                    vm.opinionType="0"; //文字\
                                    return true
                                }
                            }
                        })
                    }
                }else{
                    vm.opinionType="0"; //文字\
                    return true
                }
            }
        },
        download(formatType){
            vm = this
            this.showDownLoadTab = false;
            request({
                url: url_file_downFormatLoad,
                method: 'get',
                params: {id:vm.activeFile.id,formatType:formatType}
            }).then(function (res) {
            	if(res.result=='success'){
                    location.href = url_file_downFormatLoad+'?flag=1&id='+vm.activeFile.id+'&formatType='+formatType
            	}else{
            		newbootbox.alert(res.msg)
            	}
            })
        },
        sendSH(){
            var type = this.saveWrite();
            vm = this;
            console.log(fileFrom)
            opinionSaveServlet(function(){
            	authorizeStartDate = authorizeStartDate + " 00:00";
                newbootbox.newdialog({
                    id:"sshDialog",
                    width:800,
                    height:600,
                    header:true,
                    title:"送审核",
                    classed:"cjDialog",
                    url:rootPath + "/qxj/html/ssh.html?id="+id+'&opinionContent='+(vm.opinionType=="0"?vm.opinionContent:vm.opinionPicture)+
                    '&opinionType='+vm.opinionType+'&fileFrom='+fileFrom+'&fromMsg='+fromMsg+'&startDate='+authorizeStartDate+"&deleteMark="+deleteMark+'&receiverIsMe='+receiverIsMe+"&flowType="+flowType
                })
            })
        },
        sendSH2(){
        	authorizeStartDate = authorizeStartDate + " 00:00";
            newbootbox.newdialog({
                id:"authorizeDialog",
                width:800,
                height:600,
                header:true,
                title:"公文授权",
                classed:"cjDialog",
                url:rootPath + "/qxj/html/authorize.html?startDate="+authorizeStartDate+"&deleteMark="+deleteMark+"&fileFrom="+fileFrom+'&fromMsg='+fromMsg
            })
        },
        spSucess(){
            var name = this.saveWrite();
            vm = this
            opinionSaveServlet(function(){
            	$.ajax({
            		url:sendOrFinshApprove.url,
            		data:{id:id,operateFlag:"01",approveContent:(vm.opinionType=="0"?vm.opinionContent:vm.opinionPicture),opinionType:vm.opinionType},
            		type: "POST",
            		async:false,
            		success:function(data){
            			if (data.result == 'success') {
            				newbootbox.alert('审批完成！').done(function(){
                                changToNum2(function(){
                                    if (fromMsg == '1') {
                                        windowClose();
                                    } else {
                                        window.top.bubbleCountStatistics()
    //                                    location.reload();
                                        if(fileFrom=='qxjsp'){
                                            //window.top.iframe1.location = '/app/qxjgl/qxj/html/CZSP_table.html'
                                            window.top.iframe1.location = '/app/qxjgl/qxj/html/qxjView.html?id='+id+'&fileFrom='+fileFrom+'&receiverIsMe='+receiverIsMe+"&flowType="+flowType;
                                        }else{
                                            window.top.iframe1.location = '/app/qxjgl/qxj/html/table.html'
                                        }
                                    }
                                })
            				});
            				//changToNum();
            			} else {
            				newbootbox.alert('审批失败！').done(function () {
                                window.top.bubbleCountStatistics();
            					location.reload()
            				});
            			}
            		}
            	});
            })
        },
        thxg:function(){
            var name = this.saveWrite()
            opinionSaveServlet(function(){
            	newbootbox.newdialog({
            		id:"thxgDialog",
            		width:800,
            		height:600,
            		header:true,
            		title:"退回",
            		classed:"cjDialog",
            		url:rootPath + "/qxj/html/thxg.html?id="+id+"&opinionContent="+(vm.opinionType=="0"?vm.opinionContent:vm.opinionPicture)+"&opinionType="+vm.opinionType+'&fromMsg='+fromMsg+"&fileFrom="+fileFrom+"&receiverIsMe="+receiverIsMe+"&flowType="+flowType
            	})
            })
        },
        sendBGTFlow (){
            var name = this.saveWrite()
            opinionSaveServlet(function(){
            	newbootbox.newdialog({
            		id:"csbgtDialog",
            		width:600,
            		height:400,
            		header:true,
            		title:"呈送办公厅",
            		classed:"cjDialog",
            		url:rootPath + "/qxj/html/csbgt.html?id="+id+"&opinionContent="+(vm.opinionType=="0"?vm.opinionContent:vm.opinionPicture)+"&opinionType="+vm.opinionType+'&fromMsg='+fromMsg+"&fileFrom="+fileFrom+"&receiverIsMe="+receiverIsMe+"&flowType="+flowType
            	})
            })
        },
        xjapply(){
            newbootbox.newdialog({
                id: "xjsqadd",
                width: 580,
                height: 400,
                header: true,
                title: "销假申请",
                classed:"cjDialog",
                url: rootPath + "/qxj/html/xj_add.html?id=" + id+"&flag="+1+'&fromMsg='+fromMsg+"&fileFrom="+fileFrom
            });
        },
        getOpinion(){
            vm = this
            request({
                url: url_getAllPublicOpinion,
                method: 'get',
                params: {id:id}
            }).then(function (res) {
                if(res){
                    vm.opinionArr = res
                    $(vm.opinionArr).each(function(i,data){
                        if(data.tempType=='0'){
                            vm.opinionType=data.opinionType
                            if(data.opinionType=='1'){
                                vm.opinionPicture = data.opinion
                            }else{
                                vm.opinionContent = data.opinion
                            }
                        }
                    })
                    vm.showOpinion=true
                }else{
                    vm.showOpinion=false
                }
                $(".slimScrollDiv,.scroller").each(function(){
                    $(this).css({"height":"100%"});
                });
            })
        },
        getLeaveInfo(){
        	 vm = this
             request({
                 url: url_getLeaveInfo,
                 method: 'get',
                 params: {id:id,receiverIsMe:receiverIsMe,flowType:flowType}
             }).then(function (res) {
                 res.applicationDate = res.applicationDate.substring(0,10)
                 res.planTimeStart = res.planTimeStart.substring(0,10)
                 res.planTimeEnd = res.planTimeEnd.substring(0,10)
                 if (res.actualTimeStart != null && res.actualTimeEnd != null) {
                     res.actualTimeStart = res.actualTimeStart.substring(0, 10);
                     res.actualTimeEnd = res.actualTimeEnd.substring(0, 10);
                 } else {
                     res.actualVocationDate = null;
                 }
                 authorizeStartDate = res.planTimeStart.substring(0,16);
                 res.start_endTime = res.planTimeStart+' 至 '+ res.planTimeEnd
                 deleteMark = res.deleteMark;
            	 setformdata(res);
            	 vm.status = res.status;
            	 vm.flowType = res.flowType;
            	 receiverIsMe = res.receiverIsMe;
            	 flowType = res.flowType;
                /* //有无上一页
                 if(res.preId == "noPredId" || res.preId == "" ){
                     vm.prev  = true;
                 } else {
                     vm.prev  = false;
                     vm.prevId = res.preId;
                 }
                 //有无下一页
                 if (res.sufId == "noSufId" || res.sufId =="" ){
                     vm.next  = true;
                 } else {
                     vm.next  = false;
                     vm.nextId = res.sufId;
                 }*/
             })
        },
        editInfo(){
            newbootbox.newdialog({
                id:"qjEdit",
            	width:980,
				height:600,
                header:true,
                title:"请假编辑",
                classed:"cjDialog",
                url:rootPath + "/qxj/html/qj_edit.html?id="+id+'&fromMsg='+fromMsg+"&fileFrom="+fileFrom+"&leaverIds="+deleteMark
            })
        },
        editFile(){
            var ios = navigator.platform;
            vm = this;
            $.ajax({
                url: url_isOrNotFormatFile,
                data: {id: vm.activeFile.id},
                type: "POST",
                success: function (data) {
                    if(data.hasStreamId=="" || data.hasStreamId==null || data.hasStreamId=="undefined" || data.hasStreamId==undefined){
                        newbootbox.alert('原始文件没有对应流式文件，不能编辑！');
                        return ;
                    }else{
                        if(ocx && !!ocx){
                            ocx.performClick('t_handtool');//阅读模式
                        }
                        console.log("======="+data.isEdit)
                        if (data.isEdit == '1') {
                            newbootbox.oconfirm({
                                title: "提示",
                                message: "该文件当前版本已加载签批内容，如进入编辑则签批签字被清除，是否确认进入？",
                                callback1: function () {
                                    if (ios == "Win32" || ios == "Windows") {
                                        window.open("/app/qxjgl/qxj/html/newedit.html");
                                    } else {
                                        runPluginByParam();
                                    }
                                }
                            });
                        } else {
                        	 if (ios == "Win32" || ios == "Windows") {
                                 window.open("/app/qxjgl/qxj/html/newedit.html");
                             } else {
                                 runPluginByParam();
                             }
                        }
                    }
                }
            })
        },
        clearWrite(){
            if(this.writeType=='pointer'){
                clearsign();
            }else{
                $("#opinionContent").val("");
            }
        },
        setPen(num){
            this.penNum =num
            this.showSetPen = false
            if(this.penNum == "0.5"){
                this.defaultPenWidth="signpen_05mm";
            }else if(this.penNum == "1"){
                this.defaultPenWidth="signpen_1mm";
            }else if(this.penNum == "2"){
                this.defaultPenWidth="softpen_2mm";
            }else if(this.penNum == "3"){
                this.defaultPenWidth="softpen_3mm";
            }else{
                this.defaultPenWidth="signpen_05mm";
            }
            document.getElementById("signtool").SetPenWidth(this.defaultPenWidth);
            var penwidth = document.getElementById("signtool").GetPenWidth();
        },
        setpenchoose(penNum){
        	this.showSetPen = false;
        	var penText = "0.5";
        	var defaultPenWidth = "signpen_05mm"
            if(penNum == "0.5"){
            	penText = "0.5";
                this.defaultPenWidth="signpen_05mm";
            }else if(penNum == "1"){
            	penText = "1";
                defaultPenWidth="signpen_1mm";
            }else if(penNum == "2"){
            	penText = "2";
                defaultPenWidth="softpen_2mm";
            }else if(penNum == "3"){
            	penText = "3";
                defaultPenWidth="softpen_3mm";
            }else{
            	penText = "0.5";
                defaultPenWidth="signpen_05mm";
            }
			$("#penNum").text(penText);
			document.getElementById("signtool").SetPenWidth(defaultPenWidth);
			$.ajax({
            	url:'/app/qxjgl/documentinputtemplateset/save',
            	data:{penWidth:defaultPenWidth,tempIndex:"2"},
            	type: "GET",
            	success:function(data){
            	}
            });
		},
        initWrite(){
            vm=this
            this.$nextTick(function(){
                if(vm.writeType=='pointer'&&vm.showWrite){
                    tablet();
                    document.getElementById("signtool").SetPenColor("#000");//设置笔的颜色
                }
            },2000)
        },
        //左移
        moveLeft:function(){
            var left=parseInt($("#tabcont").css("left"));
            var LiWidth=parseInt($("#tabcont li").width())+2;
            var MoveWidth=left;
            if(left==0){
                return ;
            }else if(left<0&&left>(-LiWidth)){
                MoveWidth+=Math.abs(left);
            }else{
                MoveWidth+=LiWidth;
            };
            $("#tabcont").animate({left:MoveWidth+"px"},300);
        },
        //右移
        moveRight:function(event){
            var parentWidth = $("#tabcont1").width();
            var Width = $("#tabcont").width();
            var left=parseInt($("#tabcont").css("left"));
            var LiWidth=parseInt($("#tabcont li").width())+2;
            var MoveWidth=left;
            if(Width-parentWidth-Math.abs(left)>=LiWidth){
                MoveWidth-=LiWidth;
            }else if((Width-parentWidth-Math.abs(left)>0)&&(Width-parentWidth-Math.abs(left)<LiWidth)){
                MoveWidth-=(Width-parentWidth-Math.abs(left));
            }else{
                MoveWidth=left;
            }
            $("#tabcont").animate({left:MoveWidth+"px"},300)
        },
        //上一页、下一页的跳转
        pageFn:function(data){
            var that = this;
            if (data == 'prev') {

                if (this.prev) {
                    newbootbox.alert('已是第一条！');
                    return ;
                }
            } else {

                if(this.next){
                    newbootbox.alert("已是最后一条！");
                    return;
                }

            }
            $.ajax({
                url:getNextPageUrl,
                type: "GET",
                async:false,
                success:function(data){
                    that.prevId = data.preId;
                    that.nextId = data.sufId;
                }
            })
            //status == 30 或者 status == 10且flowType == 13 不进行意见的保存
            console.log("上下篇意见的保存"+this.flowType,this.status);
            if(this.status == 30 || (this.status == 10 && this.flowType == 13)) {
                var id = data == 'prev'?this.prevId:this.nextId;
                var url=rootPath + '/qxj/html/qxjView.html?id='+id+"&filefrom=qxjsp&receiverIsMe="+receiverIsMe+"&flowType="+flowType;
                window.top.iframe1.location.href = url;
            } else {
                //上下篇是进行临时意见的保存
                var name = this.saveWrite('test');
                opinionSaveServlet(function(){
                    var id = data == 'prev'?that.prevId:that.nextId;
                    var url=rootPath + '/qxj/html/qxjView.html?id='+id+"&filefrom=qxjsp&receiverIsMe="+receiverIsMe+"&flowType="+flowType
                    window.top.iframe1.location.href = url;
                })
            }
        },
         //意见初始化页面签批记忆功能
        initmemory:function(){
        	var that = this;
        	$.ajax({
        		url:"/app/qxjgl/documentinputtemplateset/info",
        		async : false,
        		success:function(data){
        			if(data.info.tempIndex == "2"){//插件
        				var defaultPenWidth = "signpen_05mm";
        				$("#penNum").text(0.5);
        				if(data.info.penWidth!= null && data.info.penWidth!="" &&　typeof(data.info.penWidth)!="undefined"){
        					if(data.info.penWidth == "signpen_05mm"){
        						$("#penNum").text(0.5);
        					}
        					if(data.info.penWidth == "signpen_1mm"){
        						$("#penNum").text(1);
        					}
        					if(data.info.penWidth == "softpen_2mm"){
        						$("#penNum").text(2);
        					}
        					if(data.info.penWidth == "softpen_3mm"){
        						$("#penNum").text(3);
        					}
        					defaultPenWidth = data.info.penWidth;
        				}
        				setTimeout(function(){
        					tablet();
        					document.getElementById("signtool").SetPenColor("#000");//设置笔的颜色
        					document.getElementById("signtool").SetPenWidth(defaultPenWidth);
        					var penwidth = document.getElementById("signtool").GetPenWidth();
        				},1000);

        				that.writeType ='pointer';
        				$(".css3").text("手写签批 :");
        				$(".css3").attr("data","0");
        			}else{
        				that.writeType ='text';
        				$(".css3").text("输入签批 :");
        				$(".css3").attr("data","1");
        			}
        		}
        	});
        },
        //对公文处签字笔的保存
        savePen:function(penIndex){
        	$.ajax({
        		url:SavePenUrl,
        		async:false,
        		data:{"pen":penIndex},
        	    success:function(data){
        	    	if(data.result=='success'){
        	    	}
        	    }
        	});

        },
        // 对公文处签字笔的查询
        initUserInfo:function(){
        	$.ajax({
        		url:getPenUrl,
        		async:false,
        	    success:function(data){
        	    	if(data.penIndex!=null && typeof(data.penIndex)!="undefined"&& data.penIndex!=""){
        	    		vm.penIndex = data.penIndex;
        	    	}
        	    }
        	});
        }
    },
    watch:{
        writeType:function(newVal,oldVal){
            var that = this;
            console.log(newVal);
            if(newVal=='pointer'){
                var penNum = $("#penNum").text();
                if($.trim(penNum) == "0.5"){
                    penNum="signpen_05mm";
                }else if($.trim(penNum) == "1"){
                    penNum="signpen_1mm";
                }else if($.trim(penNum) == "2"){
                    penNum="softpen_2mm";
                }
                else if($.trim(penNum) == "3"){
                    penNum="softpen_3mm";
                }else{
                    penNum="signpen_05mm";
                }
                $("#opinionContent").hide();
                $("#write").show();
                $(".commonView").hide();
                $(".setpen").show();
                document.getElementById("signtool").SetPenColor("#000");//设置笔的颜色
                $.ajax({
                    url:'/app/qxjgl/documentinputtemplateset/save',
                    data:{tempIndex:"2",penWidth:penNum},
                    type: "GET",
                    async:false,
                    success:function(data){
                         that.initmemory();
                    }
                });
                this.initWrite()
            }else {
               $.ajax({
                    url:'/app/qxjgl/documentinputtemplateset/save',
                    data:{tempIndex:"1",penWidth:''},
                    type: "GET",
                    async:false,
                    success:function(data){
                        that.initmemory();
                    }
                });
            }
        }
    }
})
function refreshFileList() {
   v_edit.getMenu();
   v_edit.getFileList(true)
}
function refresh(){
	v_edit.getMenu();
	v_edit.getFileList()
}
function pageModule(){
    function initmemory(){
    }
    function initother (){
        //签批---清屏
        $("#clear").click(function(){
            if($.trim($("span.css3").text()) =="手写签批 :"){
                clearsign();
                $("#write").hide();
                initmemory();
            }else{
                $("#opinionContent").val("");
            }
        });
    }
    return{
        //加载页面处理程序
        initControl:function(){
            initother();
        }
    }
}
$("#uploadForm").validate({
    submitHandler: function() {
    	  newbootbox.alert('正在上传中,请稍候...',true)
        var ajax_option ={
            type: "post",
            url:uploadFileUrl,//默认是form action
            //data:{},
            success:function(data){
                $("#dialogzz").hide();
                if(data.result == "success"){
                    newbootbox.alert('上传成功！').done(function(){
                        $(".fileinput-filename").text("");
                        $("#pdf").val("");
                        v_edit.getMenu()
                        v_edit.getFileList(true)
                    });
                }else{
                    newbootbox.alert("上传失败！");
                }
            }
        }
        $('#uploadForm').ajaxSubmit(ajax_option);
    }
});
$("#pdf").change(function(){
    $("#uploadForm").submit();
});

function countWidth(){
    var length1 = v_edit.menuArr.length;
    var totalWidth = ($(".newtabcont1").width()||920)-30-length1*2;
    var minCount = (totalWidth-totalWidth%300)/300;
    var maxCount = (totalWidth-totalWidth%200)/200;
    if(length1==0||length1<=minCount){
        $("#tabcont li").width(300);
        $(".arrow-left").hide();
        $(".arrow-right").hide();
        $("#newtabcont").css({"padding-left":"0","padding-right":"0"})
        $("#tabcont1").width("100%").css({"overflow":"auto"});
    }else if(length1>maxCount){
        $("#tabcont li").width(200);
        $(".arrow-left").show();
        $(".arrow-right").show();
        $("#newtabcont").css({"padding-left":"30px","padding-right":"30px","width":"98%"})
        $("#tabcont1").width("100%").css({"overflow":"hidden"});
    }else{
        $("#tabcont li").width(totalWidth/length1);
        $(".arrow-left").hide();
        $(".arrow-right").hide();
        $("#newtabcont").css({"padding-left":"0","padding-right":"0"})
        $("#tabcont1").width("100%").css({"overflow":"auto"});
    };
}
//判断当前空间中的文件是否编辑过
function isedit(){
    var editflag;
    if(ocx){
        editflag = ocx.isDocumentModified() || incrementalseal()!=0;
        return editflag;
    }else{
        return false;
    }
}
//正文和意见的保存
function opinionSaveServlet(callback1){
	if(isDealUser !='1'){
		callback1();
		return
	}
    var iseditFlag = isedit();
	console.log("***************"+iseditFlag)
    //是否编辑||是否是新增的文||版式文档中的签章是否有变化
    if (iseditFlag) {
        $.ajax({
            url : "/app/base/user/getToken",
            type : "GET",
            async : false,
            success : function(data) {//插件读取文件
                var urlSaveofd=location.protocol+"//"+location.host+"/app/qxjgl/servlet/ofd/saveOfdFile?access_token=" + data.result;
                urlSaveofd+="&fileName=" +v_edit.activeFile.documentTitle ;
                urlSaveofd+="&fileId="+v_edit.activeFile.id;
                savetoremote(urlSaveofd, function () {
                    if (callback1) {
                        callback1();
                        console.log('正常保存显示');
                    } else {
                        callback1();
                        console.log('正常保存显示');
                    }
                });
            }
        })
    }else{
        callback1();
    }
}

function runPluginByParam(){
    var streamdownUrl = "";
    var suffix = 'docx';
    $.ajax({
        url:url_get_stream_file,
        data:{id:v_edit.activeFile.id},
        async:false,
        type: "GET",
        success:function(data){
            streamdownUrl = data.url;
            if(data.type == 2){
                suffix = 'wps';
            }else{
                suffix=data.suffix;
            }
        }
    });
    var streamupurl="";
    var formatupurl="";
    var url=location.protocol+"//"+location.host+"/app/qxjgl/servlet/streamFile/save?fileId="+ v_edit.activeFile.id
    //生成流式和版式文件的上传地址
    $.ajax({
    	url:"/app/base/user/getToken",
        type: "GET",
        async:false,
        success:function(data){
        	tokenAccess = data.result;
			url+="&access_token=" + tokenAccess;
            streamupurl = url;
            formatupurl = url;
            streamupurl+="&streamOrFormatFileType=stream.fromcsseoa";//国产机的流式必须为这个
            streamupurl+="&fileName=" + v_edit.activeFile.id+"."+suffix;
            formatupurl+="&streamOrFormatFileType=format";
            formatupurl+="&fileName=" + v_edit.activeFile.id+".ofd";
            //调用csseoa控件，执行本地wps编辑
            pluginRun(v_edit.activeFile.id,suffix,streamdownUrl,streamupurl,formatupurl,function(){
                v_edit.getMenu()
                v_edit.getFileList(true)
            });
        }
    });
}