package com.his.net.agv.nettybak.service;


/**  
 * @Package com.his.net.agv.nettybak.service
 * @Description: agv导航任务回调接口
 * @author ZhengMaoDe   
 * @date 2018年8月15日 下午3:01:13 
 */
public interface AgvCallBackService<T> {
	
	/**
	 * "升顶"完成时触发的回调
	 * @param arg
	 */
	public void upOver(T arg);
	/**
	 * 离开"升顶"地点时触发的回调
	 * @param arg
	 */
	public void leaveUp(T arg);
	
	/**
	 * "落顶"完成时触发的回调
	 * @param arg
	 */
	public void downOver(T arg);
}
