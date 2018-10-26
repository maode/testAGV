package com.his.net.agv;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.his.net.agv.util.ByteUtils;
import com.his.net.agv.util.UnsignedUtil;

/**  
 * @Title: Test.java
 * @Package com.his.comm.testqd
 * @Description: 与业务无关，各种小测试在这里验证结果
 * @author ZhengMaoDe   
 * @date 2018年4月24日 下午2:01:30 
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//UnsignedUtil.
	}

	public static byte getXor(byte[] datas){  
		  
	    byte temp=datas[0];  
	          
	    for (int i = 1; i <datas.length; i++) {  
	        temp ^=datas[i];  
	    }  
	  
	    return temp;  
	} 
	
	public static void testwf() {
		File path =new File("logs");
		try {
			System.out.println("当前目录为："+path.getCanonicalPath());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        if(!path.exists()) {
        	path.mkdirs();
        }
        PrintWriter pw=null;
		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter(path+"/log.txt")));
			pw.println("测试一行111");
			pw.println("测试一行222");
			pw.println("测试一行333");
			pw.flush();
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("创建文件输出流失败");
		}
	}
	
}
