package com.his.net.agv.nettybak.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.his.net.agv.nettybak.entity.AgvEvent;
import com.his.net.agv.nettybak.entity.PlcCmd;
import com.his.net.agv.nettybak.entity.PlcResult;
import com.his.net.agv.nettybak.listener.AgvListener;
import com.his.net.agv.nettybak.netty.AgvConstants;
import com.his.net.agv.nettybak.netty.AgvUtils;
import com.his.net.agv.nettybak.netty.client.plc.PlcClient;
import com.his.net.agv.nettybak.util.HexUtils;
import com.his.net.agv.nettybak.util.PlcCmdFactory;

public class PlcClientUI extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public PlcClientUI() {
		super("AGV测试");
		btLink = new JButton("连接AGV");
		labelIP = new JLabel("IP:");//ip 标签
		tfIP = new JTextField(AgvConstants.PLC_DEFAULT_HOST,10);// ip文本框
		labelPort = new JLabel("Port:");//端口标签
		tfPort = new JTextField(AgvConstants.PLC_DEFAULT_PORT+"",5);// 端口文本框
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
		btStop=new JButton("停止运动");
		btStopJack=new JButton("停止顶升");
		btAsk=new JButton("问询");
		
		

		btLink.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				plcClient=new PlcClient(tfIP.getText(), Integer.valueOf(tfPort.getText()), true);
				try {
					plcClient.start();
					println("PLC链接完成");
				}catch (Exception ex) {
					println("PLC链接失败:"+ex.getMessage());
				}
				//注册监听器
				AgvUtils.addListener(new AgvListener() {
					@Override
					public void onReadMsg(AgvEvent evt) {
						PlcResult result=evt.getPlcResult();
						StringBuilder msgsb=new StringBuilder("PLC返回的消息为：").append(HexUtils.toHexString(result.toByteArray()));
						msgsb.append("\n")
						.append("PLC返回的消息解析后为:[")
						.append("状态:").append(result.getStatus()).append(",")
						.append("速度:").append(result.getSpeed()).append(",")
						.append("X坐标:").append(result.getPgvX()).append(",")
						.append("Y坐标:").append(result.getPgvY()).append(",")
						.append("角度:").append(result.getPgvAngle()).append(",")
						.append("TAG:").append(result.getPgvTag()).append(",")
						.append("电池电流:").append(result.getElectricityC()).append(",")
						.append("电池电压:").append(result.getElectricityV()).append(",")
						.append("电池温度:").append(result.getTemperature()).append(",")
						.append("电池剩余:").append(result.getDumpEnergy()).append("]")
						.append("\n")
						;
						println(msgsb.toString());
					}
				});
			}
		});
		btAdvance.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				displayAndWriter(PlcCmdFactory.getForward());
			}
		});
		btRetreat.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				displayAndWriter(PlcCmdFactory.getBackWards());
			}
		});
		btRight.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				displayAndWriter(PlcCmdFactory.getToRight());
			}
		});
		btLeft.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				displayAndWriter(PlcCmdFactory.getToLeft());
			}
		});
		btUp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				displayAndWriter(PlcCmdFactory.getUp(0));
			}
		});
		btDown.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				displayAndWriter(PlcCmdFactory.getDown(0));
			}
		});
		btWhistle.addActionListener(new ActionListener() {
			//TODO 鸣笛暂时没有
			@Override
			public void actionPerformed(ActionEvent e) {}
		});
		btClockwise.addActionListener(new ActionListener() {
			//TODO 旋转暂时没有
			@Override
			public void actionPerformed(ActionEvent e) {
				displayAndWriter(PlcCmdFactory.getDextrorotation());
			}
		});
		btAnticlockwise.addActionListener(new ActionListener() {
			//TODO 旋转暂时没有			
			@Override
			public void actionPerformed(ActionEvent e) {
				displayAndWriter(PlcCmdFactory.getLevorotation());
			}
		});
		btClockwiseAuto.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {}
		});
		btAnticlockwiseAuto.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {}
		});
		btFast.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {}
		});
		btMedium.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {}
		});
		btSlow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {}
		});
		btStop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				displayAndWriter(PlcCmdFactory.getStopRun());
			}
		});
		btStopJack.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				displayAndWriter(PlcCmdFactory.getStopJack(0));
			}
		});
		btAsk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				displayAndWriter(PlcCmdFactory.getInquiryStatus());
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
		//TODO 配置显示按钮
		bottomPanel.add(labelIP);
		bottomPanel.add(tfIP);
		bottomPanel.add(labelPort);
		bottomPanel.add(tfPort);
		bottomPanel.add(btLink);
		bottomPanel.add(btAdvance);
		bottomPanel.add(btRetreat);
		bottomPanel.add(btRight);
		bottomPanel.add(btLeft);
		bottomPanel.add(btClockwise);
		bottomPanel.add(btAnticlockwise);
		bottomPanel.add(btAsk);
		bottomPanel.add(btUp);
		bottomPanel.add(btDown);
		//bottomPanel.add(btWhistle);
		//bottomPanel.add(btClockwiseAuto);
		//bottomPanel.add(btAnticlockwiseAuto);
		//bottomPanel.add(btFast);
		//bottomPanel.add(btMedium);
		//bottomPanel.add(btSlow);
		bottomPanel.add(btStopJack);
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
	/** @Fields btStop : 停止运动 */
	public JButton btStop;
	/** @Fields btStopJack : 停止顶升 */
	public JButton btStopJack;
	/** @Fields btAsk : 问询 */
	public JButton btAsk;
	
	
	/** @Fields taShow : 信息显示区域 */
	public JTextArea taShow;
	/** @Fields server : PLC客户端 */
	private PlcClient plcClient;
	
	
	
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
    
    /**向展示区域追加显示内容,当大于指定行数时,会清空展示区域
     * @param s
     */
    public synchronized void println(String s) {
        if (s != null) {
        	SimpleDateFormat smd=new SimpleDateFormat("HH:mm:ss");
        	String nowTime=smd.format(new Date());
        	s=nowTime+" "+s+"\n";
        	
        	JTextArea textArea=taShow;
        	int rows=textArea.getLineCount();
        	if(rows>=200){
        		textArea.setText(s);//大于指定行时，清空窗口。
        	}else{
        		textArea.append(s);
        	}
        }
    }
    
    public void displayAndWriter(PlcCmd cmd) {
    	plcClient.writeAndFlush(cmd);
    	String msg=new StringBuilder("发送到PLC的消息为：").append(HexUtils.toHexString(cmd.toByteArray())).toString();
    	println(msg);
    }
}