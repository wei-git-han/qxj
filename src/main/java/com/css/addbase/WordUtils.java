package com.css.addbase;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import org.apache.log4j.Logger;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class WordUtils {
	
	private static Logger LOG = Logger.getLogger(WordUtils.class);
	private Configuration configure = null;
	
	public WordUtils(){
		configure = new Configuration(Configuration.VERSION_2_3_24);
		configure.setDefaultEncoding("utf-8");
	}
	
	/**
	 * 根据DOC模板生成word文件
	 * @param dataList	word中需要放的数据
	 * @param tempFileName	模板文件名
	 * @param filePath	生成文件存放的路径
	 */
	@SuppressWarnings("deprecation")
	public boolean createDoc(Map<String, Object> dataList, String tempFileName, String filePath){
		try {
			//装载模板
			Template temp = null;
			//加载模板文件
			configure.setClassForTemplateLoading(this.getClass(), "/com/css/app/rwgl/word/templates");
			//设置对象包装器
			configure.setObjectWrapper(new DefaultObjectWrapper());
			//设置异常处理器
			configure.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
			//定义Template对象，注意模板类型名字与fileName要一致
			temp = configure.getTemplate(tempFileName + ".xml");
			//输出文档
			File outFile = new File(filePath);
			Writer out = null;
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"));
			temp.process(dataList, out);
			//window下，执行delete方法没有问题，linux下执行，会把文件删除
			//outFile.delete();
			return true;
		} catch (IOException e) {
			System.out.println("Word文档转换，抛出IOException!!!!");
			e.printStackTrace();
		} catch (TemplateException e) {
			System.out.println("Word文档转换，抛出TemplateException!!!!");
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 根据DOC模板生成word文件
	 * 
	 * @param dataList
	 *            word中需要放的数据
	 * @param tempFileName
	 *            模板文件名
	 */
	public static ByteArrayOutputStream createDoc(Map<String, Object> dataList, String path) {
		ByteArrayOutputStream outs = new ByteArrayOutputStream();
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_24);
		cfg.setDefaultEncoding("utf-8");
		int lastIndex = path.lastIndexOf("/");
		String fileName = path.substring(lastIndex + 1);
		String dir = path.substring(0, lastIndex);

		// 加载模板文件
		cfg.setClassForTemplateLoading(WordUtils.class, dir);
		// 设置异常处理器
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
		try {
			// 定义Template对象，注意模板类型名字与fileName要一致
			Template temp = cfg.getTemplate(fileName);// 装载模板
			Writer out = new BufferedWriter(new OutputStreamWriter(outs, "utf-8"));
			temp.process(dataList, out);
			return outs;
		} catch (Exception e) {
			System.out.println("【报错信息】Word文档转换出错，" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 根据DOC模板生成word文件
	 * 
	 * @param dataList
	 *            word中需要放的数据
	 * @param templatePath 
	 * 				模板路径 
	 * @param tempFileName
	 *            模板文件名
	 */
	public static void creatDoc(Map<String, Object> dataList, String fileName, String templateName, String templatePath) {
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_24);
		cfg.setDefaultEncoding("utf-8");
		Writer out = null;
		FileOutputStream fos = null;
		// 加载模板文件
		cfg.setClassForTemplateLoading(WordUtils.class, templatePath);
		// 设置异常处理器
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
		Template temp =null;
		try {
			// 定义Template对象，注意模板类型名字与fileName要一致
			temp = cfg.getTemplate(templateName);// 装载模板
			fos = new FileOutputStream(new File(fileName));
			out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"));
			//合成對象
			temp.process(dataList, out);
		} catch (Exception e) {
			LOG.error("【报错信息】文档转换出错，" + e.getMessage());
			e.printStackTrace();
		}finally {
			try {
				if(null!=out) {
					out.close();
				}
				if(null!=fos) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}
}
