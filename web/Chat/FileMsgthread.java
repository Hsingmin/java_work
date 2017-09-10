package FileTran;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import MyFrame.Myframe;

public class FileMsgthread extends Thread {

	private Myframe mf;
	private Socket sock = null;
	private ObjectInputStream in = null;
	private byte[] b = null;
	private OutputStream out = null;
	private String msg;
	private JFileChooser jfc;
	private File fi;
	public FileMsgthread(Socket sock, Myframe mf) {
		this.sock = sock;
		this.mf = mf;
		msg = mf.getPane().getContendjt().getText();
	}

	public void run() {
		try {
			in = new ObjectInputStream(this.sock.getInputStream());
			List li = null;
			List lb = null;

			li = (List) this.in.readObject();

			lb = li.subList(0, li.size() - 2);
			int len = (Integer) li.get(li.size() - 2);
			String file = (String) li.get(li.size() - 1);
			
			int judge=JOptionPane.showConfirmDialog(mf, "正在接受用户 "
					+ this.sock.getInetAddress().getHostAddress()
					+ " 发送文件:\n"+file);
			if(judge==0){
				this.jfc=new JFileChooser();
				this.fi=new File(file);
				this.jfc.setSelectedFile(this.fi);
				this.jfc.showSaveDialog(mf);
				this.fi=this.jfc.getSelectedFile();
				this.out = new FileOutputStream(this.fi);
			}else{
				return;
			}
			this.mf.getPane().getContendjt().setText(msg + "接收中.......\n");
			int i = 0;
			while (i < lb.size()) {
				b = (byte[]) lb.get(i);
				if (i == (lb.size() - 1)) {
					out.write(b, 0, len);
					out.flush();
					break;
				}
				out.write(b);
				out.flush();
				i++;
			}

			this.mf.getPane().getContendjt().setText(msg + "文件接收完毕.....\n");

		} catch (FileNotFoundException f) {
			JOptionPane.showMessageDialog(null, "文件存储有误...");
			return;
		} catch (EOFException eof) {
			JOptionPane.showMessageDialog(null, "无法接收文件...");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			JOptionPane.showMessageDialog(null, "输入有误...");
			return;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			JOptionPane.showMessageDialog(null, "无法找到文件");
			return;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (sock != null) {
				try {
					sock.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (this.out != null) {
				try {
					this.out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

}
