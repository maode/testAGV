package com.his.net.agv.nettybak.netty.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import lombok.Getter;

/**  
 * @Title: BaseChannelInitializer.java
 * @Package com.his.net.agv.nettybak.netty.client
 * @Description: TODO
 * @author ZhengMaoDe   
 * @date 2018年7月13日 下午12:52:18 
 */
@Getter
public abstract class BaseChannelInitializer<C extends Channel> extends ChannelInitializer<C> {
	private BaseClientHandler<?> businessHandler;
	public BaseChannelInitializer(BaseClientHandler<?> handler) {
		this.businessHandler=handler;
	}

}
