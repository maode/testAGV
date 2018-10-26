package com.his.net.agv.nettybak.netty.server.plc;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;



/**  
 * @Title: PlcServerInitializer.java
 * @Package com.his.net.agv.nettybak.netty.server.plc
 * @Description: 处理器初始化绑定
 * @author ZhengMaoDe   
 * @date 2018年7月16日 上午10:05:20 
 */
public class PlcServerInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	public void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();

		//pipeline.addLast("decoder", new PlcDecoder());
		//pipeline.addLast("encoder", new PlcEncoder());
		pipeline.addLast("handler", new PlcServerHandler());

		System.out.println("AgvClient:" + ch.remoteAddress() + "连接上");
	}
}