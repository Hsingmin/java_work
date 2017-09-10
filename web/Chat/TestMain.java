package MyFrame;

import FileTran.FileServer;

public class TestMain {

	public static Myframe mf ;    //界面	
	public static Server s;        // 消息服务器
	public static FileServer fs;    //文件服务器
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		mf =new Myframe();
		s =new Server(mf);
		fs =new FileServer(mf);
		s.start();
		fs.start();
        
	}

}
