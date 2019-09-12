var url1 = {"url":rootPath +"/leaveorback/xjinfo","dataType":"text"};//销假编辑
var url2 = {"url":rootPath +"/leaveorback/commitback","dataType":"text"};//销假提交
var id = getUrlParam('id');
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
    '						<span v-text="item.userName" style="font-size: 14px;color: #6699ff"></span>'+
    // '						<span class="opinionStatus" v-else-if="item.flowType">[<font>已审批</font>]</span>'+
    '					</span>'+
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
    data(){
        return {
            showEdit:false
        }
    },
    created(){
        this.showEdit = this.$parent.showWrite
    },
    methods:{
        editOpinion(item){
            v_edit.editOpinion(item)
        }
    }
});
Vue.component('opinion-message',opinionMessage);
var vue_list = new Vue({
    el : '#vue_list',
    data : {
        opinionArr :[],
        showOpinion:true
    },
    created : function() {
        this.getOpinionArr()
    },
    methods : {
        getOpinionArr : function(){
            vm = this;
            Vue.http.get( "/app/qxjgl/application/getAllPublicOpinion?id="+id).then(function(response) {
                if(response.data.length>0){
                    vm.opinionArr = response.data
                }else{
                    vm.showOpinion=false
                }
            })
        }
    }
})