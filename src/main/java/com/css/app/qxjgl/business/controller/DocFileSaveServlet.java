package com.css.app.qxjgl.business.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.css.addbase.AppConfig;
import com.css.addbase.FileBaseUtil;
import com.css.addbase.suwell.OfdTransferUtil;
import com.css.app.qxjgl.business.entity.DocumentFile;
import com.css.app.qxjgl.business.service.DocumentFileService;
import com.css.base.utils.UUIDUtils;

import cn.com.css.filestore.impl.HTTPFile;
import dm.jdbc.util.StringUtil;
@WebServlet("/app/qxjgl/servlet/streamFile/save")
public class DocFileSaveServlet extends HttpServlet {
	
	/**
	 * 保存信息
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private DocumentFileService documentFileService;
	@Autowired
	private AppConfig appConfig;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {		
		String streamOrFormatFileType = request.getParameter("streamOrFormatFileType");//流式或者版式的保存
		String qxjDocId = request.getParameter("fileId");
		String windowsFlag = request.getParameter("windowsFlag");
		System.out.println("*****************开始本地编辑流式以及版式的上传保存*********************");
		String httpFileId = "";//上传后返回的最新的流式或者版式的文件服务ID
		DocumentFile draft = documentFileService.queryObject(qxjDocId);
		if(draft != null) {			
			String suffix=draft.getFileName().substring(draft.getFileName().lastIndexOf("."));
			System.out.println("**********开始保存文件***********" + System.currentTimeMillis());
			httpFileId = FileBaseUtil.fileuploadtoFileServerReturnID(request,qxjDocId+"."+suffix,streamOrFormatFileType);
            System.out.println("**********完成保存文件***********" + System.currentTimeMillis()+"httpFileID==" + httpFileId);
		}
		if(StringUtils.equals("format", streamOrFormatFileType)) {//版式文件的保存
			if(StringUtils.isNotBlank(httpFileId)) {
				draft.setFileServerFormatId(httpFileId);
			}
		}else {
			if(StringUtils.isNotBlank(httpFileId)) {
				draft.setFileServerStreamId(httpFileId);
				if(StringUtil.equals("1", windowsFlag)) {
					HTTPFile hf = new HTTPFile(httpFileId);
					String formatId = "";
					try {
						String path = appConfig.getLocalFilePath() + UUIDUtils.random() + "." + hf.getSuffix();
						try {
							FileUtils.moveFile(new File(hf.getFilePath()) , new File(path));
						} catch (IOException e) {
							e.printStackTrace();
						}
						if(StringUtils.isNotBlank(path)){
							formatId = OfdTransferUtil.convertLocalFileToOFD(path);
						}
						//删除本地的临时流式文件
						if(new File(path).exists()){
							new File(path).delete();
						}
						draft.setFileServerFormatId(formatId);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		documentFileService.update(draft);
	}
}
