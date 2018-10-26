package com.his.net.agv;

import com.his.net.agv.client.QdMain;
import com.his.net.agv.clientSh.ShMain;
import com.his.net.agv.nettybak.ui.PlcClientUI;
import com.his.net.agv.nettybak.ui.PlcClientUIMain;
import com.his.net.agv.nettybak.ui.Q600ClientUIMain;

/**  
 * @Title: Main.java
 * @Package com.his.net.agv
 * @Description: 测试入口
 * @author ZhengMaoDe   
 * @date 2018年7月2日 上午10:55:31 
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//PLC通信测试（UI版）
		PlcClientUIMain.main(null);
		//Q600通信测试(无UI，命令行交互)
		//Q600ClientUIMain.main(null);
		//青岛AGV通信测试（BIO版，已弃用）
		//QdMain.main(null);
		//上海AGV通信测试（BIO版，已弃用）
		//ShMain.main(null);
	}

}
