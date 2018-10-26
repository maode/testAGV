package com.his.net.agv.util;

import java.nio.ByteBuffer;

/**  
 * @Title: ByteUtils.java
 * @Package com.his.net.agv.client
 * @Description: 字节操作和转换工具类
 * @author ZhengMaoDe   
 * @date 2018年4月25日 下午12:45:21 
 */
public class ByteUtils {


    /** 
     * 将boolean转成byte[] 
     * @param val 
     * @return byte[]
     */  
    public static byte[] Boolean2ByteArray(boolean val) {
        int tmp = (val == false) ? 0 : 1;
        return ByteBuffer.allocate(4).putInt(tmp).array();
    }


    /** 
     * 将byte[]转成boolean
     * @param data
     * @return boolean
     */ 
    public static boolean ByteArray2Boolean(byte[] data) {
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
    public static byte[] Int2ByteArray(int val) {
        return ByteBuffer.allocate(4).putInt(val).array();
    }


    /** 
     * 将byte[]转成int
     * @param data
     * @return int
     */ 
    public static int ByteArray2Int(byte[] data) {
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
    public static byte[] Float2ByteArray(float val) {
        return ByteBuffer.allocate(4).putFloat(val).array();
    }


    /** 
     * 将byte[]转成float
     * @param data
     * @return float
     */ 
    public static float ByteArray2Float(byte[] data) {
        if (data == null || data.length < 4) {
            return -1234.0f;
        }
        return ByteBuffer.wrap(data).getFloat();
    }


    /** 
     * 将byte[]数组转成short[]数组
     * @param b
     * @return short[]
     */ 
    public static short[] byteArray2ShortArray(byte[] b) {
        int len = b.length / 2;
        int index = 0;
        short[] re = new short[len];
        byte[] buf = new byte[2];
        for (int i = 0; i < b.length;) {
            buf[0] = b[i];
            buf[1] = b[i + 1];
            short st = byte2Short(buf);
            re[index] = st;
            index++;
            i += 2;
        }
        return re;
    }


    /**
     * 将一个short[]数组反转为byte[]字节数组
     * @param b
     */
    public static byte[] shortArray2ByteArray(short[] b) {
        byte[] rebt = new byte[b.length * 2];
        int index = 0;
        for (int i = 0; i < b.length; i++) {
            short st = b[i];
            byte[] bt = shortToByte(st);
            rebt[index] = bt[0];
            rebt[index + 1] = bt[1];
            index += 2;
        }
        return rebt;
    }


    /**
     * short转换为byte[]
     * @param number
     * @return byte[]
     */
    public static byte[] shortToByte(short number) {
        int temp = number;
        byte[] b = new byte[2]; // 将最低位保存在最低位
        b[0] = (byte)(temp & 0xff);
        temp = temp >> 8; // 向右移8位
        b[1] = (byte)(temp & 0xff);
        return b;
    }

    /**
     * byte[]转换为short
     * @param b
     * @return short
     */
    public static short byte2Short(byte[] b) {
        short s = 0;
        short s0 = (short) (b[0] & 0xff);// 最低位
        short s1 = (short) (b[1] & 0xff);
        s1 <<= 8;
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
     * 将一个byte转为字符串表示的bit 
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
//		for (int i = 0; i < len; i++) {
//			res[i] = bytes[i];
//		}
		for (int i = 0; i < len; i++) {
			b = bytes[i];
			res[len - i - 1] = b;
		}
		return res;
	}
	
    /** 
     * @功能 字节的转换与短整型 
     * @param 两位的字节数组 
     * @return 短整型 
     */  
    public static short byteToShort(byte[] b) {  
        short s = 0;  
        short s0 = (short) (b[0] & 0xff);// 最低位  
        short s1 = (short) (b[1] & 0xff);  
        s1 <<= 8;  
        s = (short) (s0 | s1);  
        return s;  
    }  
  
    /** 
     * @方法功能 整型与字节数组的转换 
     * @param 整型 
     * @return 四位的字节数组 
     */  
    public static byte[] intToByte(int i) {  
        byte[] bt = new byte[4];  
        bt[0] = (byte) (0xff & i);  
        bt[1] = (byte) ((0xff00 & i) >> 8);  
        bt[2] = (byte) ((0xff0000 & i) >> 16);  
        bt[3] = (byte) ((0xff000000 & i) >> 24);  
        return bt;  
    }  
  
    /** 
     * @方法功能 字节数组和整型的转换 
     * @param 字节数组 
     * @return 整型 
     */  
    public static int bytesToInt(byte[] bytes) {  
        int num = bytes[0] & 0xFF;  
        num |= ((bytes[1] << 8) & 0xFF00);  
        num |= ((bytes[2] << 16) & 0xFF0000);  
        num |= ((bytes[3] << 24) & 0xFF000000);  
        return num;  
    }  
  
    /** 
     * @方法功能 字节数组和长整型的转换 
     * @param 字节数组 
     * @return 长整型 
     */  
    public static byte[] longToByte(long number) {  
        long temp = number;  
        byte[] b = new byte[8];  
        for (int i = 0; i < b.length; i++) {  
            b[i] = new Long(temp & 0xff).byteValue();  
            // 将最低位保存在最低位  
            temp = temp >> 8;  
            // 向右移8位  
        }  
        return b;  
    }  
  
    /** 
     * @方法功能 字节数组和长整型的转换 
     * @param 字节数组 
     * @return 长整型 
     */  
    public static long byteToLong(byte[] b) {  
        long s = 0;  
        long s0 = b[0] & 0xff;// 最低位  
        long s1 = b[1] & 0xff;  
        long s2 = b[2] & 0xff;  
        long s3 = b[3] & 0xff;  
        long s4 = b[4] & 0xff;// 最低位  
        long s5 = b[5] & 0xff;  
        long s6 = b[6] & 0xff;  
        long s7 = b[7] & 0xff; // s0不变  
        s1 <<= 8;  
        s2 <<= 16;  
        s3 <<= 24;  
        s4 <<= 8 * 4;  
        s5 <<= 8 * 5;  
        s6 <<= 8 * 6;  
        s7 <<= 8 * 7;  
        s = s0 | s1 | s2 | s3 | s4 | s5 | s6 | s7;  
        return s;  
    }  
}
