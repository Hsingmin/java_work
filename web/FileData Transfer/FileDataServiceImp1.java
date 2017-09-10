import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
 
public class FileDataServiceImpl extends UnicastRemoteObject implements FileDataService {
     
    Map<String, OutputStream> os = new HashMap<String, OutputStream>();
 
    public FileDataServiceImpl() throws RemoteException {
        super();
    }
     
    public String start(String filename) throws RemoteException{
        File file = new File(filename);
        try {
            if (!file.exists())
                file.createNewFile();
            OutputStream upOS = new BufferedOutputStream(new FileOutputStream(file));
            String uploadId = UUID.randomUUID().toString();
            os.put(uploadId, upOS);
            return uploadId;
        }catch (Exception e) {
            throw new RemoteException("初始化文件上传失败：",e);
        }
         
    }
 
    public void upload(String uploadId,byte[] part,int offset,int len) throws RemoteException {
        OutputStream upOS = os.get(uploadId);
        if(upOS == null){
            throw new RemoteException("上传文件的id不存在:" + uploadId);
        }
        try {
            upOS.write(part,offset,len);
        } catch (Exception e) {
            throw new RemoteException("File Up[" + uploadId+"]Exception:",e);
        }
    }
     
     
    public void finish(String uploadId) throws RemoteException{
        try {
            os.get(uploadId).close();
        } catch (IOException e) {
            throw new RemoteException("结束文件上传失败：",e);
        }
    }
 
}