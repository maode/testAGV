package com.his.net.agv.nettybak.entity;

import java.nio.ByteBuffer;

import com.his.net.agv.nettybak.util.CRC16;

import lombok.Getter;
import lombok.Setter;

/**  
 * @Title: PlcCmd.java
 * @Package com.hsmart.hjac.entity
 * @Description: 下发至plc的字节指令封装
 * @author ZhengMaoDe   
 * @date 2018年7月11日 下午2:29:42 
 */
@Getter
@Setter
public class PlcCmd {
	/** @Fields BASE_LEN : 固定部分的长度(不包括dataLength和data和校验和) */
	public static final int BASE_LEN=6;
	/** @Fields CHECK_LEN : 校验和固定长度 */
	public static final int CHECK_LEN=2;
	
	
	/** @Fields code : 站号【可作为agv编号使用】，目前未包含实际意义，暂定值为0x01 */
	private byte code=0x01;
	/** @Fields typeCode : 功能码 */
	private byte typeCode;
	/** @Fields start : 起始字节 */
	private short start;
	/** @Fields regQuantity : 寄存器数量 */
	private short regQuantity;
	/** @Fields dataLength : 指令字节数（data的字节数）[非固定-只有运动指令有] */
	private byte dataLength;
	/** @Fields data : 具体指令[非固定-只有运动指令有] */
	private byte[] data;
	/** @Fields checksum : 校验和 */
	private short checksum;
	
	
	
	/**返回当前指令的字节数组表现形式-不包含校验和
	 * @return
	 */
	public byte[] toNoCheckByteArray() {
		//固定长度加实际指令长度（不包括两位校验和）
		int length=BASE_LEN;
		if(dataLength>0) {
			length+=dataLength+1;//dataLength自身占用一个字节
		}
		if(length==BASE_LEN) {
			return ByteBuffer.allocate(length)
					.put(code)
					.put(typeCode)
					.putShort(start)
					.putShort(regQuantity)
					.array();
		}else {
			return ByteBuffer.allocate(length)
					.put(code)
					.put(typeCode)
					.putShort(start)
					.putShort(regQuantity)
					.put(dataLength)
					.put(data)
					.array();
		}
	}
	/**返回当前指令的字节数组表现形式-包含校验和
	 * @return
	 */
	public byte[] toByteArray() {
		byte[] bytes=this.toNoCheckByteArray();
		return ByteBuffer.allocate(bytes.length+CHECK_LEN).put(bytes).putShort(getChecksum()).array();
	}
	
	/**获取当前指令的校验和,当校验和未设置时,会根据当前指令内容进行生成
	 * @return
	 */
	public short getChecksum() {
		if(checksum==0) {
			this.checksum=(short)CRC16.calcCrc16(toNoCheckByteArray());
		}
		return this.checksum;
	}
}
