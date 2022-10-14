package assignment_3.part_a_application;

import com.amazonaws.AmazonServiceException;

import java.io.File;

public class UploadFile {
    AWSConnection awsConnection = new AWSConnection();
    S3Bucket s3Bucket = new S3Bucket();
    public void uploadSingleFile(String bucketName, File file) {
        final String fileName = file.getName();
        System.out.println("Uploading "+fileName+" to "+ bucketName +" S3 bucket...");
        try {
            awsConnection.createAmazonS3ClientBuilder().putObject(bucketName, fileName, file);
            System.out.println("File uploaded successfully.");
        } catch (final AmazonServiceException e) {
            e.printStackTrace();
        }
    }
    public void uploadFiles(String folderName){
        final File[] allFiles = new File(folderName).listFiles();
        if (allFiles != null) {
            System.out.println("\n");
            System.out.println("Uploading files");
            System.out.println("------------------------------------------");
            for (final File file : allFiles) {
                uploadSingleFile("sourceb00899516", file);
                try {
                    Thread.sleep(200);
                } catch (final InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("------------------------------------------");
        } else {
            System.out.println("File upload Failed");
        }
    }
}
