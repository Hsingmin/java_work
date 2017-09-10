package MyFrame;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;


public class Server extends Thread{

	private ServerSocket server=null;
	private Socket sock =null;
	private Myframe mf;
	private String port;
	
	public Server(){
	
	}
	public Server(Myframe mf){
		
		this.mf =mf;
		this.port=mf.getPane().getPortjt().getText();
	}
	public void run(){	
		
		try {
			
			server =new ServerSocket(Integer.parseInt(this.port));
			
			while(true){
			sock =server.accept();
			new Msgthread(sock,this.mf).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(mf, "没有监听端口"+this.port);
			return;
		}
		
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
	

}
