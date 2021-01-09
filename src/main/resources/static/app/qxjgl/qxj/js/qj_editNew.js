var url2 = {"url": rootPath + "/dicvocationsort/dict","dataType":"text"} //字典请假类型
var url1 = {"url":"/app/qxjgl/application/editInfo","dataType":"text"};  //编辑详情
var chooseOneJuPersons = {"url":"/leave/apply/chooseOneJuPersons","dataType":"text"};
var saveOrUpdateLeaveUrl = {"url": "/app/qxjgl/application/saveLeaveApplication","dataType":"text"}//保存或修改请假单
//var url4 = {"url": rootPath + "/leaveorback/getUser","dataType":"text"} //获取登录人
var getDefaultParamUrl = {"url": "/app/qxjgl/application/getDefaultParam","dataType":"text"} //获取登录人信息
var allUserTreeUrl = {"url":"/app/base/user/allTree","dataType":"text"};//所有人员树
var returnDate = {"url":rootPath +"/leaveOrBack/calculateHolidays","dataType":"text"};
var loginUserId = getUrlParam("loginUserId")||"";//登录人Id
var url3 = {"url": rootPath + "/dicvocationsort/type","dataType":"text"} //入参type 0请假类型；1因公出差；2交通工具类型  出参：result：success；list）
var addressUrl = {"url": rootPath + "/provincecitydistrict/getPCD","dataType":"text"} //地点接口入参：pid：默认不传，出参：result：success；list，，该接口点击请假申请时调用
var id = getUrlParam('id');
var allFlowPeopleTreeUrl = {"url":"/app/base/user/allTxlUserTree","dataType":"text"};//所有跟随人员树
var FlowPeopleUrl = {"url":"/app/base/user/getPersonTxlUser","dataType":"text"};//跟随具体人员
var fromMsg= getUrlParam("fromMsg");
var flowLength = 0,dataLenth = 0;
var _flag = '';

