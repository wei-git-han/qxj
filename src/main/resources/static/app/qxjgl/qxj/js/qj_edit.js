var leaverIds = getUrlParam('leaverIds');
var url1 = {"url":"/app/qxjgl/application/editInfo","dataType":"text"};  //编辑
var url2 = {"url":rootPath +"/dicvocationsort/dict?leaverIds="+leaverIds,"dataType":"text"};     //休假类别
var url3 = {"url":"/app/qxjgl/application/saveLeaveApplication","dataType":"text"};  //编辑保存成功
var url4 = {"url":rootPath +"/customuser/tree","dataType":"text"};  //编辑保存成功
var url5 = {"url":rootPath +"/leaveorback/getUser","dataType":"text"}//获取登陆人
var returnDate = {"url":rootPath +"/leaveOrBack/calculateHolidays","dataType":"text"};
var allUserTreeUrl = {"url":"/app/base/user/tree","dataType":"text"};//所有人员树
var url31 = {"url": rootPath + "/dicvocationsort/type","dataType":"text"} //入参type 0请假类型；1因公出差；2交通工具类型  出参：result：success；list）
var id = getUrlParam('id');
var tishi="";
var userTree2 = {"url":rootPath +"/orguser/chairmantree","dataType":"text"};
var fileFrom = getUrlParam("fileFrom");
var fromMsg= getUrlParam("fromMsg");
var pageModule = function(){
	var initloginUser = function(){
		$ajax({
			url:url5,
			success:function(data){
				setformdata(data);
			}
		})
	}
	var initvehicle = function(){
		$ajax({
			url:url31,
			data:{type:'2'},
			success:function(data){
//                $("#vehicle").html("<option value='无'>无</option>");
                var html = "";
                $.each(data.list,function(i){
                	html+='<option value='+data.list[i].id+' data-flag='+data.list[i].flag+'>'+data.list[i].text+'</option>';
                });
                $("#vehicle").append(html);
			}
		})
	}
	var initxjlb = function(){
		$ajax({
			url:url2,
			success:function(data){
				initselect("xjlb",data.xjlb);
			}
		});
	};
	
	var initdatafn = function(){
		$ajax({
			url:url1,
			data:{id:id},
			success:initdata
		})
	}
	
	var initdata = function(data){
    	setformdata(data);
		initxjlb();
		setTimeout(function () {
			$("#xjlb option").each(function(){
				if($(this).text() == data.lb){
					$(this).attr({"selected": true});
					// this.selected=true;
				}
			});
			$("#vehicle option").each(function(){
				if($(this).text() == data.vehicle){
					$(this).attr({"selected": true});
					// this.selected=true;
				}
			});
			$('#xjlb').val(data.lb)
//            $('#xjlb').attr('data-type',_type2)
			$('#xjlb').attr('data-id',data.qjId)
			if(data.qjlb == '0'){// 因私请假alert('因私请假')
	        	if(data.flag === '2') { // 因私请假 选择的交通工具需要审批  页面显示“地方驾驶证号”、“车辆号牌”、“驾车人”、“乘坐人员”
	        		$('.isPrevate').show();
	        		$('.needOne').hide();
	                $('.needTwo').hide();
	                $('#car_jsid').val(data.carJsid) // 地方驾驶证号
	                $('#car_card').val(data.carCard) // 车辆号牌
	                $('#driverId').val(data.driver) // 驾车人
	        	} else {
	        		$('.isPrevate').hide();
	        		$('.needOne').hide();
	                $('.needTwo').hide();
	        	}
			}else{ // 因公出差  已选择交通工具
				if(data.flag === '2') { // 因公出差 选择的交通工具需要审批  页面显示“到达单位”、“车型及出车数量”、“乘员及装载货物情况”
					$('.isPrevate').hide();
	                $('.needOne').show();
	                $('.needTwo').show();
	                $('#to_place').val(data.toPlace) //到达单位
	                $('#cartypeCarnumber').val(data.cartypeCarnumber) //车型及出车数量
	                $('#peopleThing').val(data.peopleThing) //乘员及装载货
				} else { // 因公出差 选择的交通工具不需要审批  页面只显示“到达单位”
					$('.isPrevate').hide();
	                $('.needOne').show();
	                $('.needTwo').hide();
	                $('#to_place').val(data.toPlace) //到达单位
				}
			}
		},50)
	};

	$("#xjsjTo").datepicker({
		format:"yyyy-mm-dd",
	    language:"zh-CN",
	    rtl: Metronic.isRTL(),
	    orientation: "right",
	    startDate:new Date(),
	    autoclose: true,
	}).on('changeDate',function(end){
		$("#xjsjFrom").datepicker("setEndDate",$("#xjsjTo").val());
		$ajax({
			url:returnDate,
			dataType:'POST',
			data:{startDateStr:$("#xjsjFrom").val(),toDateStr:$("#xjsjTo").val()},
			success:function(data){
				setformdata(data);
			}
		}) 
	});
	var initother = function(){
		
		$("#xjlb").change(function(){
			if($("#xjlb option:selected").text() == "年假"){
				$("#njtsDiv").show();
			}else{
				$("#njtsDiv").hide();
			}
		})
		
		$("#xjsjFrom").datepicker({
			format:"yyyy-mm-dd",
		    language:"zh-CN",
		    rtl: Metronic.isRTL(),
		    orientation: "right",
		    startDate:new Date(),
		    autoclose: true,
		}).on('changeDate',function(end){
			$("#xjsjTo").datepicker("setStartDate",$("#xjsjFrom").val());
			var starday = new Date($("#xjsjFrom").val());
			var endday = new Date($("#xjsjTo").val());
			if(endday < starday){
				$("#xjsjTo").datepicker("setDate",$("#xjsjFrom").val());
			};
			$ajax({
				url:returnDate,
				dataType:'POST',
				data:{startDateStr:$("#xjsjFrom").val(),toDateStr:$("#xjsjTo").val()},
				success:function(data){
					setformdata(data);
				}
			});
		/*	$("#xjts").val(GetDateDiff($("#xjsjFrom").val(),$("#xjsjTo").val()));*/
		});
		
		$("#xjsjTo").datepicker({
			format:"yyyy-mm-dd",
		    language:"zh-CN",
		    rtl: Metronic.isRTL(),
		    orientation: "right",
		    startDate:new Date(),
		    autoclose: true,
		}).on('changeDate',function(end){
			$("#xjsjFrom").datepicker("setEndDate",$("#xjsjTo").val());
			$ajax({
				url:returnDate,
				dataType:'POST',
				data:{startDateStr:$("#xjsjFrom").val(),toDateStr:$("#xjsjTo").val()},
				success:function(data){
					setformdata(data);
				}
			});
			/*$("#xjts").val(GetDateDiff($("#xjsjFrom").val(),$("#xjsjTo").val()));*/
		});
		
        $("#undertaker").createUserTree({
            url : allUserTreeUrl,
            width : "100%",
            data:{id:1},
            success : function(data, treeobj) {},
            selectnode : function(e, data) {
                $("#undertakerId").val(data.node.id);
                $("#undertaker").val(data.node.text);
            }
        });
		$(".input-group-btn").click(function(){
			$(this).prev().focus();
		});
		
		$("#ghjhgz_edit_form").validate({
			    submitHandler: function() {
					var elementarry = ["sqrq","sqr","sqrId","deptDuty","xjlb","syts","xjsjFrom","xjsjTo","xjts","shouldTakDays","csld",
						"csldId","csparea","qjzt","spzt","mobile","place","origin","orgId","parentOrgId","orgName","turnOver",
						'status',"holidayNum","weekendNum","linkMan","undertaker","undertakerId","undertakerMobile"];
					var paramdata = getformdata(elementarry);
					paramdata.id=id;
					paramdata.xjlb = $('#xjlb').attr('data-id')
	                paramdata.place = $('#place').val().split('/')[0];
	                paramdata.city = $('#place').val().split('/')[1];
					paramdata.explain = $.trim($('#otherReasons').val());
	                paramdata.address = $.trim($('#detailedAddress').val());

	                //如果因公隐藏 可一定选择了因私请假
	                if($('.isPublicNeed').is(':hidden')){
						if($('#vehicle').val() != '无'){
							paramdata.carJsid = $.trim($('#car_jsid').val());
							paramdata.carCard = $.trim($('#car_card').val());
							paramdata.driver = $('#driver').val();
							paramdata.passenger = $.trim($('#passenger').val());
						}
					}else{
	                    if($('#vehicle').val() != '无'){
	                        paramdata.toPlace = $.trim($('#to_place').val());
	                        paramdata.cartypeCarnumber = $.trim($('#cartypeCarnumber').val());
	                        paramdata.peopleThing = $.trim($('#peopleThing').val());
	                    }else{
	                        paramdata.toPlace = $.trim($('#to_place').val());
	                    }
					}
					$("#qjDialog").removeClass("none");
					$ajax({
						url:url3,
						type: "POST",
						data:paramdata,
						success:function(data){
                            $("#qjDialog").addClass("none")
							if(data.result=="success"){
								newbootbox.newdialogClose("qjEdit");
								var tstext = "保存成功！"
								newbootbox.alert(tstext).done(function(){
//									window.top.iframe1.setParams({'showTab':1});
									if(fromMsg=='1'){
										setParams({'showTab':1});
									}else{
										window.top.iframe1.setParams({'showTab':1});
									}
								});
							}else{
								newbootbox.alert("保存失败！");
							}
						}
					}) 
			      
			    }
		});
		
		 $("#save").click(function(){
		 	$("#ghjhgz_edit_form").submit();
		 });
		$("#close").click(function(){
			newbootbox.newdialogClose("qjEdit");
		})
		//点击请假类别
		$('#xjlb').on('click',function(e){
			stopPropagation(e)
            //默认请假子类
			$('.reasonsOne').addClass('firstSelecte').siblings().removeClass('firstSelecte')
            $ajax({
                url:url31,
				data:{type:'0'},
                success:function(data){
                	if(data && data.list&&data.list.length>0){
                        var _html = '';
                        for(var i=0;i<data.list.length;i++){
                            _html += '<li class="bigTypeChild" data-type="reasons" data-type2="0" data-id="'+data.list[i].id+'">'+data.list[i].text+'</li>'
                        }
                        $('#listRight').html(_html)
                        $('#reasonsBox').show()
					}
                }
            })
		})
		$(document)
			.on('click',function(){
                $('#reasonsBox,#placeBox').hide()
			})
            //点击请假，省类别第一级菜单
            .on('click','.bigType',function(e){
            	var _type = $(this).attr('data-type');
                stopPropagation(e)
				$(this).addClass('firstSelecte');
                $(this).siblings('.bigType').removeClass('firstSelecte');
				if(_type == 'reasons'){
                	var _type = $(this).attr('data-type2')
                    $ajax({
                        url:url31,
                        data:{type:_type},
                        success:function(data){
                            if(data && data.list && data.list.length>0){
                                var _html = '';
                                for(var i=0;i<data.list.length;i++){
                                    _html += '<li class="bigTypeChild" data-type="reasons" data-id="'+data.list[i].id+'" data-type2="'+_type+'">'+data.list[i].text+'</li>'
                                }
                                $('#listRight').html(_html)
							}
                        }
                    })
				}else{
                	var _id = $(this).attr('data-id');
                    $ajax({
                        url:addressUrl,
						data:{pid:_id},
                        success:function(data){
                            var _html = '';
                            for(var i=0;i<data.list.length;i++){
                                _html += '<li class="bigTypeChild" data-id="'+data.list[i].id+'" >'+data.list[i].name+'</li>'
                            }
                            $('#placeRight').html(_html)
                        }
                    })
				}

            })
            //点击请假类别第二级菜单
            .on('click','.bigTypeChild',function(){
                var _type = $(this).attr('data-type');
                var _id = $(this).attr('data-id')
                var flag = $("#vehicle option:selected").attr('data-flag'); // 选择的交通工具是否需要审批  2：需要审批 3：不需要审批
                if(_type == 'reasons'){
                	var _type2 = $(this).attr('data-type2');
                	//如果是因公出差
                	if(_type2 == '1'){
                        $('.isPrevate').hide();
                        $('.isPublic').show();
                		//如果交通工具为无
                        if($('#vehicle').val() === ''){
                        	$('.isPrevate').hide();
                            $('.needOne').show();
                            $('.needTwo').hide();
                        }else{
                        	if(flag === '2') { // 因公出差 选择的交通工具需要审批  页面显示“到达单位”、“车型及出车数量”、“乘员及装载货物情况”
        						$('.isPrevate').hide();
        	                    $('.needOne').show();
        	                    $('.needTwo').show();
        					} else { // 因公出差 选择的交通工具不需要审批  页面只显示“到达单位”
        						$('.isPrevate').hide();
        	                    $('.needOne').show();
        	                    $('.needTwo').hide();
        					}
						}
					}else{  //如果因私请假
                        $('.isPublic').hide();
                        if($('#vehicle').val() === ''){
                            $('.isPrevate').hide();
                        }else{
//                            $('.isPrevate').show();
                        	if(flag === '2') { // 因私请假 选择的交通工具需要审批  页面显示“地方驾驶证号”、“车辆号牌”、“驾车人”、“乘坐人员”
                        		$('.isPrevate').show();
                        		$('.needOne').hide();
        	                    $('.needTwo').hide();
                        	} else {
                        		$('.isPrevate').hide();
                        		$('.needOne').hide();
        	                    $('.needTwo').hide();
                        	}
                        }
					}

                    $('#xjlb').val($(this).text())
                    $('#xjlb').attr('data-type',_type2)
					$('#xjlb').attr('data-id',_id)
                    $('#reasonsBox').hide()
				}else{
                	var $text = $(this).parent().siblings('ul').find('.firstSelecte').text() + '/' + $(this).text()
                    $('#place').val($text)
                    $('#placeBox').hide()
				}
            })
            //选择交通工具
		$('#vehicle').off('change').on('change',function () {
			var v = $(this).val();
			var flag = $("#vehicle option:selected").attr('data-flag'); // 选择的交通工具是否需要审批  2：需要审批 3：不需要审批
			if(!$('#xjlb').val()){return;}
            var _type = $('#xjlb').attr('data-type');
			if(v != ''){ // 选择交通工具
                if(_type == '0'){// 因私请假
                	if(flag === '2') { // 因私请假 选择的交通工具需要审批  页面显示“地方驾驶证号”、“车辆号牌”、“驾车人”、“乘坐人员”
                		$('.isPrevate').show();
                		$('.needOne').hide();
	                    $('.needTwo').hide();
                	} else {
                		$('.isPrevate').hide();
                		$('.needOne').hide();
	                    $('.needTwo').hide();
                	}
				}else{ // 因公出差  已选择交通工具
					if(flag === '2') { // 因公出差 选择的交通工具需要审批  页面显示“到达单位”、“车型及出车数量”、“乘员及装载货物情况”
						$('.isPrevate').hide();
	                    $('.needOne').show();
	                    $('.needTwo').show();
					} else { // 因公出差 选择的交通工具不需要审批  页面只显示“到达单位”
						$('.isPrevate').hide();
	                    $('.needOne').show();
	                    $('.needTwo').hide();
					}
				}
			}else{
				if(_type == '0'){ // 因私请假 选择的交通工具不需要审批  页面不显示“地方驾驶证号”、“车辆号牌”、“驾车人”、“乘坐人员”
					$('.isPrevate').hide();
            		$('.needOne').hide();
                    $('.needTwo').hide();
				}else{ // 因公出差  未选择交通工具  页面只显示“到达单位”
					$('.isPrevate').hide();
                    $('.needOne').show();
                    $('.needTwo').hide();
				}
			}
        })
	}
	
	
	return{
		//加载页面处理程序
		initControl:function(){
			// initloginUser();
			// initxjlb();
			initdatafn();
			initother();
			initvehicle();
		}
	}
	
}();

