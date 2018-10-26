package com.his.net.agv.nettybak.netty;

import java.text.SimpleDateFormat;
import java.util.Date;

/**  
 * @Title: Utils.java
 * @Package tnetty
 * @Description: TODO
 * @author ZhengMaoDe   
 * @date 2018年5月22日 上午9:56:46 
 */
public class Utils {

	/**取格式化后的当前时间
	 * @return
	 */
	public static  String getNowTime() {
		SimpleDateFormat smd=new SimpleDateFormat("HH:mm:ss:SSS");
		return smd.format(new Date())+">>";
	}
}
