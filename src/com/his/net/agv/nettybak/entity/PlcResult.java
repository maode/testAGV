package com.his.net.agv.nettybak.entity;

import java.nio.ByteBuffer;

import com.his.net.agv.nettybak.util.CmdConstants;

import lombok.Getter;
import lombok.Setter;

/**  
 * @Title: PlcResult.java
 * @Package com.hsmart.hjac.entity
 * @Description: plc返回字节流解析封装
 * @author ZhengMaoDe   
 * @date 2018年7月11日 上午9:22:54 
 */
@Getter
@Setter
public class PlcResult {
	
	/** @Fields BASE_LEN : 头部固定长度(不包括data和校验和) */
	public static final int BASE_LEN=3;
	/** @Fields CHECK_LEN : 校验和固定长度 */
	public static final int CHECK_LEN=2;

	/** @Fields code : 站号（AGV唯一标识） */
	private byte code;
	/** @Fields typeCode : 功能码 */
	private byte typeCode;
	/** @Fields dataLength : 指令字节数（data的字节数） */
	private byte dataLength;
	/** @Fields data : 具体指令 */
	private byte[] data;
	/** @Fields checksum : 校验和 */
	private short checksum;
	
	/**返回当前指令的字节数组表现形式-不包含校验和
	 * @return
	 */
	public byte[] toNoCheckByteArray() {
		//固定长度3加实际指令长度
		int length=BASE_LEN;
		if(dataLength>0) {
			length+=dataLength;
			return ByteBuffer.allocate(length)
					.put(code)
					.put(typeCode)
					.put(dataLength)
					.put(data)
					.array();
		}else {
			return null;
		}
	}
	/**返回当前指令的字节数组表现形式-包含校验和
	 * @return
	 */
	public byte[] toByteArray() {
		byte[] bytes=this.toNoCheckByteArray();
		return ByteBuffer.allocate(bytes.length+CHECK_LEN).put(bytes).putShort(getChecksum()).array();
	}
	
	/**获取AGV状态值[顶升完成0x01,下降完成0x02,工作中0x04,下位机状态准备好0x08]
	 * @return
	 */
	public short getStatus() {
		if(data!=null&&data.length==CmdConstants.PLC_OUT_03_LEN) {
			return ByteBuffer.wrap(data, 0, 2).getShort();
		}
		return 0;
	}
	
	/**获取当前小车速度
	 * @return
	 */
	public short getSpeed() {
		if(data!=null&&data.length==CmdConstants.PLC_OUT_03_LEN) {
			return ByteBuffer.wrap(data, 2, 2).getShort();
		}
		return 0;
	}
	
	/**获取小车当前PGV坐标X值
	 * @return
	 */
	public short getPgvX() {
		if(data!=null&&data.length==CmdConstants.PLC_OUT_03_LEN) {
			return ByteBuffer.wrap(data, 4, 2).getShort();
		}
		return 0;
	}
	/**获取小车当前PGV坐标Y值
	 * @return
	 */
	public short getPgvY() {
		if(data!=null&&data.length==CmdConstants.PLC_OUT_03_LEN) {
			return ByteBuffer.wrap(data, 6, 2).getShort();
		}
		return 0;
	}
	/**获取小车当前PGV角度
	 * @return
	 */
	public short getPgvAngle() {
		if(data!=null&&data.length==CmdConstants.PLC_OUT_03_LEN) {
			return ByteBuffer.wrap(data, 8, 2).getShort();
		}
		return 0;
	}
	/**获取小车当前PGV的TAG
	 * @return
	 */
	public short getPgvTag() {
		if(data!=null&&data.length==CmdConstants.PLC_OUT_03_LEN) {
			return ByteBuffer.wrap(data, 10, 2).getShort();
		}
		return 0;
	}
	/**获取小车当前电流
	 * @return
	 */
	public short getElectricityC() {
		if(data!=null&&data.length==CmdConstants.PLC_OUT_03_LEN) {
			return ByteBuffer.wrap(data, 12, 2).getShort();
		}
		return 0;
	}
	/**获取小车当前电压
	 * @return
	 */
	public short getElectricityV() {
		if(data!=null&&data.length==CmdConstants.PLC_OUT_03_LEN) {
			return ByteBuffer.wrap(data, 14, 2).getShort();
		}
		return 0;
	}
	/**获取小车当前温度
	 * @return
	 */
	public short getTemperature() {
		if(data!=null&&data.length==CmdConstants.PLC_OUT_03_LEN) {
			return ByteBuffer.wrap(data, 16, 2).getShort();
		}
		return 0;
	}
	/**获取小车当前电池剩余电量
	 * @return
	 */
	public short getDumpEnergy() {
		if(data!=null&&data.length==CmdConstants.PLC_OUT_03_LEN) {
			return ByteBuffer.wrap(data, 18, 2).getShort();
		}
		return 0;
	}
}
