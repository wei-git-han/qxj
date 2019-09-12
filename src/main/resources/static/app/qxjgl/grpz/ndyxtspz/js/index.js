var tableUrl = { "url": "/app/qxjgl/personconfig/list", "dataType": "text" };
var delUrl = { "url": "/app/qxjgl/personconfig/delete", "dataType": 'json' };
var grid = null;
var pageModule = function() {
    var initgrid = function() {
        grid = $("#gridcont").createGrid({
            columns: [/*{
                    display: "年度",
                    name: "year",
                    width: "40%",
                    align: "center",
                    render: function(rowdata) {
                        return rowdata.year;
                    }
                },*/ {
                    display: "应休假天数(单位/天)",
                    name: "shouldtakdays",
                    width: "70%",
                    align: "center",
                    render: function(rowdata) {
                        return rowdata.shouldtakdays;
                    }
                },
                {
                    display: "操作",
                    name: "cz",
                    width: "30%",
                    align: "center",
                    render: function(rowdata) {
                        var str= '<a onclick="edit(\''+rowdata.id+'\')" style="padding: 0 5px;font-size: 14px;text-decoration: underline;;color: #6699ff">编辑</a>';
                        /*<a style="padding: 0 5px;font-size: 14px;text-decoration: underline;color: #6699ff" onclick="deletedata(\''+rowdata.id+'\')">删除</a>*/
                        return str;
                    }
                }
            ],

            width: "100%",
            height: "100%",
            checkbox: false,
            rownumberyon: false,
            paramobj: {},
            overflowx: false,
            pagesize: 15,
            url: tableUrl,
            loadafter:function(data){
                if(data.rows.length==0){
                    $('#addBtn').show()
                }
            }
        });

    }

    var initother = function() {
        $("#add").click(function() {
            newbootbox.newdialog({
                id: "adddialog",
                width: 700,
                height: 400,
                header: true,
                title: "新增",
                url: "/app/qxjgl/grpz/ndyxtspz/html/add.html"
            });
        });

    }

    return {
        //加载页面处理程序
        initControl: function() {
            initgrid();
            initother();
        },
        reload: function() {
            window.location.reload();
        }
    }

}();


function edit(id){
    newbootbox.newdialog({
        id: "adddialog",
        width: 700,
        height: 400,
        header: true,
        title: "编辑",
        url: "/app/qxjgl/grpz/ndyxtspz/html/add.html?id="+id
    });
}
function  deletedata(id) {
    if(!id){
        newbootbox.alertInfo('删除失败')
        return
    }
    newbootbox.confirm({
        title: "提示",
        message: "是否要进行删除操作？",
        callback1: function() {
            $ajax({
                url: delUrl,
                type: "GET",
                data: { "id": id },
                success: function(data) {
                    if (data.msg == "success") {
                        newbootbox.alertInfo('删除成功！').done(function() {
                            grid.refresh();
                        });
                    } else {
                        newbootbox.alertInfo("删除失败！");
                    }
                }
            })
        }
    });
}