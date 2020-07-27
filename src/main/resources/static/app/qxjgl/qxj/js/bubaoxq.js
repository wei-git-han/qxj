// var bubaoListUrl = {"url":"/app/gwcl/documentFlow/selectBubaoInfo","dataType":"text"};//补报列表
var bubaoListUrl = {"url":"/app/gwcl/documentFlow/queryBuBaoStatus","dataType":"text"};//补报列表
var bubaoListUrl = {"url":"/app/qxjgl/qxj/data/bbDetail.json","dataType":"text"};//补报列表
var ceHuiOne = {"url":"/app/gwcl/documentFlow/deleteReceiver","dataType":"text"};//撤回一条
var changeQueue ={"url":"/app/gwcl/documentFlow/changeQueue ","dataType":"text"};//调整顺序
var dFlowId = getUrlParam("id"); //主流程id
var leaderId = getUrlParam("leaderId")||""; //领导id
var fileFrom = getUrlParam("fileFrom")||"";
var bubaoList=[];
var pageModule = function(){
	var initOther = function(){
		//取消按钮
		$("#quxiao").click(function(){
			 newbootbox.newdialogClose("bubaoxqDialog");
		});
		//添加按钮
		$("#addBuBao").click(function(){
			newbootbox.newdialogClose("bubaoxqDialog");
			newbootbox.newdialog({
				id:"addbubaoDialog",
				width:850,
				height:600,
				header:true,
				title:"增加补报",
				classed:"cjDialog",
				url:rootPath + "/qxj/html/addbubao.html?dFlowId="+dFlowId+"&leaderId="+leaderId+"&fileFrom="+fileFrom
			})
		});
	}
	function showUp_Dowmn(list){
		var list2 = [];
		list.forEach(ele => {
			if(ele.status=='待处理'){
				list2.push(ele)
			}
		});
		if(list2.length<2){
			return list
		}
		list2.forEach((e,i)=>{
			if(i!==list2.length-1){
				e.showDown = true
			}
			if(i!==0){
				e.showUp = true;
			}
			list.forEach(ele => {
				if(ele.id == e.id){
					ele = e;
				}
			});
		})
		return list
	}
	//生成补报列表
	var initbubaoList = function(){
		$ajax({
			url:bubaoListUrl,
			data:{documentId:dFlowId},
			success:function(data){
				bubaoList=showUp_Dowmn(data.result.reverse());
				if(bubaoList.length<=0){
					$(".timelinesview").hide();
					return;
				}
				$('#bubaoList').html('');
				$.each(bubaoList,function(i,item){
					var str1 = "";
					var str2 = "";
					if(item.status=="未读"||item.status=="待处理"){
						str1+=`<span class="caozuo1">`;
						if(item.showUp){
							str1+=`<i class="fa fa-arrow-up up" onclick="upfn(${i});" style="cursor: pointer;"></i>`
						}
						if(item.showDown){
							str1+=`<i class="fa fa-arrow-down down" onclick="downfn(${i});" style="cursor: pointer;"></i>`
						}

						str1+=`<i class="fa fa-mail-reply reply" onclick="ceHuifn('${item.receiverId}');" style="cursor: pointer;"></i>
			        	</span>`;
					}else if(item.status =='已审批'){
						str2 = `<span class="bubanTime">${item.finishTime}</span>`
					}
					var str=`
							<li class="bubaoLine">
								<div class="timeline-icon">
									<i class="icontime"></i>
								</div>
								<div class="bubaoline-text">
									<span class="bubanName">${item.userName}</span>
									<span class="bubanStatus2">[${item.status}]</span>
									${str2}
								</div>
								${str1}
							</li>
					`;
					$('#bubaoList').append(str);
				});
				iShow();
			}
		});
	}		
	return{
		//加载页面处理程序
		initControl:function(){
			initOther();
			initbubaoList();
		},
		shuaxin:function(){
			initbubaoList();
		}
	};
}();
//撤回的方法
var ceHuifn = function(receiverId){
	 newbootbox.oconfirm({
    	title:"提示",
    	message: "是否要进行补报撤回？",
    	callback1:function(){
   		$ajax({
				url:ceHuiOne,
				data:{documentId:dFlowId,receiverId:receiverId},
				success:function(data){
					if(data.result=="success"){
						newbootbox.alert('撤回成功！').done(function(){
							pageModule.shuaxin();
	    				});
					}else{
						newbootbox.alert('撤回失败！').done(function(){
							pageModule.shuaxin();
	    				});
					}
				}
			})
    	}
    });
}
function iShow(){
	var arr=$(".bubaoline-text .caozuo");
	arr.each(function(i,item){
		if(i==0){
			$(this).find(".up").hide();
		};
		if(i==arr.length-1){
			$(this).find(".down").hide();
		};
	})
}
//上移    prv
function upfn(i){
	var currentId = bubaoList[i].receiverId;
	var prevId = bubaoList[--i].receiverId;
	$ajax({
		url:changeQueue,
		data:{documentFlowId:dFlowId,receiverId:currentId,receiverOtherId:prevId},
		success:function(data){
			if(data.result=="success"){
				newbootbox.alert('调整成功！').done(function(){
					pageModule.shuaxin();
				});
			}else{
				newbootbox.alert('调整失败！').done(function(){
					pageModule.shuaxin();
				});
			}
		}
	})
}
//下移   next  
function downfn(i){
	var currentId = bubaoList[i].receiverId;
	var nextId = bubaoList[++i].receiverId;
	$ajax({
		url:changeQueue,
		data:{documentFlowId:dFlowId,receiverId:currentId,receiverOtherId:nextId},
		success:function(data){
			if(data.result=="success"){
				newbootbox.alert('调整成功！').done(function(){
					pageModule.shuaxin();
				});
			}else{
				newbootbox.alert('调整失败！').done(function(){
					pageModule.shuaxin();
				});
			}
		}
	})
}




