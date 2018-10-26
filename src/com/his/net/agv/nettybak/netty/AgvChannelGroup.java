package com.his.net.agv.nettybak.netty;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.EventExecutor;

/**  
 * @Title: AgvChannelGroup.java
 * @Package com.his.net.agv.nettybak.netty
 * @Description: DefaultChannelGroup增强处理
 * <p>支持按照自定义key来存取channel和channel需要执行的指令
 * @author ZhengMaoDe   
 * @date 2018年5月23日 下午1:07:14 
 */
public class AgvChannelGroup<E> extends DefaultChannelGroup {

	/** @Fields channels : 信道 */
	private final Map<String,ChannelId> channels = new ConcurrentHashMap<String,ChannelId>();
	/** @Fields cmds : 指令 */
	private final Map<String,ConcurrentLinkedQueue<E>> cmds = new ConcurrentHashMap<String, ConcurrentLinkedQueue<E>>();
	
	public AgvChannelGroup(EventExecutor executor) {
		super(executor);
	}
	
	public boolean add(String key,Channel channel) {
		channels.put(key, channel.id());
		return super.add(channel);
	}
	
	public Channel find(String key) {
		ChannelId id=channels.get(key);
		return super.find(id);
	}
	public ConcurrentLinkedQueue<E> getCmdQueueByKey(String key){
		return cmds.get(key);
	}
	public void putCmdQueue(String key,ConcurrentLinkedQueue<E> queue) {
		cmds.put(key, queue);
	}

}
