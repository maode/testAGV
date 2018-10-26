package com.his.net.agv.nettybak.netty.client.q600;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.his.net.agv.nettybak.netty.AgvConnectionUtils;
import com.his.net.agv.nettybak.netty.AgvConstants;
import com.his.net.agv.nettybak.util.Q600CmdFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**  
 * @Title: AgvClient.java
 * @Package com.his.net.agv.nettybak.netty.client
 * @Description: q600通信客户端
 * @author ZhengMaoDe   
 * @date 2018年5月23日 上午10:38:42 
 */
public class Q600Client {
	private Logger logger=LoggerFactory.getLogger(getClass());

    private NioEventLoopGroup workGroup = new NioEventLoopGroup();
    private Channel channel;
    private Bootstrap bootstrap;
    private final String host;
    private final int port;
    private boolean sync=false;//是否同步等待连接创建完成，默认异步
    private CountDownLatch latch=new CountDownLatch(1);

    public Q600Client(){
    	this(AgvConstants.Q600_DEFAULT_HOST,AgvConstants.Q600_DEFAULT_PORT,false);
    }
    public Q600Client(String host, int port,boolean sync){
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
                    .handler(AgvConnectionUtils.q600ClientInitializer);
            doConnect();
            if(sync) {
            	latch.await(AgvConstants.DEFAULT_CONNECT_WAIT,TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
			latch.countDown();
		}
    }

    protected void doConnect() {
        if (channel != null && channel.isActive()) {
            return ;
        }

        ChannelFuture future = bootstrap.connect(host, port);

        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture futureListener) throws Exception {
                if (futureListener.isSuccess()) {
                    channel = futureListener.channel();
                    latch.countDown();//连接成功，递减锁存器
                    logger.debug("链接成功!递减锁存器.");
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
			String msg="";
			switch(readLine) {
			case "1" : msg = Q600CmdFactory.Q600_GET_POSE; break;
			case "2" : msg = Q600CmdFactory.Q600_CHECK_OBS; break;
			case "3" : msg = Q600CmdFactory.Q600_START_NAVIGATION; break;
			case "4" : msg = Q600CmdFactory.Q600_START_NAVIGATION_LOOP; break;
			case "0" : msg = Q600CmdFactory.Q600_STOP_NAVIGATION; break;
			}
			
			
			if("loop".equals(readLine)) {
				while(true) {
					msg=Q600CmdFactory.Q600_GET_POSE;
					logger.debug("我是客户端，我准备发送的消息为-问询坐标：" + msg );
					channel.writeAndFlush(msg);
					Thread.sleep(1000);
				}
			}
			
			
			logger.debug("我是客户端，我准备发送的消息为：" + msg);
			channel.writeAndFlush(msg);
		}

	}
	
	
    
    
    public static void main(String[] args) {
    	Q600Client client = new Q600Client(AgvConstants.Q600_DEFAULT_HOST,AgvConstants.Q600_DEFAULT_PORT,true);
    	//AgvClient client = new AgvClient("127.0.0.1",6666);
        client.start();
        try {
			client.test();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    
	
}









