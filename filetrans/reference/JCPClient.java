import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;


public class JCPClient {

    /**
     * @param args
     */
    public static void main(String[] args)throws Exception {
        // TODO Auto-generated method stub
        int num = args.length;
        if(num<=3){
            System.out.println("please input server ip port filefrom fileto!");
            System.exit(0);
        }
        System.out.println("host:"+args[0]);
        System.out.println("port:"+args[1]);
        System.out.println("filefrom:"+args[2]);
        System.out.println("fileto:"+args[3]);
        InetAddress addr = InetAddress.getByName(args[0]);
        Socket socket = 
              new Socket(addr, Integer.parseInt(args[1]));
        OutputStream out = socket.getOutputStream();
        byte[] cmd = new byte[128];
        byte[] tcmd = "cp".getBytes();
        for(int i=0;i<tcmd.length;i++){
            cmd[i] = tcmd[i];
        }
        cmd[tcmd.length] = '-0';
        out.write(cmd,0,cmd.length);
        //文件名
        byte[] file = new byte[256];
        byte[] tfile = args[3].getBytes();
        for(int i=0;i<tfile.length;i++){
            file[i] = tfile[i];
        }
        file[tfile.length] = '-0';
        out.write(file,0,file.length);
        
        //大小
        File filein = new File(args[2]);
        byte[] size = new byte[64];
        byte[] tsize = (""+filein.length()).getBytes();
        
        for(int i=0;i<tsize.length;i++){
            size[i] = tsize[i];
        }
        size[tsize.length] = '-0';
        out.write(size,0,size.length);
        
        FileInputStream fis = null;
        byte[] buf = new byte[1024*10];
        //char[] bufC = new char[1024*10];
        fis = new FileInputStream(filein);
        int readsize = 0;
        //OutputStream ops = socket.getOutputStream();
        while((readsize = fis.read(buf, 0, buf.length))>0){
            out.write(buf,0,readsize);
            out.flush();
        }
        socket.close();
    }

}
