package com.his.net.agv.nettybak.util;


/**  
 * @Title: CmdConstants.java
 * @Package com.hsmart.hjac.util
 * @Description: 指令相关常量类
 * @author ZhengMaoDe   
 * @date 2018年8月26日 上午11:37:54 
 */
public interface CmdConstants {

	//===== 发送至PLC的
	/** @Fields TYPE10_LEN : 功能码10(运动控制)对应的有效字节长度 14 */
	public static final byte PLC_IN_10_LEN=0x0e;
	
	
	
	
	//===== PLC返回的
	/** @Fields PLC_OUT_03_LEN : plc反馈指令功能码03(问询状态)对应的有效字节长度 24 */
	public static final byte PLC_OUT_03_LEN=0x18;
}
