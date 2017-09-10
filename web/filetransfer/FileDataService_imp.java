package filetransfer;
 
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
 
public class FileDataService_imp extends UnicastRemoteObject implements FileDataService{
 
    public FileDataService_imp() throws RemoteException {
 
    }
 
    @Override
    public void upload(String filename, byte[] fileContent) throws RemoteException{
        File file = new File(filename);
        try {
            if (!file.exists())
                file.createNewFile();
            BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(file));
            os.write(fileContent);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
 
;   }
 
}