var GetDateDiff=function(startDate,endDate){
	var startDate = new Date(Date.parse(startDate.replace(/-/g,"/"))).getTime();
	var endDate = new Date(Date.parse(endDate.replace(/-/g,"/"))).getTime();
	var dates=Math.abs((startDate-endDate))/(1000*60*60*24);
	return dates+1;
}

var xjtsErrorfn = function(){
	if($("#xjlb option:selected").text() == "年假"){
		if(parseInt($("#xjts").val()) > parseInt($("#syts").val())){
			$("#xjtsError").css({"display":"inline-block"})
		}else{
			$("#xjtsError").css({"display":"none"})
		}
	}
}
//修改页面参数并刷新
var setParams = function(obj){//{key:'val'}
	if(!obj){
		obj = {}
	}
	obj.isReload = true;
    var url = window.top.location.href.toString();
    for(i in obj){
        var re = eval('/('+i+'=)([^&]*)/gi');
        if(re.test(url)){
            url = url.replace(re,i+'='+obj[i])
        }else{
            if(url.indexOf('?')>-1){
                url +='&'+i+'='+obj[i]
            }else{
                url +='?'+i+'='+obj[i]
            }
        }
    }
    window.top.location.href = url;
};
//阻止冒泡
function stopPropagation(e){
    if(e.stopPropagation){
        e.stopPropagation()
    }else{
        e.cancelBubble = true;
    }
}