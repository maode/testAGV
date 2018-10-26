package com.his.net.agv.nettybak.entity;

import java.util.List;

/**  
 * @Title: AgvEvent.java
 * @Package com.his.net.agv.nettybak.entity
 * @Description: agv通信事件对象
 * @author ZhengMaoDe   
 * @date 2018年7月11日 上午11:47:42 
 */
public interface AgvEvent extends Cloneable {

	/**返回当前位置
	 * @return
	 */
	String getPosition();
	
	/**返回当前行进中的路线
	 * @return
	 */
	List<String> getLine();
	
	/**返回当前agv的code
	 * @return
	 */
	String getAgvCode();
	
	/**返回当前plcc返回的信息
	 * @return
	 */
	PlcResult getPlcResult();
}
