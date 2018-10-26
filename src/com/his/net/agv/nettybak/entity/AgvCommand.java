package com.his.net.agv.nettybak.entity;

import java.util.function.BiPredicate;

import com.his.net.agv.nettybak.service.AgvCallBack;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**  
 * @Title: AgvCommand.java
 * @Package com.his.net.agv.nettybak.entity
 * @Description: AGV[逻辑]指令封装<br/>
 * 对原生下位机指令的封装，设置了每条指令的执行条件，执行后需要触发的回调，执行后指令状态的切换
 * @author ZhengMaoDe   
 * @date 2018年6月21日 上午10:06:25 
 */
@Getter
@Setter
@NoArgsConstructor
public class AgvCommand<C,T> {

	/** @Fields cmds : 当前要执行的一组指令 */
	private C[] cmds;
	
	/** @Fields point : 当前指令执行条件，如果值为空，则立即执行，否则等待agv返回信息符合该条件时再执行 */
	private T condition;
	
	/** @Fields callBack : 配置当前指令成功执行后，要使用的业务回调 */
	private AgvCallBack callBack;
	
	/** @Fields afterSwitch : 当前指令执行完成后，是否切换到Q600/PLC */
	private boolean afterSwitch=false;
	
	/** @Fields conditionCheck : 当前指令的执行条件判断逻辑 */
	private BiPredicate<T, T> conditionCheck;
	
	/** @Fields serviceCmds : 业务指令，不会发送至下位机，与下位机无关的用于业务判断的指令 */
	private String[] serviceCmds;

	
	
	
	public void setServiceCmds(String... serviceCmds) {
		this.serviceCmds = serviceCmds;
	}
	@SuppressWarnings("unchecked")
	public void setCmds(C... cmds) {
		this.cmds = cmds;
	}
	/**是否包含指令执行条件
	 * @return
	 */
	public boolean hasCondition() {
		return condition!=null;
	}
	/**是否包含下位机指令
	 * @return
	 */
	public boolean hasCmds() {
		return (cmds!=null&&cmds.length>0);
	}
	/**是否包含业务指令
	 * @return
	 */
	public boolean hasServiceCmds() {
		return (serviceCmds!=null&&serviceCmds.length>0);
	}
	/**是否包含回调
	 * @return
	 */
	public boolean hasCallBack() {
		return callBack!=null;
	}
	/**agv返回值是否与当前条件匹配，
	 * 没有条件或条件匹配返回true，否则返回false
	 * @param agvResult
	 * @return
	 */
	public boolean conditionMatch(T agvResult) {
		return condition==null||conditionCheck.test(condition, agvResult);
	}
}
