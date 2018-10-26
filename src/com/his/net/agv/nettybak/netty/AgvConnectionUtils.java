package com.his.net.agv.nettybak.netty;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.his.net.agv.nettybak.entity.AgvSession;
import com.his.net.agv.nettybak.listener.AgvListener;
import com.his.net.agv.nettybak.netty.client.plc.PlcClientInitializer;
import com.his.net.agv.nettybak.netty.client.plc.PlcDefaultClientHandler;
import com.his.net.agv.nettybak.netty.client.q600.Q600ClientInitializer;
import com.his.net.agv.nettybak.netty.client.q600.Q600DefaultClientHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Title: AgvConnectionUtils.java
 * @Package com.his.net.agv.nettybak.netty
 * @Description: agv链接工具类
 * @author ZhengMaoDe
 * @date 2018年7月12日 下午5:34:49
 */
public class AgvConnectionUtils {
	private static final Logger logger=LoggerFactory.getLogger(AgvConnectionUtils.class);
	private static NioEventLoopGroup workGroup = new NioEventLoopGroup();
	
	public static Q600ClientInitializer q600ClientInitializer=new Q600ClientInitializer(new Q600DefaultClientHandler()); 
	private static Bootstrap q600Bootstrap = new Bootstrap().group(workGroup).channel(NioSocketChannel.class)
			.handler(q600ClientInitializer);
	
	public static PlcClientInitializer plcClientInitializer=new PlcClientInitializer(new PlcDefaultClientHandler());
	public static Bootstrap plcBootstrap = new Bootstrap().group(workGroup).channel(NioSocketChannel.class)
			.handler(plcClientInitializer);
	
	
	/**为当前的agv业务处理器添加监听器
	 * @param listener
	 */
	public static void addListener(AgvListener listener) {
		q600ClientInitializer.getBusinessHandler().addListener(listener);
		plcClientInitializer.getBusinessHandler().addListener(listener);
	}
	
	
	/**
	 * 链接q600，链接成功后，会将channel放入当前agvsession
	 * <p> 如果链接失败，会每隔默认秒数进行自动重连。
	 * <p> 默认秒数见：AgvConstants.DEFAULT_REDIRECT_SECOND 
	 * @param ip
	 * @param port
	 * @param sync 是否同步等待链接返回 true:等待 false:不等待
	 */
	public static void connectQ600(String ip,int port,boolean sync) {
		
		AgvSession session=AgvUtils.getAgvSessionByQ600IP(ip);
		Channel channel=session.getQ600Channel();
        if (channel != null && channel.isActive()) {
            return ;
        }

        ChannelFuture future = null;
        if(sync) {
        	try {
        		future = q600Bootstrap.connect(ip, port).sync();
        		logger.debug("Q600链接执行完同步等待channel逻辑");
        	} catch (InterruptedException e) {
        		e.printStackTrace();
        	}
        }else {
        	future = q600Bootstrap.connect(ip, port);
        }
        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture futureListener) throws Exception {
                if (futureListener.isSuccess()) {
                	Channel channel = futureListener.channel();
                    AgvUtils.getAgvSessionByQ600IP(ip).setQ600Channel(channel);
                    logger.debug("Q600链接成功!当前Q600的IP为："+ip);
                } else {
                    logger.debug("Q600链接失败!当前Q600的IP为："+ip);

                    futureListener.channel().eventLoop().schedule(new Runnable() {
                        @Override
                        public void run() {
                        	connectQ600(ip,port,sync);
                        }
                    }, AgvConstants.DEFAULT_REDIRECT_SECOND, TimeUnit.SECONDS);
                }
            }
        });
        
		
	}
	/**
	 * 链接plc，链接成功后，会将channel放入当前agvsession
	 * <p> 如果链接失败，会每隔默认秒数进行自动重连。
	 * <p> 默认秒数见：AgvConstants.DEFAULT_REDIRECT_SECOND 
	 * @param ip
	 * @param port
	 * @param sync 是否同步等待链接返回 true:等待 false:不等待
	 */
	public static void connectPlc(String ip,int port,boolean sync) {
		
		AgvSession session=AgvUtils.getAgvSessionByPlcIP(ip);
		Channel channel=session.getPlcChannel();
        if (channel != null && channel.isActive()) {
            return ;
        }

        ChannelFuture future = null;
        if(sync) {
        	try {
        		future=plcBootstrap.connect(ip, port).sync();
				logger.debug("PLC链接执行完同步等待channel逻辑");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }else {
        	future=plcBootstrap.connect(ip, port);
        }
        
        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture futureListener) throws Exception {
                if (futureListener.isSuccess()) {
                	Channel channel = futureListener.channel();
                    AgvUtils.getAgvSessionByPlcIP(ip).setPlcChannel(channel);
                    logger.debug("PLC链接成功!当前PLC的IP为："+ip);
                } else {
                    logger.debug("PLC链接失败!当前PLC的IP为："+ip);

                    futureListener.channel().eventLoop().schedule(new Runnable() {
                        @Override
                        public void run() {
                        	connectPlc(ip,port,sync);
                        }
                    }, AgvConstants.DEFAULT_REDIRECT_SECOND, TimeUnit.SECONDS);
                }
            }
        });
        

		
	}

	/**链接agv
	 * @param q600Ip
	 * @param q600Port
	 * @param plcIp
	 * @param plcPort
	 * @param sync
	 */
	public static void connect(String q600Ip,int q600Port,String plcIp,int plcPort,boolean sync) {
		connectQ600(q600Ip, q600Port, sync);
		connectPlc(plcIp, plcPort, sync);
	}
	/**链接agv 同步
	 * @param q600Ip
	 * @param q600Port
	 * @param plcIp
	 * @param plcPort
	 * @return
	 */
	public static AgvSession connectSync(String q600Ip,int q600Port,String plcIp,int plcPort) {
		connect(q600Ip, q600Port, plcIp, plcPort, true);
		return AgvUtils.getAgvSessionByQ600IP(q600Ip);
	}
	/**链接agv 异步
	 * @param q600Ip
	 * @param q600Port
	 * @param plcIp
	 * @param plcPort
	 * @return
	 */
	public static void connectAsync(String q600Ip,int q600Port,String plcIp,int plcPort) {
		connect(q600Ip, q600Port, plcIp, plcPort, false);
	}
}
