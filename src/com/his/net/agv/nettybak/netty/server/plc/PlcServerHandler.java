package com.his.net.agv.nettybak.netty.server.plc;


import java.nio.CharBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.his.net.agv.nettybak.entity.PlcResult;
import com.his.net.agv.nettybak.util.ByteUtils;
import com.his.net.agv.nettybak.util.HexUtils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;



/**  
 * @Title: PlcServerHandler.java
 * @Package com.his.net.agv.nettybak.netty.server.plc
 * @Description: 自定义业务处理器
 * @author ZhengMaoDe   
 * @date 2018年7月16日 上午10:05:35 
 */
@Sharable                                        
public class PlcServerHandler extends ChannelInboundHandlerAdapter  { 

	private Logger logger = LoggerFactory.getLogger(getClass());

	
    @Override
    public void channelRead(ChannelHandlerContext ctx,
        Object msg) {
        ByteBuf in = (ByteBuf) msg;
        int len=in.readableBytes();
        byte[] read=new byte[len];
        in.readBytes(read);
        logger.debug("我是测试服务端，接受到的数据字节长度为: {} ,值为：{}",len,HexUtils.toHexString(read));     
        //自定义返回数据
        //ByteBuf ou=ByteBufUtil.encodeString(ctx.alloc(), CharBuffer.wrap("xxxxxxxx\n"), CharsetUtil.UTF_8);
        ByteBuf ou=ctx.alloc().buffer();
        String hexStr="01030A2CBA00010001000100011384";
        //带校验和的问询反馈
        byte[] bytes=new byte[] { 0x01, 0x03, 0x18, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01,(byte) 0xe6,0x6a };
		ou.writeBytes(bytes);
        logger.debug("我是测试服务端，我准备返回:{} ",HexUtils.toHexString(bytes));
        ctx.writeAndFlush(ou);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    	logger.debug("-- plc test server channelReadComplete----");
//        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)//4
//        .addListener(ChannelFutureListener.CLOSE);
    }
    
    

    
	/**
	 * 测试用-链接建立后发一些垃圾数据,测试数据过滤
	 * @param ctx
	 * @throws Exception
	 * @see io.netty.channel.ChannelInboundHandlerAdapter#channelActive(io.netty.channel.ChannelHandlerContext)
	 */
//	@Override
//	public void channelActive(ChannelHandlerContext ctx) throws Exception {
//		// TODO Auto-generated method stub
//		int i=1;
//		do {
//
//			
//			ByteBuf ou=ByteBufUtil.encodeString(ctx.alloc(), CharBuffer.wrap("wwwwwww\n"), CharsetUtil.UTF_8);
//			ByteBuf ou2=ByteBufUtil.encodeString(ctx.alloc(), CharBuffer.wrap("ooooo\n"), CharsetUtil.UTF_8);
//			ByteBuf ou3=ByteBufUtil.encodeString(ctx.alloc(), CharBuffer.wrap("3333"), CharsetUtil.UTF_8);
//			ByteBuf ou4=ByteBufUtil.encodeString(ctx.alloc(), CharBuffer.wrap("4444\n"), CharsetUtil.UTF_8);
//			PlcResult result=new PlcResult();
//			result.setCode((byte)3);
//			ByteBuf ou5=ctx.alloc().buffer();
//			ByteBuf ou6=ctx.alloc().buffer();
//			ou5.writeBytes(HexUtils.fromHexString("01030A2CBA00010001000100011384"));
//			ou6.writeBytes(HexUtils.fromHexString("01030A2CBA00010001000100011384"));
//			//ctx.write(ou2);
//			//ctx.write(ou4);
//			ctx.write(ou3);
//			ctx.flush();
//			ctx.write(ou5);
//			ctx.flush();
//			ctx.write(ou);
//			ctx.write(ou6);
//			ctx.flush();
//			System.out.println("写了第"+i+"遍,即将沉睡");
//				
//		}while(false);
//		
//		//ctx.write(msg)
//	}

	@Override
    public void exceptionCaught(ChannelHandlerContext ctx,
        Throwable cause) {
        cause.printStackTrace();               
        ctx.close();                           
    }
	

	
}