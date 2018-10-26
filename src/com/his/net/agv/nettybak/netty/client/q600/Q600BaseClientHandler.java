package com.his.net.agv.nettybak.netty.client.q600;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.his.net.agv.nettybak.netty.AgvConnectionUtils;
import com.his.net.agv.nettybak.netty.client.BaseClientHandler;
import com.his.net.agv.nettybak.util.Q600CmdFactory;

import io.netty.channel.ChannelHandlerContext;

/**  
 * @Title: AgvClientHandler.java
 * @Package com.his.net.agv.nettybak.netty.client
 * @Description: q600业务处理器基类
 * @author ZhengMaoDe   
 * @date 2018年5月23日 上午10:32:16 
 */
public abstract class Q600BaseClientHandler extends  BaseClientHandler<String> {
	
	private static final Logger logger=LoggerFactory.getLogger(Q600BaseClientHandler.class);
	

    
    /**
     * 断开自动重连 
     * @param ctx
     * @throws Exception
     * @see io.netty.channel.ChannelInboundHandlerAdapter#channelInactive(io.netty.channel.ChannelHandlerContext)
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    	super.channelInactive(ctx);
    	logger.debug("---" + ctx.channel().remoteAddress() + " is inactive---连接中断开，执行重连逻辑");
        InetSocketAddress addr=(InetSocketAddress)ctx.channel().remoteAddress();
        String ip=addr.getAddress().getHostAddress();
        int port=addr.getPort();
        AgvConnectionUtils.connectQ600(ip, port, false);
    }
    /**处理指定的时间间隔内没有读到数据
     * @param ctx
     */
    protected void handleReaderIdle(ChannelHandlerContext ctx) {
       logger.debug("---READER_IDLE---指定的时间间隔内没有读到agv返回的数据，准备发送 问询坐标 指令");
       //到了指定的间隔时间，就问询一次坐标
       ctx.channel().writeAndFlush(Q600CmdFactory.Q600_GET_POSE);
       logger.debug("---READER_IDLE--发送问询坐标指令完成");
    }

}