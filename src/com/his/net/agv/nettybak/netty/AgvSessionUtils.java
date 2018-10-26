package com.his.net.agv.nettybak.netty;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.his.net.agv.nettybak.entity.AgvEntity;
import com.his.net.agv.nettybak.entity.AgvSession;

/**  
 * @Package com.his.net.agv.nettybak.netty
 * @Description: agvSession操作工具类
 * @author ZhengMaoDe   
 * @date 2018年8月17日 上午9:47:47 
 */
public class AgvSessionUtils {


	
	/** @Fields agvSessions : 所有agv会话,sessionKey目前为agv的code[“Q600的IP”加“逗号”加“PLC的IP”] */
	private static final Map<String,AgvSession> agvSessions=new ConcurrentHashMap<String,AgvSession>();
	
	/** @Fields agvSessionQ600Key : 所有q600的ip对应的sessionKey */
	private static final Map<String,String> agvSessionQ600Key=new ConcurrentHashMap<String,String>();
	/** @Fields agvSessionPLCKey : 所有plc的ip对应的sessionKey */
	private static final Map<String,String> agvSessionPLCKey=new ConcurrentHashMap<String,String>();
	
	/**初始化当前agv的session
	 * @param agv
	 */
	public static void initAgvSession(AgvEntity agv) {
		AgvSession session = new AgvSession();
		session.setAgvEntity(agv);
		agvSessions.put(agv.getAgvCode(), session);
		agvSessionPLCKey.put(agv.getPlcIp(), agv.getAgvCode());
		agvSessionQ600Key.put(agv.getQ600Ip(), agv.getAgvCode());
	}
		
	/**根据q600的ip获取当前agvSession
	 * @param ip
	 * @return
	 */
	public static AgvSession getAgvSessionByQ600IP(String ip) {
		String sessionKey=agvSessionQ600Key.get(ip);
		return sessionKey==null ? null : agvSessions.get(sessionKey);
	}
	/**根据plc的ip获取当前agvSession
	 * @param ip
	 * @return
	 */
	public static AgvSession getAgvSessionByPlcIP(String ip) {
		String sessionKey=agvSessionPLCKey.get(ip);
		return sessionKey==null ? null : agvSessions.get(sessionKey);
	}
	/**根据sessionKey获取当前agvSession
	 * @param sessionKey
	 * @return
	 */
	public static AgvSession getAgvSessionByKey(String sessionKey) {
		return agvSessions.get(sessionKey);
	}
	/**获取当前所有agvSession
	 * @return
	 */
	public static Map<String,AgvSession> getAllAgvSessions() {
		return agvSessions;
	}
	

	
}
