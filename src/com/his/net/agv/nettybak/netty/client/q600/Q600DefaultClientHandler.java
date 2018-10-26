package com.his.net.agv.nettybak.netty.client.q600;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.his.net.agv.nettybak.entity.AgvSession;
import com.his.net.agv.nettybak.entity.Q600Command;
import com.his.net.agv.nettybak.netty.AgvUtils;
import com.his.net.agv.nettybak.util.Q600CmdFactory;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;

/**  
 * @Title: DefaultQ600AgvClientHandler.java
 * @Package com.his.net.agv.nettybak.netty.client
 * @Description: q600默认业务处理器
 * @author ZhengMaoDe   
 * @date 2018年7月10日 上午9:20:12 
 */
@Sharable
public class Q600DefaultClientHandler extends Q600BaseClientHandler {
	
	private Logger logger=LoggerFactory.getLogger(getClass());

	
	/**判断如果q600返回的信息为坐标信息，则触发反馈位置监听
	 * @param session
	 * @param s
	 */
	public void ifPosition(AgvSession session,String s) {
		if(StringUtils.isBlank(s)) {
			return;
		}
		String[] resultArray = s.split(" ");
		if (resultArray.length == 7&&"P".equals(resultArray[0])) {
			session.getAgvEntity().setCurrentX(resultArray[2]);
			session.getAgvEntity().setCurrentY(resultArray[3]);
			//onReturnPosition(ObjectUtils.clone(session.getAgvEntity()));
			//clone方式改为通过返回只含有get方法的接口类型确保对象状态不被改变,避免clone的资源消耗
			onReturnPosition(session.getAgvEntity());
		}
	}


	/**
	 * 
	 * 
	 * 处理下发Q600指令
	 * @param ctx
	 * @param s
	 * @see com.his.net.agv.nettybak.netty.client.q600.Q600BaseClientHandler#businessHandler(io.netty.channel.ChannelHandlerContext, java.lang.String)
	 */
	@Override
	protected void businessHandler(ChannelHandlerContext ctx, String s) {
		Channel channel=ctx.channel();
		//目前channelKey为远程主机的ip，根据具体情况，channelKey可以在消息体中携带（Q600除外，不支持）
		 InetSocketAddress insocket = (InetSocketAddress) channel.remoteAddress();
         String remoteIP = insocket.getAddress().getHostAddress();
         //logger.debug("我是Q600客户端，当前链接的Q600IP为："+remoteIP);
         //System.out.println("SYSOUT-我是Q600客户端，我接收到的消息为："+s);
         logger.debug("我是Q600客户端，当前链接的Q600IP为：{}我接收到的消息为：{}",remoteIP,s);
	
	}

}
