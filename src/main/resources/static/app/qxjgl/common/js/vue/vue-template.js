var fileMessage = Vue.extend({
    props:['file','editShow'],
    template:`<div class="Fileblock">
    	<div class="FileTitle"><span v-text="file.fileTitle"></span></div>
    	<div class="FileList">
    		<div class="FileHeys" v-for="(item,index) in file.child" v-bind:id="item.id" v-bind:class="{'active':activeFile.id==item.id}">
    			<div style="overflow:hidden;line-height: 24px;">
    				<div class="FileHeys_title">
    					<a class="FileHeys_a" href="#"  v-bind:data_type="file.fileType"  v-text="item.name" v-on:click="viewFile(item)">附件名称</a>
    					<p class="pull-right hide-icon" >
    						<i class="fa fa-chevron-circle-up zt zt2" id="uptree" title="上移" v-show="index!=0" v-on:click="upFile(item.id,(file.child)[index-1].id)"></i>
    						<i class="fa fa-chevron-circle-down zt zt2" id="downtree" title="下移" v-show="index!=((file.child).length-1)" v-on:click="downFile(item.id,(file.child)[index+1].id)"></i>
    						<i class="fa fa-pencil-square-o zt zt2" title="重命名" @click="reFileName(item)" ></i>
    						<i class="fa fa-trash-o zt zt2" id="deltree" title="删除"  v-on:click="delFile(item,file.fileType,file)"></i>
    					</p>
                       <div class="pull-right" style="cursor: pointer;"><img v-if="item.stampFlag==2" src="../../../common/images/u519.svg" style="margin: 0 15px 3px 0"><img v-else-if="item.stampFlag==1" src="../../../common/images/u524.svg" style="margin: 0 15px 3px 0"></div>
    				</div>
    			</div>
    		</div>
    	</div>
    </div>`,
    data(){
        return {
            activeFile:{},
        }
    },
    created(){
        this.initSeletedFile()
    },
    computed:{
    },
    mounted(){
    },
    methods:{
        viewFile:function(file){
            this.activeFile = file
            this.$parent.selectFile(file)
        },
        initSeletedFile(){
            this.activeFile = this.$parent.activeFile
        },

        //上移文件
        upFile : function(currendId,prevId){
            Vue.http.get(url_file_up,{params:{currentId:currendId,prevId:prevId}}).then(function(response) {
                if(response.data.result=='success'){
                    v_edit.getMenu();
                    v_edit.getFileList(true);
                }
            }, function(response) {
                console.log('error:upFile');
            });
        },
        //下移文件
        downFile : function(currendId,nextId){
            Vue.http.get(url_file_down,{params:{currentId:currendId,nextId:nextId}}).then(function(response) {
                if(response.data.result=='success'){
                    v_edit.getMenu();
                    v_edit.getFileList(true);
                }
            }, function(response) {
                console.log('error:downFile');
            });
        },
        reFileName(item){
            newbootbox.newdialog({
                id:"reNameDialog",
                width:400,
                height:200,
                header:true,
                title:"重命名文件",
                classed:"cjDialog",
                url:rootPath + "/qxj/html/changeFileName.html?id="+item.id+'&fileName='+encodeURI(encodeURI(item.name))
            })
        },
        //删除文件
        delFile : function(item,type){
        	var msg="删除后文件不能恢复，是否确认删除文件？";
        	if(type=='cpj'){
        		msg="删除请假单后本次请假取消，是否确认操作？";
        	}
            vm = this;
            newbootbox.oconfirm({
                title: "提示",
                message: msg,
                callback1:function(){
                    if(type=='cpj'){
                        Vue.http.get(url_delete_info,{params:{leaveId:item.leaveId}}).then(function(response) {
                            if(response.data.msg=='success'){
                                if(fromMsg=='1'){
                                    windowClose()
                                }else if(fileFrom=='qxjsp'){
                                    location.href="/app/qxjgl/qxj/html/CZSP_table.html"
                                }else{
                                    location.href="/app/qxjgl/qxj/html/table.html"
                                }
                            }
                        }, function(response) {
                            console.log('error:delFile');
                        });
                    }else{
                        Vue.http.get(url_file_detele,{params:{id:item.id}}).then(function(response) {
                            if(response.data.msg=='success'){
                                v_edit.getMenu();
                                v_edit.getFileList();
                            }
                        }, function(response) {
                            console.log('error:delFile');
                        });
                    }

                }
            });
        },
    }
});


