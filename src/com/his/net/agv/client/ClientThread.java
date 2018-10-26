package com.his.net.agv.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.his.net.agv.util.HexUtils;

public class ClientThread extends Thread {
    ClientUI ui;
    Socket client;
    BufferedInputStream reader;
    BufferedOutputStream writer;

    public ClientThread(ClientUI ui) {
        this.ui = ui;
        String ip=ui.tfIP.getText();
        int port =Integer.valueOf(ui.tfPort.getText());
        try {
            client = new Socket(ip, port);//这里设置连接服务器端的IP的端口
            println("连接服务器成功："+ip+":"+port);
            reader = new BufferedInputStream(client.getInputStream());
            writer = new BufferedOutputStream(client.getOutputStream());
        } catch (IOException e) {
            println("连接服务器失败："+ip+":"+port);
            println(e.toString());
            e.printStackTrace();
        }
        this.start();
    }

    public void run() {
        byte[] result = new byte[11];
        while (true) {
            try {
            	reader.read(result);
            } catch (IOException e) {
                println("服务器断开连接");

                break;
            }
            if (result != null) {
                println("服务端返回>>" + HexUtils.bytesToHexString(result));
            }
        }
    }

    public void sendMsg(byte[] msg) {
        try {
            writer.write(msg);
            writer.flush();
           println("本程序发送>>"+HexUtils.bytesToHexString(msg));
        } catch (Exception e) {
            println(e.toString());
        }
    }
    
    public synchronized void println(String s) {
        if (s != null) {
            this.ui.taShow.setText(this.ui.taShow.getText() + s + "\n");
            System.out.println(s + "\n");
        }
    }
    
}