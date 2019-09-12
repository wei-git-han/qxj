package com.css.taskqueue;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.css.addbase.token.TokenConfig;


/**
 * 消费者消费队列中的任务，当队列为空时，消费者阻塞自己，唤醒生产者
 * notify()是随机唤醒等待队列中的一个线程
 *
 */

public class ConsumerPC  extends Thread{
	private RestTemplate restTemplate = new  RestTemplate();
	
	private Queue<Map<String, Object>> queue;
	private int skipTime=0;
	private int maxSize;
	private static final Queue<Map<String, Object>> queue2 = new LinkedBlockingQueue<>();//创建一个存储消费队列
	String msgUrl;
	String accessToken;
	String appId;
	String appSecret;
	String urlAccessToken;
	Object result = null;
	Object httpEntity  = null;
	
	public ConsumerPC(Queue<Map<String, Object>> queue,int maxSize,String name) {
		super(name);
		this.queue = queue;
		this.maxSize = maxSize;
	}
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		while(true) {
			
			while(queue.isEmpty()) {
				if(skipTime==0) {
					System.out.println("当前队列中没有PC端消息任务，桌面消息发送进入挂起状态");
				}
				if(skipTime<3) {
					skipTime++;
				}
				//this.suspend();
				try {
					this.sleep(1000*skipTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("pc消息发送认读队列恢复执行");
			skipTime=0;
			queue2.addAll(queue);
			queue.clear();
		/*	synchronized (queue) {
				queue.notifyAll();
			}*/
			Map<String, Object> map =null;// 移除并返回队列头部的元素
			while((map=queue2.poll())!=null) {
				try {
					accessToken = (String) map.get("accessToken");
					appId = (String) map.get("appId");
					appSecret = (String) map.get("appSecret");
					msgUrl = (String) map.get("url");
					urlAccessToken = msgUrl + accessToken;
					httpEntity = map.get("httpEntity");

					System.out.println("PC发送消息start:{}" + result);
					System.out.println("httpEntity:::" + JSONObject.toJSONString(httpEntity)+urlAccessToken);
					result = restTemplate.postForEntity(urlAccessToken, map.get("httpEntity"),
							(Class<Object>) map.get("clazz"));
					System.out.println("PC发送消息end:{}" + result);
				} catch (Exception e) {
                   e.printStackTrace();
                   System.out.println("桌面消息发送失败");
				}
				result = "";
			}
		}
	}
}
