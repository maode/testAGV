package com.his.net.agv.nettybak.util;


/**  
 * @Title: Q600CmdFactory.java
 * @Package com.his.net.agv.nettybak.util
 * @Description: Q600原始指令工厂类
 * @author ZhengMaoDe   
 * @date 2018年7月4日 下午1:20:45 
 */
public class Q600CmdFactory {
	/** @Fields Q600_START_NAVIGATION : 开始导航指令 */
	public static final String Q600_START_NAVIGATION="STAR@0#\n";
	/** @Fields Q600_START_NAVIGATION_LOOP : 开始导航指令-循环模式 */
	public static final String Q600_START_NAVIGATION_LOOP="STAR@1#\n";
	/** @Fields Q600_STOP_NAVIGATION : 停止导航指令 */
	public static final String Q600_STOP_NAVIGATION="STOPNAV\n";
	/** @Fields Q600_GET_NODE : 问询当前到达哪个动作点指令 */
	public static final String Q600_GET_NODE="GETNODE\n";
	/** @Fields Q600_CHECK_OBS : 问询当前雷达前方的障碍物指令 */
	public static final String Q600_CHECK_OBS="CHEKOBS\n";
	/** @Fields Q600_SET_WAY_PREFIX : 设置路径编号指令前缀 */
	public static final String Q600_SET_WAY_PREFIX="SET@";
	/** @Fields Q600_SET_WAY_SUFFIX : 设置路径编号指令后缀 */
	public static final String Q600_SET_WAY_SUFFIX="#\n";
	/** @Fields Q600_GET_POSE : 问询当前坐标 */
	public static final String Q600_GET_POSE="GETPOSE\n";
	
	
	/** @Fields LINE_START : 路线开始-业务处理指令，用于监听触发判断，不发送至下位机 */
	public static final String LINE_START="LINE_START";
	/** @Fields LINE_END : 路线结束-业务处理指令，用于监听触发判断，不发送至下位机 */
	public static final String LINE_END="LINE_END";
	
	
}
