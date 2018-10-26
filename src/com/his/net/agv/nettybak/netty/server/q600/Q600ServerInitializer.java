package com.his.net.agv.nettybak.netty.server.q600;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;


/**  
 * @Title: AgvServerInitializer.java
 * @Package com.his.net.agv.nettybak.nettyServer
 * @Description: 处理器初始化绑定
 * @author ZhengMaoDe   
 * @date 2018年5月23日 上午9:52:49 
 */
public class Q600ServerInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	public void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();

		pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
		pipeline.addLast("decoder", new StringDecoder());
		pipeline.addLast("encoder", new StringEncoder());
		pipeline.addLast("handler", new Q600ServerHandler());

		System.out.println("AgvClient:" + ch.remoteAddress() + "连接上");
	}
}