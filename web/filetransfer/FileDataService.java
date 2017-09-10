package filetransfer;
 
import java.net.URL;
import java.rmi.Remote;
import java.rmi.RemoteException;
 
public interface FileDataService extends Remote{
 
    public void upload(String filename, byte[] file) throws RemoteException;
 
}
