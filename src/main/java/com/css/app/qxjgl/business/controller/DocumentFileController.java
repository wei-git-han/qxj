package com.css.app.qxjgl.business.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.css.addbase.AppConfig;
import com.css.addbase.FileBaseUtil;
import com.css.addbase.msg.MsgTipUtil;
import com.css.addbase.suwell.OfdTransferUtil;
import com.css.app.qxjgl.business.entity.DocumentFile;
import com.css.app.qxjgl.business.service.DocumentFileService;
import com.css.base.utils.Response;
import com.css.base.utils.UUIDUtils;

import cn.com.css.filestore.impl.HTTPFile;


/**
 * 请假单的相关文件表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2019-08-14 11:19:46
 */
@Controller
@RequestMapping("/app/qxjgl/documentfile")
public class DocumentFileController {
	private static Logger logger = LoggerFactory.getLogger(DocumentFileController.class);
	@Autowired
	private DocumentFileService documentFileService;
	@Autowired
	private AppConfig appConfig;
	/**
	 * 文件上传保存
	 * @param leaveId 主记录id
	 * @param pdf 文件
	 */
	@ResponseBody
	@RequestMapping("/uploadFile")
	public void savePDF(String leaveId,@RequestParam(required = false) MultipartFile[] pdf){
			String formatDownPath = "";//版式文件下载路径
			String retFormatId = null;//返回的版式文件id
			String streamId = null;//流式文件id
			String formatId  = null;//版式文件id
			JSONObject json = new JSONObject();
			if(StringUtils.isNotBlank(leaveId)) {				
				if (pdf != null && pdf.length > 0) {
					for (int i = 0; i < pdf.length; i++) {
						String fileName=pdf[i].getOriginalFilename();
						//获取文件后缀
						String fileType =fileName.substring(fileName.lastIndexOf(".")+1);
						//如果文件是流式则流式转换为版式
						if(!StringUtils.equals("ofd", fileType)){
							streamId = FileBaseUtil.fileServiceUpload(pdf[i]);
							HTTPFile hf = new HTTPFile(streamId);
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
							} catch (Exception e) {
								e.printStackTrace();
							}
						}else{
							formatId=FileBaseUtil.fileServiceUpload(pdf[i]);	
						}
						if(StringUtils.isNotBlank(formatId)) {
							if(i==0) {
								retFormatId=formatId;
								//获取版式文件的下载路径
								HTTPFile httpFiles = new HTTPFile(formatId);
								if(httpFiles!=null) {
									formatDownPath = httpFiles.getAssginDownloadURL(true);
								}
							}
							//保存文件相关数据
							DocumentFile file=new DocumentFile();
							file.setId(UUIDUtils.random());
							file.setLeaveId(leaveId);
							file.setSort(documentFileService.queryMinSort(leaveId,"fj"));
							file.setFileName(fileName);
							file.setCreatedTime(new Date());
							file.setFileType("fj");
							if(StringUtils.isNotBlank(streamId)) {
								file.setFileServerStreamId(streamId);
							}
							file.setFileServerFormatId(formatId);
							documentFileService.save(file);
						}
					}
					json.put("smjId", retFormatId);
					json.put("smjFilePath", formatDownPath);
					json.put("result", "success");
				}
			}else {
				json.put("result", "fail");
			}
			Response.json(json);
	}

	/**
	 * @description:详情页文件页签列表
	 * @param id 主文件id
	 * @date:2019年9月4日
	 * @Version v1.0
	 */
	@ResponseBody
	@RequestMapping("/tabList")
	public void list(String id){
		Map<String, Object> map = new HashMap<>();
		map.put("leaveId", id);		
		List<DocumentFile> documentFileList = documentFileService.queryList(map);
		Response.json(documentFileList);
	}
	
	/**
	 * @description:详情页右侧文件列表
	 * @param id 主文件id
	 * @date:2019年9月4日
	 * @Version v1.0
	 */
	@ResponseBody
	@RequestMapping("/fileList")
	public void draftType(String id) {
		Map<String, Object> map = new HashMap<>();
		map.put("leaveId", id);
		List<DocumentFile> queryType = documentFileService.queryList(map);
		JSONArray childJson1 = new JSONArray();
		JSONArray childJson2 = new JSONArray();
		for (DocumentFile draft : queryType) {
			String typeId = draft.getFileType();
			if ("cpj".equals(typeId)) {
				JSONObject childJson11 = new JSONObject();
				childJson11.put("id", draft.getId() == null ? "":draft.getId());
				childJson11.put("name", draft.getFileName()== null ? "":draft.getFileName());
				childJson11.put("type", draft.getFileType()== null ? "":draft.getFileType());
				childJson11.put("fileId", draft.getFileServerFormatId()== null ? "":draft.getFileServerFormatId());
				childJson11.put("leaveId", draft.getLeaveId()== null ? "":draft.getLeaveId());
				childJson1.add(childJson11);
			} else if ("fj".equals(typeId)) {
				JSONObject childJson22 = new JSONObject();
				childJson22.put("id", draft.getId() == null ? "":draft.getId());
				childJson22.put("name", draft.getFileName()== null ? "":draft.getFileName());
				childJson22.put("type", draft.getFileType()== null ? "":draft.getFileType());
				childJson22.put("fileId", draft.getFileServerFormatId()== null ? "":draft.getFileServerFormatId());
				childJson2.add(childJson22);
			} 
		}
		JSONArray json = new JSONArray();
		JSONObject json1 = new JSONObject();
		JSONObject json2 = new JSONObject();
		if (childJson1.size() > 0) {
			json1.put("fileTitle", "主文件");
			json1.put("fileType", "cpj");
			json1.put("child", childJson1);
			json.add(json1);
		}else {
			json1.put("fileTitle", "主文件");
			json1.put("fileType", "cpj");
			json1.put("child", "");
			json.add(json1);
		}
		if (childJson2.size() > 0) {
			json2.put("fileTitle", "附件");
			json2.put("fileType", "fj");
			json2.put("child", childJson2);
			json.add(json2);
		}else {
			json2.put("fileTitle", "附件");
			json2.put("fileType", "fj");
			json2.put("child", "");
			json.add(json2);
		}		
		Response.json(json);
	}
	
	/**
	 * @description:获取选中页签的单个文件
	 * @param id 文件表id
	 * @date:2019年9月4日
	 * @Version v1.0
	 */
	@ResponseBody
	@RequestMapping("/getTabFile")
	public void getTabFile(String id){
		DocumentFile documentFile = documentFileService.queryObject(id);
		JSONObject json= new JSONObject();
		if(documentFile!=null) {
			String formatId=documentFile.getFileServerFormatId();
			logger.info("版式文件id:{}", formatId);
			if(StringUtils.isNotBlank(formatId)){
				//获取版式文件的下载路径
				HTTPFile httpFiles = new HTTPFile(formatId);
				if(httpFiles!=null) {
					json.put("downFormatIdUrl", httpFiles.getAssginDownloadURL(true));
				}
			}
		}
		Response.json(json);
	}
	
	/**
	 * @description:文件排序上移
	 * @param currendId 当前文件id
	 * @param prevId 前一文件id
	 * @author:zhangyw
	 * @date:2019年9月5日
	 * @Version v1.0
	 */	
	@ResponseBody
	@RequestMapping("/upSort")
	public void upFile(String currentId,String prevId) {
		if(StringUtils.isNotBlank(prevId) && StringUtils.isNotBlank(currentId)) {			
			DocumentFile currend = documentFileService.queryObject(currentId);
			DocumentFile prev = documentFileService.queryObject(prevId );	
			if(null!=currend&&null!=prev) {		
				DocumentFile draft = new DocumentFile();
				draft.setId(currend.getId());
				draft.setSort(prev.getSort());
				documentFileService.update(draft );
				draft.setId(prev.getId());
				draft.setSort(currend.getSort());
				documentFileService.update(draft );
			}
			Response.json("result","success");
		}else {
			Response.json("result","fail");
		}	
	}
	
	/**
	 * @description:文件排序下移
	 * @param currendId 当前文件id
	 * @param nextId 后一文件id
	 * @author:zhangyw
	 * @date:2019年9月5日
	 * @Version v1.0
	 */
	@ResponseBody
	@RequestMapping("/downSort")
	public void downFile(String currentId,String nextId) {
		if(StringUtils.isNotBlank(nextId) && StringUtils.isNotBlank(currentId)) {
			DocumentFile draft = new DocumentFile();
			DocumentFile currend = documentFileService.queryObject(currentId);
			DocumentFile next = documentFileService.queryObject(nextId);		
			if(null!=currend&&null!=next) {
				draft.setId(currend.getId());
				draft.setSort(next.getSort());
				documentFileService.update(draft );
				draft.setId(next.getId());
				draft.setSort(currend.getSort());
				documentFileService.update(draft );
			}
			Response.json("result","success");
		}else {
			Response.json("result","fail");
		}
	}
	
	/**
	 * @description:文件重命名
	 * @param id 重命名文件id
	 * @param newName 新的名字
	 * @date:2019年9月5日
	 * @Version v1.0
	 */
	@ResponseBody
	@RequestMapping("/update")
	public void update(String id,String newName){
		DocumentFile  qxjDocumentFile = new DocumentFile();
		qxjDocumentFile.setId(id);
		qxjDocumentFile.setFileName(newName);
		documentFileService.update(qxjDocumentFile);
		Response.ok();
	}
	
	/**
	 * @description:文件下载
	 * @param id documentFil表id
	 * @param formatType 流式还是版式标识
	 * @param flag 1：为第二次请求
	 * @date:2019年9月3日
	 * @Version v1.0
	 */
	@ResponseBody
	@RequestMapping("/getDownloadFile")
	public void info(String id,String formatType,String flag){
		DocumentFile documentFile = documentFileService.queryObject(id);
		JSONObject json= new JSONObject();
		if(documentFile!=null) {
			String fileId=null;
			String msg="";
			String fileName=documentFile.getFileName();
			if(StringUtils.equals("format", formatType)) {
				fileId=documentFile.getFileServerFormatId();
				msg="获取不到对应版式文件";
			}else if(StringUtils.equals("stream", formatType)) {
				fileId=documentFile.getFileServerStreamId();
				msg="原始文件没有对应流式文件";
			}
			if(StringUtils.isNotBlank(fileId)){
				//获取版式文件的下载路径
				HTTPFile httpFile = new HTTPFile(fileId);
				if(httpFile!=null) {
					json.put("result", "success");
					if(StringUtils.equals("1", flag)) {
						InputStream is = httpFile.getInputSteam();
						String downloadName=httpFile.getFileName();
						if(StringUtils.isNotBlank(fileName)) {
							downloadName =fileName.substring(0,fileName.lastIndexOf("."));
						}
						if(StringUtils.equals("format", formatType)) {
							Response.download(downloadName+".ofd", is);
						}else {
							Response.download(fileName, is);
						}
						return;
					}
				}
			}else {
				json.put("result", "fail");
				json.put("msg", msg);
			}
		}
		Response.json(json);
	}
	
	/**
	 * @description:删除附件
	 * @param id 要删除的附件id
	 * @author:zhangyw
	 * @date:2019年9月5日
	 * @Version v1.0
	 */
	@ResponseBody
	@RequestMapping("/deleteById")
	public void deleteById(String id){
		documentFileService.delete(id);		
		Response.ok();
	}
	
	/**
	 * @description:编辑附件时判断是否有流式文件
	 * @param id 所选文件id
	 * @date:2019年9月5日
	 * @Version v1.0
	 */
	@ResponseBody
	@RequestMapping("/isOrNotFormatFile")
	public void isOrNotFormatFile(String id){
		DocumentFile draft = documentFileService.queryObject(id);
		JSONObject json = new JSONObject();
		String streamId="";
		String isEdit="";
		//判断是否有流式文件
		if(draft != null){
			streamId=draft.getFileServerStreamId();
			isEdit=draft.getIsEdit();
		}
		json.put("hasStreamId", streamId);
		json.put("isEdit", isEdit);
		Response.json(json);
	}
	
	/**
	 * @description:编辑文件时获取最新的流式url
	 * @param id 所选文件id
	 * @date:2019年9月5日
	 * @Version v1.0
	 */
	@ResponseBody
	@RequestMapping("/getStreamFileUrl")
	public void streamFileUrl(String id) {
		String url = "";
		String type = "1"; // 判断当前需要加载或初始化插件的类型；1流式插件初始化；2版式文件初始化；
		String fileId = ""; // 文件服务流式文件ID
		//全部版式流式取法
		if (StringUtils.isNotBlank(id)) {
			DocumentFile draft = documentFileService.queryObject(id);
			if (draft != null) {
				fileId = draft.getFileServerStreamId();
			}
		}				
		// 判断当前文件是否为版式文件
		 String suffix="";
		if(StringUtils.isNotBlank(fileId)){
			HTTPFile hf = new HTTPFile(fileId);
		    url = hf.getAssginDownloadURL();
		    suffix = hf.getSuffix();
		    if(!StringUtils.equals(suffix, "docx") && !StringUtils.equals(suffix, "doc") && !StringUtils.equals(suffix, "wps") && !StringUtils.equals(suffix, "uot")){
		    	type = "2";
		    }
		}
		JSONObject jo = new JSONObject();
		jo.put("url", url);
		jo.put("type", type);
		jo.put("suffix", suffix);
		Response.json(jo);
	}

}
