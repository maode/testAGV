package com.his.net.agv.nettybak.listener;

import com.his.net.agv.nettybak.entity.AgvEvent;

/**  
 * @Title: AgvListener.java
 * @Package com.his.net.agv.nettybak.listener
 * @Description: Agv通信事件监听
 * @author ZhengMaoDe   
 * @date 2018年7月11日 上午11:41:57 
 */
public interface AgvListener {

	/**当下位机反馈当前agv位置时触发
	 * @param evt
	 */
	default void onReturnPosition(AgvEvent evt) {};
	
	/**当agv行程结束时触发
	 * @param evt
	 */
	default void onTripEnd(AgvEvent evt) {};
	
	/**当agv行程开始时触发
	 * @param evt
	 */
	default void onTripStart(AgvEvent evt) {};
	/**当agv有数据反馈时触发
	 * @param evt
	 */
	default void onReadMsg(AgvEvent evt) {};
}
