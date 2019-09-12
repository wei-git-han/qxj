package com.css.app.qxjgl.business.controller;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;

import com.css.base.utils.Response;

import cn.com.css.filestore.impl.HTTPFile;

/**
 * 版式文件远程存储，调用接口
 */
@WebServlet("/app/qxjgl/servlet/suwell/saveCommentPicture")
public class PictureUploadServlet extends HttpServlet {

	private static final long serialVersionUID = -5153639658993615031L;	
	// 接受上传的文件的大小4G
	private static final long MAX_FILE_SIZE = 4L * 1024 * 1024 * 1024;
	// 缓存大小1M
	private static final int BUFFER_SIZE = 1 * 1024 * 1024;
	public static final DateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// -------------------将上传文件保存到服务器中的files文件夹begin------------------
		try {
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (!isMultipart) {
				return;
			}
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(BUFFER_SIZE);// max memory cache
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setFileSizeMax(MAX_FILE_SIZE);
			String charset = request.getCharacterEncoding();
			if (charset == null) {
				charset = "UTF-8";
			}
			upload.setHeaderEncoding(charset);
			List<FileItem> items = upload.parseRequest(request);
			if ((items == null) || items.isEmpty()) {
				System.out.println("没有任何文件上传!");
				return;
			}
			FileItem fi = null;
			for (FileItem item : items) {
				if (!item.isFormField()) {
					fi = item;
					break;
				}
			}
			String fileName = fi.getFieldName();
			InputStream fileStream = fi.getInputStream();
			if (fileStream == null || StringUtils.isEmpty(fileName)) {
				return;
			}
			HTTPFile httpFile = HTTPFile.save(fileStream, fileName);
			// fileId作为文件在文件服务中的唯一标识，用来获取文件。
			// 数据库表原存储文件的路径地方之间存该fileId。
			String fileId = httpFile.getFileId();
			
			Response.json(fileId);
			
			System.out.println("图片意见保存成功");
		} catch (Exception e) {
			System.out.println("commons-fileupload- 1.3.1.jar中写出FileItem.write()发生异常");
			response.sendError(500, e.getMessage());
		}
		// --------------------将上传文件保存到服务器中的files文件夹end------------------
	}
}
