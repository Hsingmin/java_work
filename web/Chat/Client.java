package MyFrame;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class Client {
		//默认服务器IP
		private final String I ="192.168.3.108";
		///////////////////////////////////
		private Myframe mf;
		private String msg;
		private Socket client=null;
		private BufferedWriter bf =null;
		private String ip;
		private String port;
//		private InputStream in =null;
//		private ObjectOutputStream out =null;
//		private byte[] b =null;
		//默认发送
		public Client(String msg,String port, String theIp){
			//我的电脑的IP地址，当需要吧自己的当终端用时使用，系统消息会全部转发到自己这
			this.ip=I; 
			//测试时可使用这段，系统消息发当前电脑ip
			if(theIp != null && !"".equals(theIp)){
				this.ip=theIp;
			}
			
			this.msg=msg;
			this.port=port;
			this.Send();
			
		}
		//正常消息发送
		public Client(Myframe mf){
			this.mf=mf;
			this.port=mf.getPane().getPortjt().getText();
			this.msg="我&"+mf.getPane().getMsgjt().getText();
			this.ip=(String) mf.getPane().getIpjt().getSelectedItem();
			
			this.Send();
		}
	
		//发送
		private void Send(){
			//System.out.println("IP:"+this.ip+"端口："+this.port+"发送:"+this.msg);
		try {
			client =new Socket(this.ip,Integer.parseInt(this.port));
	
			bf =new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			bf.write(this.msg);
			
			bf.flush();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(mf, "IP"+this.ip+"不存在，请确认...");
			mf.getPane().getMsgjt().setText("");
			return;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(mf, "系统繁忙，请稍后再试..");
			mf.getPane().getMsgjt().setText("");
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(mf, "系统繁忙请稍后再试！");
			return;
		}finally{
			if(bf!=null){
				try {
					bf.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(client!=null){
				try {
					client.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
		}
		
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		public String getIp() {
			return ip;
		}
		public void setIp(String ip) {
			this.ip = ip;
		}
		public String getPort() {
			return port;
		}
		public void setPort(String port) {
			this.port = port;
		}
	

}
