package com.his.net.agv.nettybak.entity;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;

/**  
 * @Title: AgvStatus.java
 * @Package com.his.net.agv.nettybak.entity
 * @Description: agv实体类(面向下位机)
 * @author ZhengMaoDe   
 * @date 2018年7月11日 上午9:52:28 
 */
@Getter
@Setter
public class AgvEntity implements AgvEvent{
	
	/** @Fields q600Ip : Q600模块IP */
	private String q600Ip;
	/** @Fields q600Port : Q600模块端口号 */
	private int q600Port;
	/** @Fields plcIp : plc模块IP */
	private String plcIp;
	/** @Fields plcPort : plc模块端口号 */
	private int plcPort;
	/** @Fields agvCode : 编号 */
	private String agvCode;
	/** @Fields currentWay : 当前执行路线 */
	private List<String> currentWay;
	/** @Fields currentX : 当前坐标X的值 */
	private String currentX;
	/** @Fields currentY : 当前坐标Y的值 */
	private String currentY;
	/** @Fields jackStatus : 顶升状态: 1-升起,2-降落,4-工作中 */
	private int jackStatus;
	/** @Fields plcResult : plc反馈的信息实体 */
	private PlcResult plcResult;
	
	
	
	@Override
	public String getPosition() {
		StringBuilder sb=new StringBuilder(getCurrentX()).append(",").append(getCurrentX());
		return sb.toString();
	}
	@Override
	public List<String> getLine() {
		return getCurrentWay();
	}
	
	/**
	 * 返回当前对象的一个副本
	 * @return
	 * @throws CloneNotSupportedException
	 * @see java.lang.Object#clone()
	 */
	@Override
	public AgvEntity clone() throws CloneNotSupportedException {
		return (AgvEntity)super.clone();
	}
	
	public String getAgvCode() {
		return StringUtils.isEmpty(agvCode) ? q600Ip+plcIp : agvCode;
	}

	


}
