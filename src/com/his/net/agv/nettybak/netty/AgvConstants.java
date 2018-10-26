package com.his.net.agv.nettybak.netty;


/**  
 * @Title: AgvConstants.java
 * @Package com.his.net.agv.nettybak.netty
 * @Description: agv常量类
 * @author ZhengMaoDe   
 * @date 2018年7月10日 下午3:27:10 
 */
public interface AgvConstants {
	/** @Fields Q600_DEFAULT_HOST : 默认q600的ip */
	//String Q600_DEFAULT_HOST = "192.168.1.101";
	String Q600_DEFAULT_HOST = "127.0.0.1";
	/** @Fields Q600_DEFAULT_PORT : 默认q600的端口 */
	int Q600_DEFAULT_PORT = 2111;
	/** @Fields Q600_DEFAULT_ERROR : 默认Q600的坐标误差（mm） */
	int Q600_DEFAULT_ERROR = 20;

	/** @Fields PLC_DEFAULT_HOST : 默认plc的ip */
	String PLC_DEFAULT_HOST = "127.0.0.1";
	//String PLC_DEFAULT_HOST = "192.168.1.21";
	/** @Fields PLC_DEFAULT_PORT : 默认plc的端口 */
	int PLC_DEFAULT_PORT = 4001;
	
	
	/** @Fields DEFAULT_REDIRECT_SECOND : 默认agv的断线重连时间 */
	int DEFAULT_REDIRECT_SECOND = 3;
	/** @Fields DEFAULT_HEARTBEAT_SECOND : 默认agv的心跳包间隔时间 */
	int DEFAULT_HEARTBEAT_SECOND = 1;
	
	/** @Fields DEFAULT_CONNECT_WAIT : agv采用同步连接时，plc和Q600的默认同步等待时间（秒） */
	int DEFAULT_CONNECT_WAIT=5;

}
