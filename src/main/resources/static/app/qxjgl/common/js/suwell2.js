/**
 * shuwen	数科服务以及页签切换公用方法
 * @time 2017-08-29
 */
var ocx = null;
/**
* @Title: 当前文档中的签章或签名个数
* @Author: jekyll14(zhang xiaoming)
* @CreateTime: 2019/1/11 21:15
*/
var sealcountinofdfile = 0;


/**
 * 初始化数科插件，打开pdf文档
 * 
 * @param filePath
 *            文件路径
 * @param webPath
 *            域名
 */
function openOFDFile(filePath, areaDivId,width,height, optionType) {
	if (filePath != '' && filePath != null) {
		ocx = suwell.ofdReaderInit(areaDivId, "100%", "100%");
		if (ocx) {
			ocx.registListener("f_open", "openperform", false);
			ocx.openFile(filePath, false);
			ocx.registListener("f_open", "openafterform", false);
			//隐藏工具栏，导航栏
			ocx.setCompsiteVisible("navigator",false);
			ocx.setCompsiteVisible('toolbar',false);
			
			ocx.setCompsiteVisible('t_toolfile',false);
			ocx.setCompsiteVisible('menu',false);
			ocx.setCompsiteVisible('menu_tool',false);
			 
			 
			ocx.setScale(100); 
			// 隐藏顺时针旋转90度
			//基础工具栏 选择复制工具放开
			ocx.setCompsiteVisible("toolbar_basetool",false);
			
			ocx.setCompsiteVisible("toolbar_annott",false);
			ocx.setCompsiteVisible("t_freetext",false);
			
			ocx.setCompsiteVisible("toolbar_edit",false);
			// 隐藏图形注解工具栏
			ocx.setCompsiteVisible("toolbar_annotp",false);
			//隐藏区域手写签批
			ocx.setCompsiteVisible("t_tabletdlg",false);
			//隐藏区域手写签批
			ocx.setCompsiteVisible("t_tablet",false);
			//隐藏区域手型工具（阅读模式）
			ocx.setCompsiteVisible("t_handtool",false);
            //注册保存事件的监听
			ocx.registListener("f_saveurl", "openperform", false);
			ocx.registListener("f_saveurl", "saveaftertodo", false);
		}
	}
}

