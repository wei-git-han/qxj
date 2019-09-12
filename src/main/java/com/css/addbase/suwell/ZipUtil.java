/**
 * 数科ofd转换控件，生成zip包文件封装类
 */
package com.css.addbase.suwell;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

import convert.ByteTool;
import convert.EnvelopeOptions;
import convert.XmlTool;
import cpcns.util.StringTool;
public class ZipUtil {
	/**
	 * @param metas 数据源键值集合
	 * @return
	 * @throws Exception
	 */
	private static  Logger log=Logger.getLogger(ZipUtil.class);
	
	private static byte[] metadata(Map<String, String> metas) throws Exception {
		if ((metas == null) || metas.isEmpty()) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("\r\n");
		sb.append("<MetaRoot xs:SchemaLocation=\"metadata.xsd\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema-instance\">");
		sb.append("\r\n");
		for (Map.Entry<String, String> entry : metas.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			sb.append("    ");
			sb.append("<"+key+">"+XmlTool.HTMLDecode(value)+"</"+key+">");
			sb.append("\r\n");
			log.debug("输出xml中map属性key,"+key+",value:"+value);
		}
		sb.append("</MetaRoot>");
		String content = sb.toString();
		return content.getBytes("utf-8");
	}
	private static byte[] metadata(Map<String, String> metas,Map<String, String> custems) throws Exception {
		if ((metas == null) || metas.isEmpty()) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("\r\n");
		sb.append("<MetaRoot xs:SchemaLocation=\"metadata.xsd\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema-instance\">");
		sb.append("\r\n");
		for (Map.Entry<String, String> entry : metas.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			sb.append("    ");
			sb.append("<"+key+">"+XmlTool.HTMLDecode(value)+"</"+key+">");
			sb.append("\r\n");
			log.debug("输出xml中map属性key:"+key+"*************value:"+value);
		}
		if(custems.size()>0){
			sb.append("    ");
			sb.append("<CustomDatas>");
			sb.append("\r\n");
			for (Map.Entry<String, String> entry : custems.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				sb.append("    ");
				sb.append("    ");
				sb.append("<CustomData Name=\""+key+"\">");
				sb.append(XmlTool.HTMLDecode(value));
				sb.append("</CustomData>");
				sb.append("\r\n");
			}
			sb.append("    ");
			sb.append("</CustomDatas>");
			sb.append("\r\n");
		}
		sb.append("</MetaRoot>");
		String content = sb.toString();
		return content.getBytes("utf-8");
	}

	public static File zipFile(Map<String, String> metas,List<String> fileList) throws Exception {
		return zipFile(metas,fileList, null);
	}
	
	public static File zipFile(Map<String, String> metas,List<String> fileList, EnvelopeOptions eos) throws Exception {
		return zipFile(metadata(metas), fileList, "common.all","", eos);
	}
	
	public static File zipFile(Map<String, String> metas,Map<String, String> custems,
			List<String> fileList, EnvelopeOptions eos,String tp) throws Exception {
		return zipFile(metadata(metas,custems), fileList, "common.all",tp, eos);
	}

	/**
	 * 
	 * @param metadata 文档简介
	 * @param fileList 文件集合
	 * @param type 文件类型
	 * @param eos 信封包装
	 * @return
	 * @throws Exception
	 */
	private static File zipFile(byte[] metadata, List<String> fileList,
			String type,String target, EnvelopeOptions eos) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("\r\n");
		//根据文件类型定义不同的标签属性
		sb.append("<FileRoot  Type=\""+type+ "\">");	
		sb.append("\r\n");
		String mdName = null;
		if (metadata != null) {
			mdName = ByteTool.md5(metadata) + ".xml";
			sb.append("    ");
			sb.append("<Metadata>"+mdName+"</Metadata>");
			sb.append("\r\n");
		}
		Map<String, String> names = new HashMap<String, String>();
		int i = 1;
		String name =null;
		//往OFD.XML写入节点
		if ((fileList != null) && !fileList.isEmpty()) {
			sb.append("    ");
			sb.append("<DocBody>");
			sb.append("\r\n");
			for (String s : fileList) {
				if (StringTool.isBlank(s)) {
					continue;
				}
				String n = String.valueOf(i++);
				String format = s.substring(s.lastIndexOf(".") + 1, s.length());
				name = "/Files/" + n + "." + format;
				sb.append("        ");
				sb.append("<Component ID=\""+n+"\">");
				sb.append("\r\n");
				sb.append("            ");
				sb.append("<File Format=\""+format+"\" Title=\""+new File(s).getName()+"\">");
				sb.append("\r\n");
				sb.append("                ");
				names.put(s, name);
				sb.append("<FileLoc>"+name+"</FileLoc>");
				sb.append("\r\n");
				sb.append("            ");
				sb.append("</File>");
				sb.append("\r\n");
				sb.append("        ");
				sb.append("</Component>");
				sb.append("\r\n");
			}
			sb.append("    ");
			sb.append("</DocBody>");
			sb.append("\r\n");
		}
		sb.append("</FileRoot>");
		String content = sb.toString();
		//进行打包
		try {
			//创建zip文件
			File zip = File.createTempFile("file_", ".zip");
			//开始打包
			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zip));
			makeZip(zos, fileList, names);
			ZipEntry me = new ZipEntry("Main.xml");
			zos.putNextEntry(me);
			zos.write(content.getBytes("utf-8"));
			zos.closeEntry();
			if (metadata != null) {
				ZipEntry de = new ZipEntry(mdName);
				zos.putNextEntry(de);
				zos.write(metadata);
				zos.closeEntry();
			}
			//关闭打包流
			zos.close();
			return zip;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	//将生成的xml文件打成Zip的包
	@SuppressWarnings("unused")
	private static void makeZip(ZipOutputStream zos, List<String> fileList,
			Map<String, String> names) throws IOException {
		if (fileList != null && fileList.size() > 0) {
			for (String file : fileList) {
				File f = new File(file);
				//如果文件存在则放入Zip文件里，否则打包下一个文件   ||file_name.equals("1.ofd")
				if (f != null && f.isFile()) {
					String val = names.get(file);
					String file_name= val == null ? f.getName() : val;
					String format = file_name.substring(file_name.lastIndexOf(".") + 1, file_name.length());
					ZipEntry ze = new ZipEntry(file_name);
					zos.putNextEntry(ze);
					copyFileToZip(zos, f);
					zos.closeEntry();
				} else {
					makeZip(zos, fileList, names);
				}
			}
		}
	}
	
	private static void copyFileToZip(ZipOutputStream zos, File file)
			throws IOException {
		FileInputStream fis = new FileInputStream(file);
		try {
			byte[] b = new byte[1024 * 10];
			int l = -1;
			while ((l = fis.read(b)) != -1) {
				zos.write(b, 0, l);
			}
		} finally {
			fis.close();
		}
	}
}
