package com.his.net.agv.client;


/**  
 * @Title: QdMain.java
 * @Package com.his.comm.testqd.client
 * @Description: 青岛AGV测试
 * @author ZhengMaoDe   
 * @date 2018年4月24日 上午10:45:23 
 */
public class QdMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		startServer();
		//上面的代码模拟启动一个测试服务，AGV联调时注释掉
		startClient();
	}

	public static void startServer(){
		TestServer ts=new TestServer();
		ts.start();
	}
	
	public static void startClient(){
		new ClientUI();
	}
}
