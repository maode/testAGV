package com.his.net.agv.nettybak.netty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.his.net.agv.nettybak.entity.AgvEntity;
import com.his.net.agv.nettybak.entity.AgvEvent;
import com.his.net.agv.nettybak.entity.AgvSession;
import com.his.net.agv.nettybak.listener.AgvListener;

/**
 * @Title: AgvUtils.java
 * @Package com.his.net.agv.nettybak.netty
 * @Description: agv操作工具类
 * @author ZhengMaoDe
 * @date 2018年7月2日 下午2:15:10
 */
public class AgvUtils {


	
	/**初始化当前agv的session
	 * @param agv
	 */
	public static void initAgvSession(AgvEntity agv) {
		AgvSessionUtils.initAgvSession(agv);
	}
	/**初始化所有agv链接
	 * @param agvs 所有需要建立链接的agv实体
	 * @param sync 是否同步等待每一个链接建立完成
	 * <p>如果sync为true，则该方法会同步等待每一个链接建立成功或超时。
	 * 为false则不等待。如果选择同步方式，则该方法执行完成后。通过调用以下方法：
	 * <p>AgvUtils.getAgvSessionByKey <br/>
	 * AgvUtils.getAgvSessionByQ600IP <br/>
	 * AgvUtils.getAgvSessionByPlcIP
	 * <p>均能立即返回agv的会话信息
	 */
	public static void initAgvConnection(List<AgvEntity> agvs,boolean sync) {
		agvs.forEach(agv -> {
			initAgvSession(agv);
			AgvConnectionUtils.connect(agv.getQ600Ip(), agv.getQ600Port(), agv.getPlcIp(), agv.getPlcPort(), sync);
		});
	}

	/**初始化所有agv链接-异步方式
	 * @param agvs 所有需要建立链接的agv实体
	 */
	public static void initAgvConnection(List<AgvEntity> agvs) {
		initAgvConnection(agvs, false);
	}
		
	/**根据q600的ip获取当前agvSession
	 * @param ip
	 * @return
	 */
	public static AgvSession getAgvSessionByQ600IP(String ip) {
		return AgvSessionUtils.getAgvSessionByQ600IP(ip);
	}
	/**根据plc的ip获取当前agvSession
	 * @param ip
	 * @return
	 */
	public static AgvSession getAgvSessionByPlcIP(String ip) {
		return AgvSessionUtils.getAgvSessionByPlcIP(ip);
	}
	/**根据sessionKey获取当前agvSession
	 * @param sessionKey
	 * @return
	 */
	public static AgvSession getAgvSessionByKey(String sessionKey) {
		return AgvSessionUtils.getAgvSessionByKey(sessionKey);
	}
	
	/**获取当前所有agv对象
	 * @return
	 */
	public static List<AgvEvent> getAllAgvStatus() {
		List<AgvEvent> events=new ArrayList<>();
		Collection<AgvSession> sessions=AgvSessionUtils.getAllAgvSessions().values();
		for(AgvSession session:sessions) {
			events.add(session.getAgvEntity());
		}
		return events;
	}
	/**
	 * 给所有的agv注册监听器
	 * @param listener
	 */
	public static void addListener(AgvListener listener) {
		AgvConnectionUtils.addListener(listener);
	}
	

	
}
