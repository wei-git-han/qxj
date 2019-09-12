package com.css.addbase.suwell.include;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Base64;

import com.css.base.utils.StringUtils;
/**
 * 接收扫描得到的图片流信息，生成图片文件；
 * 此处用于使用扫描仪扫描生成的图片，生成OFD文件；
 * @author shuwen
 *
 */
public class PictureToOfd {
	/**
	 * 生成图片文件的路径 
	 * 
	 * @param smData
	 * @param dataId
	 * @param tmpPath
	 * @return
	 */
	public static List<String> getImageFilePath(String[] smDatas, String dataId, String tmpPath) {
		// 存放所有的图片地址
		List<String> imgList = new LinkedList<String>();
		if (smDatas != null && smDatas.length > 0) {
			// 图片的名称
			String imgFileName = "";
			// 解析获取扫描件（流文件的形式）
			for (int i = 0; i < smDatas.length; i++) {
				if (StringUtils.isNotBlank(smDatas[i])) {
					// 将扫描件转换承图片文件，临时存放
					imgFileName = tmpPath + "/" + dataId + "_" + i + ".jpg";
					// 将获得的扫描件内容，转换成图片格式
					boolean backImg = transterStreamToImg(i, smDatas[i], imgFileName);
					if (backImg) {
						imgList.add(imgFileName);
					}
				}
			}
		}
		return imgList;
	}
	
	/**
	 * 将扫描件字符串转换成图片文件 shuwen
	 * 
	 * @param index
	 *            扫描件索引
	 * @param smjStr
	 *            扫描件字符串
	 * @param imgFileName
	 *            扫描件生成图片的地址和名称
	 * @param base64
	 *            扫描件字符串以base64形式存储的，需要转换
	 */
	private static boolean transterStreamToImg(int index, String smjStr, String imgFileName) {
		try {
			File file = new File(imgFileName);
			if (!file.exists()) {
				file.createNewFile();
			}
			// 将获取的扫描件先转换成流文件的格式
			InputStream smwjStream = new ByteArrayInputStream(smjStr.getBytes());
			// 获取字节流
			byte[] bytesmj = getByteArrayByIns(smwjStream);
			if (bytesmj != null && bytesmj.length > 0) {
				byte[] bs = Base64.getMimeDecoder().decode(bytesmj);
				if (bs != null && bs.length > 0) {
					// 定义文件输入流，将其以图片的形式写出
					FileOutputStream os = new FileOutputStream(imgFileName);
					os.write(bs);
					os.flush();
					os.close();
					return true;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			e.getMessage();
			System.out.println("打开文件时，出错，请从新操作！！！！");
		} catch (IOException e) {
			e.printStackTrace();
			e.getMessage();
			System.out.println("转换第" + index + "张图片时，出错，请从新操作！！！！");
		}
		return false;
	}
	
	/**
	 * 将流文件读出成字节数组 shuwen
	 * 
	 * @param ins
	 * @return
	 */
	private static byte[] getByteArrayByIns(InputStream ins) {
		try {
			// 将字节数组流输出
			ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
			int ch;
			while ((ch = ins.read()) != -1) {
				bytestream.write(ch);
			}
			bytestream.flush();
			bytestream.close();
			byte[] buffer = bytestream.toByteArray();
			if (buffer != null && buffer.length > 0) {
				return buffer;
			}
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}
		return null;
	}

}
