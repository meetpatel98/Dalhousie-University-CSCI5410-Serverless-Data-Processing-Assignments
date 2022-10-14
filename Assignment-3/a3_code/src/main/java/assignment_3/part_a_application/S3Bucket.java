package assignment_3.part_a_application;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;

import java.util.List;

public class S3Bucket {
    Bucket sourceBucket;
    Bucket tagsBucket;
    public S3Bucket() {
    }

    public boolean isBucketExists(String bucketName, AmazonS3 amazonS3ClientBuilder){
        List<Bucket> bucketList = amazonS3ClientBuilder.listBuckets();
        for (Bucket bucket: bucketList){
            if(bucket.getName().equals(bucketName)){
                return true;
            }
        }
        return false;
    }

    public Bucket createBucket(String bucketName){
        AWSConnection awsConnection = new AWSConnection();
        if (isBucketExists(bucketName, awsConnection.createAmazonS3ClientBuilder())){
            System.out.println("Bucket "+bucketName+ " already exists....");
        }else {
            try {
                System.out.println("Creating bucket with name " + bucketName + "...");
                Bucket bucket = awsConnection.createAmazonS3ClientBuilder().createBucket(bucketName);
                System.out.println("Bucket with name "+bucketName+ " is created successfully");
                System.out.println("------------------------------------");
                return bucket;
            }catch (AmazonS3Exception e){
                System.err.println(e.getErrorMessage());
            }
        }
        return null;
    }

    public void createbucketWithName(){
        AWSConnection awsConnection = new AWSConnection();
        try{
            final String bucketName1 = "sourceb00899516";
            final String bucketName2 = "tagsb00899516";
            if (awsConnection.createAmazonS3ClientBuilder() != null){
                sourceBucket = createBucket(bucketName1);
                tagsBucket = createBucket(bucketName2);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
