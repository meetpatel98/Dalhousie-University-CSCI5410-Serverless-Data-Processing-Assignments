import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
public class DatasetProcessing {
    GCSConnection gcsConnection = new GCSConnection();
    private static final String SOURCE_BUCKET = "sourcedatab00899516";

    public boolean checkIfBucketExists(){
        Storage storage = gcsConnection.establishConnection();
        if(storage.get(SOURCE_BUCKET) == null){
            return false;
        } else return storage.get(SOURCE_BUCKET).exists();
    }

    public Storage createBucket() {
        Storage storage = gcsConnection.establishConnection();
        if (storage != null) {
            if (!checkIfBucketExists()) {
                storage.create(BucketInfo.of(SOURCE_BUCKET));
                System.out.println("Bucket " + SOURCE_BUCKET + " created successfully.");
            } else {
                System.out.println("Bucket " + SOURCE_BUCKET + " exists already.");
            }
        }
        return storage;
    }
    private void uploadFilesToGCSBucket(Storage storage) {
        try {
            File[] files = new File("Dataset/Test/").listFiles();
            if (files == null) {
                return;
            }
            System.out.println("\nUploading Files to "+SOURCE_BUCKET+"\n");
            for (File file : files) {
                System.out.println("Dataset/Test/" + file.getName());
                storage.create(BlobInfo.newBuilder(BlobId.of(SOURCE_BUCKET, file.getName())).build(), Files.readAllBytes(Paths.get("Dataset/Test/" + file.getName())));
                System.out.println(file.getName() + " has been uploaded to bucket " + SOURCE_BUCKET + ".\n");
            }
        } catch (final IOException e) {
            System.out.println("Error while uploading file: " + e.getMessage());
        }
    }
    public void execute(){
        Storage storage = createBucket();
        uploadFilesToGCSBucket(storage);
    }
}
