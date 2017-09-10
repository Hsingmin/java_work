package FileTran;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import MyFrame.Myframe;

public class FileClient extends Thread {

	private Socket client = null;
	private ObjectOutputStream out = null;
	private InputStream in = null;
	private Myframe mf;
	private String msg;
	private String ip;
	private String fileload ;
	private JFileChooser jfc;
	private File fi;
	public FileClient(Myframe mf) {
		this.mf = mf;
		this.msg = mf.getPane().getContendjt().getText();
		this.ip = (String) mf.getPane().getIpjt().getSelectedItem();
	}

	public void run() {
		this.jfc=new JFileChooser();
		this.jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int reval=this.jfc.showOpenDialog(mf);
		if(reval==JFileChooser.APPROVE_OPTION){
			fi=this.jfc.getSelectedFile();
		}else if(reval==1){
			return;
			
		}else{
			JOptionPane.showMessageDialog(mf, "操作有误请稍后再试！！");
			return;
		}
		this.mf.getPane().getContendjt().setText(msg + "发送中.......\n");
		String[] file = fi.toString().split("\\\\", -1);
		byte[] b = new byte[10240];
		try {
			List li = new ArrayList();
			client = new Socket(this.ip, 9500);
			in = new FileInputStream(this.fi);
			out = new ObjectOutputStream(client.getOutputStream());
			int l = 0;
			int len = 0;
			byte[] a = null;
			while ((l = in.read(b)) != -1) {
				a = new byte[1024];
				a = b.clone();
				len = l;
				li.add(a);

			}
				
			li.add(len);
			li.add(file[file.length - 1]);
			
			out.writeObject(li);
			out.flush();
			this.mf.getPane().getContendjt().setText(msg + "文件发送完毕.....\n");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "没有连接到服务器...");
			return;
		} catch (FileNotFoundException f) {
			JOptionPane.showMessageDialog(null, "文件没有找到，请确认...");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			JOptionPane.showMessageDialog(null, "输入有误！！");
			return;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
