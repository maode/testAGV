package com.his.net.agv.client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**  
 * @Title: TestServer.java
 * @Package com.his.comm
 * @Description: 为当前包下的client启动一个测试服务
 * @author ZhengMaoDe   
 * @date 2018年4月18日 下午5:30:38 
 */
public class TestServer extends Thread{

	@Override
	public  void run() {

		int port = 6666;
		ServerSocket server=null;
			try {
				server=new ServerSocket(port);
				System.out.println("服务端开启端口："+port+" 等待客户端连接");
				System.out.println(server.getInetAddress());
				System.out.println(server.getLocalSocketAddress());
				while(true) {
					Socket client=server.accept();
					new ServerThread(client).start();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
	}
	
	public static void main(String args[]){
		TestServer ts=new TestServer();
		ts.start();
	}
}
