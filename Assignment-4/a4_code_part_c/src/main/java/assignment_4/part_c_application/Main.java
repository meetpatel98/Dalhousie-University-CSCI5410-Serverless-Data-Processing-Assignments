package assignment_4.part_c_application;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String bucketName1 = "twitterdatab00899516";
        String bucketName2 = "sentimentanalysisb008999516";
        S3Bucket s3Bucket = new S3Bucket();
        s3Bucket.createBucket(bucketName1);
        s3Bucket.createBucket(bucketName2);
        UploadFile uploadFile = new UploadFile();
        uploadFile.uploadFile(bucketName1);
    }
}