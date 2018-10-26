package com.his.net.agv.clientSh;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.his.net.agv.util.BitUtils;

public class ClientUI extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public ClientUI() {
		super("AGV测试");
		btLink = new JButton("连接AGV");
		labelIP = new JLabel("IP:");//ip 标签
		tfIP = new JTextField(10);// ip文本框
		labelPort = new JLabel("Port:");//端口标签
		tfPort = new JTextField(5);// 端口文本框
		taShow = new JTextArea();// 显示区域
		
		btAdvance=new JButton("前进");
		btRetreat=new JButton("后退");
		btRight=new JButton("右移");
		btLeft=new JButton("左移");
		btUp=new JButton("上升");
		btDown=new JButton("下降");
		btWhistle=new JButton("鸣笛");
		btClockwise=new JButton("正转");
		btAnticlockwise=new JButton("反转");
		btClockwiseAuto=new JButton("正导航");
		btAnticlockwiseAuto=new JButton("反导航");
		btFast=new JButton("快速");
		btMedium=new JButton("中速");
		btSlow=new JButton("慢速");
		btStop=new JButton("停止");
		
		

		btLink.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				server = new ClientThread(ClientUI.this);
			}
		});
		btAdvance.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				byte[] command=new byte[3];
				command[0]=BitUtils.BitStrToByte("00010001");//手动行驶使能-前进
				command[2]=BitUtils.BitStrToByte("00000001");//速度控制-高速
				globalComm=command;
				server.sendMsg(command);
			}
		});
		btRetreat.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				byte[] command=new byte[3];
				command[0]=BitUtils.BitStrToByte("00100001");//手动行驶使能-后退
				command[2]=BitUtils.BitStrToByte("00000001");//速度控制-高速
				globalComm=command;
				server.sendMsg(command);
			}
		});
		btRight.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				byte[] command=new byte[3];
				command[0]=BitUtils.BitStrToByte("10000001");//手动行驶使能-右移
				command[2]=BitUtils.BitStrToByte("00000001");//速度控制-高速
				globalComm=command;
				server.sendMsg(command);
			}
		});
		btLeft.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				byte[] command=new byte[3];
				command[0]=BitUtils.BitStrToByte("01000001");//手动行驶使能-左移
				command[2]=BitUtils.BitStrToByte("00000001");//速度控制-高速
				globalComm=command;
				server.sendMsg(command);
			}
		});
		btUp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				byte[] command=new byte[3];
				command[0]=BitUtils.BitStrToByte("00000110");//手动行驶使能-上升
				command[2]=BitUtils.BitStrToByte("00000001");//速度控制-高速
				server.sendMsg(command);
			}
		});
		btDown.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				byte[] command=new byte[3];
				command[0]=BitUtils.BitStrToByte("00001010");//手动行驶使能-下降
				command[2]=BitUtils.BitStrToByte("00000001");//速度控制-高速
				server.sendMsg(command);
			}
		});
		btWhistle.addActionListener(new ActionListener() {
			//TODO 鸣笛暂时没有
			@Override
			public void actionPerformed(ActionEvent e) {
				byte[] command=new byte[3];
				server.sendMsg(command);
			}
		});
		btClockwise.addActionListener(new ActionListener() {
			//TODO 旋转暂时没有
			@Override
			public void actionPerformed(ActionEvent e) {
				byte[] command=new byte[3];
				server.sendMsg(command);
			}
		});
		btAnticlockwise.addActionListener(new ActionListener() {
			//TODO 旋转暂时没有			
			@Override
			public void actionPerformed(ActionEvent e) {
				byte[] command=new byte[3];
				server.sendMsg(command);
			}
		});
		btClockwiseAuto.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				byte[] command=new byte[3];
				command[1]=BitUtils.BitStrToByte("00000011");//导航控制-正向导航
				command[2]=BitUtils.BitStrToByte("00000001");//速度控制-高速
				server.sendMsg(command);
			}
		});
		btAnticlockwiseAuto.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				byte[] command=new byte[3];
				command[1]=BitUtils.BitStrToByte("00000101");//导航控制-反向导航
				command[2]=BitUtils.BitStrToByte("00000001");//速度控制-高速
				server.sendMsg(command);
			}
		});
		btFast.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				byte[] command=new byte[3];
				command=globalComm;
				command[2]=BitUtils.BitStrToByte("00000001");//速度控制-高速
				server.sendMsg(command);
			}
		});
		btMedium.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				byte[] command=new byte[3];
				command=globalComm;
				command[2]=BitUtils.BitStrToByte("00000010");//速度控制-中速
				server.sendMsg(command);
			}
		});
		btSlow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				byte[] command=new byte[3];
				command=globalComm;
				command[2]=BitUtils.BitStrToByte("00000100");//速度控制-低速
				server.sendMsg(command);
			}
		});
		btStop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				byte[] command=new byte[3];
				globalComm=command;
				server.sendMsg(command);
			}
		});
