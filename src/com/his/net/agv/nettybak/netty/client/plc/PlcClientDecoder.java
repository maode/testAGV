package com.his.net.agv.nettybak.netty.client.plc;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.his.net.agv.nettybak.entity.PlcResult;
import com.his.net.agv.nettybak.util.CRC16;
import com.his.net.agv.nettybak.util.HexUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.ByteProcessor.IndexOfProcessor;



/**  
 * @Package com.his.net.agv.nettybak.netty.client.plc
 * @Description: plc通信解码器-解码Plc反馈的信息
 * @author ZhengMaoDe   
 * @date 2018年7月24日 下午2:33:05 
 */
public class PlcClientDecoder extends ByteToMessageDecoder {

	private static final Logger logger=LoggerFactory.getLogger(PlcClientDecoder.class);
	
	/**
	 * 解码PLC返回的数据.
	 * <p>该方法执行结束,如果没有新的可读数据,即ridx==widx,则会释放该字节缓冲区资源
	 * @param ctx
	 * @param in
	 * @param out
	 * @throws Exception
	 * @see io.netty.handler.codec.ByteToMessageDecoder#decode(io.netty.channel.ChannelHandlerContext, io.netty.buffer.ByteBuf, java.util.List)
	 */
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
	
		
		//-----TEST START
		in.markReaderIndex();
        int len=in.readableBytes();
        byte[] read=new byte[len];
        in.readBytes(read);
        logger.trace("decode解码PLC反馈的数据,接受到的数据字节长度为: {} ,值为：{}",len,HexUtils.toHexString(read));    
        in.resetReaderIndex();
		//-----TEST END
        
        
        
		//按照 站号和功能码 组合做头部分帧
		
		int numIdx=in.forEachByte(new IndexOfProcessor((byte) 0x01));//在当前可读字节中寻找"站号"第一次出现的位置[数据过滤,认为站号之前的未读数据为垃圾数据]
		if(numIdx==-1) {
			return;//没有找到则return继续等待新内容写入
		}
		in.readerIndex(numIdx);//将当前读索引设置为站号所在位置
		if (in.readableBytes() < PlcResult.BASE_LEN) {
			 return;//以当前站号为起始,可读数据小于头部固定长度(站号+功能码+字节总数),则return继续等待写入
		 }
		byte typeCode=in.getByte(in.readerIndex()+1);//以不推进readerIndex的方式获取到当前指令对应的功能码.(功能码固定位于指令开头的索引+1处)
		//如果在对应位置读取不到正确的功能码,则认为当前位置不是正确的头部位置,以推进readIndex的方式,丢弃该位置和该位置以前的数据
		if(typeCode!=0x03) {
			in.readerIndex(in.readerIndex()+1+1);//in.readerIndex()+1只包括该位置以前的数据,所以要再加一次1,把该位置的也丢弃
			return;
		}
		
		byte dataLength=in.getByte(in.readerIndex()+2);//以不推进readerIndex的方式获取到当前指令对应的字节总数.(字节总数固定位于指令开头的索引+2处)
		if(in.readableBytes()<PlcResult.BASE_LEN+dataLength+PlcResult.CHECK_LEN) {
			return;//以当前站号为起始,可读数据不小于头部固定长度(站号+功能码+字节总数),但可读数据小于当前指令的字节总数,则return继续等待写入
		}
		
		//所有条件都符合,尝试读取封装数据
		 PlcResult result= packPlcResult(in);
		 //如果result不为空,则调用out.add(result); 数据会进入信道的下一个处理器,
		 if(result!=null) {
			 out.add(result);
		 }

	}
	
	/**
	 * 将plc反馈的字节数据封装为对应的实体对象
	 * <p>如果封装完成后的对象,校验和与计算出的校验和不匹配,则认为数据在传输中出现错误,则返回null
	 * @param in
	 * @return
	 */
	public PlcResult packPlcResult(ByteBuf in) {
		PlcResult plcResult=new PlcResult();
		plcResult.setCode(in.readByte());
		plcResult.setTypeCode(in.readByte());
		plcResult.setDataLength(in.readByte());
		byte[] datas=new byte[plcResult.getDataLength()];
		in.readBytes(datas);
		plcResult.setData(datas);
		plcResult.setChecksum(in.readShort());
		short ck=(short)CRC16.calcCrc16(plcResult.toNoCheckByteArray());
		if(ck!=0&&ck==plcResult.getChecksum()) {
			return plcResult;
		}else {
			return null;
		}
	}

}
