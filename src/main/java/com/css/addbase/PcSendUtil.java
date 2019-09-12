package com.css.addbase;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.util.LinkedMultiValueMap;

import com.css.taskqueue.ConsumerPC;


/**
 * PC端发送消息工具类 
 */
@Configuration
public class PcSendUtil {
	
	private static final Queue<Map<String, Object>> bufferPC = new LinkedBlockingQueue<>();//创建一个长度为100的队列  模拟存储生产者
	int maxSizePC =400;
	Thread consumerPC = new ConsumerPC(bufferPC, maxSizePC, "PCCONSUMER");
	public PcSendUtil() {
		consumerPC.start();
	}

	/**
	 * 桌面消息放入任务队列
	 */
	public void sendPC(String url,String accessToken,HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity,
			Class clazz,String appId,String appSecret) {
			while(bufferPC.size() >= maxSizePC) {
				try {
					System.out.println("PC消息队列已满，线程暂时阻塞");
					
					//synchronized (bufferPC) {
						//bufferPC.wait();
						Thread.sleep(20);
					//}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			//consumerPC.resume();
			Map<String, Object> map = new HashMap<>();
			map.put("url", url);
			map.put("accessToken", accessToken);
			map.put("httpEntity", httpEntity);
			map.put("clazz", clazz);
			map.put("appId", appId);
			map.put("appSecret", appSecret);
			//synchronized (bufferPC) {
				bufferPC.add(map);
			//	bufferPC.notifyAll();
			//}
	}
}
