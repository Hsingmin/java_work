package filetransfer;
 
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
 
public class FileServer {
 
    FileDataService fileDataService;
 
    public FileServer() {
        try {
            fileDataService = new FileDataService_imp();
            LocateRegistry.createRegistry(9001);
            Naming.rebind("rmi://localhost:9001/FileDataService", fileDataService);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
 
 
    }
 
    /**
     * @param args
     */
    public static void main(String[] args) {
        new FileServer();
 
    }
 
}
