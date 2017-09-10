import java.rmi.Remote;
import java.rmi.RemoteException;
 
public interface FileDataService extends Remote {
     
    public static final String FILE_SERVICE = "rmi://localhost:9001/FileDataService";
     
    public String start(String filename) throws RemoteException;
 
    public void upload(String uploadId,byte[] part,int offset,int len) throws RemoteException;
     
     
    public void finish(String uploadId) throws RemoteException;
 
}