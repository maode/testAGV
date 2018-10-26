package com.his.net.agv.nettybak.util;

import java.nio.ByteBuffer;

import com.his.net.agv.nettybak.entity.PlcCmd;

/**  
 * @Title: PlcCmdFactory.java
 * @Package com.hsmart.hjac.util
 * @Description: plc原始指令工厂类
 * @author ZhengMaoDe   
 * @date 2018年7月20日 上午10:55:33 
 */
public class PlcCmdFactory {
	

	/**获取一个 “升顶” 指令
	 * @return
	 */
	public static PlcCmd getUp(int tag) {
		PlcCmd cmd=getBase10();
		byte[] data=ByteBuffer.allocate(CmdConstants.PLC_IN_10_LEN)
		.putShort((short)0x01)//“升顶”指令对应的值为"0x01",用short类型自动填补一个字节的运动字节为0
		.putLong(0)//填补8个字节的坐标为0
		.putInt(tag).array()
		;
		cmd.setData(data);
		return cmd;
	}
	/**获取一个 “落顶” 指令
	 * @return
	 */
	public static PlcCmd getDown(int tag) {
		PlcCmd cmd=getBase10();
		byte[] data=ByteBuffer.allocate(CmdConstants.PLC_IN_10_LEN)
		.putShort((short)0x02)//“落顶”指令对应的值为"0x02",用short类型自动填补一个字节的运动字节为0
		.putLong(0)//填补8个字节的坐标为0
		.putInt(tag).array()
		;
		cmd.setData(data);
		return cmd;
	}
	/**获取一个停止 “顶升功能” 指令
	 * @return
	 */
	public static PlcCmd getStopJack(int tag) {
		PlcCmd cmd=getBase10();
		//“停止顶升”指令对应的值为0,字节数组创建后,所有字节默认均为0
		byte[] data=new byte[CmdConstants.PLC_IN_10_LEN];
		cmd.setData(data);
		return cmd;
	}
	
	/**获取一个 “问询” 指令 (问询:坐标,电池,顶升 及其他一些状态)
	 * @return
	 */
	public static PlcCmd getInquiryStatus() {
		return getBase03();
	}
	
	/**获取一个"向前"行驶 指令
	 * @return
	 */
	public static PlcCmd getForward() {
		PlcCmd cmd=getBase10();
		byte[] data=ByteBuffer.allocate(CmdConstants.PLC_IN_10_LEN)
		.putShort((short)0x0100)//“向前”指令对应的值为"0x01",用short类型,后两个0用来填补一个字节的顶升指令为0
		.putLong(0)//填补8个字节的坐标,值为0
		.putInt(0)//填补4个字节的tag,值为0
		.array()
		;
		cmd.setData(data);
		return cmd;
	}
	/**获取一个"向后"行驶 指令
	 * @return
	 */
	public static PlcCmd getBackWards() {
		PlcCmd cmd=getBase10();
		byte[] data=ByteBuffer.allocate(CmdConstants.PLC_IN_10_LEN)
				.putShort((short)0x0200)//“向后”指令对应的值为"0x02",用short类型,后两个0用来填补一个字节的顶升指令为0
				.putLong(0)//填补8个字节的坐标,值为0
				.putInt(0)//填补4个字节的tag,值为0
				.array()
				;
		cmd.setData(data);
		return cmd;
	}
	/**获取一个"向左"行驶 指令
	 * @return
	 */
	public static PlcCmd getToLeft() {
		PlcCmd cmd=getBase10();
		byte[] data=ByteBuffer.allocate(CmdConstants.PLC_IN_10_LEN)
				.putShort((short)0x0300)//“向左”指令对应的值为"0x03",用short类型,后两个0用来填补一个字节的顶升指令为0
				.putLong(0)//填补8个字节的坐标,值为0
				.putInt(0)//填补4个字节的tag,值为0
				.array()
				;
		cmd.setData(data);
		return cmd;
	}
	/**获取一个"向右"行驶 指令
	 * @return
	 */
	public static PlcCmd getToRight() {
		PlcCmd cmd=getBase10();
		byte[] data=ByteBuffer.allocate(CmdConstants.PLC_IN_10_LEN)
				.putShort((short)0x0400)//“向右”指令对应的值为"0x04",用short类型,后两个0用来填补一个字节的顶升指令为0
				.putLong(0)//填补8个字节的坐标,值为0
				.putInt(0)//填补4个字节的tag,值为0
				.array()
				;
		cmd.setData(data);
		return cmd;
	}
	/**获取一个"左旋"  指令
	 * @return
	 */
	public static PlcCmd getLevorotation() {
		PlcCmd cmd=getBase10();
		byte[] data=ByteBuffer.allocate(CmdConstants.PLC_IN_10_LEN)
				.putShort((short)0x0500)//“左旋”指令对应的值为"0x05",用short类型,后两个0用来填补一个字节的顶升指令为0
				.putLong(0)//填补8个字节的坐标,值为0
				.putInt(0)//填补4个字节的tag,值为0
				.array()
				;
		cmd.setData(data);
		return cmd;
	}
	/**获取一个"右旋"  指令
	 * @return
	 */
	public static PlcCmd getDextrorotation() {
		PlcCmd cmd=getBase10();
		byte[] data=ByteBuffer.allocate(CmdConstants.PLC_IN_10_LEN)
				.putShort((short)0x0600)//“右旋”指令对应的值为"0x06",用short类型,后两个0用来填补一个字节的顶升指令为0
				.putLong(0)//填补8个字节的坐标,值为0
				.putInt(0)//填补4个字节的tag,值为0
				.array()
				;
		cmd.setData(data);
		return cmd;
	}
	/**获取一个"停止"行驶 指令
	 * @return
	 */
	public static PlcCmd getStopRun() {
		PlcCmd cmd=getBase10();
		byte[] data=ByteBuffer.allocate(CmdConstants.PLC_IN_10_LEN)
				.putShort((short)0x0000)//“停止”指令对应的值为"0x00",用short类型,后两个0用来填补一个字节的顶升指令为0
				.putLong(0)//填补8个字节的坐标,值为0
				.putInt(0)//填补4个字节的tag,值为0
				.array()
				;
		cmd.setData(data);
		return cmd;
	}

	
	
	/**功能码10类型的指令基础封装（控制类型-控制运动及控制顶升）
	 * @return
	 */
	public static PlcCmd getBase10() {
		PlcCmd cmd=new PlcCmd();
		cmd.setTypeCode((byte)0x10);//运动和顶升相关功能码为0x10
		cmd.setStart((short)0x00);//[寄存器]起始字节，固定为0x00
		cmd.setRegQuantity((short)0x07);//寄存器数量，固定为0x0E(14个有效字节占7个寄存器)
		cmd.setDataLength((byte)0x0E);//实际指令长度，固定为14个字节
		return cmd;
	}	
	
	/**功能码03类型的指令基础封装（问询类型-问询坐标,电池以及顶升状态）
	 * @return
	 */
	private static PlcCmd getBase03() {
		PlcCmd cmd=new PlcCmd();
		cmd.setTypeCode((byte)0x03);//问询坐标及顶升状态相关功能码为0x03
		cmd.setStart((short)0x20);//[寄存器]起始字节，固定为0x20
		cmd.setRegQuantity((short)0x0C);//寄存器数量，固定为0x0C
		return cmd;
	}

}
