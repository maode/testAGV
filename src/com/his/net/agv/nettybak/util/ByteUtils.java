package com.his.net.agv.nettybak.util;

import java.nio.ByteBuffer;

/**  
 * @Title: ByteUtils.java
 * @Package com.his.net.agv.client
 * @Description: 字节操作和转换工具类(参数和返回值中字节数组均为大端序)
 * @author ZhengMaoDe   
 * @date 2018年4月25日 下午12:45:21 
 */
public class ByteUtils {


    /** 
     * 将boolean转成byte[] 
     * @param val 
     * @return byte[]
     */  
    public static byte[] boolean2ByteArray(boolean val) {
        int tmp = (val == false) ? 0 : 1;
        return ByteBuffer.allocate(4).putInt(tmp).array();
    }


    /** 
     * 将byte[]转成boolean
     * @param data
     * @return boolean
     */ 
    public static boolean byteArray2Boolean(byte[] data) {
        if (data == null || data.length < 4) {
            return false;
        }
        int tmp = ByteBuffer.wrap(data, 0, 4).getInt();
        return (tmp == 0) ? false : true;
    }


    /** 
     * 将int转成byte[] 
     * @param val 
     * @return byte[]
     */  
    public static byte[] int2ByteArray(int val) {
        return ByteBuffer.allocate(4).putInt(val).array();
    }


    /** 
     * 将byte[]转成int
     * @param data
     * @return int
     */ 
    public static int byteArray2Int(byte[] data) {
        if (data == null || data.length < 4) {
            return 0xDEADBEEF;
        }
        return ByteBuffer.wrap(data, 0, 4).getInt();
    }


    /** 
     * 将float转成byte[] 
     * @param val 
     * @return byte[]
     */  
    public static byte[] float2ByteArray(float val) {
        return ByteBuffer.allocate(4).putFloat(val).array();
    }


    /** 
     * 将byte[]转成float
     * @param data
     * @return float
     */ 
    public static float byteArray2Float(byte[] data) {
        if (data == null || data.length < 4) {
            return -1234.0f;
        }
        return ByteBuffer.wrap(data).getFloat();
    }






    /**
     * short转换为byte[]
     * @param number
     * @return byte[]
     */
    public static byte[] short2ByteArray(short number) {
        byte[] b = new byte[2]; 
        // 向右移8位，保留高有效位（并保存到低地址。即：b[0]）
        b[0] = (byte)((number >> 8) & 0xff);
        // 强转，高位被丢弃，保留低有效位（并保存到高地址。即：b[1])
        b[1] = (byte)(number & 0xff);
        return b;
    }

    /**
     * byte[]转换为short
     * @param b
     * @return short
     */
    public static short byteArray2Short(byte[] b) {
        short s = 0;
        short s0 = (short) (b[0] & 0xff);// 高位
        short s1 = (short) (b[1] & 0xff);
        s0 <<= 8;
        s = (short) (s0 | s1);
        return s;
    }
    
    /** 
     * 将一个byte转换为一个长度为8的byte数组，数组每个值代表一位bit的值 
     */  
    public static byte[] byte2BitArray(byte b) {  
        byte[] array = new byte[8];  
        for (int i = 7; i >= 0; i--) {  
            array[i] = (byte)(b & 1);  
            b = (byte) (b >> 1);  
        }  
        return array;  
    }  
    /** 
     * 将一个byte转换为一个长度为8的字符串表示的bit 
     */  
    public static String byte2BitStr(byte b) {  
        return ""  
                + (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1)  
                + (byte) ((b >> 5) & 0x1) + (byte) ((b >> 4) & 0x1)  
                + (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1)  
                + (byte) ((b >> 1) & 0x1) + (byte) ((b >> 0) & 0x1);  
    }
    
    /**
     * 将一个byte数组转为字符串表示的bit
     * @param bytes
     * @return
     */
    public static String byteArray2BitStr(byte[] bytes) {
    	StringBuffer sb=new StringBuffer(bytes.length*8);
    	for(byte b:bytes) {
    		sb.append(byte2BitStr(b));
    	}
    	return sb.toString(); 
    }
    
	/**
	 * 字节序转换方法
	 * @param bytes
	 * 			要转换的字节数组
	 * @return	
	 * 			转换完成后的字节数组<br/>
	 * 			如果bytes为大端序则返回转换后的小端序字节数组，反之亦然。
	 */
	public static byte[] ReversalEndian(byte bytes[]) {
		byte b;
		int len = bytes.length;
		byte res[] = new byte[len];
		for (int i = 0; i < len; i++) {
			b = bytes[i];
			res[len - i - 1] = b;
		}
		return res;
	}
  
    /** 
     * @方法功能 字节数组和长整型的转换 
     * @param 字节数组 
     * @return 长整型 
     */  
    public static byte[] long2ByteArray(long val) {
        return ByteBuffer.allocate(8).putLong(val).array();
    }  
  
    /**将byte[]转成long
     * @param b
     * @return
     */
    public static long byteArray2Long(byte[] b) {
        long s = 0;  
        long s0 = b[0] & 0xff; 
        long s1 = b[1] & 0xff;  
        long s2 = b[2] & 0xff;  
        long s3 = b[3] & 0xff;  
        long s4 = b[4] & 0xff; 
        long s5 = b[5] & 0xff;  
        long s6 = b[6] & 0xff;  
        long s7 = b[7] & 0xff;
        s6 <<= 8;  
        s5 <<= 16;  
        s4 <<= 24;  
        s3 <<= 8 * 4;  
        s2 <<= 8 * 5;  
        s1 <<= 8 * 6;  
        s0 <<= 8 * 7;  
        s = s0 | s1 | s2 | s3 | s4 | s5 | s6 | s7;  
        return s;  
    }  
}
