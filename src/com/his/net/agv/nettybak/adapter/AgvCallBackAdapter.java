package com.his.net.agv.nettybak.adapter;

import com.his.net.agv.nettybak.service.AgvCallBack;

import lombok.Getter;

/**  
 * @Title: AgvCallBackAdapter.java
 * @Package com.hsmart.his.modules.agv.netty
 * @Description: agv业务层回调方法适配器
 * @author ZhengMaoDe   
 * @date 2018年7月4日 上午10:29:11 
 */
@Getter
public abstract class AgvCallBackAdapter<S,A> implements AgvCallBack {
	
	/** @Fields callBackService : 执行具体回调的对象 */
	private S callBackService;
	
	/** @Fields args : 执行具体回调的对象所需的参数 */
	private A args;
	
	
	public AgvCallBackAdapter(S callBackService,A args){
		this.callBackService=callBackService;
		this.args=args;
	}

}
