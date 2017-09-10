import java.io.FileInputStream;
import java.rmi.Naming;
 
public class FileClient {
 
    public FileClient() {
    }
 
    public static void main(String[] args) {
        try {
            FileInputStream fis = new FileInputStream("d:/r1.log");
            FileDataService fileDataService = (FileDataService) Naming.lookup(FileDataService.FILE_SERVICE);
            String uploadId = fileDataService.start("d:/r12.log");
            byte[] buffer = new byte[1024];
            int len = fis.read(buffer);
            while(len> -1){
                fileDataService.upload(uploadId, buffer, 0, len);
                System.out.println("read part");
                len = fis.read(buffer);
            }
            fileDataService.finish(uploadId);
            System.out.println("file upload finished");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}