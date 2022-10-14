package part_c_aws_s3;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

public class DynamoDBConnection {
    private static final String AWS_ACCESS_KEY_ID = "ASIAVTXDLTV6ASJ67LBM";
    private static final String AWS_SECRET_ACCESS_KEY = "qNkMmdezImxgu2pldEar7HRDt966aFxobKSUwuFw";
    private static final String AWS_SESSION_TOKEN = "FwoGZXIvYXdzEN3//////////wEaDDIQCJb6TB8qf8439CLA" +
            "AbXtHAVE5LtR0H0CNL2l+TaBEMsd9BeWcJnvoW91diAz7pBA7uPQG8ox6U/0M7QLPA3BT+/rgVnCf88jW3TU2z1ft" +
            "jUmMqpOoW1g3AkeE9JGjkD1qME99CuHrIcT22y+48UcFwGLP7EsyS5oujaeaiC/Rgq+5Du1MI/LRCrLROk9Ym58dE" +
            "s9vYDPyjEAtrE2d1Dlsz93LUiAGiXg/BM5CHfKmhJoO1sBorh6/bBJRgMpYRGCRVkH79rsyXEZYydlqCiAgcGUBjI" +
            "tvPCuqdqA4Jl0cQ5pBqBm8yjpKbwMW/VE1LxvUbO7MUUzLIlcJ3oF4OGqs5S3";
    public DynamoDB createDynamoDBClientBuilder() {
        BasicSessionCredentials basicSessionCredentials = new BasicSessionCredentials(AWS_ACCESS_KEY_ID, AWS_SECRET_ACCESS_KEY, AWS_SESSION_TOKEN);
        AmazonDynamoDB awsDynamoDB =
                AmazonDynamoDBClientBuilder.standard().withCredentials
                                (new AWSStaticCredentialsProvider(basicSessionCredentials))
                        .withRegion(Regions.US_EAST_1)
                        .build();
        DynamoDB dynamoDB = new DynamoDB(awsDynamoDB);
        return dynamoDB;
    }

}
