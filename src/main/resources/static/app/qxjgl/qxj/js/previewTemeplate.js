var previewUrl = {"url":rootPath +"/application/getQjspd","dataType":"text"};//审批预览接口ype : 0：审批单预览；1：私家车长途外出审批单预览；2：长途车审批表预览
var type = window.location.href.split('?')[1].split('=')[1];
$ajax({
    url:previewUrl,
    data:{type:type},
    success:function(res){
        $('.isLoding').hide();
        $('#OFD-div').show();
        openOFDFile(res.downFormatIdUrl, "PLUGIN_INIT_OFDDIV",$("#embedwrap").width(),$("#embedwrap").height(), "yes");
    }
});