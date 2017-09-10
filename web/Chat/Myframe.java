package MyFrame;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Toolkit;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Myframe extends JFrame {

	private Container cpne;
	private MyPane pane;
	String   myip = null;
	
	public Myframe(){
		Toolkit tk =Toolkit.getDefaultToolkit();
		int h=(int)tk.getScreenSize().getHeight();
		int w=(int)tk.getScreenSize().getWidth();
		this.setSize(600, 400);
		this.setLocation((w-600)/2, (h-400)/2);
		this.setResizable(false);
		this.setTitle("聊天室");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addContend();

		this.setVisible(true);
	
	}

	private void addContend() {
		// TODO Auto-generated method stub
		InetAddress   myComputer=null;
        try 
         { 
            myComputer=InetAddress.getLocalHost(); //获得本机主机信息（主机名和ip地址）
            myip=myComputer.getHostAddress(); //得到IP地址
            JOptionPane.showMessageDialog(this, "本机IP地址为："+myip);
          
           } 
            catch   (UnknownHostException   e){
            	myip="无法获得当前主机IP";
            	JOptionPane.showMessageDialog(this, myip);
                
            }
		
		this.cpne=this.getContentPane();
		
		this.pane =new MyPane(this);
		
		
		this.cpne.add(this.pane);
		
	}

	public MyPane getPane() {
		return pane;
	}

	public void setPane(MyPane pane,String s) {
		this.pane = pane;
	}

	public String getMyip() {
		return myip;
	}

	public void setMyip(String myip) {
		this.myip = myip;
	}
	
	
}
