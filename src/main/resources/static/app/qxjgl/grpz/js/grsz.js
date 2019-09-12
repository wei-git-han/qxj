var menuNav = { url: './data/menu.json', dataType: 'text' };
var pageModule = function() {

    var initMenu = function() {
        $ajax({
            url: menuNav,
            //data:{menuId:menuId},
            async: false,
            success: function(data) {
                $.each(data, function(i, item) {
                    if (i == 0) {
                        $("#menuNav").append('<li class="menu2_list2 active"><a href="' + item.href + '" target="iframe2">' + item.text + '</a></li>');
                        $("#iframe2").attr("src", item.href);
                    } else {
                        $("#menuNav").append('<li class="menu2_list2"><a href="' + item.href + '" target="iframe2">' + item.text + '</a></li>');
                    }
                });

                $(".menu2_list2>a").click(function() {
                    $(".menu2_list2").removeClass("active");
                    $(this).parent().addClass("active");
                });
            }
        });
    }

    return {
        //加载页面处理程序
        initControl: function() {
            initMenu();
        }
    }

}();