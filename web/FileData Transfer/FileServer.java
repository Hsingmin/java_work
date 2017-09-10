import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
 
public class FileServer {
 
    FileDataService fileDataService;
 
    public FileServer() {
        try {
            fileDataService = new FileDataServiceImpl();
            LocateRegistry.createRegistry(9001);
            Naming.rebind(FileDataService.FILE_SERVICE, fileDataService);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
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