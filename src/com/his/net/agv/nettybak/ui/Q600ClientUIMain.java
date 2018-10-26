package com.his.net.agv.nettybak.ui;

import com.his.net.agv.nettybak.netty.client.q600.Q600Client;
import com.his.net.agv.nettybak.netty.server.q600.Q600Server;

/**
 * @Title: Q600ClientUIMain.java
 * @Package com.his.net.agv.nettybak.ui
 * @Description: Q600通信测试的入口（因为简单，没做UI，为了统一好找，先放这个包里）
 * @author ZhengMaoDe
 * @date 2018年8月26日 下午3:54:04
 */
public class Q600ClientUIMain {
	
	public static void main(String[] args) {
		Q600Server.main(null);
		//上面的代码模拟启动一个测试服务，AGV联调时注释掉
		Q600Client.main(null);
	}
}
