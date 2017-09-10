package filetrans;

import java.io.*;
import java.net.*;
import java.util.*;

/**
* @author: Alfred Lee
* @date: Sept.26th 2015
*/

class SyncCodeRunnable implements Runnable{
	public SyncCodeRunnable(Socket s, String path){
		sock = s;
		filePath = path;
	}

	public void run(){
		try{
			try{
				File fromFile = new File(filePath);
				is = new FileInputStream(fromFile);
				dout = new DataOutputStream(sock.getOutputStream());
				byte[] b = new byte[(int) fromFile.length()];

				filename = fromFile.getName();
				System.out.println(filename);
				dout.writeBytes("synchronizing..." + "\r\n");
				dout.writeBytes(filename + "\r\n");

				while((length = is.read(b, 0, b.length)) > 0){
					dout.write(b, 0, length);
					dout.flush();
				}
			}catch(IOException e){
				e.printStackTrace();
			}
			finally{
				if(dout != null)
					dout.close();
				if(is != null)
					is.close();
				if(sock != null)
					sock.close();
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

	private Socket sock;
	private String filePath;
	private String filename;
	private FileInputStream is;
	private DataOutputStream dout;
	private int length;
}