//使用下面注释掉的部分自定义关闭方法不管用～～
//		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
//		this.addWindowListener(new WindowAdapter() {
//			public void windowClosing(WindowEvent e) {
//				int a = JOptionPane.showConfirmDialog(null, "确定关闭吗？", "温馨提示",
//						JOptionPane.YES_NO_OPTION);
//				System.out.println("点击关闭按钮后返回值："+a);
//				if (a == 0) {
//					System.out.println("值为0关闭窗口");
//					System.exit(0); // 关闭
//				}else{
//					return;
//				}
//			}
//		});
		JPanel bottomPanel = new JPanel(new FlowLayout());
		bottomPanel.add(labelIP);
		bottomPanel.add(tfIP);
		bottomPanel.add(labelPort);
		bottomPanel.add(tfPort);
		bottomPanel.add(btLink);
		bottomPanel.add(btAdvance);
		bottomPanel.add(btRetreat);
		bottomPanel.add(btRight);
		bottomPanel.add(btLeft);
		bottomPanel.add(btUp);
		bottomPanel.add(btDown);
		//TODO bottomPanel.add(btWhistle);
		//bottomPanel.add(btClockwise);
		//bottomPanel.add(btAnticlockwise);
		bottomPanel.add(btClockwiseAuto);
		bottomPanel.add(btAnticlockwiseAuto);
		bottomPanel.add(btFast);
		bottomPanel.add(btMedium);
		bottomPanel.add(btSlow);
		bottomPanel.add(btStop);
		this.add(bottomPanel, BorderLayout.SOUTH);
		final JScrollPane sp = new JScrollPane();
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		sp.setViewportView(this.taShow);
		this.taShow.setEditable(false);
		this.add(sp, BorderLayout.CENTER);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bottomPanel.setPreferredSize(new Dimension(800,100));
		this.setSize(800, 500);
		this.setLocation(300, 100);
		this.setVisible(true);
	}

	/** @Fields labelIP : IP文本框标签 */
	public JLabel labelIP;
	/** @Fields labelPort : 端口文本框标签 */
	public JLabel labelPort;
	/** @Fields tfIP : IP文本框 */
	public JTextField tfIP;
	/** @Fields tfPort : 端口文本框 */
	public JTextField tfPort;
	
	/** @Fields btLink : 链接AGV */
	public JButton btLink;
	/** @Fields btAdvance : 前进 */
	public JButton btAdvance;
	/** @Fields btRetreat : 后退 */
	public JButton btRetreat;
	/** @Fields btRight : 右移 */
	public JButton btRight;
	/** @Fields btLeft : 左移 */
	public JButton btLeft;
	/** @Fields btUp : 上升 */
	public JButton btUp;
	/** @Fields btDown : 下降 */
	public JButton btDown;
	/** @Fields btWhistle : 鸣笛 */
	public JButton btWhistle;
	/** @Fields btClockwise : 正转 */
	public JButton btClockwise;
	/** @Fields btAnticlockwise : 反转 */
	public JButton btAnticlockwise;
	/** @Fields btClockwiseAuto : 正导航 */
	public JButton btClockwiseAuto;
	/** @Fields btAnticlockwiseAuto : 反导航 */
	public JButton btAnticlockwiseAuto;
	/** @Fields btFast : 快速 */
	public JButton btFast;
	/** @Fields btMedium : 中速 */
	public JButton btMedium;
	/** @Fields btSlow : 慢速 */
	public JButton btSlow;
	/** @Fields btStop : 停止 */
	public JButton btStop;
	
	
	/** @Fields taShow : 信息显示区域 */
	public JTextArea taShow;
	/** @Fields server : 客户端线程 */
	public ClientThread server;
	/** @Fields globalComm : 全局命令，存放最后一次运动指令 */
	private byte[] globalComm=new byte[3];
	
	
	
    //重写窗口事件，自定义关闭事件  
    @Override  
    protected void processWindowEvent(WindowEvent e) {  
        if (e.getID() == WindowEvent.WINDOW_CLOSING){
        	this.close(e.getComponent());
        	return; //直接返回，阻止默认动作，阻止窗口关闭
        }else{
        	super.processWindowEvent(e); //非关闭操作时，执行窗口事件的默认动作
        }
    }
    
    /**关闭窗口方法<br/>
     * 关闭前会进行询问
     * @param component 确定在其中显示对话框的 Frame；如果为 null 或者 parentComponent 不具有 Frame，则使用默认的 Frame
     */
    private void close(Component component){
		int a = JOptionPane.showConfirmDialog(component, "确定关闭吗？", "温馨提示",
				JOptionPane.YES_NO_OPTION);
		System.out.println("点击关闭按钮后返回值（0关闭1不关闭）："+a);
		if (a == JOptionPane.YES_OPTION) {
			System.out.println("值为0关闭窗口");
			System.exit(0); // 关闭
		}
    }
}