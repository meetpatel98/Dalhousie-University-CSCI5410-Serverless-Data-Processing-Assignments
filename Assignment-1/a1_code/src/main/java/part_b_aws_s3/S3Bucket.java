package part_b_aws_s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;

import java.util.List;

public class S3Bucket {
    public boolean isBucketExists(String bucketName, AmazonS3 amazonS3ClientBuilder){
        List<Bucket> bucketList = amazonS3ClientBuilder.listBuckets();
        for (Bucket bucket: bucketList){
            if(bucket.getName().equals(bucketName)){
                return true;
            }
        }
        return false;
    }

    public void createBucket(String bucketName){
        AWSConnection awsConnection = new AWSConnection();
        if (isBucketExists(bucketName, awsConnection.createAmazonS3ClientBuilder())){
            System.out.println("Bucket "+bucketName+ " already exists....");
        }else {
            try {
                System.out.println("Creating bucket " + bucketName + "...");
                awsConnection.createAmazonS3ClientBuilder().createBucket(bucketName);
                System.out.println("Bucket "+bucketName+ " is created successfully");
            }catch (AmazonS3Exception e){
                System.err.println(e.getErrorMessage());
            }
        }
    }
}
