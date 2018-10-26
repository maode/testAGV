package com.his.net.agv.nettybak.netty.client.plc;

import com.his.net.agv.nettybak.entity.PlcCmd;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;



/**  
 * @Package com.his.net.agv.nettybak.netty.client.plc
 * @Description: plc通信编码器-编码发送给PLC的指令
 * @author ZhengMaoDe   
 * @date 2018年7月24日 下午2:33:39 
 */
public class PlcClientEncoder extends MessageToByteEncoder<PlcCmd>  {

	@Override
	protected void encode(ChannelHandlerContext ctx, PlcCmd cmd, ByteBuf out) throws Exception {
		byte[] bytes=cmd.toByteArray();
		out.writeBytes(bytes);
	}

}
