package assignment_3.part_a_application;

public class Main {
    public static void main(String[] args) {
        S3Bucket s3Bucket = new S3Bucket();
        s3Bucket.createbucketWithName();
        UploadFile uploadFile = new UploadFile();
        uploadFile.uploadFiles("tech");
    }
}
