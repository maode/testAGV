package com.his.net.agv.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.his.net.agv.util.BitUtils;
import com.his.net.agv.util.HexUtils;

/**  
 * @Title: ServerThread.java
 * @Package com.his.net.agv.client
 * @Description: 服务端响应客户端socket线程
 * @author ZhengMaoDe   
 * @date 2018年5月16日 下午6:32:44 
 */
public class ServerThread extends Thread {

	private Socket client;
	
	public ServerThread(Socket client) {
		this.client=client;
	}
	@Override
	public void run() {
		try {
			System.out.println("~~~服务端监听到客户端地址："+client.getRemoteSocketAddress());
			BufferedInputStream reader=new BufferedInputStream(client.getInputStream());
			BufferedOutputStream writer=new BufferedOutputStream(client.getOutputStream());
			while(true){
				System.out.println("进了循环准备读取数据。");
				byte[] ib=new byte[11];
				reader.read(ib);
				System.out.println("读取客户端传递数据："+HexUtils.bytesToHexString(ib));
				ib[0]=BitUtils.setBitValue(ib[0], 0, (byte) 0x01);//将首部第一个字节的第0位置1，然后返回给客户端
				System.out.println("服务端准备回写数据，数据为："+HexUtils.bytesToHexString(ib));
				writer.write(ib);
				writer.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("客户端关闭了链接");
		}		
		
	}
}
