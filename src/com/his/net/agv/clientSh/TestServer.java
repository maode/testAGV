package com.his.net.agv.clientSh;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.his.net.agv.util.ByteUtils;


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
			Socket socket=server.accept();
			System.out.println("~~~服务端监听到客户端地址："+socket.getRemoteSocketAddress());
			BufferedInputStream reader=new BufferedInputStream(socket.getInputStream());
			BufferedOutputStream writer=new BufferedOutputStream(socket.getOutputStream());
			while(true){
				System.out.println("进了循环准备读取数据。");
				byte[] ib=new byte[3];
				reader.read(ib);
				System.out.println("读取客户端传递数据[二进制]："+ByteUtils.byteArray2BitStr(ib));
				//返回一组固定数
				//this.writeOne(writer);
				//循环返回随机数
				this.writeLoop(writer);
				
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("客户端关闭了链接");
		}finally{
			System.out.println("进入finally");
			if(server!=null&&!server.isClosed()){
				try {
					System.out.println("执行close~~");
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 模拟回写一次
	 */
	private void writeOne(BufferedOutputStream writer){
		byte[] ob=new byte[] {2,0,90,30,40,50,11,11,22,22};//返回模拟参数给客户端-定长10
		System.out.println("服务端准备回写数据，数据为[二进制]："+ByteUtils.byteArray2BitStr(ob));
		try {
			writer.write(ob);
			writer.flush();		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 模拟循环回写
	 */
	private void writeLoop(BufferedOutputStream writer){
		//-------loop s
		Runnable run=new Runnable() {
			@Override
			public void run() {
				for(int i=0;i<2000;i++){
					try {
						byte[] loopByte=new byte[]{(byte)(Math.random()*100),(byte)(Math.random()*100),(byte)(Math.random()*100),(byte)(Math.random()*100),(byte)(Math.random()*100),(byte)(Math.random()*100),(byte)(Math.random()*100),(byte)(Math.random()*100),(byte)(Math.random()*100),(byte)(Math.random()*100)};
						writer.write(loopByte);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try {
					writer.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		Thread thread=new Thread(run);
		thread.start();
		//------loop e		
	}
	
	
	public static void main(String args[]){
		TestServer ts=new TestServer();
		ts.start();
	}
}
