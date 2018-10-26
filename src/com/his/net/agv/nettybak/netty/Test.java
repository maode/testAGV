package com.his.net.agv.nettybak.netty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import com.his.net.agv.nettybak.entity.AgvEntity;
import com.his.net.agv.nettybak.netty.server.plc.PlcServer;
import com.his.net.agv.nettybak.netty.server.q600.Q600Server;

/**  
 * @Title: Test.java
 * @Package com.his.net.agv.nettybak.netty
 * @Description: 一个测试类
 * @author ZhengMaoDe   
 * @date 2018年7月13日 下午7:07:04 
 */
public class Test {

	/**
	 * @param args
	 */
	public static void initAgvServer() {
		new PlcServer().start(AgvConstants.PLC_DEFAULT_PORT);
		new Q600Server().start(AgvConstants.Q600_DEFAULT_PORT);

	}
	public static void initAgvServerAndClient() {
		new PlcServer().start(AgvConstants.PLC_DEFAULT_PORT);
		new Q600Server().start(AgvConstants.Q600_DEFAULT_PORT);
		
		List<AgvEntity> agvs=new ArrayList<>();
		AgvEntity agv=new AgvEntity();
		agv.setPlcIp("127.0.0.1");
		agv.setPlcPort(AgvConstants.PLC_DEFAULT_PORT);
		agv.setQ600Ip("127.0.0.1");
		agv.setQ600Port(AgvConstants.Q600_DEFAULT_PORT);
		agv.setAgvCode(agv.getQ600Ip()+","+agv.getPlcIp());
		agvs.add(agv);
		
		AgvUtils.initAgvConnection(agvs, true);
		
	}
	
	/**
	 * 测试解码器数据过滤
	 * @param args
	 */
	public static void main4(String[] args) {
		new PlcServer().start(AgvConstants.PLC_DEFAULT_PORT);
		//AgvConnectionUtils.connectPlc("127.0.0.1", AgvConstants.PLC_DEFAULT_PORT, false);
		AgvConnectionUtils.plcBootstrap.connect("127.0.0.1", AgvConstants.PLC_DEFAULT_PORT);
	}
	public static void main(String[] args) {
		byte x=(byte) 0xed;
		System.out.println(x);
	}

	
	public static void main2(String[] args) {
		
		//ExecutorService pool=Executors.newCachedThreadPool();
		//ExecutorService pool=Executors.newFixedThreadPool(24);
		ExecutorService pool=ForkJoinPool.commonPool();
		
		System.out.println(Thread.currentThread().getId()+"当前线程开始``````");
		
		for(int i=0;i<500;i++) {
			CompletableFuture.runAsync(()->{try {
				Thread.sleep(500L);
				System.out.println(Thread.currentThread().getId()+":1组异步任务睡醒执行");
				ForkJoinPool.commonPool().awaitQuiescence(1, TimeUnit.SECONDS);
				//System.out.println("异步任务睡醒执行");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			},pool);
		}
		for(int i=0;i<500;i++) {
			CompletableFuture.runAsync(()->{try {
				Thread.sleep(500L);
				System.out.println(Thread.currentThread().getId()+":2组异步任务睡醒执行");
				//System.out.println("异步任务睡醒执行");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			},pool);
		}
		for(int i=0;i<500;i++) {
			CompletableFuture.runAsync(()->{try {
				Thread.sleep(500L);
				System.out.println(Thread.currentThread().getId()+":3组异步任务睡醒执行");
				//System.out.println("异步任务睡醒执行");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			},pool);
		}
		for(int i=0;i<500;i++) {
			CompletableFuture.runAsync(()->{try {
				Thread.sleep(500L);
				System.out.println(Thread.currentThread().getId()+":4组异步任务睡醒执行");
				//System.out.println("异步任务睡醒执行");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			},pool);
		}
		
		CompletableFuture<String>  f2=CompletableFuture.supplyAsync(()->{try {
			//Thread.sleep(400L);
			System.out.println(Thread.currentThread().getId()+":------------------------任务2睡醒执行");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}return "12";},pool);
		CompletableFuture<String>  f3=CompletableFuture.supplyAsync(()->{try {
			//Thread.sleep(100L);
			System.out.println(Thread.currentThread().getId()+":------------------------任务3睡醒执行");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}return "13";},pool);
		
		f2.thenAccept(r -> System.out.println(Thread.currentThread().getId()+":任务2==thenAccept结果:"+r));
		f3.thenAccept(r -> System.out.println(Thread.currentThread().getId()+":任务3==thenAccept结果:"+r));
//		try {
//			String sync=f2.get();
//			System.out.println(Thread.currentThread().getId()+":任务2==get结果:"+sync);
//		} catch (InterruptedException | ExecutionException e) {
//			e.printStackTrace();
//		}
		
		System.out.println(Thread.currentThread().getId()+"执行当前线程别的逻辑``````");
		System.out.println(Thread.currentThread().getId()+"执行当前线程别的逻辑``````");
		System.out.println(Thread.currentThread().getId()+"执行当前线程别的逻辑``````");
		System.out.println(Thread.currentThread().getId()+"执行当前线程别的逻辑``````");
		System.out.println(Thread.currentThread().getId()+"执行当前线程别的逻辑``````");
		System.out.println(Thread.currentThread().getId()+"当前线程结束");
		
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
}
