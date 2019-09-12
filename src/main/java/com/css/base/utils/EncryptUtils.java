package com.css.base.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.CRC32;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;

public class EncryptUtils {
	private static char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
	
	/** 
	 * 获取MD2码 
	 * @return String 
	 * */ 
	public static String md2(byte[] data) {
		return DigestUtils.md2Hex(data);
	}
	
	/** 
	 * 获取MD2码 
	 * @return String 
	 * @throws IOException 
	 * */ 
	public static String md2(InputStream data) throws IOException {
		return DigestUtils.md2Hex(data);
	}
	
	/** 
	 * 获取MD2码 
	 * @return String 
	 * */ 
	public static String md2(String data) {
		return DigestUtils.md2Hex(data);
	}
	
	/** 
	 * 获取字符串MD5码 
	 * @param byte[] data
	 * @return String 
	 * */ 
	public static String md5(byte[] data) {
		return DigestUtils.md5Hex(data);
	}
	
	/** 
     * 获取MD5码 
     * @return String 
	 * @throws IOException 
     * */ 
	public static String md5(InputStream data) throws IOException {
		return DigestUtils.md5Hex(data);
	}
	
	/** 
	 * 获取MD5码 
	 * @return String 
	 * */ 
	public static String md5(String data) {
		return DigestUtils.md5Hex(data);
	}
	
	/** 
     * 获取MD5值
     * @return String 
     * @throws IOException 
     */  
    public static String md5(FileInputStream data) throws IOException {  
    	return DigestUtils.md5Hex(IOUtils.toByteArray(data));
    } 
    
	/** 
     * 获取SHA1码 
     * @return String 
     * */ 
	public static String sha1(byte[] data) {
		return DigestUtils.sha1Hex(data);
	}
	
	/** 
	 * 获取SHA1码 
	 * @return String 
	 * @throws IOException 
	 * */ 
	public static String sha1(InputStream data) throws IOException {
		return DigestUtils.sha1Hex(data);
	}
	
	/** 
	 * 获取SHA1码 
	 * @return String 
	 * */ 
	public static String sha1(String data) {
		return DigestUtils.sha1Hex(data);
	}
	
	/** 
     * 获取SHA1码
     * @return String
	 * @throws IOException 
     * */  
    public static String sha1(FileInputStream data) throws IOException { 
    	return DigestUtils.sha1Hex(IOUtils.toByteArray(data));
    } 
    
	/** 
	 * 获取SHA256码 
	 * @return String 
	 * */ 
	public static String sha256(byte[] data) {
		return DigestUtils.sha256Hex(data);
	}
	
	/** 
     * 获取SHA256码 
     * @return String 
	 * @throws IOException 
     * */ 
	public static String sha256(InputStream data) throws IOException {
		return DigestUtils.sha256Hex(data);
	}
	
	/** 
	 * 获取SHA256码 
	 * @return String 
	 * */ 
	public static String sha256(String data) {
		return DigestUtils.sha256Hex(data);
	}
	
	/** 
	 * 获取SHA384码 
	 * @return String 
	 * */ 
	public static String sha384(byte[] data) {
		return DigestUtils.sha384Hex(data);
	}
	
	/** 
     * 获取SHA384码 
     * @return String 
	 * @throws IOException 
     * */ 
	public static String sha384(InputStream data) throws IOException {
		return DigestUtils.sha384Hex(data);
	}
	
	/** 
	 * 获取SHA384码 
	 * @return String 
	 * */ 
	public static String sha384(String data) {
		return DigestUtils.sha384Hex(data);
	}
	
	/** 
	 * 获取SHA512码 
	 * @return String 
	 * */ 
	public static String sha512(byte[] data) {
		return DigestUtils.sha512Hex(data);
	}
	
	/** 
     * 获取SHA512码 
     * @return String 
	 * @throws IOException 
     * */ 
	public static String sha512(InputStream data) throws IOException {
		return DigestUtils.sha512Hex(data);
	}
	
	/** 
	 * 获取SHA512码 
	 * @return String 
	 * */ 
	public static String sha512(String data) {
		return DigestUtils.sha512Hex(data);
	}
	
    /** 
     * 获取CRC32码 
     * @return String 
     * */  
    public static String crc32(FileInputStream data) {  
        CRC32 crc32 = new CRC32();  
        try {  
            byte[] buffer = new byte[8192];  
            int length;  
            while ((length = data.read(buffer)) != -1) {  
                crc32.update(buffer, 0, length);  
            }  
            return Long.toHexString(crc32.getValue()).toUpperCase();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
            return null;  
        } catch (IOException e) {  
            e.printStackTrace();  
            return null;  
        } finally {  
            try {  
                if (data != null)  
                    data.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }
    
    /**
     * 获取文件的md5值
     * @param filePath
     * @return
     */
    public static String fileMD5(String filePath) {
    	// 定义缓冲区大小
    	int bufferSize = 256 * 1024;
    	FileInputStream fileInputStream = null;
    	DigestInputStream digestInputStream = null;
    	try {
    		// 初始化一个md5转换器
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			fileInputStream = new FileInputStream(filePath);
			digestInputStream = new DigestInputStream(fileInputStream, messageDigest);
			byte[] buffer = new byte[bufferSize];
			while(digestInputStream.read(buffer) > 0);
			messageDigest = digestInputStream.getMessageDigest();
			byte[] result = messageDigest.digest();
			return byteArrayToHex(result).toLowerCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				digestInputStream.close();
				fileInputStream.close();
			} catch (Exception e2) {
			}
		}
    }
    
    /**
     * 获取文件的md5值
     * @param file
     * @return
     */
    public static String fileMD5(File file) {
    	// 定义缓冲区大小
    	int bufferSize = 256 * 1024;
    	FileInputStream fileInputStream = null;
    	DigestInputStream digestInputStream = null;
    	try {
    		// 初始化一个md5转换器
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			fileInputStream = new FileInputStream(file);
			digestInputStream = new DigestInputStream(fileInputStream, messageDigest);
			byte[] buffer = new byte[bufferSize];
			while(digestInputStream.read(buffer) > 0);
			messageDigest = digestInputStream.getMessageDigest();
			byte[] result = messageDigest.digest();
			return byteArrayToHex(result).toLowerCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				digestInputStream.close();
				fileInputStream.close();
			} catch (Exception e2) {
			}
		}
    }
    
    /**
     * 获取文件的md5值
     * @param fileIns
     * @return
     */
    public static String fileMD5(InputStream fileIns) {
    	// 定义缓冲区大小
    	int bufferSize = 256 * 1024;
    	DigestInputStream digestInputStream = null;
    	try {
    		// 初始化一个md5转换器
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			digestInputStream = new DigestInputStream(fileIns, messageDigest);
			byte[] buffer = new byte[bufferSize];
			while(digestInputStream.read(buffer) > 0);
			messageDigest = digestInputStream.getMessageDigest();
			byte[] result = messageDigest.digest();
			return byteArrayToHex(result).toLowerCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				digestInputStream.close();
				fileIns.close();
			} catch (Exception e2) {
			}
		}
    }
    
    private static String byteArrayToHex(byte[] byteArray) {
    	char[] resultCharArray = new char[byteArray.length * 2];
    	int index = 0;
    	for(byte b : byteArray) {
    		resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
    		resultCharArray[index++] = hexDigits[b & 0xf];
    	}
    	return new String(resultCharArray);
    }
    
}
