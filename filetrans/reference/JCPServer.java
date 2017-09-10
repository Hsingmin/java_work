import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

class CPThread extends Thread{
    Socket socket = null;
    public CPThread(Socket socket){
        this.socket = socket;
    }
    public void run(){
        try{
            InputStream in = 
                      socket.getInputStream();
            PrintWriter out = 
                  new PrintWriter(
                    new BufferedWriter(
                      new OutputStreamWriter(
                        socket.getOutputStream())),true);
            
            while(true)
            {
                //第一个参数为命令
                byte cmd[] = new byte[128];
                int b = 0;
                while(b<cmd.length){
                    b += in.read(cmd, b, cmd.length-b);
                }
                int ends = 0;
                for(int i=0;i<cmd.length;i++){
                    if(cmd[i]=='-0'){
                        ends = i;
                        break;
                    }
                }
                String cmds = new String(cmd,0,ends);
                if("exit".equals(cmds)){
                    System.exit(0);
                }
                else if("cp".equals(cmds)){
                    byte[] filename = new byte[256];
                    b = 0;
                    while(b<filename.length){
                        b += in.read(filename, b, filename.length-b);
                    }
                    ends = 0;
                    for(int i=0;i<filename.length;i++){
                        if(filename[i]=='-0'){
                            ends = i;
                            break;
                        }
                    }
                    String filenames = new String(filename,0,ends);
                    File fileout = new File(filenames);
                    if(fileout.isFile()){
                        throw new Exception("file exists"+fileout.getAbsolutePath());
                    }
                    FileOutputStream fos = new FileOutputStream(fileout);
                    
                    byte[] filesize = new byte[64];
                    b = 0;
                    while(b<filesize.length){
                        b += in.read(filesize, b, filesize.length-b);
                    }
                    
                    ends = 0;
                    for(int i=0;i<filesize.length;i++){
                        if(filesize[i]=='-0'){
                            ends = i;
                            break;
                        }
                    }
                    String filesizes = new String(filesize,0,ends);
                    
                    System.out.println("filesize:"+filesizes);
                    int ta = Integer.parseInt(filesizes);
                    byte[] buf = new byte[1024*10];
                    //InputStream ins = socket.getInputStream();
                    while(true){
                        if(ta==0){
                            break;
                        }
                        int len = ta;
                        if(len>buf.length){
                            len = buf.length;
                        }
                        int rlen = in.read(buf, 0, len);
                        
                        //int rlen = ins.read(buf, 0, len);
                        ta -= rlen;
                        if(rlen>0){
                            fos.write(buf,0,rlen);
                            fos.flush();
                        }
                        else{
                            break;
                        }
                    }
                    out.println("cp finish!");
                    fos.close();
                    break;
                }
                else{
                    System.out.println("err command!");
                    out.println("err command!");
                    break;
                }
            }
            socket.close();
        }catch(Exception e){
            e.printStackTrace();
            
        }
    }
}
public class JCPServer {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        int num = args.length;
        if(num<=0){
            System.out.println("please input server port!");
            System.exit(0);
        }
        int port = Integer.parseInt(args[0]);
        System.out.println("you input port:"+port);
        //绑定接受数据端口
        ServerSocket s = new ServerSocket(port);
        while(true){
            Socket socket = s.accept();
            new CPThread(socket).start();
        }
    }
}
