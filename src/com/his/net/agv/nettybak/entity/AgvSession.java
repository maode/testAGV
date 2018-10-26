package com.his.net.agv.nettybak.entity;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import io.netty.channel.Channel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**  
 * @Title: AgvSession.java
 * @Package com.his.net.agv.nettybak.entity
 * @Description: 上位机与agv的会话对象
 * <p>每个会话对象包含两条信道（一条Q600信道，一条PLC信道）
 * @author ZhengMaoDe   
 * @date 2018年7月10日 下午1:46:56 
 */
@Getter
@Setter
@NoArgsConstructor
public class AgvSession {

	/** @Fields q600Channel : TODO */
	private Channel q600Channel;
	/** @Fields plcChannle : TODO */
	private Channel plcChannel;
	/** @Fields waitingPLC : 是否等待plc反馈状态 true:等待，false:不等待 */
	private boolean waitingPLC=false;
	/** @Fields q600Cmds : q600指令队列 */
	private Queue<Q600Command> q600Cmds=new ConcurrentLinkedQueue<Q600Command>();
	/** @Fields plcCmds : plc指令队列 */
	private Queue<PlcCommand> plcCmds=new ConcurrentLinkedQueue<PlcCommand>();
	/** @Fields agvEntity : 当前agv实体 */
	private AgvEntity agvEntity;
	

	
	
	public AgvSession putQ600Command(Q600Command q600Cmd) {
		this.q600Cmds.offer(q600Cmd);
		return this;
	}
	public AgvSession putPlcCommand(PlcCommand plcCmd) {
		this.plcCmds.offer(plcCmd);
		return this;
	}
}
