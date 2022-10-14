import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.io.FileInputStream;
import java.io.IOException;

public class GCSConnection {
    public Storage establishConnection() {
        try {
            Credentials credentials = GoogleCredentials.fromStream(new FileInputStream("csci5410-assignment4-partb-bbfabf0ee9a5.json"));
            return StorageOptions.newBuilder().setCredentials(credentials).setProjectId("csci5410-assignment4-partb").build().getService();
        } catch (IOException e) {
            System.out.println("Error while creating GCP Connection: " + e.getMessage());
        }
        return null;
    }
}
