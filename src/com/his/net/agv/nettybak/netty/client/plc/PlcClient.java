package com.his.net.agv.nettybak.netty.client.plc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.his.net.agv.nettybak.entity.PlcCmd;
import com.his.net.agv.nettybak.netty.AgvConnectionUtils;
import com.his.net.agv.nettybak.netty.AgvConstants;
import com.his.net.agv.nettybak.util.HexUtils;
import com.his.net.agv.nettybak.util.PlcCmdFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;


/**  
 * @Title: PlcClient.java
 * @Package com.his.net.agv.nettybak.netty.client.plc
 * @Description: plc通信客户端
 * @author ZhengMaoDe   
 * @date 2018年7月11日 上午9:32:46 
 */
public class PlcClient {
	private Logger logger=LoggerFactory.getLogger(getClass());

    private NioEventLoopGroup workGroup = new NioEventLoopGroup();
    private Channel channel;
    private Bootstrap bootstrap;
    private final String host;
    private final int port;
    private boolean sync=false;//是否同步等待连接创建完成，默认异步

    public PlcClient(){
    	this(AgvConstants.PLC_DEFAULT_HOST,AgvConstants.PLC_DEFAULT_PORT,false);
    }
    public PlcClient(String host, int port,boolean sync){
        this.host = host;
        this.port = port;
        this.sync=sync;
    }



    public void start() {
        try {
            bootstrap = new Bootstrap();
            bootstrap
                    .group(workGroup)
                    .channel(NioSocketChannel.class)
                    .handler(AgvConnectionUtils.plcClientInitializer);
            if(sync) {
            	ChannelFuture future=bootstrap.connect(host, port).sync();
            	channel=future.channel();
            	addReconnection(future);
            }else {
            	doConnect();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void doConnect() {
        if (channel != null && channel.isActive()) {
        	logger.debug("当前链接有活动的channel,不执行重连逻辑");
            return ;
        }

        ChannelFuture future = bootstrap.connect(host, port);

        addReconnection(future);
        
    }

    public void addReconnection(ChannelFuture future) {
        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture futureListener) throws Exception {
                if (futureListener.isSuccess()) {
                    channel = futureListener.channel();
                } else {
                    logger.debug("链接失败"+AgvConstants.DEFAULT_REDIRECT_SECOND+"s后执行重连！");

                    futureListener.channel().eventLoop().schedule(new Runnable() {
                        @Override
                        public void run() {
                            doConnect();
                        }
                    }, AgvConstants.DEFAULT_REDIRECT_SECOND, TimeUnit.SECONDS);
                }
            }
        });   	
    }




	/**
	 * 本地非集成环境测试方法
	 * 
	 * @throws Exception
	 */
	public void test() throws Exception {

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			String readLine = in.readLine();
			PlcCmd msg=null;
			switch(readLine) {
			case "1" : msg = PlcCmdFactory.getUp(0); break;
			case "2" : msg = PlcCmdFactory.getDown(0); break;
			case "0" : msg = PlcCmdFactory.getStopJack(0); break;
			case "55" : msg = PlcCmdFactory.getInquiryStatus(); break;
			case "11" : msg = PlcCmdFactory.getForward(); break;
			case "12" : msg = PlcCmdFactory.getBackWards(); break;
			case "13" : msg = PlcCmdFactory.getToLeft(); break;
			case "14" : msg = PlcCmdFactory.getToRight(); break;
			case "15" : msg = PlcCmdFactory.getLevorotation(); break;
			case "16" : msg = PlcCmdFactory.getDextrorotation(); break;
			case "00" : msg = PlcCmdFactory.getStopRun(); break;
			}
			
			if("loop".equals(readLine)) {
				while(true) {
					msg=PlcCmdFactory.getUp(0);
					logger.debug("我是客户端，我准备发送的消息为-升顶：" + HexUtils.toHexString(msg.toByteArray()) );
					channel.writeAndFlush(msg);
					Thread.sleep(1000);
				}
			}
			if("loopd".equals(readLine)) {
				msg=PlcCmdFactory.getStopJack(0);
				logger.debug("我是客户端，我准备发送的消息为-停止顶升功能：" + HexUtils.toHexString(msg.toByteArray()) );
				channel.writeAndFlush(msg);
				while(true) {
					msg=PlcCmdFactory.getDown(0);
					logger.debug("我是客户端，我准备发送的消息为-落顶：" + HexUtils.toHexString(msg.toByteArray()) );
					channel.writeAndFlush(msg);
					Thread.sleep(1000);
				}
			}
			if("loopr".equals(readLine)) {
				while(true) {
					msg=PlcCmdFactory.getInquiryStatus();
					logger.debug("我是客户端，我准备发送的消息为-问询：" + HexUtils.toHexString(msg.toByteArray()) );
					channel.writeAndFlush(msg);
					Thread.sleep(1000);
				}
			}
			
			logger.debug("我是客户端，我准备发送的消息为：" + HexUtils.toHexString(msg.toByteArray()) );
			channel.writeAndFlush(msg);
		}

	}
	
	
    public void writeAndFlush(PlcCmd msg) {
    	logger.debug("我是客户端，我准备发送的消息为：" + HexUtils.toHexString(msg.toByteArray()) );
    	channel.writeAndFlush(msg);
    }
    
    public static void main(String[] args) throws Exception {
    	PlcClient client = new PlcClient(AgvConstants.PLC_DEFAULT_HOST,AgvConstants.PLC_DEFAULT_PORT,true);
        client.start();
        client.test();
    }

    
	
}









