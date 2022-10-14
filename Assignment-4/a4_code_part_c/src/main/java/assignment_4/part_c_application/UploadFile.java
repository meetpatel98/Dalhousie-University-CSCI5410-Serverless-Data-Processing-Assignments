package assignment_4.part_c_application;

import com.amazonaws.AmazonServiceException;

import java.io.File;
import java.nio.file.Paths;
public class UploadFile {
    AWSConnection awsConnection = new AWSConnection();
    public void uploadFile(String bucketName) {
        final String path = "./src/main/java/assignment_4/part_c_application/file_mongo_tweets.txt";
        final File file = new File(path);
        final String fileName = Paths.get(path).getFileName().toString();
        try {
            awsConnection.createAmazonS3ClientBuilder().putObject(bucketName, fileName, file);
            System.out.println("File uploaded successfully.");
        } catch (final AmazonServiceException e) {
            e.printStackTrace();
        }
    }
}
