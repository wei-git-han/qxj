package com.css.app.qxjgl.business.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.css.addbase.FileBaseUtil;
import com.css.app.qxjgl.business.entity.DocumentFile;
import com.css.app.qxjgl.business.service.DocumentFileService;

@WebServlet("/servlet/opinion/localTransferSave")
public class LocalTransferOpinionUpdateServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	@Autowired
	private DocumentFileService documentFileService;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		String fileName = request.getParameter("fileName");
		String qxjDocId = request.getParameter("fileId");
		String streamOrFormatFileType = request.getParameter("streamOrFormatFileType");
		String fileId = FileBaseUtil.fileuploadtoFileServerReturnID(request, fileName, streamOrFormatFileType);
		Logger.getLogger(LocalTransferOpinionUpdateServlet.class).info("==获取文件ID=="+fileId);
		//进入此方法，说明是点击编辑后，在编辑页面进行“保存”操作时，流式文件已更新，该更新对应的版式文件，所以之前在版式文件编辑的内容会被清空
		DocumentFile draft = documentFileService.queryObject(qxjDocId);
		if(StringUtils.equals("format", streamOrFormatFileType)){
			draft.setFileServerFormatId(fileId);
		}else{
			draft.setFileServerStreamId(fileId);
		}
		documentFileService.update(draft);		
		Logger.getLogger(LocalTransferOpinionUpdateServlet.class).info("end======");
	}
}
