package com.his.net.agv.nettybak.ui;

import com.his.net.agv.nettybak.netty.server.plc.PlcServer;

/**
 * @Title: PlcClientUIMain.java
 * @Package com.his.net.agv.nettybak.ui
 * @Description: PLC控制UI的入口
 * @author ZhengMaoDe
 * @date 2018年8月26日 下午3:54:04
 */
public class PlcClientUIMain {
	
	public static void main(String[] args) {
		PlcServer.main(null);
		//上面的代码模拟启动一个测试服务，AGV联调时注释掉
		new PlcClientUI();
	}
}
