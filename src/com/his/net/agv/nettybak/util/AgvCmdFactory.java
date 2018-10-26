package com.his.net.agv.nettybak.util;

import java.util.function.BiPredicate;

import com.his.net.agv.nettybak.entity.PlcCmd;
import com.his.net.agv.nettybak.entity.PlcCommand;
import com.his.net.agv.nettybak.entity.PlcResult;
import com.his.net.agv.nettybak.entity.Q600Command;
import com.his.net.agv.nettybak.service.AgvCallBack;

/**  
 * @Title: AgvCmdFactory.java
 * @Package com.his.net.agv.nettybak.util
 * @Description: agv逻辑指令组-工厂类
 * @author ZhengMaoDe   
 * @date 2018年7月3日 下午2:44:45 
 */
public class AgvCmdFactory {


	
	
	/**返回一条没有限制条件，立即执行的Q600指令
	 * @param cmds 要下的发指令
	 * @return
	 */
	public static Q600Command getQ600CommandAtOnce(String... cmds) {
		return getQ600Command(null, null, null,cmds);
	}
	/**返回一条没有限制条件，立即执行的Q600业务指令
	 * @param serviceCmds 要下的发指令
	 * @return
	 */
	public static Q600Command getQ600ServiceCommandAtOnce(String... serviceCmds) {
		Q600Command cmd=new Q600Command();
		cmd.setServiceCmds(serviceCmds);
		return cmd;
	}
	/**返回一条没有限制条件，立即执行的plc指令
	 * @param cmds 要下的发指令
	 * @return
	 */
	public static PlcCommand getPlcCommandAtOnce(PlcCmd... cmds) {
		return getPlcCommand(null, null, null,cmds);
	}
	

	
	
	/**返回一条切换到plc的q600指令
	 * @param callback 要执行的回调
	 * @param condition 执行当前指令以及执行当前回调的条件(如果条件为空则立即执行)
	 * @param conditionCheck 条件判断表达式
	 * @return
	 */
	public static Q600Command switchToPlcAndCall(AgvCallBack callback,String condition,BiPredicate<String, String> conditionCheck) {
		return getQ600Command(callback, condition, conditionCheck,true,null);
	}
	/**返回一条切换到q600的plc指令
	 * @param callback 要执行的回调
	 * @param condition 下发当前指令以及执行当前回调的条件(如果条件为空则立即执行)
	 * @param conditionCheck 条件判断表达式
	 * @return
	 */
	public static PlcCommand switchToQ600AndCall(AgvCallBack callback,PlcResult condition,BiPredicate<PlcResult, PlcResult> conditionCheck) {
		return getPlcCommand(callback, condition, conditionCheck,true,null);
	}
	/**返回一条切换到plc的q600指令
	 * @param condition 下发当前指令以及执行当前回调的条件(如果条件为空则立即执行)
	 * @param conditionCheck 条件判断表达式
	 * @return
	 */
	public static Q600Command switchToPlc(String condition,BiPredicate<String, String> conditionCheck) {
		return switchToPlcAndCall(null, condition, conditionCheck);
	}
	/**返回一条切换到q600的plc指令
	 * @param condition 下发当前指令以及执行当前回调的条件(如果条件为空则立即执行)
	 * @param conditionCheck 条件判断表达式
	 * @return
	 */
	public static PlcCommand switchToQ600(PlcResult condition,BiPredicate<PlcResult, PlcResult> conditionCheck) {
		return switchToQ600AndCall(null, condition, conditionCheck);
	}
	
	
	/**将指令条件和内容封装为q600指令对象并返回-不设置切换
	 * @param callback 要执行的回调
	 * @param condition 下发当前指令以及执行当前回调的条件(如果条件为空则立即执行)
	 * @param conditionCheck 条件判断表达式
	 * @param cmds 要下的发指令
	 * @return
	 */
	public static Q600Command getQ600Command(AgvCallBack callback,String condition,BiPredicate<String, String> conditionCheck,String... cmds ) {
		return getQ600Command( callback, condition, conditionCheck,false,cmds);
	}
	/**将指令条件和内容封装为plc指令对象并返回-不设置切换
	 * @param callback 要执行的回调
	 * @param condition 下发当前指令以及执行当前回调的条件(如果条件为空则立即执行)
	 * @param conditionCheck 条件判断表达式
	 * @param cmds 要下的发指令
	 * @return
	 */
	public static PlcCommand getPlcCommand(AgvCallBack callback,PlcResult condition,BiPredicate<PlcResult, PlcResult> conditionCheck,PlcCmd... cmds) {
		return getPlcCommand(callback, condition, conditionCheck,false,cmds);
	}

	
	/**将指令条件和内容封装为q600指令对象并返回-可设置切换
	 * @param callback 要执行的回调
	 * @param condition 下发当前指令以及执行当前回调的条件(如果条件为空则立即执行)
	 * @param conditionCheck 条件判断表达式
	 * @param afterSwitch 是否切换到plc
	 * @param cmds 要下的发指令
	 * @return
	 */
	public static Q600Command getQ600Command(AgvCallBack callback,String condition,BiPredicate<String, String> conditionCheck,boolean afterSwitch,String[] cmds) {
		Q600Command cmd=new Q600Command();
		cmd.setCondition(condition);
		cmd.setConditionCheck(conditionCheck);
		cmd.setCallBack(callback);
		cmd.setAfterSwitch(afterSwitch);
		cmd.setCmds(cmds);
		return cmd;
	}	
	/**将指令条件和内容封装为plc指令对象并返回-可设置切换
	 * @param callback 要执行的回调
	 * @param condition 下发当前指令以及执行当前回调的条件(如果条件为空则立即执行)
	 * @param conditionCheck 条件判断表达式
	 * @param afterSwitch 是否切换到q600
	 * @param cmds 要下的发指令
	 * @return
	 */
	public static PlcCommand getPlcCommand(AgvCallBack callback,PlcResult condition,BiPredicate<PlcResult, PlcResult> conditionCheck,boolean afterSwitch,PlcCmd[] cmds) {
		PlcCommand cmd=new PlcCommand();
		cmd.setCondition(condition);
		cmd.setConditionCheck(conditionCheck);
		cmd.setCallBack(callback);
		cmd.setAfterSwitch(afterSwitch);
		cmd.setCmds(cmds);
		return cmd;
	}
}
