package MyFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import FileTran.FileClient;

public class MyPane extends JPanel {
	// 默认服务器端口
	private final String P = "9527";
	// ////////////////////////////////
	private JTextField msgjt;
	private JComboBox ipjt;
	private JTextField portjt;
	private JButton clearjb;
	private JButton sendjb;
	private JButton exitjb;
	private JButton filejb;
	private JTextArea contendjt;
	private JScrollPane scrolljs;
	private Myframe mf;
	private int n = 0;
	private String fport = "9527";

	public MyPane(Myframe mf) {
		this.mf = mf;
		this.setBackground(Color.lightGray);
		// this.setBounds(0, 0, 600, 400);
		this.addContend();
	}

	private void addContend() {
		// TODO Auto-generated method stub
		this.setLayout(null);
		// 端口
		this.portjt = new JTextField();
		this.portjt.setBounds(520, 320, 70, 30);
		this.portjt.setForeground(Color.RED);
		this.portjt.setFont(new Font("宋体", Font.PLAIN, 25));
		this.portjt.setText("9527");
		this.add(this.portjt);

		// 输入框
		this.msgjt = new JTextField();
		this.msgjt.setBounds(10, 320, 250, 30);
		this.msgjt.setFont(new Font("宋体", Font.PLAIN, 20));
		this.msgjt.setForeground(Color.BLUE);
		this.add(this.msgjt);

		// 文件发送按钮
		this.filejb = new JButton("文件发送");
		this.filejb.setBounds(505, 200, 90, 30);
		this.add(this.filejb);
		this.filejb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new FileClient(mf).start();

			}
		});

		// 发送按钮
		this.sendjb = new JButton("发送");
		this.sendjb.setBounds(270, 320, 60, 30);
		this.add(this.sendjb);
		this.sendjb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String msg = mf.getPane().getMsgjt().getText();
				String port = mf.getPane().getPortjt().getText();
				String nowip = (String) mf.getPane().getIpjt()
						.getSelectedItem();
				if (msg.equals("")) {
					JOptionPane.showMessageDialog(mf, "内容不能为空！");
					// 目标端口是否改变
					if (!(port.equals(fport))) {
						JOptionPane.showMessageDialog(mf, "正在向服务器发送端口...");
						new Client("客户端&端口改为:" + port, P, mf.getMyip());
						fport = mf.getPane().getPortjt().getText();

					}
					// 获取目标电脑IP
					else if (n == 0) {
						String myip = null;
						InetAddress myComputer = null;
						try {
							myComputer = InetAddress.getLocalHost(); // 取得主机位置对象
							myip = myComputer.getHostAddress();
							// this.getPane().getContendjt().setText("目标用户IP："+this.ip);
							// port=mf.getPane().getPortjt().getText();
							JOptionPane
									.showMessageDialog(mf, "正在向服务器发送测试消息...");
							new Client("客户端&端口:" + port + "   (测试 )", P, mf
									.getMyip());
							fport = mf.getPane().getPortjt().getText();
							n = 1;
							return;
						} catch (UnknownHostException e) {
							myip = "暂时无法获取该用户IP";
							// String port=mf.getPane().getPortjt().getText();
							new Client(myip + "端口为 ：" + port, port, mf
									.getMyip());

							fport = mf.getPane().getPortjt().getText();
						}
						n = 1;

					}
					return;
				} else if (!nowip
						.matches("([0-9]{1,2}|1[0-9]{2}|2[0-4][0-9]|25[0-5])[.]"
								+ "([0-9]{1,2}|1[0-9]{2}|2[0-4][0-9]|25[0-5])[.]"
								+ "([0-9]{1,2}|1[0-9]{2}|2[0-4][0-9]|25[0-5])[.]"
								+ "([0-9]{1,2}|1[0-9]{2}|2[0-4][0-9]|25[0-5])")) {
					JOptionPane.showMessageDialog(mf, "IP不符合规范请确认....");
				} else {
					new Client(mf);
					if (!mf.getPane().getMsgjt().getText().equals("")) {
						// System.out.println(mf.getPane().getContendjt().getText());
						mf.getPane()
								.getContendjt()
								.setText("我说：  "+ msg+ "  （"+ new SimpleDateFormat("MM-dd HH:mm:ss").format(new Date())
												+ "）\n"+ mf.getPane().getContendjt().getText());
					}
					fport = mf.getPane().getPortjt().getText();

				}
				mf.getPane().getMsgjt().setText("");
			}
		});

		// ip地址
		this.ipjt = new JComboBox();
		this.ipjt.setBounds(340, 320, 170, 30);
		this.ipjt.setEditable(true);
		this.ipjt.setFont(new Font("宋体", Font.PLAIN, 22));
		this.ipjt.setForeground(Color.RED);
		this.ipjt.addItem("127.0.0.1");
		this.ipjt.addItem(mf.getMyip());

		this.add(this.ipjt);
		// 清屏按钮
		this.clearjb = new JButton("清屏");
		this.clearjb.setBounds(510, 100, 60, 30);
		this.add(this.clearjb);
		this.clearjb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				mf.getPane().getContendjt().setText("");
			}
		});

		// 退出按钮
		this.exitjb = new JButton("退出");
		this.exitjb.setBounds(510, 30, 60, 30);
		this.add(this.exitjb);
		this.exitjb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.exit(0);

			}
		});

		// 内容栏
		this.contendjt = new JTextArea();
		this.contendjt.setFont(new Font("宋体", Font.PLAIN, 20));
		this.contendjt.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.contendjt.setEditable(false);
		this.scrolljs = new JScrollPane(this.contendjt);
		this.scrolljs.setBounds(0, 0, 500, 300);
		this.add(this.scrolljs);

	}

	public JTextField getMsgjt() {
		return msgjt;
	}

	public void setMsgjt(JTextField msgjt) {
		this.msgjt = msgjt;
	}

	public JComboBox getIpjt() {
		return ipjt;
	}

	public void setIpjt(JComboBox ipjt) {
		this.ipjt = ipjt;
	}

	public JTextField getPortjt() {
		return portjt;
	}

	public void setPortjt(JTextField portjt) {
		this.portjt = portjt;
	}

	public JTextArea getContendjt() {
		return contendjt;
	}

	public void setContendjt(JTextArea contendjt) {
		this.contendjt = contendjt;
	}

}
