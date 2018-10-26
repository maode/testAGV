package com.his.net.agv.nettybak.util;

import com.his.net.agv.nettybak.entity.PlcResult;

/**  
 * @Title: PlcConditionUtils.java
 * @Package com.hsmart.hjac.util
 * @Description: plc指令条件工具类
 * <p>顶升状态:[上升完成0x01,下降完成0x02,工作中0x04,下位机状态准备好0x08]
 * @author ZhengMaoDe   
 * @date 2018年7月20日 下午3:26:42 
 */
public class PlcConditionUtils {

	/**返回一条“升顶完成” 指令
	 * @return
	 */
	public static PlcResult getUpDoneCondition() {
		PlcResult condition=new PlcResult();
		condition.setTypeCode((byte)0x03);//反馈信息中-位置和顶升状态相关的功能码为0x03
		byte[] data=new byte[CmdConstants.PLC_OUT_03_LEN];
		//顶升状态相关的指令位于data的寄存器1,占用2字节,即一个short,而升顶完成状态值为0x01,所以只操作首部字节即可
		data[0]=0x01;
		condition.setData(data);
		return condition;
	}
	/**返回一条“落顶完成” 指令
	 * @return
	 */
	public static PlcResult getDownDoneCondition() {
		PlcResult condition=new PlcResult();
		condition.setTypeCode((byte)0x03);//反馈信息中-位置和顶升状态相关的功能码为0x03
		byte[] data=new byte[CmdConstants.PLC_OUT_03_LEN];
		//顶升状态相关的指令位于data的寄存器1,占用2字节,即一个short,而落顶完成状态值为0x02,所以只操作首部字节即可
		data[0]=0x02;
		condition.setData(data);
		return condition;
	}
	
	/**判断plc返回的指令中顶升状态是否和当前封装的指令中的顶升状态是否相同
	 * @param jackingCondition
	 * @param result
	 * @return
	 */
	public static boolean plcJackingConditionEquals(PlcResult jackingCondition,PlcResult result) {
		return jackingCondition.getTypeCode()==result.getTypeCode()
				&&jackingCondition.getStatus()==result.getStatus();
	}	
	
	
}
