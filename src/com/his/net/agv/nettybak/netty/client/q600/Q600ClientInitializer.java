package com.his.net.agv.nettybak.netty.client.q600;

import com.his.net.agv.nettybak.netty.AgvConstants;
import com.his.net.agv.nettybak.netty.client.BaseChannelInitializer;
import com.his.net.agv.nettybak.netty.client.BaseClientHandler;

import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

/**  
 * @Title: AgvClientInitializer.java
 * @Package com.his.net.agv.nettybak.netty.client
 * @Description: q600通信处理器初始化
 * @author ZhengMaoDe   
 * @date 2018年5月23日 上午10:34:15 
 */
public class Q600ClientInitializer extends BaseChannelInitializer<SocketChannel> {
	

		public Q600ClientInitializer(BaseClientHandler<?> handler) {
			super(handler);
		}

		@Override
	    public void initChannel(SocketChannel ch) throws Exception {
	        ChannelPipeline pipeline = ch.pipeline();
	        
	        //pipeline.addLast(new IdleStateHandler(AgvConstants.DEFAULT_HEARTBEAT_SECOND, 0, 0));//心跳
	        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
	        pipeline.addLast("decoder", new StringDecoder());
	        pipeline.addLast("encoder", new StringEncoder());
	        pipeline.addLast("handler", this.getBusinessHandler());
	    }
		
	}