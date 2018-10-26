package com.his.net.agv.nettybak.netty.client.plc;

import com.his.net.agv.nettybak.netty.AgvConstants;
import com.his.net.agv.nettybak.netty.client.BaseChannelInitializer;
import com.his.net.agv.nettybak.netty.client.BaseClientHandler;

import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;


/**  
 * @Title: PlcClientInitializer.java
 * @Package com.his.net.agv.nettybak.netty.client.plc
 * @Description: plc通信处理器初始化
 * @author ZhengMaoDe   
 * @date 2018年7月11日 上午9:34:15 
 */
public class PlcClientInitializer extends BaseChannelInitializer<SocketChannel> {
	
	public PlcClientInitializer(BaseClientHandler<?> handler) {
		super(handler);
	}

		@Override
	    public void initChannel(SocketChannel ch) throws Exception {
	        ChannelPipeline pipeline = ch.pipeline();
	        
	       // pipeline.addLast(new IdleStateHandler(AgvConstants.DEFAULT_HEARTBEAT_SECOND, 0, 0));//心跳
	        pipeline.addLast("decoder", new PlcClientDecoder());
	        pipeline.addLast("encoder", new PlcClientEncoder());
	        pipeline.addLast("handler", this.getBusinessHandler());
	    }
	}