package filetrans;

import java.io.*;
import java.net.*;
import java.util.*;

/**
* @description: file transfer server.
* @author: Alfred Lee
* @date: Sept.20th 2015
*/

public class FileClient{
	public static void main(String[] args) throws IOException{
		String host = args[0];
		int port = 8189;
		int timeout = 2000;
	
		try(Socket s = new Socket()){
			s.connect(new InetSocketAddress(host,port),timeout);
			InputStream inStream = s.getInputStream();
			OutputStream outStream = s.getOutputStream();

			Scanner in = new Scanner(inStream);
			PrintWriter out = new PrintWriter(outStream,true);
			BufferedReader userin = new BufferedReader(new InputStreamReader(System.in));
			
			boolean interrupt = false;
			while(!interrupt){
				String line = in.nextLine();
				System.out.println(line);
				//Scanner userin = new Scanner(System.in);
				out.println(userin.readLine());
				if(line.trim().equals("STOP"));
					interrupt = true;
			}
		}
	}
}

