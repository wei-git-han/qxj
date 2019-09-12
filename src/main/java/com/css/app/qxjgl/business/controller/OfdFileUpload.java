package com.css.app.qxjgl.business.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.css.addbase.FileBaseUtil;
import com.css.app.qxjgl.business.entity.DocumentFile;
import com.css.app.qxjgl.business.service.DocumentFileService;
import com.css.base.utils.Response;
/**
 * 此类用于保存版式文件的上传
 *
 */
@WebServlet("/app/qxjgl/servlet/ofd/saveOfdFile")
public class OfdFileUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final Logger logger = LoggerFactory.getLogger(OfdFileUpload.class);
	
	@Autowired
	private DocumentFileService documentFileService;
	
	public OfdFileUpload(){}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		String fileName = request.getParameter("fileName");
		String docFileId = request.getParameter("fileId");
		DocumentFile docFile = documentFileService.queryObject(docFileId);
		System.out.println("OfdFileUpload:*************版式签批保存上传********************");
		String fileId =FileBaseUtil.fileuploadtoFileServerReturnID(request, fileName, "format");
		System.out.println("OfdFileUpload:*************版式签批保存上传********************上传得到的fileId="+fileId);
		if(StringUtils.isNotBlank(fileId)) {
			docFile.setFileServerFormatId(fileId);
		}else {
			logger.info("QXJ_DOCUMENT_FILE表中Id为{}的文件签批失败",docFileId);
		}
		documentFileService.update(docFile);
		JSONObject jo=new JSONObject();
		jo.put("result","success");
		Response.json(jo);
	}
}
