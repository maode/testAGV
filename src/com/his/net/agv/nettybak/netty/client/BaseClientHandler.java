package com.his.net.agv.nettybak.netty.client;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.his.net.agv.nettybak.entity.AgvEvent;
import com.his.net.agv.nettybak.listener.AgvListener;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;

/**  
 * @Title: BaseClientHandler.java
 * @Package com.his.net.agv.nettybak.netty.client
 * @Description: 所有agv客户端业务处理器基类
 * @author ZhengMaoDe   
 * @date 2018年7月13日 上午10:19:11 
 */
public abstract class BaseClientHandler<I> extends SimpleChannelInboundHandler<I> implements AgvListener{

	private static final Logger logger=LoggerFactory.getLogger(BaseClientHandler.class);
	private List<AgvListener> listeners=new ArrayList<AgvListener>(1);

	/** @return listeners */
	public List<AgvListener> getListeners() {
		return listeners;
	}

	/** @param listeners 添加 listener */
	public void addListener(AgvListener listener) {
		this.listeners.add(listener);
	}

	/**
	 * 需要实现的处理业务逻辑的方法
	 */
	protected abstract void businessHandler(ChannelHandlerContext ctx, I s);
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, I s) throws Exception {
		logger.trace("我是BaseClientHandler的channelRead0，我默认会调用businessHandler方法");
		businessHandler(ctx, s);
	}
	
    /**
     * 默认心跳处理
     * <p>读超时默认调用handleReaderIdle
     * <br/>写超时默认调用handleWriterIdle
     * <br/>读/写超时默认调用handleAllIdle
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
    /**处理指定的时间间隔内没有读到数据的空实现-如有需要请覆写
     * @param ctx
     */
    protected void handleReaderIdle(ChannelHandlerContext ctx) {
       logger.trace("---READER_IDLE---指定的时间间隔内没有读到数据");
    }

    /**处理指定的时间间隔内没有写入数据的空实现-如有需要请覆写
     * @param ctx
     */
    protected void handleWriterIdle(ChannelHandlerContext ctx) {
       logger.trace("---WRITER_IDLE---指定的时间间隔内没有写入数据");
    }

    /**处理指定的时间间隔内没有读/写入数据的空实现-如有需要请覆写
     * @param ctx
     */
    protected void handleAllIdle(ChannelHandlerContext ctx) {
       logger.trace("---ALL_IDLE---指定的时间间隔内没有读/写数据");
    }	
	/**
	 * 反馈位置时-事件监听的默认实现
	 * @param evt
	 * @see com.his.net.agv.nettybak.listener.AgvListener#onReturnPosition(com.his.net.agv.nettybak.entity.AgvEvent)
	 */
	@Override
	public void onReturnPosition(AgvEvent evt) {
		logger.trace(new StringBuilder(evt.getAgvCode()).append(":触发onReturnPosition事件").toString());
		CompletableFuture.runAsync(()->{
			logger.trace(new StringBuilder(evt.getAgvCode()).append(":执行onReturnPosition事件").toString());
			this.getListeners().forEach(listener -> listener.onReturnPosition(evt));
		});
	}

	
	/**
	 * 行程结束时-事件监听的默认实现 
	 * @param evt
	 * @see com.his.net.agv.nettybak.listener.AgvListener#onTripEnd(com.his.net.agv.nettybak.entity.AgvEvent)
	 */
	@Override
	public void onTripEnd(AgvEvent evt) {
		logger.trace(new StringBuilder(evt.getAgvCode()).append(":触发onTripEnd事件").toString());
		CompletableFuture.runAsync(() -> {
			logger.trace(new StringBuilder(evt.getAgvCode()).append(":执行onTripEnd事件").toString());
			this.getListeners().forEach(listener -> listener.onTripEnd(evt));
		});
	}

	
	/**
	 * 行程开始时-事件监听的默认实现 
	 * @param evt
	 * @see com.his.net.agv.nettybak.listener.AgvListener#onTripStart(com.his.net.agv.nettybak.entity.AgvEvent)
	 */
	@Override
	public void onTripStart(AgvEvent evt) {
		logger.trace(new StringBuilder(evt.getAgvCode()).append(":触发onTripStart事件").toString());
		CompletableFuture.runAsync(()->{
			logger.trace(new StringBuilder(evt.getAgvCode()).append(":执行onTripStart事件").toString());
			this.getListeners().forEach(listener -> listener.onTripStart(evt));
		});
		
	}
	/**
	 * 下位机反馈数据时-事件监听的默认实现 
	 * @param evt
	 * @see com.his.net.agv.nettybak.listener.AgvListener#onReadMsg(com.his.net.agv.nettybak.entity.AgvEvent)
	 */
	@Override
	public void onReadMsg(AgvEvent evt) {
		logger.trace(new StringBuilder(evt.getAgvCode()).append(":触发onReadMsg事件").toString());
		CompletableFuture.runAsync(()->{
			logger.trace(new StringBuilder(evt.getAgvCode()).append(":执行onReadMsg事件").toString());
			this.getListeners().forEach(listener -> listener.onReadMsg(evt));
		});
		
	}

	

}
