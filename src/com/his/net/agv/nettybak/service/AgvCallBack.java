package com.his.net.agv.nettybak.service;


/**  
 * @Package com.hsmart.hjac
 * @Description: agv业务回调接口
 * @author ZhengMaoDe   
 * @date 2018年8月3日 下午6:41:37 
 */
public interface AgvCallBack {

	/**
	 * 业务回调方法,具体业务系统来实现.一般用于agv完成特定任务后执行某个业务回调
	 * @param arg
	 */
	public void callBack();
}
