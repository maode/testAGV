package com.his.net.agv.client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.his.net.agv.util.HexUtils;

public class ClientUI extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public ClientUI() {
		super("AGV测试");
		btStart = new JButton("连接AGV");
		labelIP = new JLabel("IP:");//ip 标签
		tfIP = new JTextField(10);// ip文本框
		labelPort = new JLabel("Port:");//端口标签
		tfPort = new JTextField(5);// 端口文本框
		taShow = new JTextArea();// 显示区域
		
		btAdvance=new JButton("前进");
		btFast=new JButton("快进");
		btRight=new JButton("右转");
		
		

		btStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				server = new ClientThread(ClientUI.this);
			}
		});
		
		
		btFast.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				byte[] command=HexUtils.hexStringToByte("aa550702e80300000000ee");//快进1m/s
				server.sendMsg(command);
			}
		});

		btAdvance.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//byte[] command=HexUtils.hexStringToByte("aa55070264000000000061");//前进0.1m/s
				byte[] command=HexUtils.hexStringToByte("aa40012908e803e803e803e80369060d");//新 旋转
				server.sendMsg(command);
			}
		});
		
		btRight.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				byte[] command=HexUtils.hexStringToByte("aa55070200000000e803ee");//右转1000 mrand/s
				//byte[] command=HexUtils.hexStringToByte("aa400129080000000000000000a1290d");//新 停止
				server.sendMsg(command);
			}
		});
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int a = JOptionPane.showConfirmDialog(null, "确定关闭吗？", "温馨提示",
						JOptionPane.YES_NO_OPTION);
				if (a == 1) {
					System.exit(0); // 关闭
				}
			}
		});
		JPanel top = new JPanel(new FlowLayout());
		top.add(labelIP);
		top.add(tfIP);
		top.add(labelPort);
		top.add(tfPort);
		top.add(btStart);
		top.add(btAdvance);
		top.add(btFast);
		top.add(btRight);
		this.add(top, BorderLayout.SOUTH);
		final JScrollPane sp = new JScrollPane();
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		sp.setViewportView(this.taShow);
		this.taShow.setEditable(false);
		this.add(sp, BorderLayout.CENTER);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 500);
		this.setLocation(300, 100);
		this.setVisible(true);
	}

	/** @Fields btStart : 启动按钮 */
	public JButton btStart;
	/** @Fields labelIP : IP文本框标签 */
	public JLabel labelIP;
	/** @Fields labelPort : 端口文本框标签 */
	public JLabel labelPort;
	/** @Fields tfIP : IP文本框 */
	public JTextField tfIP;
	/** @Fields tfPort : 端口文本框 */
	public JTextField tfPort;
	/** @Fields advance : 前进 */
	public JButton btAdvance;
	/** @Fields fast : 快进 */
	public JButton btFast;
	/** @Fields right : 右转 */
	public JButton btRight;
	/** @Fields taShow : 信息显示区域 */
	public JTextArea taShow;
	/** @Fields server : 客户端线程 */
	public ClientThread server;

}