package part_b_aws_s3;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String bucketName = null;
        while (isNull(bucketName) || isEmpty(bucketName)) {
            System.out.println("Enter bucket name");
            bucketName = scanner.nextLine();
        }
        S3Bucket s3Bucket = new S3Bucket();
        s3Bucket.createBucket(bucketName);

        String input;
        System.out.println("Want to upload file? Enter Yes  or No");
        input = scanner.nextLine();
        if(input.equalsIgnoreCase("Yes")){
            UploadFile uploadFile = new UploadFile();
            uploadFile.uploadFile(bucketName);
        }else if (input.equalsIgnoreCase("No")){
            System.exit(0);
        }
    }
    private static boolean isEmpty(String bucketName) {
        return bucketName.trim().isEmpty();
    }
    private static boolean isNull(String bucketName) {
        return bucketName == null;
    }
}
