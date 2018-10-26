package com.his.net.agv.clientSh;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTextArea;

import com.his.net.agv.util.ByteUtils;

public class ClientThread extends Thread {
    ClientUI ui;
    Socket client;
    BufferedInputStream reader;
    BufferedOutputStream writer;
    /** @Fields logFileName : 全局日志文件名，保存最后一次写入的日志文件名 */
    String[] logFile;
    /** @Fields createFile : 是否创建新的日志文件 */
    boolean createFile=true;

    public ClientThread(ClientUI ui) {
        this.ui = ui;
        String ip=ui.tfIP.getText();
        int port =Integer.valueOf(ui.tfPort.getText());
        try {
            client = new Socket(ip, port);//这里设置连接AGV端的IP的端口
            println("连接AGV成功："+ip+":"+port);
            reader = new BufferedInputStream(client.getInputStream());
            writer = new BufferedOutputStream(client.getOutputStream());
        } catch (IOException e) {
            println("连接AGV失败："+ip+":"+port);
            println(e.toString());
            e.printStackTrace();
        }
        this.start();
    }

    public void run() {
        byte[] result = new byte[10];//返回的字节长度
        while (true) {
            try {
            	reader.read(result);
            } catch (IOException e) {
                println("AGV断开连接");

                break;
            }
            if (result != null) {
                println("AGV返回[二进制]>>" + getFormatData(result));
                writeLogFile(result);
            }
        }
    }

    public void sendMsg(byte[] msg) {
        try {
            writer.write(msg);
            writer.flush();
           println("本程序发送[二进制]>>"+getFormatData(msg));
        } catch (Exception e) {
            println(e.toString());
        }
    }
    
    public synchronized void println(String s) {
        if (s != null) {
        	SimpleDateFormat smd=new SimpleDateFormat("HH:mm:ss");
        	String nowTime=smd.format(new Date());
        	s=nowTime+" "+s+"\n";
        	
        	JTextArea textArea=this.ui.taShow;
        	int rows=textArea.getLineCount();
        	if(rows>=200){
        		textArea.setText(s);//大于指定行时，清空窗口。
        	}else{
        		textArea.append(s);
        	}
            System.out.println(s);
        }
    }
    
    /**字节格式化为易读的二进制信息
     * @param bytes
     * @return
     */
    public synchronized String getFormatData(byte[] bytes) {
    	StringBuffer sb=new StringBuffer();
        for(int i=0;i<bytes.length;i++) {
        	byte m=bytes[i];
        	sb.append("字节").append(i+"[").append(ByteUtils.byte2BitStr(m)).append("],");
        }
        return sb.toString();
    }
    /**解析AGV反馈的字节为易读的参数信息
     * @param bytes
     * @return
     */
    public synchronized String getParseData(byte[] bytes) {
    	if(bytes==null||bytes.length!=10) {
    		return null;
    	}
    	
    	StringBuffer sb=new StringBuffer();
    	
    	byte charge=bytes[0];
    	String chStr=ByteUtils.byte2BitStr(charge);
    	String tf="否";
    	if("00000010".equals(chStr)) {
    		tf="是";
    	}
    	sb.append("[充电完成情况：").append(tf).append("],");
    	
    	sb.append("[故障信息：暂无-后期定义").append("],");
    	
    	byte power =bytes[2];
    	sb.append("[当前电流值（A）:").append(power&0xFF).append("],");

    	byte powerV =bytes[3];
    	sb.append("[当前总电压（V）:").append(powerV&0xFF).append("],");
    	
    	byte battery =bytes[4];
    	sb.append("[电池充放电次数 (次):").append(battery&0xFF).append("],");
    	
    	byte batterypercent =bytes[5];
    	sb.append("[剩余电量百分比（0-100）:").append(batterypercent&0xFF).append("],");
    	
    	byte[] speed=new byte[] {bytes[6],bytes[7]};
    	short ss=ByteUtils.byte2Short(speed);
    	sb.append("[当前速度（毫米/每秒）:").append(ss&0xFFFF).append("],");
 
    	byte[] code=new byte[] {bytes[8],bytes[9]};
    	short cs=ByteUtils.byte2Short(code);
    	sb.append("[二维码信息（四位整数）:").append(cs&0xFFFF).append("],");
    	
    	return sb.toString();
    }
    
    
    
    
    /**写日志文件
     * @param bytes
     */
    public synchronized void writeLogFile(byte[] bytes) {

    	Date now=new Date();
    	//创建目录部分
		File path =new File("logs");
        if(!path.exists()) {
        	path.mkdirs();
        }
        
        //创建文件部分
        //判断是否创建
        if(logFile!=null&&logFile.length==2) {
        	//判断是否创建
        	int idx=Integer.parseInt(logFile[1]);
        	if(idx<1000) {
        		createFile=false;
        	}else {
        		createFile=true;
        	}
        	
        }else {
        	createFile=true;
        }
        
        if(createFile) {
        	//创建
        	SimpleDateFormat sdf=new SimpleDateFormat("MM-dd HH.mm.ss");
        	logFile=new String[] {sdf.format(now)+".log","0"};
        	
        }
        
        
        String fname=logFile[0];
        int line=Integer.parseInt(logFile[1]);
        
        //窗口打印一下当前写入日志文件的磁盘路径
        try {
			//println("日志被写入 "+path.getCanonicalPath()+" 目录下的 "+fname);
			println("日志被写入 "+new File(path.getCanonicalPath()+"/"+fname).getCanonicalPath());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        
        //写部分
        PrintWriter pw=null;
		try {
			
			OutputStreamWriter ow=new OutputStreamWriter(new FileOutputStream(path+"/"+fname,true), "UTF-8");
			pw = new PrintWriter(new BufferedWriter(ow));
			SimpleDateFormat wsmd=new SimpleDateFormat("HH:mm:ss");
			String nowTime=wsmd.format(now);
			pw.println(nowTime+" AGV返回原始指令》"+getFormatData(bytes));
			logFile[1]=String.valueOf(++line);
			pw.println(nowTime+" 程序解析后的内容》"+getParseData(bytes));
			logFile[1]=String.valueOf(++line);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("创建文件输出流失败");
		}finally{
			System.out.println("进入写日志文件的finally块，执行刷新和关闭");
			if(pw!=null){
				if(pw.checkError()){
					System.out.println("日志输出流在底层输出流上或在格式转换过程中遇到错误");
				}
				pw.close();
			}else{
				System.out.println("日志输出流引用为null");
			}
		}
	    	
    }
    
}