
package MyFrame;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

public class Msgthread extends Thread {

	private BufferedReader bf =null;
	private Socket sock =null;
	private String msg=null;
	private Myframe mf;

	public Msgthread(Socket sock,Myframe mf){
		this.sock =sock;
		this.mf=mf;
	}
	//接收消息
	public void run(){
		
		try {
			bf =new BufferedReader(new InputStreamReader(this.sock.getInputStream()));
			String ms=bf.readLine();
			//解析名字
			String[] mess = ms.split("\\&",-1);
			//获取文本框原有消息
			msg=mf.getPane().getContendjt().getText();
			//接受消息
			mf.getPane().getContendjt().setText(this.sock.getInetAddress().getHostAddress()+" 说："+mess[1]+"  （"+new SimpleDateFormat("MM-dd HH:mm:ss").format(new Date())+"）\n"+this.msg);
			//增加该IP
			if(!mf.getPane().getIpjt().getSelectedItem().equals(this.sock.getInetAddress().getHostAddress())){
			mf.getPane().getIpjt().addItem(this.sock.getInetAddress().getHostAddress());
			}
			//获得接受IP
			mf.getPane().getIpjt().setSelectedItem(this.sock.getInetAddress().getHostAddress());
			
		}
		catch (Exception e) {
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
			if(sock!=null){
				try {
					sock.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
	}
	
	
	//接收文件
	public void FileWrite(){

		
		
		
	
	}
	
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
