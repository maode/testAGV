package com.his.net.agv.nettybak.netty.server.q600;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;



/**  
 * @Title: AgvServerHandler.java
 * @Package com.his.net.agv.nettybak.nettyServer
 * @Description: 自定义业务处理器
 * @author ZhengMaoDe   
 * @date 2018年5月23日 上午9:53:58 
 */
@Sharable                                        //1
public class Q600ServerHandler extends SimpleChannelInboundHandler<String> { // (1)

	private Logger logger = LoggerFactory.getLogger(getClass());
	

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {  // (2)
		Channel incoming = ctx.channel();
		
		// Broadcast a message to multiple Channels
		//channels.writeAndFlush("[SERVER] - " + incoming.remoteAddress() + " 加入-广播\n");//广播-字符
		//channels.writeAndFlush(new AgvCmdQ600());//广播-字节
		logger.debug("[SERVER] - " + incoming.remoteAddress() + " 加入");
		logger.debug("打印remoteAddress().toString()："+incoming.remoteAddress().toString());
		//channels.add(ctx.channel());
		//AgvUtils.getChannelGroup().add(incoming.remoteAddress().toString(), ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {  // (3)
		Channel incoming = ctx.channel();
		
		// Broadcast a message to multiple Channels
		//channels.writeAndFlush("[SERVER] - " + incoming.remoteAddress() + " 离开-广播\n");//广播
		logger.debug("它离开了："+incoming.remoteAddress().toString());
		// A closed Channel is automatically removed from ChannelGroup,
		// so there is no need to do "channels.remove(ctx.channel());"
    }

    @Override
	protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception { // (4)
		Channel incoming = ctx.channel();//当前发送消息的客户端
//		for (Channel channel : channels) {
//            if (channel != incoming){
//                channel.writeAndFlush("[" + incoming.remoteAddress() + "]" + s + "\n");
//            } else {
//            	channel.writeAndFlush(incoming.remoteAddress()+"[you]" + s + "\n");
//            }
//        }
		logger.debug("我是服务端，我接收到的消息为："+s);
		String rmsg="bbbbbbbbbb\n";
		logger.debug("我是服务端，我准备发送的消息为："+rmsg);
		incoming.writeAndFlush(rmsg);
	}
  
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception { // (5)
        Channel incoming = ctx.channel();
		System.out.println("AgvClient:"+incoming.remoteAddress()+"在线");
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
        Channel incoming = ctx.channel();
		System.out.println("AgvClient:"+incoming.remoteAddress()+"掉线");
	}
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (7)
    	Channel incoming = ctx.channel();
		System.out.println("AgvClient:"+incoming.remoteAddress()+"异常");
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}