var flowMessage = Vue.extend({
    props:["flow","child"],
    template:
    '<div class="timelinesviewFlow">'+
    '	<div class="timelinesviewHeys" v-if="flow.juzhangInfo">'+
    '		<p v-text="flow.juzhangInfo"></p>'+
    '		<p><span v-text="flow.createdTime"></span><a href="#" class="pull-right" v-on:click="showViewFlow($event);">查看</a></p>'+
    '	</div>'+
    '	<ul class="timelines" id="timelines" v-show="!flow.juzhangInfo">'+
    '		<li v-for="(item,index) in child">'+
    '			<div class="timeline-icon">'+
    '				<i class="icontime"></i>'+
    '			</div>'+
    '			<div class="timeline-user">'+
    '				<span class="user" v-text="item.createdTime"></span>'+
    '			</div>'+
    '			<div class="timeline-list">'+
    '  				<h6>'+
    '     				<span v-bind:title="item.senderDeptName" class="H6Span blue">'+
    '     					<i class="fa fa-user"></i>'+
    '     					<font v-text="item.senderName"></font>'+
    '     				</span>'+
    /* '    				<span v-if="item.isRead!=null" v-text="item.isRead"></span>'+*/
    '					<i class="fa fa-long-arrow-right"></i>'+
    '					<span v-bind:title="item.receiverDeptName" class="H6Span blue">'+
    '						<i class="fa fa-user"></i>'+
    '						<font v-text="item.receiverName"></font>'+
    '					</span>'+
    ' 				</h6>'+
    /*			'				<h6>'+
                '
                '				</h6>'+*/
    '			</div>'+
    '		</li>'+
    '	</ul>'+
    '</div>',
    methods:{
        //显示流程意见
        showViewFlow:function(event){
            if($(event.target).text() == "查看"){
                $(event.target).parents(".timelinesviewFlow").find(".timelines").slideDown(500);
                $(event.target).text("收起");
            }else{
                $(event.target).parents(".timelinesviewFlow").find(".timelines").slideUp(500);
                $(event.target).text("查看");
            }
        },
    }

});

/*var flowMessage = Vue.extend({
	props:["flow","child"],
	template:
			'<div class="timelinesviewFlow">'+
			'	<div class="timelinesviewHeys" v-if="flow.juzhangInfo">'+
			'		<p v-text="flow.juzhangInfo"></p>'+
			'		<p><span v-text="flow.createdTime"></span><a href="#" class="pull-right" v-on:click="showViewFlow($event);">查看</a></p>'+
			'	</div>'+
			'	<ul class="timelines" id="timelines" v-show="!flow.juzhangInfo">'+
			'		<li v-for="(item,index) in child">'+
			'			<div class="timeline-icon">'+
			'				<i class="icontime"></i>'+
			'			</div>'+
			'			<div class="timeline-user">'+
			'				<span class="user" v-text="item.createdTime"></span>'+
			'			</div>'+
			'			<div class="timeline-list">'+
		    '  				<h6>'+
		    '     				<span v-bind:title="item.receiverDeptName" class="H6Span blue">'+
		    '     					<i class="fa fa-user"></i>'+
		    '     					<font v-text="item.receiverName"></font>'+
		    '     				</span>'+
		    '    				 <span v-if="item.isRead!=null" v-text="item.isRead"></span>'+
		    ' 				</h6>'+
			'				<div class="timeline-flow">'+
			'					<p v-text="item.text"></p>'+
		    '  				 </div>'+
			'				<h6>'+
			'					<span v-bind:title="item.senderDeptName" class="H6Span blue">'+
			'						<i class="fa fa-user"></i>'+
			'						<font v-text="item.senderName"></font>'+
			'					</span>'+
			'				</h6>'+
			'			</div>'+
			'		</li>'+
			'	</ul>'+
			'</div>',
	methods:{
		//显示流程意见
		showViewFlow:function(event){
			if($(event.target).text() == "查看"){
				$(event.target).parents(".timelinesviewFlow").find(".timelines").slideDown(500);
				$(event.target).text("收起");
			}else{
				$(event.target).parents(".timelinesviewFlow").find(".timelines").slideUp(500);
				$(event.target).text("查看");
			}
		},
	}

});*/

