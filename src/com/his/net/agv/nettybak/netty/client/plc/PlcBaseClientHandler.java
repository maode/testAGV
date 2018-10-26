package com.his.net.agv.nettybak.netty.client.plc;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.his.net.agv.nettybak.entity.PlcResult;
import com.his.net.agv.nettybak.netty.AgvConnectionUtils;
import com.his.net.agv.nettybak.netty.client.BaseClientHandler;
import com.his.net.agv.nettybak.util.Q600CmdFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;

/**  
 * @Title: PlcBaseClientHandler.java
 * @Package com.his.net.agv.nettybak.netty.client.plc
 * @Description: plc通信业务处理器基类
 * @author ZhengMaoDe   
 * @date 2018年7月11日 上午9:31:41 
 */
public abstract class PlcBaseClientHandler extends  BaseClientHandler<PlcResult> {
	
	
	private Logger logger=LoggerFactory.getLogger(getClass());
	
	
	
	/**
	 * 需要实现的处理业务逻辑的方法
	 */
	protected abstract void businessHandler(ChannelHandlerContext ctx, PlcResult s);
	

	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, PlcResult s) throws Exception {
		businessHandler(ctx, s);
	}
    
    /**
     * 心跳处理
     * @param ctx
     * @param evt
     * @throws Exception
     * @see io.netty.channel.ChannelInboundHandlerAdapter#userEventTriggered(io.netty.channel.ChannelHandlerContext, java.lang.Object)
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // IdleStateHandler 所产生的 IdleStateEvent 的处理逻辑.
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            switch (e.state()) {
                case READER_IDLE:
                    handleReaderIdle(ctx);
                    break;
                case WRITER_IDLE:
                    handleWriterIdle(ctx);
                    break;
                case ALL_IDLE:
                    handleAllIdle(ctx);
                    break;
                default:
                    break;
            }
        }
    }
    
    /**
     * 信道被激活（每当成功建立链接时，信道会被激活）
     * @param ctx
     * @throws Exception
     * @see io.netty.channel.ChannelInboundHandlerAdapter#channelActive(io.netty.channel.ChannelHandlerContext)
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
       logger.debug("---" + ctx.channel().remoteAddress() + " 信道被激活---");
       
    }

    
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
        AgvConnectionUtils.connectPlc(ip, port, false);
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

    /**处理指定的时间间隔内没有写入数据
     * @param ctx
     */
    protected void handleWriterIdle(ChannelHandlerContext ctx) {
       logger.debug("---WRITER_IDLE---指定的时间间隔内没有写入数据");
    }

    /**处理指定的时间间隔内没有读/写入数据
     * @param ctx
     */
    protected void handleAllIdle(ChannelHandlerContext ctx) {
       logger.debug("---ALL_IDLE---指定的时间间隔内没有读/写入数据");
    }
}