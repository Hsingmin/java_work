package FileTran;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import MyFrame.Myframe;

public class FileServer extends Thread{

	private Myframe mf;
	private Socket sock = null;
	private ServerSocket server = null;

	 public FileServer(Myframe mf){
	 this.mf=mf;
	 }
	public void run() {
		// TODO Auto-generated method stub

		ServerSocket server = null;
		Socket sock = null;
		try {

			server = new ServerSocket(9500);
			while (true) {
				sock = server.accept();

				new FileMsgthread(sock,this.mf).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