// var opinionMessage = Vue.extend({
//     props:["opinion","child","children"],
//     template:
//     '<div class="timelinesviewFlow">'+
//     '	<div class="timelinesviewHeys" v-if="opinion.statuss==\'close\'">'+
//     '		<p v-text="opinion.juzhangInfo"></p>'+
//     '		<p><span v-text="opinion.createdTime"></span><a href="#" class="pull-right" v-on:click="showViewFlow($event);">查看</a></p>'+
//     '	</div>'+
//     '	<div class="timelinesview" v-show="opinion.statuss==\'open\'">'+
//     '		<div class="timelinesheys" v-for="(item,index) in child">'+
//     '			<div class="timeline-icon">'+
//     '				<i class="icontime"></i>'+
//     '			</div>'+
//     '			<div class="timeline-user">'+
//     '				<span class="user" v-text="item.createdTime"></span>'+
//     '			</div>'+
//     '			<div class="timeline-body">'+
//     '				<h6 v-on:click="viewOpinionFile(item.id,item.teamId,item.dFlowId,item.documentId,item.status)">'+
//     '					<span v-bind:class="item.opinionPeopleName" class="H6Span blue">'+
//     '						<i class="fa fa-user" style="padding-left:5px"></i>'+
//     '						<font v-text="item.opinionPeopleName"></font>'+
//     '						<span class="opinionStatus" v-if="item.status == 0">[<font>发起会签</font>]</span>'+
//     '						<span class="opinionStatus" v-if="item.status == 1">[<font>会签中</font>]</span>'+
//     '						<span class="opinionStatus" v-if="item.status == 2">[<font>会签完成</font>]</span>'+
//     '						<span class="opinionStatus" v-if="item.status == 3">[<font>发起局秘核文</font>]</span>'+
//     '						<span class="opinionStatus" v-if="item.status == 4">[<font>核文中</font>]</span>'+
//     '						<span class="opinionStatus" v-if="item.status == 5">[<font>核文完成</font>]</span>'+
//     '						<span class="opinionStatus" v-if="item.status == 9">[<font>发起联签</font>]</span>'+
//     '						<span class="opinionStatus" v-if="item.status == 10">[<font>联签完成</font>]</span>'+
//     '						<span class="opinionStatus" v-if="item.status == 20">[<font>申请盖章</font>]</span>'+
//     '						<span class="opinionStatus" v-if="item.status == 21">[<font>已盖章</font>]</span>'+
//     '						<span class="opinionStatus" v-if="item.status == 22">[<font>盖章退回</font>]</span>'+
//     '						<span class="opinionStatus" v-if="item.status == 23">[<font>已发文</font>]</span>'+
//     '						<span class="opinionStatus" v-if="item.status == 111">[<font>核文退回</font>]</span>'+
//     '					</span>'+
//     '					<span v-text="item.opinionPeopleTxt"></span>'+
//     '				</h6>'+
//     '				<div class="timeline-content" v-if="item.status != 23">'+
//     '					<div style="padding:5px 10px;background:#fff;">'+
//     '						<img v-if="item.opinionLayout==1"  :src="item.opinionContent" style="max-width:100%"/>'+
//     '						<span v-else-if="item.opinionLayout!=1&&item.explain != null && item.explain != \'\'"  v-bind:title="item.explain" v-text="item.explain"></span>'+
//     '						<span v-else-if="item.opinionLayout!=1&&item.explain == null || item.explain == \'\'" v-bind:title="item.opinionContent" v-text="item.opinionContent"></span>'+
//     '					</div>'+
//     '					<div class="moreUser" v-for="(item,index) in item.children">'+
//     '						<h6 v-on:click="viewOpinionFile(item.id,item.teamId,item.dFlowId,item.documentId,item.status)">'+
//     '							<span v-bind:class="item.opinionPeopleName" class="H6Span blue">'+
//     '								<i class="fa fa-user"></i>'+
//     '								<font v-text="item.opinionPeopleName"></font>'+
//     '								<span class="H6Span blue" v-if="item.status == 0">(<font v-text="item.organName"></font>)<span class="opinionStatus">[<font>发起会签</font>]</span></span>'+
//     '								<span class="H6Span blue" v-if="item.status == 1">(<font v-text="item.organName"></font>)<span class="opinionStatus">[<font>会签中</font>]</span></span>'+
//     '								<span class="H6Span blue" v-if="item.status == 2">(<font v-text="item.organName"></font>)<span class="opinionStatus">[<font>会签完成</font>]</span></span>'+
//     '								<span class="opinionStatus" v-if="item.status == 3">[<font>发起局秘核文</font>]</span>'+
//     '								<span class="opinionStatus" v-if="item.status == 4">[<font>核文中</font>]</span>'+
//     '								<span class="opinionStatus" v-if="item.status == 5">[<font>核文完成</font>]</span>'+
//     '								<span class="H6Span blue" v-if="item.status == 9">(<font v-text="item.organName"></font>)<span class="opinionStatus">[<font>联签中</font>]</span></span>'+
//     '								<span class="H6Span blue" v-if="item.status == 10">(<font v-text="item.organName"></font>)<span class="opinionStatus">[<font>联签完成</font>]</span></span>'+
//     '							</span>'+
//     '							<span v-text="item.opinionPeopleTxt"></span>'+
//     '							<p class="moreCreatedTime">'+
//     /*'								<font  v-text="item.createdTime.substring(0,16)"></font>'+*/
//     '								<font  v-if="item.finishTime" v-text="item.finishTime.substring(0,16)"></font>'+
//     '								<font  v-else-if="item.readTime" v-text="item.readTime.substring(0,16)"></font>'+
//     '								<font  v-else v-text="item.createdTime.substring(0,16)"></font>'+
//     '								<font  v-text="item.flag"></font>'+
//     '								<i  class="fa fa-clock-o" data-toggle="tooltip" data-html="true" v-if="item.readTime&& item.finishTime" v-bind:title="item.readTime.substring(0,16)+\'已读\'+item.finishTime.substring(0,16)+\'会签\'">'+
//     '								</i>'+
//     '								<i  class="fa fa-clock-o" data-toggle="tooltip" data-html="true" v-else-if="item.readTime" v-bind:title="item.readTime.substring(0,16)+\'已读\'">'+
//     '								</i>'+
//     '								<i  class="fa fa-clock-o" data-toggle="tooltip" data-html="true" v-else-if="item.finishTime" v-bind:title="item.finishTime.substring(0,16)+\'会签\'">'+
//     '								</i>'+
//     '								<i  class="fa fa-clock-o" v-else>'+
//     '								</i>'+
//     '							</p>'+
//     '                           <p  v-if="item.status == 2" style="margin:5px 0 0"><i class="fa fa-link" style="font-size:12px;margin-right:5px"></i><a>查看修订文件</a></p>'+
//     '						</h6>'+
//     '						<div style="padding:5px 10px;" v-if="item.opinionLayout==1">'+
//     '							<img   :src="item.opinionContent" style="max-width:100%"/>'+
//     '						</div>'+
//     '						<div v-else v-text="item.opinionContent" class="moreExplain" v-bind:title="item.opinionContent"></div>'+
//     '       		 	</div>'+
//     '       		 </div>'+
//     '				<div class="timeline-content" v-if="item.status == 23">'+
//     '					<div style="padding:5px 10px;">'+
//     '						<span v-if="item.explain != null && item.explain != \'\'"  v-bind:title="item.explain" v-text="item.explain"></span>'+
//     '						<span v-if="item.explain == null || item.explain == \'\'" v-bind:title="item.opinionContent" v-text="item.opinionContent"></span>'+
//     '					</div>'+
//     '					<div class="row" v-show="showChildren" style="margin-left:0px;margin-right:0px;border: 1px dotted rgb(153, 153, 153);background-color: rgb(237, 240, 243);padding-bottom: 5px;">'+
//     '                         <div style="padding:5px">接收单位：</div>'+
//     '                         <div class="col-md-6" style="color:#999;letter-spacing: 1px;padding: 0 5px;" v-for="(record,index) in item.records">'+
//     '                               <span v-text="record.receiveOrgName" style="color:#666"></span>'+
//     '                               （<span  v-if="record.receiveFlag==2">已退回</span>'+
//     '                                <span  v-else-if="record.receiveFlag==1">已接收&nbsp;&nbsp;<span v-text="record.receiveTime"></span>'+
//     '                               </span>'+
//     '                               <span v-else>未接收</span>'+
//     '                         ）</div>'+
//     '       		 	</div>'+
//     '					<div style="padding:5px 10px;">'+
//     '							<a @click="showChildren=true" v-show="!showChildren">展开</a>'+
//     '							<a @click="showChildren=false" v-show="showChildren">收起</a>'+
//     '					</div>'+
//     '       		 </div>'+
//     '				<div class="timeline-content" v-else-if="item.status == 201">'+
//     '					<div class="row" v-show="showYZChildren&&YzId==item.id" style="margin-left:0px;margin-right:0px;border: 1px dotted rgb(153, 153, 153);background-color: rgb(237, 240, 243);padding-bottom: 5px;">'+
//     '                         <div style="padding:5px">阅知记录：</div>'+
//     '                         <div class="col-md-2" style="color:#999;letter-spacing: 1px;padding: 0 5px;" v-for="(record,index) in yzList">'+
//     '                               <span v-text="record.receiverName" style="color:#666"></span>'+
//     '                               （<span  v-if="record.readStatus==0">未读</span>'+
//     '                                <span  v-else>已读</span>）'+
//     '                         </div>'+
//     '       		 	</div>'+
//     '					<div style="padding:5px 10px;">'+
//     '							<a @click="getReadInfo(item.id)" v-show="!showYZChildren||YzId!=item.id">展开</a>'+
//     '							<a @click="showYZChildren=false" v-show="showYZChildren&&YzId==item.id">收起</a>'+
//     '					</div>'+
//     '       		 </div>'+
//     '			</div>'+
//     '		</div>'+
//     '	</div>'+
//     '</div>',
//     data(){
//         return {
//             showChildren:false,
//             showYZChildren:false,
//             yzList:[],
//             YzId:""
//         }
//     },
//     methods:{
//         //显示流程意见
//         showViewFlow:function(event){
//             if($(event.target).text() == "查看"){
//                 $(event.target).parents(".timelinesviewFlow").find(".timelinesview").slideDown(500);
//                 $(event.target).text("收起");
//             }else{
//                 $(event.target).parents(".timelinesviewFlow").find(".timelinesview").slideUp(500);
//                 $(event.target).text("查看");
//             }
//         },
//         getReadInfo(id){
//             vm = this;
//             this.YzId = id
//             Vue.http.get('/app/gwcl/documentcopytoread/readInfo',{params:{flowId:dFlowId,opinionId:id}}).then(function(res){
//                 vm.yzList = res.data.list;
//                 if(res.data&&vm.yzList.length>0){
//                     vm.showYZChildren = true
//                 }
//                 console.log(res.data)
//             }, function(response) {
//                 console.log('error');
//             })
//         },
//         //显示全部意见
//         showAllText : function(id){
//             var allText = $("#showalltext_"+id).parents(".timeline-content").attr("title");
//             $("#showalltext_"+id).parent().find("span:eq(0)").text(allText);
//             $("#showalltext_"+id).parent().find("span:eq(1)").hide();
//             $("#showalltext_"+id).hide();
//             $("#hidealltext_"+id).show();
//         },
//         hideAllText:function(id){
//             var hidealltext = ($("#showalltext_"+id).parent().find("span:eq(0)").text()).substring(0,27);
//             $("#showalltext_"+id).parent().find("span:eq(0)").text(hidealltext);
//             $("#hidealltext_"+id).hide();
//             $("#showalltext_"+id).parent().find("span:eq(1)").show();
//             $("#showalltext_"+id).show();
//         },
//         viewOpinionFile:function(opinionId,teamId,dFlowId,documentId,status){
//             if(documentId == null || documentId == "" || typeof(documentId) == "undefined"||status==1||status==4||status == 9){
//                 return;
//             }
//             $.ajax({
//                 url:checkopinionUrl.url,
//                 data:{opinionId:opinionId,teamId:teamId,dFlowId:dFlowId},
//                 success:function(result) {//判断当前意见是不是拟稿人最初的意见，如果是 页签名字为初"始版本查看";其他都是 意见查看
//                     if(result.result == 'success'){
//                         //设置弹出多个新窗口，因此多个窗口的名字不能一直，故设为空
//                         window.open('/app/gwcl/document/gwqc/html/draftOpinion.html?opinionId='+opinionId+'&dFlowId='+dFlowId+'&teamId='+teamId ,'');
//                     }else{
//                         //设置弹出多个新窗口，因此多个窗口的名字不能一直，故设为空
//                         window.open('/app/gwcl/document/gwqc/html/opinion.html?opinionId='+opinionId+'&dFlowId='+dFlowId+'&teamId='+teamId ,'');
//                     }
//                 }
//             });
//
//         }
//     }
// });
var opinionMessage = Vue.extend({
    props:["opinion"],
    template:
    '<div class="timelinesviewFlow">'+
    '	<div class="timelinesviewHeys">'+
    '	</div>'+
    '	<div class="timelinesview" >'+
    '		<div class="timelinesheys" v-for="(item,index) in opinion">'+
    '			<div class="timeline-icon">'+
    '				<i class="icontime"></i>'+
    '			</div>'+
    '			<div class="timeline-user">'+
    '				<span class="user" v-text="item.opinionDate"></span>'+
    '			</div>'+
    '			<div class="timeline-body">'+
    '				<h6 >'+
    '					<span v-bind:class="item.opinionPeopleName" class="H6Span blue">'+
    '						<i class="fa fa-user" style="padding-left:5px"></i>'+
    '						<span v-text="item.userName" style="font-size: 16px"></span>'+
    '					</span>'+
    '					<span class="pull-right" v-show="item.tempType==0" @click="editOpinion(item)" style="padding: 0 7px;font-size:14px;color: #6699ff">编辑</span>'+
    '				</h6>'+
    '				<div class="timeline-content" >'+
    '					<div style="padding:5px 10px;background:#fff;">'+
    '						<img v-if="item.opinionType==1"  :src="item.opinion" style="max-width:100%"/>'+
    '						<span v-else  v-bind:title="item.opinion" v-text="item.opinion"></span>'+
    '					</div>'+
    '       		 </div>'+
    '			</div>'+
    '		</div>'+
    '	</div>'+
    '</div>',
    methods:{
        editOpinion(item){
        	this.$parent.editOpinion(item)
        }
    }
});