var url2 = {"url": rootPath + "/dicvocationsort/dict","dataType":"text"} //字典请假类型
var chooseOneJuPersons = {"url":"/leave/apply/chooseOneJuPersons","dataType":"text"};
var saveOrUpdateLeaveUrl = {"url": "/app/qxjgl/application/saveLeaveApplication","dataType":"text"}//保存或修改请假单
//var url4 = {"url": rootPath + "/leaveorback/getUser","dataType":"text"} //获取登录人
var getDefaultParamUrl = {"url": "/app/qxjgl/application/getDefaultParam","dataType":"text"} //获取登录人信息
var allUserTreeUrl = {"url":"/app/base/user/allTree","dataType":"text"};//所有人员树
var returnDate = {"url":rootPath +"/leaveOrBack/calculateHolidays","dataType":"text"};
var loginUserId = getUrlParam("loginUserId")||"";//登录人Id
var url3 = {"url": rootPath + "/dicvocationsort/type","dataType":"text"} //入参type 0请假类型；1因公出差；2交通工具类型  出参：result：success；list）
var addressUrl = {"url": rootPath + "/provincecitydistrict/getPCD","dataType":"text"} //地点接口入参：pid：默认不传，出参：result：success；list，，该接口点击请假申请时调用




var pageModule = function(){

	var initloginUser = function(){
		$ajax({
			url:getDefaultParamUrl,
			success:function(data){
				setformdata(data);
				if(getCookie('deptDutyArr')){
					var docTypeArr = JSON.parse(getCookie('deptDutyArr'))
					docTypeArr.map(function(item){
						if(item.userId == loginUserId){
							$("#deptDuty").val(item.deptDutyText);
						}
					});
				}
				$('#linkMan,#driver').val(data.undertaker);
				$('#mobile').val(data.undertakerMobile);
			}
		})
	}

	var initvehicle = function(){
		$ajax({
			url:url3,
			data:{type:'2'},
			success:function(data){
                $("#vehicle").html("<option value='无'>无</option>");
                var html = "";
                $.each(data.list,function(i){
                	html+='<option value='+data.list[i].id+'>'+data.list[i].text+'</option>';
                });
                $("#vehicle").append(html);
			}
		})
	}

	var initother = function(){
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
			//$("#xjts").val(GetDateDiff($("#xjsjFrom").val(),$("#xjsjTo").val()));
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
			})
		});

		$(".input-group-btn").click(function(){
			$(this).prev().focus();
		});
		/*************************/
		//所有人员树
		$("#sqr").createNewUserTree({
			url : allUserTreeUrl,
			width : "100%",
            plugins:'checkbox',
			params:{id:1},
			success : function(data, treeobj) {},
			selectnode : function(e, data,treessname,treessid) {
				$ajax({
					url:chooseOneJuPersons,
					dataType:'POST',
					data:{userIds:treessid.toString()},
					success:function(data){
						if (data.result == 'fail') {
							newbootbox.alert("请选择同一个局的人！");
							$("#save").hide();
						} else {
							$("#parentOrgId").val(data.orgId);
							$("#orgName").val(data.orgName);
							$("#save").show();
							console.log(data.orgId)
							if (data.orgId != undefined) {
								$ajax({
									url:{"url": rootPath + "/dicvocationsort/dict?orgId="+data.orgId,"dataType":"text"},
									success:function(data){
										if (data.xjlb == '') {
											initselect("xjlb", data.xjlb);
											newbootbox.alert("请联系本局管理员配置请假类别！");
											$("#xjlb").attr("disabled",true);
											$("#save").hide();
										} else {
											$("#xjlb").attr("disabled",false);
											initselect("xjlb", data.xjlb);
										}
									}
								});
							}
						}
					}
				});
				$("#sqrId").val(treessid);
				$("#sqr").val(treessname);
			}
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
        $("#driver").createUserTree({
            url : allUserTreeUrl,
            width : "100%",
            data:{id:1},
            success : function(data, treeobj) {},
            selectnode : function(e, data) {
                $("#driverId").val(data.node.id);
                $("#driver").val(data.node.text);
            }
        });
        $("#linkMan").createUserTree({
            url : allUserTreeUrl,
            width : "100%",
            data:{id:1},
            success : function(data, treeobj) {},
            selectnode : function(e, data) {
                $("#linkManId").val(data.node.id);
                $("#linkMan").val(data.node.text);
            }
        });
		$("#xjts").bind('input propertychange',function(val){
			$("#holidayNum").val("")
			$("#weekendNum").val("")
		})

		 $("#save").click(function(){
			 // newbootbox.newdialogClose("qjAdd");
			 $("#commentForm").submit();
		 });

	     $("#mobile").on("blur", function(){
	    	var reg = /^[1][3,4,5,7,8][0-9]{9}$/;
	    	if(!reg.test($(this).val())) {
		    	$(this).next().show();
		    	$("#mobile").focus();
	    	}else{
		    	$(this).next().hide();
	    	}
	     });

	     //重置
	     $("#reset").click(function(){
	    	 removeInputData(["xjts","fdjjr","zlrts"]);
	    	 $ajax({
				url:returnDate,
				dataType:'POST',
				data:{startDateStr:$("#xjsjFrom").val(),toDateStr:$("#xjsjTo").val()},
				success:function(data){
					setformdata(data);
				}
			})
	     });

		$("#commentForm").validate({

	    	submitHandler: function() {
				var elementarry = ["sqrq","sqr","sqrId","deptDuty","xjlb","syts","xjsjFrom","xjsjTo","xjts","shouldTakDays","csld",
					"csldId","csparea","qjzt","spzt","mobile","origin","orgId","parentOrgId","orgName","vehicle","turnOver",
					'status',"holidayNum","weekendNum","linkMan","linkManId","undertaker","undertakerId","undertakerMobile"];
				var paramdata = getformdata(elementarry);
//                newbootbox.newdialogClose("qjAdd");
//                window.parent.parent.frames["iframe1"].openLoading()
				//请假补充说明
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

				window.parent.parent.openLoading()
				$ajax({
					url:saveOrUpdateLeaveUrl,
					type: "POST",
					data:paramdata,
					success:function(data){
						if(data.result=="success"){
//							window.parent.parent.frames["iframe1"].closeLoading()
							window.parent.parent.closeLoading()
							newbootbox.alertInfo("生成请假单成功！").done(function(){
								newbootbox.newdialogClose("qjAdd");
								window.top.iframe1.location.href=rootPath + '/qxj/html/qxjView.html?id='+data.id+'&showTab=1'
							});
						}else{
							newbootbox.alertInfo(data.result+"！").done(function(){
//								window.parent.parent.frames["iframe1"].closeLoading()
								window.parent.parent.closeLoading()
							});
						}
//						newbootbox.newdialogClose("qjAdd");
					},


				})
	    	}
		});

		$("#xjlb").change(function(){
			if($("#xjlb option:selected").text()=="年假"){
				$("#njtsDiv").show();
			}else{
				$("#njtsDiv").hide();
			}
		});

		$("#close").click(function(){
			newbootbox.newdialogClose("qjAdd");
		})
		$("#deptDuty").blur(function(){
			//登记录入同账号类别记忆功能
			var deptDutyArr = []
			if(getCookie('deptDutyArr')){
				deptDutyArr = getCookie('deptDutyArr');
				deptDutyArr = JSON.parse(deptDutyArr).filter(function(item){
					item.userId != loginUserId
				});
			}
			deptDutyArr.push({
				userId: loginUserId,deptDutyText: $("#deptDuty").val()
			})
			document.cookie = 'deptDutyArr='+JSON.stringify(deptDutyArr);
		})

		var xjtsErrorfn = function(){
			if($("#xjlb option:selected").text() == "年假"){
				if(parseInt($("#xjts").val()) > parseInt($("#syts").val())){
					$("#xjtsError").css({"display":"inline-block"})
				}else{
					$("#xjtsError").css({"display":"none"})
				}
			}
		}



		//点击请假类别
		$('#xjlb').on('click',function(e){
			stopPropagation(e)
            //默认请假子类
			$('.reasonsOne').addClass('firstSelecte').siblings().removeClass('firstSelecte')
            $ajax({
                url:url3,
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
        //点击地点
        $('#place').on('click',function(e){
            stopPropagation(e)
            $ajax({
                url:addressUrl,
                success:function(data){
                    var _html = '';
                    for(var i=0;i<data.list.length;i++){
                    	if(i==0){
                            _html += '<li class="bigType firstSelecte" data-id="'+data.list[i].id+'">'+data.list[i].name+'</li>'
                        }else{
                            _html += '<li class="bigType" data-id="'+data.list[i].id+'">'+data.list[i].name+'</li>'
                        }
                    }
                    $('#placeLeft').html(_html)
					var _fistId = data.list[0].id;
                    $ajax({
                        url:addressUrl,
						data:{pid:_fistId},
                        success:function(data){
                            var _html2 = '';
                            for(var j=0;j<data.list.length;j++){
                                _html2 += '<li class="bigTypeChild" data-id="'+data.list[j].id+'">'+data.list[j].name+'</li>'
                            }
                            $('#placeRight').html(_html2)
                            $('#placeBox').show()
                        }
                    })
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
                        url:url3,
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
                if(_type == 'reasons'){
                	var _type2 = $(this).attr('data-type2');
                	//如果是因公出差
                	if(_type2 == '1'){
                        $('.isPrevate').hide();
                        $('.isPublic').show();
                		//如果交通工具为无
                        if($('#vehicle').val() == '无'){
                            $('.needTwo').hide();
                        }else{
                            $('.needTwo').show();
						}
					}else{  //如果因私请假
                        $('.isPublic').hide();
                        if($('#vehicle').val() == '无'){
                            $('.isPrevate').hide();
                        }else{
                            $('.isPrevate').show();
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
            if(!$('#xjlb').val()){return;}
            var _type = $('#xjlb').attr('data-type');
			if(v != '无'){
                if(_type == '0'){
                    $('.isPrevate').show();
                    $('.isPublic').hide();
				}else{
                    $('.isPrevate').hide();
                    $('.isPublic').show();
                    $('.needTwo').show();
				}
			}else{
				if(_type == '0'){
					$('.isPrevate').hide();
					$('.isPublic').hide();
				}else{
					$('.isPrevate').hide();
					$('.isPublic').show();
					$('.needTwo').hide();
				}
			}
        })
    }


	return{
		//加载页面处理程序
		initControl:function(){
			initloginUser();
            initvehicle();
			initother();
		}

	}

}();

var GetDateDiff=function(startDate,endDate){
	var startDate = new Date(Date.parse(startDate.replace(/-/g,"/"))).getTime();
	var endDate = new Date(Date.parse(endDate.replace(/-/g,"/"))).getTime();
	var dates=Math.abs((startDate-endDate))/(1000*60*60*24);
	return dates+1;
}
var getCookie = function(name){
	var arr,reg = new RegExp("(^|)"+name+"=([^;]*)(;|$)");
	if(arr = document.cookie.match(reg)){
		return unescape(arr[2]);
	}else{
		return null
	}
}


//阻止冒泡
function stopPropagation(e){
    if(e.stopPropagation){
        e.stopPropagation()
    }else{
        e.cancelBubble = true;
    }
}
