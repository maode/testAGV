package com.his.net.agv.nettybak.netty.client.plc;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.his.net.agv.nettybak.entity.AgvEntity;
import com.his.net.agv.nettybak.entity.AgvSession;
import com.his.net.agv.nettybak.entity.PlcCmd;
import com.his.net.agv.nettybak.entity.PlcCommand;
import com.his.net.agv.nettybak.entity.PlcResult;
import com.his.net.agv.nettybak.netty.AgvUtils;
import com.his.net.agv.nettybak.util.HexUtils;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;


/**  
 * @Title: PlcDefaultClientHandler.java
 * @Package com.hsmart.hjac.netty.client.plc
 * @Description: plc通信默认业务处理器
 * @author ZhengMaoDe   
 * @date 2018年7月11日 上午9:34:31 
 */
@Sharable
public class PlcDefaultClientHandler extends PlcBaseClientHandler {
	
	private Logger logger=LoggerFactory.getLogger(getClass());



	
	/**
	 * 处理下发plc指令
	 * @param ctx
	 * @param s
	 * @see com.hsmart.hjac.netty.client.q600.Q600BaseClientHandler#businessHandler(io.netty.channel.ChannelHandlerContext, java.lang.String)
	 */
	@Override
	protected void businessHandler(ChannelHandlerContext ctx, PlcResult s) {
		//接收到消息后触发onReadMsg事件
		AgvEntity entity=new AgvEntity();
		entity.setPlcResult(s);
		onReadMsg(entity);
		
		
		Channel channel=ctx.channel();
		//目前channelKey为远程主机的ip，根据具体情况，channelKey可以在消息体中携带（Q600除外，不支持）
		 InetSocketAddress insocket = (InetSocketAddress) channel.remoteAddress();
         String remoteIP = insocket.getAddress().getHostAddress();
         //logger.debug("我是Q600客户端，当前链接的Q600IP为："+remoteIP);
         //System.out.println("SYSOUT-我是Q600客户端，我接收到的消息为："+s);
         logger.debug("我是plc客户端，当前链接的plc-IP为：{}我接收到的消息为：{}",remoteIP,HexUtils.toHexString(s.toByteArray()));
	
	}




	
	/**
	 * 
	 * 
	 * 测试方法-链接建立成功后发送一条指令
	 * @param ctx
	 * @throws Exception
	 * @see com.hsmart.hjac.netty.client.plc.PlcBaseClientHandler#channelActive(io.netty.channel.ChannelHandlerContext)
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelActive(ctx);
		PlcCmd cmd=new PlcCmd();
		//ctx.writeAndFlush(cmd);
	}
	
	

}
