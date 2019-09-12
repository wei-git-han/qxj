var saveUrl = { "url": "/app/qxjgl/personconfig/save", "dataType": "text" };
var editUrl = { "url": "/app/qxjgl/personconfig/update", "dataType": "text" };
var userTree = { "url": "/app/base/user/tree", "dataType": "text" }; //人员选择树
var getInfo = { "url": "/app/qxjgl/personconfig/info", "dataType": "text" };
var id = getUrlParam('id')
var pageModule = function() {
    var year = new Date().getFullYear()
    $('#year').val(year)
    var initother = function() {
        // 编辑调用
        if(id){
            $ajax({
                url:getInfo,
                data:{id:id},
                success:function (data) {
                    $('#year').val(data.year)
                    $('#shouldtakdays').val(data.shouldtakdays)
                }
            })
        }
        $("#year").datepicker({
            format: "yyyy",
            language: "zh-CN",
            rtl: Metronic.isRTL(),
            orientation: "right",
            autoclose: true,
            startView: 2,
            maxViewMode: 2,
            minViewMode: 2
        }).on('changeDate', function(end) {

            //$("#xjts").val(GetDateDiff($("#xjsjFrom").val(),$("#xjsjTo").val()));
        });

        $("#quxiao").click(function() {
            newbootbox.newdialogClose("adddialog");
        })

        $("#save").click(function() {
            var elementarry =  ["shouldtakdays","year"];
            var paramdata = getformdata(elementarry);
          //编辑保存
            if(id){
                paramdata.id=id
                $ajax({
                    url: editUrl,
                    data: paramdata,
                    type: "GET",
                    success: function(data) {
                        newbootbox.newdialogClose("adddialog");
                        if (data.msg == "success") {
                            newbootbox.alertInfo('保存成功！').done(function() {
                                window.top.iframe1.window.iframe2.window.pageModule.reload();
                            });
                        } else {
                            newbootbox.alertInfo("保存失败！");
                        }
                    }
                });
             //新增保存
            }else{
                $ajax({
                    url: saveUrl,
                    data: paramdata,
                    type: "GET",
                    success: function(data) {
                        newbootbox.newdialogClose("adddialog");
                        if (data.msg == "success") {
                            newbootbox.alertInfo('保存成功！').done(function() {
                                window.top.iframe1.window.iframe2.window.pageModule.reload();
                            });
                        }  else {
                            newbootbox.alertInfo("保存失败！");
                        }
                    }
                });
            }


        })
    }

    return {
        //加载页面处理程序
        initControl: function() {
            initother();
        }
    };

}();