var qjType = '';




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

    var initother = function(){
        //取消编辑
        $("#close").click(function(){
            newbootbox.newdialogClose("qjEdit");
        })
        $('#vehicle').click(function(){
            if($("#vehicle").html() == ''){
                newbootbox.alertInfo("暂无数据请配置交通工具！")
            }
        })

        $(document)
            //添加地点
            .on('click','.addAddress',function(){
                dataLenth ++;
                var _html = `<div class="addAddressList">
                                <div class="form-group">
                                    <label class="col-xs-2  control-label text-right">地点<span class="required">*</span>：</label>
                                    <div class="col-xs-10" style="width: 33%">
                                        <input class="form-control place" id="place" name="place" required="required" placeholder="请选择" style="background: #fff;" readonly/>
                                        <label class="error" for="place" style="display: none">这是必填字段</label>
                                        <div id="placeBox" class="placeBox" style="position: absolute;z-index: 100;display: none">
                                            <div style="display: flex;padding: 1px;border: 1px solid #ddd;min-width: 298px;background: #ddd;">
                                                <ul style="line-height: 25px;flex:1;max-width:135px;max-height: 170px;overflow-y: auto" id="placeLeft" class="listLeft placeLeft"></ul>
                                                <ul style="line-height: 25px;flex:1;background: #fff;text-align: center;max-height: 170px;overflow-y: auto" class="placeRight" id="placeRight"></ul>
                                            </div>
                                        </div>
                                    </div>
    
                                    <label class="col-xs-2 control-label">起止时间 <span class="required" style="margin-top: 8px;">*</span>：</label>
                                    <div class="col-xs-4" style="position:relative;">
                                        <div class="input-group  date date-picker" data-date-format="yyyy-mm-dd" style="width:50%;float:left;">
                                            <input type="text" class="form-control datee" id="xjsjFrom${dataLenth}" name="xjsjFrom${dataLenth}" required="required" style="background:#fff;cursor:pointer" />
                                            <span class="input-group-btn">
                                        <button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
                                    </span>
                                        </div>
                                        <div class="input-group  date date-picker" data-date-format="yyyy-mm-dd" style="width:50%;float:left;">
                                            <input type="text" class="form-control datee" id="xjsjTo${dataLenth}" name="xjsjTo${dataLenth}" required="required" style="background:#fff;cursor:pointer" />
                                            <span class="input-group-btn">
                                        <button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
                                    </span>
                                        </div>
    
                                    </div>
                                    <i class="removeAddress fa fa-minus-circle" style="color: #007eff;cursor: pointer"></i>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-xs-2 control-label">具体位置：</label>
                                        <input class="form-control detailedAddress" style="width: 33% !important;float: left" placeholder="请填写具体位置"/>
                                        <label class="col-xs-2 control-label">风险等级：</label>
                                        <div class="col-xs-4" style="float: none !important;display: inline-block">
                                            <select class="form-control fxdj">
                                                <option value="无风险">无风险</option>
                                                <option value="低风险">低风险</option>
                                                <option value="中风险">中风险</option>
                                                <option value="高风险">高风险</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>`
                $('.addAddressBox').append(_html)

                $("#xjsjFrom"+dataLenth).datepicker({
                    format:"yyyy-mm-dd",
                    language:"zh-CN",
                    rtl: Metronic.isRTL(),
                    orientation: "right",
                    startDate:new Date(),
                    autoclose: true,
                }).on('changeDate',function(end){
                    $("#xjsjTo"+dataLenth).datepicker("setStartDate",$("#xjsjFrom"+dataLenth).val());
                    var starday = new Date($("#xjsjFrom"+dataLenth).val());
                    var endday = new Date($("#xjsjTo"+dataLenth).val());
                    if(endday < starday){
                        $("#xjsjTo"+dataLenth).datepicker("setDate",$("#xjsjFrom"+dataLenth).val());
                    };
                    $ajax({
                        url:returnDate,
                        dataType:'POST',
                        data:{startDateStr:$("#xjsjFrom"+dataLenth).val(),toDateStr:$("#xjsjTo"+dataLenth).val()},
                        success:function(data){
                            setformdata(data);
                        }
                    });
                });
                $("#xjsjTo"+dataLenth).datepicker({
                    format:"yyyy-mm-dd",
                    language:"zh-CN",
                    rtl: Metronic.isRTL(),
                    orientation: "right",
                    startDate:new Date(),
                    autoclose: true,
                }).on('changeDate',function(end){
                    $("#xjsjFrom"+dataLenth).datepicker("setEndDate",$("#xjsjTo"+dataLenth).val());
                    $ajax({
                        url:returnDate,
                        dataType:'POST',
                        data:{startDateStr:$("#xjsjFrom"+dataLenth).val(),toDateStr:$("#xjsjTo"+dataLenth).val()},
                        success:function(data){
                            setformdata(data);
                        }
                    })
                });
            })
            .on('click','.addFlowPeople',function(){
                flowLength++;
                var _html = `<div class="form-group flowPeopleList">
                                <label class="col-xs-2  control-label text-right">随员：</label>
                                <div class="col-xs-3">
                                    <div class="form-control" style="padding:0">
                                        <div class="selecttree">
                                            <input type="text" class="form-control flowPeople" id="flowPeople${flowLength}"  style="background-color: #FFF;"/>
                                            <input type="hidden" class="form-control flowPeopleId" id="flowPeople${flowLength}Id"/>
                                        </div>
                                    </div>
                                </div>
                                <input class="form-control bzb" id="flowPeople${flowLength}bzb" style="width: 17% !important;float: left;margin-right: 5px" placeholder="部职别"/>
                                <input class="form-control zj" style="width: 15% !important;float: left;margin-right: 5px" placeholder="职级"/>
                                <div class="col-xs-3" style="width: 23% !important;position: relative;float: none;float: left">
                                    <input class="form-control sfhsjc" placeholder="本人核酸检测结果" style="background: #fff" readonly/>
                                    <div class="hsjcBox" style="position: absolute;z-index: 100;display: none">
                                        <div style="display: flex;padding: 1px;border: 1px solid #ddd;min-width: 180px;background: #ddd;">
                                            <ul style="line-height: 25px;flex: 1" class="listLeft">
                                                <li class="bigType reasonsTwo firstSelecte" data-type="hsjc" data-type2="1">有要求</li>
                                                <li class="bigType reasonsOne" data-type="hsjc" data-type2="0">无要求</li>
                                            </ul>
                                            <ul style="line-height: 25px;flex:1;background: #fff;text-align: center" class="listRight">
                                                <li class="bigTypeChild" data-type="hsjc">阴性</li>
                                                <li class="bigTypeChild" data-type="hsjc">阳性</li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                                <i class="removeAddress fa fa-minus-circle" style="color: #007eff;cursor: pointer"></i>
                            </div>`
                $('.flowPeopleBox').append(_html)
                //随员树
                $("#flowPeople"+flowLength).createNewUserTree({
                    url : allFlowPeopleTreeUrl,
                    width : "100%",
                    //plugins:'checkbox',
                    params:{id:1},
                    success : function(data, treeobj) {},
                    selectnode : function(e, data,treessname,treessid,el,post) {
                        $ajax({
                            url:FlowPeopleUrl,
                            dataType:'POST',
                            data:{userIds:treessid.toString()},
                            success:function(data){
                                if (data.result == 'fail') {
                                    newbootbox.alert("请选择同一个局的人！");
                                }
                            }
                        });
                        $("#"+el+'Id').val(treessid);
                        $("#"+el+'bzb').val(post);
                        $("#"+el).val(treessname);
                    }
                });
            })
            //删除地点
            .on('click','.removeAddress',function(){
                $(this).parents('.flowPeopleList').remove();
                $(this).parents('.addAddressList').remove();
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
        $("#xjsjFrom0").datepicker({
            format:"yyyy-mm-dd",
            language:"zh-CN",
            rtl: Metronic.isRTL(),
            orientation: "right",
            startDate:new Date(),
            autoclose: true,
        }).on('changeDate',function(end){
            $("#xjsjTo0").datepicker("setStartDate",$("#xjsjFrom0").val());
            var starday = new Date($("#xjsjFrom0").val());
            var endday = new Date($("#xjsjTo0").val());
            if(endday < starday){
                $("#xjsjTo0").datepicker("setDate",$("#xjsjFrom0").val());
            };
            $ajax({
                url:returnDate,
                dataType:'POST',
                data:{startDateStr:$("#xjsjFrom0").val(),toDateStr:$("#xjsjTo0").val()},
                success:function(data){
                    setformdata(data);
                }
            });
        });
        $("#xjsjTo0").datepicker({
            format:"yyyy-mm-dd",
            language:"zh-CN",
            rtl: Metronic.isRTL(),
            orientation: "right",
            startDate:new Date(),
            autoclose: true,
        }).on('changeDate',function(end){
            $("#xjsjFrom0").datepicker("setEndDate",$("#xjsjTo0").val());
            $ajax({
                url:returnDate,
                dataType:'POST',
                data:{startDateStr:$("#xjsjFrom0").val(),toDateStr:$("#xjsjTo0").val()},
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
                                    url:{"url": rootPath + "/dicvocationsort/dict","dataType":"text"},
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
        //随员树
        $("#flowPeople").createNewUserTree({
            url : allFlowPeopleTreeUrl,
            width : "100%",
            //plugins:'checkbox',
            params:{id:1},
            success : function(data, treeobj) {},
            selectnode : function(e, data,treessname,treessid,el,post) {
                $ajax({
                    url:FlowPeopleUrl,
                    dataType:'POST',
                    data:{userIds:treessid.toString()},
                    success:function(data){
                        if (data.result == 'fail') {
                            newbootbox.alert("请选择同一个局的人！");
                        }
                    }
                });
                $("#flowPeopleId").val(treessid);
                $('#flowPeoplebzb').val(post);
                $("#flowPeople").val(treessname);
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
                    "csldId","csparea","qjzt","spzt","mobile","origin","orgId","parentOrgId","orgName","turnOver","zhiji",
                    'status',"holidayNum","weekendNum","linkMan","linkManId","undertaker","undertakerId","undertakerMobile"];
                var paramdata = getformdata(elementarry);
                paramdata.id=id; //请假单id
                //paramdata.xjlb = qjType
                if(!$('#vehicle').val() || $('#vehicle').val() == undefined){
                    paramdata.vehicle = '无'
                }else{
                    paramdata.vehicle = $('#vehicle').val().join();//交通工具
                }
                var place =[],city = [],address = [],level=[],startTimeStr=[],endTimeStr = [],followUserNames=[],followUserIds=[],posts=[],levels=[],checks=[],isOk=true;
                $('.addAddressBox .addAddressList').each(function(i){
                    $(this).find('.error').hide();
                    if($(this).find('.place').val() == ''){
                        $(this).find('.error').show()
                        isOk = false;
                    }
                    place.push($(this).find('.place').val().split('/')[0]);
                    city.push($(this).find('.place').val().split('/')[1]);
                    level.push($(this).find('.fxdj').val())
                    startTimeStr.push($(this).find('.datee:first').val());
                    endTimeStr.push($(this).find('.datee:last').val());
                    if($.trim($(this).find('.detailedAddress').val()) == ''){
                        address.push('无')
                    }else{
                        address.push($.trim($(this).find('.detailedAddress').val()))
                    }
                })
                $('.flowPeopleBox .flowPeopleList').each(function(i){
                    followUserNames.push($(this).find('.flowPeople').val());
                    followUserIds.push($(this).find('.flowPeopleId').val());
                    if($(this).find('.bzb').val() == ''){
                        posts.push('null')

                    }else{
                        posts.push($(this).find('.bzb').val())
                    }
                    if($(this).find('.zj').val() == ''){
                        levels.push('null')

                    }else{
                        levels.push($(this).find('.zj').val());
                    }
                    if($(this).find('.sfhsjc').val() == ''){
                        checks.push('null')

                    }else{
                        checks.push($(this).find('.sfhsjc').val());
                    }

                })
                if(!isOk){
                    return;
                }
                paramdata.place = place.join();
                paramdata.city = city.join();
                //paramdata.explain = $.trim($('#otherReasons').val());
                paramdata.address = address.join();
                paramdata.level = level.join();
                paramdata.startTimeStr = startTimeStr.join();
                paramdata.endTimeStr = endTimeStr.join();
                paramdata.result = $('#sfhsjc').val();
                //paramdata.vacationSortId = qjTypeId;
                if(qjType == 1){
                    paramdata.followUserNames = followUserNames.join();
                    paramdata.followUserIds = followUserIds.join();
                    paramdata.posts = posts.join()+',';
                    paramdata.levels = levels.join()+',';
                    paramdata.checks = checks.join()+',';
                }

                //如果因公隐藏 可一定选择了因私请假
                if(qjType == '0'){
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

                //window.parent.parent.openLoading()
                $("#qjDialog").removeClass("none");
                $ajax({
                    url:saveOrUpdateLeaveUrl,
                    type: "POST",
                    data:paramdata,
                    success:function(data){
                        $("#qjDialog").addClass("none")
                        if(data.result=="success"){
                            newbootbox.newdialogClose("qjEdit");
                            var tstext = "保存成功！"
                            newbootbox.alert(tstext).done(function(){
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

        $(document)
        //是否需要风险等级
            .on('click','.level',function(e){
                stopPropagation(e)
                $(this).parent().find('.levelBox').show();
            })
            //点击是否需要核算检测证明
            .on('click','.sfhsjc',function(e){
                stopPropagation(e)
                $(this).parent().find('.hsjcBox').show();
            })
            //点击地点
            .on('click','.place',function(e){
                var _that = $(this);
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
                        _that.parent().find('.placeLeft').html(_html)
                        var _fistId = data.list[0].id;
                        $ajax({
                            url:addressUrl,
                            data:{pid:_fistId},
                            success:function(data){
                                var _html2 = '';
                                for(var j=0;j<data.list.length;j++){
                                    _html2 += '<li class="bigTypeChild" data-id="'+data.list[j].id+'">'+data.list[j].name+'</li>'
                                }
                                _that.parent().find('.placeRight').html(_html2)
                                _that.parent().find('.placeBox').show()
                            }
                        })
                    }
                })
            })
            .on('click',function(){
                $('#reasonsBox,.placeBox,.hsjcBox').hide()
            })
            //点击请假，省类别第一级菜单
            .on('click','.bigType',function(e){
                var _that = $(this);
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
                            }else{
                                $('#listRight').html('')
                            }
                        }
                    })
                }else if(_type == 'hsjc'){
                    var _type2 = $(this).attr('data-type2');
                    if(_type2 == '0'){  //无要求
                        $(this).parent().siblings('ul').html('')
                        $(this).parents('.hsjcBox').siblings('input').val('无要求')
                        $(this).parents('.hsjcBox').hide();
                    }else{
                        $(this).parent().siblings('ul').html('<li class="bigTypeChild" data-type="hsjc">阴性</li><li data-type="hsjc" class="bigTypeChild">阳性</li>')
                    }
                }else if(_type == 'fxdj'){
                    var _type2 = $(this).attr('data-type2');
                    if(_type2 == '0'){  //不需要
                        $(this).parent().siblings('ul').html('')
                        $(this).parents('.levelBox').siblings('input').val('不需要风险等级')
                        $(this).parents('.levelBox').siblings('input').attr('data-type','0')
                        $(this).parents('.levelBox').hide();
                    }else{
                        $(this).parent().siblings('ul').html('<li class="bigTypeChild" data-v="1" data-type="fxdj">低</li><li data-type="fxdj" data-v="2" class="bigTypeChild">中</li><li class="bigTypeChild" data-v="3" data-type="fxdj">高</li>')
                    }
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
                            _that.parent().siblings('ul').html(_html)
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
//                            $('.needTwo').show();
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
                }else if(_type == 'hsjc'){
                    $(this).parents('.hsjcBox').siblings('input').val($(this).text())
                    $(this).parents('.hsjcBox').hide();
                }else if(_type == 'fxdj'){
                    $(this).parents('.levelBox').siblings('input').val($(this).text())
                    $(this).parents('.levelBox').siblings('input').attr('data-type',$(this).attr('data-v'))
                    $(this).parents('.levelBox').hide();
                }else{
                    var $text = $(this).parent().siblings('ul').find('.firstSelecte').text() + '/' + $(this).text()
                    $(this).parents('.col-xs-10').find('.place').val($text)
                    $(this).parents('.col-xs-10').find('.placeBox').hide()
                }
            })

        //选择交通工具
        $('#vehicle').off('change').on('change',function () {
            var v = $(this).val(),flag='';
            $('#vehicle').find('option:selected').each(function(){
                if($(this).attr('data-flag') == '2'){
                    flag = '2';
                }
            })
            //var flag = $("#vehicle option:selected").attr('data-flag'); // 选择的交通工具是否需要审批  2：需要审批 3：不需要审批
            //if(!$('#xjlb').val()){return;}
            var _type = qjType;
            if(v != '' && v != null){ // 选择交通工具
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

    var initdatafn = function(){
        $ajax({
            url:url1,
            data:{id:id},
            success:initdata
        })
    }
    var initdata = function(data){
        setformdata(data);
        setTimeout(function () {
            qjType = data.qjlb;
            $('#sfhsjc').val(data.result);
            if(data.qjlb == '1'){   //因公出差
                $('#xjts').parent().hide();
                $('#xjtsLabel').hide();
                $('#holidayNum').parents('.form-group').hide();
                if(data.followList && data.followList.length>0){
                    var _followList = data.followList;
                    $('.flowPeopleBox').html('');
                    for(var i=0;i<_followList.length;i++){
                        flowLength++
                        var _htmlI = '', _html ='';
                        if(i==0){
                            _htmlI= 'addFlowPeople fa-plus-circle'
                        }else{
                            _htmlI= 'removeAddress fa-minus-circle'
                        }
                        _html = `<div class="form-group flowPeopleList">
                            <label class="col-xs-2  control-label text-right">随员：</label>
                            <div class="col-xs-3">
                                <div class="form-control" style="padding:0">
                                    <div class="selecttree">
                                        <input type="text" class="form-control flowPeople" id="flowPeople${flowLength}"  style="background-color: #FFF;" value="${_followList[i].USERNAME}"/>
                                        <input type="hidden" class="form-control flowPeopleId" id="flowPeople${flowLength}Id" value="${_followList[i].USERID}"/>
                                    </div>
                                </div>
                            </div>
                            <input class="form-control bzb" id="flowPeople${flowLength}bzb" style="width: 17% !important;float: left;margin-right: 5px" placeholder="部职别" value="${_followList[i].POST||''}"/>
                            <input class="form-control zj" style="width: 15% !important;float: left;margin-right: 5px" placeholder="职级" value="${_followList[i].LEVEL||''}"/>
                            <div class="col-xs-3" style="width: 23% !important;position: relative;float: none;float: left">
                                <input class="form-control sfhsjc" placeholder="本人核酸检测结果" style="background: #fff" readonly value="${_followList[i].CHECK||''}"/>
                                <div class="hsjcBox" style="position: absolute;z-index: 100;display: none">
                                    <div style="display: flex;padding: 1px;border: 1px solid #ddd;min-width: 180px;background: #ddd;">
                                        <ul style="line-height: 25px;flex: 1" class="listLeft">
                                            <li class="bigType reasonsTwo firstSelecte" data-type="hsjc" data-type2="1">有要求</li>
                                            <li class="bigType reasonsOne" data-type="hsjc" data-type2="0">无要求</li>
                                        </ul>
                                        <ul style="line-height: 25px;flex:1;background: #fff;text-align: center" class="listRight">
                                            <li class="bigTypeChild" data-type="hsjc">阴性</li>
                                            <li class="bigTypeChild" data-type="hsjc">阳性</li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                             <i class="fa ${_htmlI}" style="color: #007eff;cursor: pointer"></i>
                        </div>`
                        $('.flowPeopleBox').append(_html);
                        $("#flowPeople"+flowLength).createNewUserTree({
                            url : allFlowPeopleTreeUrl,
                            width : "100%",
                            //plugins:'checkbox',
                            params:{id:1},
                            success : function(data, treeobj) {},
                            selectnode : function(e, data,treessname,treessid,el,post) {
                                $ajax({
                                    url:FlowPeopleUrl,
                                    dataType:'POST',
                                    data:{userIds:treessid.toString()},
                                    success:function(data){
                                        if (data.result == 'fail') {
                                            newbootbox.alert("请选择同一个局的人！");
                                        }
                                    }
                                });
                                $("#"+el+'Id').val(treessid);
                                $("#"+el+'bzb').val(post);
                                $("#"+el).val(treessname);
                            }
                        });
                    }
                }
            }else{
                $('.flowPeopleBox').hide();
            }

            //地点
            if(data.plcaeCityList){
                $('.addAddressBox').html('')
                for(var i=0;i<data.plcaeCityList.length;i++){
                    dataLenth++;
                    var _htmlI='',_html2 = '';
                    if(i==0){
                        _htmlI= '<i class="addAddress fa fa-plus-circle" style="color: #007eff;cursor: pointer"></i>'
                    }else{
                        _htmlI= '<i class="removeAddress fa fa-minus-circle" style="color: #007eff;cursor: pointer"></i>'
                    }
                    _html2 = `<div class="addAddressList">
                            <div class="form-group">
                                <label class="col-xs-2  control-label text-right">地点<span class="required">*</span>：</label>
                                <div class="col-xs-10" style="width: 33%">
                                    <input class="form-control place" id="place" name="place" required="required" placeholder="请选择" style="background: #fff;" value="${data.plcaeCityList[i].place}/${data.plcaeCityList[i].city}" readonly/>
                                    <label class="error" for="place" style="display: none">这是必填字段</label>
                                    <div id="placeBox" class="placeBox" style="position: absolute;z-index: 100;display: none">
                                        <div style="display: flex;padding: 1px;border: 1px solid #ddd;min-width: 298px;background: #ddd;">
                                            <ul style="line-height: 25px;flex:1;max-width:135px;max-height: 170px;overflow-y: auto" id="placeLeft" class="listLeft placeLeft"></ul>
                                            <ul style="line-height: 25px;flex:1;background: #fff;text-align: center;max-height: 170px;overflow-y: auto" class="placeRight" id="placeRight"></ul>
                                        </div>
                                    </div>
                                </div>

                                <label class="col-xs-2 control-label">起止时间 <span class="required" style="margin-top: 8px;">*</span>：</label>
                                <div class="col-xs-4" style="position:relative;">
                                    <div class="input-group  date date-picker" data-date-format="yyyy-mm-dd" style="width:50%;float:left;">
                                        <input type="text" class="form-control datee" id="xjsjFrom${dataLenth}" name="xjsjFrom${dataLenth}" required="required" style="background:#fff;cursor:pointer" value="${dateForm(data.plcaeCityList[i].startTime)}"/>
                                        <span class="input-group-btn">
                                    <button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
                                </span>
                                    </div>
                                    <div class="input-group  date date-picker" data-date-format="yyyy-mm-dd" style="width:50%;float:left;">
                                        <input type="text" class="form-control datee" id="xjsjTo${dataLenth}" name="xjsjTo${dataLenth}" required="required" style="background:#fff;cursor:pointer" value="${dateForm(data.plcaeCityList[i].endTime)}" />
                                        <span class="input-group-btn">
                                    <button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
                                </span>
                                    </div>

                                </div>
                                ${_htmlI}
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-2 control-label">具体位置：</label>
                                    <input class="form-control detailedAddress" style="width: 33% !important;float: left" placeholder="请填写具体位置" value="${data.plcaeCityList[i].address || ''}"/>
                                    <label class="col-xs-2 control-label">风险等级：</label>
                                    <div class="col-xs-4" style="float: none !important;display: inline-block">
                                        <select class="form-control fxdj" value="${data.plcaeCityList[i].level}">
                                            <option ${data.plcaeCityList[i].level=='无风险'?"selected='selected'":""} value="无风险">无风险</option>
                                            <option ${data.plcaeCityList[i].level=='低风险'?"selected='selected'":""} value="低风险">低风险</option>
                                            <option ${data.plcaeCityList[i].level=='中风险'?"selected='selected'":""} value="中风险">中风险</option>
                                            <option ${data.plcaeCityList[i].level=='高风险'?"selected='selected'":""} value="高风险">高风险</option>
                                        </select>
                                    </div>
                                </div>
                            </div>`
                    $('.addAddressBox').append(_html2)
                    $("#xjsjFrom"+dataLenth).datepicker({
                        format:"yyyy-mm-dd",
                        language:"zh-CN",
                        rtl: Metronic.isRTL(),
                        orientation: "right",
                        startDate:new Date(),
                        autoclose: true,
                    }).on('changeDate',function(end){
                        $("#xjsjTo"+dataLenth).datepicker("setStartDate",$("#xjsjFrom"+dataLenth).val());
                        var starday = new Date($("#xjsjFrom"+dataLenth).val());
                        var endday = new Date($("#xjsjTo"+dataLenth).val());
                        if(endday < starday){
                            $("#xjsjTo"+dataLenth).datepicker("setDate",$("#xjsjFrom"+dataLenth).val());
                        };
                        $ajax({
                            url:returnDate,
                            dataType:'POST',
                            data:{startDateStr:$("#xjsjFrom"+dataLenth).val(),toDateStr:$("#xjsjTo"+dataLenth).val()},
                            success:function(data){
                                setformdata(data);
                            }
                        });
                    });
                    $("#xjsjTo"+dataLenth).datepicker({
                        format:"yyyy-mm-dd",
                        language:"zh-CN",
                        rtl: Metronic.isRTL(),
                        orientation: "right",
                        startDate:new Date(),
                        autoclose: true,
                    }).on('changeDate',function(end){
                        $("#xjsjFrom"+dataLenth).datepicker("setEndDate",$("#xjsjTo"+dataLenth).val());
                        $ajax({
                            url:returnDate,
                            dataType:'POST',
                            data:{startDateStr:$("#xjsjFrom"+dataLenth).val(),toDateStr:$("#xjsjTo"+dataLenth).val()},
                            success:function(data){
                                setformdata(data);
                            }
                        })
                    });
                }
            }

            //交通工具下拉
            $ajax({
                url:url3,
                async:true,
                data:{type:'2'},
                success:function(res){
                    var html = "";
                    $.each(res.list,function(i){
                        if(res.list[i].text == '无'){
                            html+='<option value='+res.list[i].id+' data-flag='+res.list[i].flag+'>'+res.list[i].text+'</option>';
                        }else{
                            html+='<option value='+res.list[i].id+' data-flag='+res.list[i].flag+'>'+res.list[i].text+'</option>';
                        }
                    });
                    $("#vehicle").html(html);
                    $("#vehicle").selectpicker('refresh');
                    $("#vehicle").selectpicker('val',data.vehicle.split(","));
                    $("#vehicle").selectpicker('refresh');

                    if(data.vehicle){
                        var carList = data.vehicle.split(",");
                        for(var j=0;j<res.list.length;j++){
                            for(var s=0;s<carList.length;s++){
                                if(carList[s] == res.list[j].id){
                                    if(res.list[j].flag == '2'){
                                        _flag = 2;
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    if(data.qjlb == '0'){// 因私请假alert('因私请假')
                        if(_flag == '2') { // 因私请假 选择的交通工具需要审批  页面显示“地方驾驶证号”、“车辆号牌”、“驾车人”、“乘坐人员”
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
                        if(_flag == '2') { // 因公出差 选择的交通工具需要审批  页面显示“到达单位”、“车型及出车数量”、“乘员及装载货物情况”
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

                }
            })
        },50)
    };

    return{
        //加载页面处理程序
        initControl:function(){
            initloginUser();
            //initvehicle();
            initother();
            initdatafn();
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

function refreshThis() {
    $("#vehicle").selectpicker('refresh');
}

function dateForm(time) {
    var date = new Date(time);
    var month = date.getMonth()+1;
    if(month<10){
        month = "0"+month;
    }
    var day = date.getDate();
    if(day<10){
        day = "0"+day;
    }
    var year = date.getFullYear();
    return year + '-' + month + '-' + day
}