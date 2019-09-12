var menuNav = {url:'common/data/menu.json',dataType:'text'};
var acquireLoginPersonRole = {url:'/leave/apply/acquireLoginPersonRole',dataType:'text'};
var role =1
var pageModule = function(){

	var initMenu= function () {
		$ajax({
			url:menuNav,
			//data:{menuId:menuId},
			async: false,
			success:function(data){
				var newData = setMenu(data)
				$.each(newData,function(i,item){
					if(i==0){
						$("#menuNav").append('<li class="menu2_list2 active"><a href="'+item.href+'" target="iframe2">'+item.text+'</a></li>');
						$("#iframe2").attr("src",item.href);
					}else{
						$("#menuNav").append('<li class="menu2_list2"><a href="'+item.href+'" target="iframe2">'+item.text+'</a></li>');
					}
				});
				
				$(".menu2_list2>a").click(function(){
					$(".menu2_list2").removeClass("active");
					$(this).parent().addClass("active");
				});
			}
		});
	}
	var haveRole = function(){
		$ajax({
			url:acquireLoginPersonRole,
			//data:{menuId:menuId},
			async: false,
			success:function(data){
				if (data.loginPersonRole == 6 || data.loginPersonRole == 4) {
					role = 6;
				}
				if (data.loginPersonRole == 5) {
					role = 5;
				}
				if (data.loginPersonRole == 3) {
					role = 4;
				}
			}
		})
	}
	var setMenu = function(menuList){
		var newArr = []
		$.each(menuList,function(i,v){
			if(v.role.indexOf(role)>-1){
				newArr.push(v)
			}
		})
		return newArr
	}
	return{
		//加载页面处理程序
		initControl:function(){
			haveRole()
			initMenu();
		}
	}
	
}();


	