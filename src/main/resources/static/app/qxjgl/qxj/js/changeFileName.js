var url1 = {"url":rootPath +"/documentfile/update","dataType":"text"};
var id = getUrlParam("id");
var fileName = decodeURI(getUrlParam("fileName")).split('.')[0];
var fileType = decodeURI(getUrlParam("fileName")).split('.')[1];
var fromPage = getUrlParam("fromPage")
var pageModule = function(){
    var initother = function(){
        $("#fileName").val(fileName);
        $("#commentForm").validate({
            submitHandler: function() {
                var newName = $("#fileName").val();
                $ajax({
                    url:url1,
                    data:{newName:newName+'.'+fileType,id:id},
                    success:function(data){
                        if(data.msg=="success"){
                            newbootbox.alert("保存成功！").done(function(){
                            	 newbootbox.newdialogClose("reNameDialog");
                                window.top.iframe1.refreshFileList()
                            });
                        }else{
                            newbootbox.alert("重命名失败！");
                            newbootbox.newdialogClose("reNameDialog");
                        }
                    }
                })
            }
        });
        $("#baocun").click(function(){
            $("#commentForm").submit();
        })
        $("#quxiao").click(function(){
            newbootbox.newdialogClose("reNameDialog");
        })
    }

    return{
        //加载页面处理程序
        initControl:function(){
            initother();
        }
    }

}();