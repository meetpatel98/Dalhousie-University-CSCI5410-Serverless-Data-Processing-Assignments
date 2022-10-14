package assignment_3.part_a_application;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
public class AWSConnection {
    private static final String AWS_ACCESS_KEY_ID = "ASIAVTXDLTV6KQROAMCN";
    private static final String AWS_SECRET_ACCESS_KEY = "CZFd7kNhFHhElIzb5zdAqe9jizBl5SObNn/I38OA";
    private static final String AWS_SESSION_TOKEN = "FwoGZXIvYXdzEIX//////////wEaDO7rUh9qgArwGtlLbSLAATsjzoWPaBNNi" +
            "GSOEhr9b9Xjr+P1CMf6ItDGY3643cUh9oeyJ/FkN3WoqjxkCCczAbrc6Y90F9ka3tBmB3a9jm/rHO" +
            "7fCCzAmESniUELZqaxLNOM2U9lhDmS9k3sF1K0VPlnjFvJVMZT3apEgxGOZnCJD7DA80uk4TF4Ft" +
            "TUYeYhpVb3kS9B1nb7csUjvh26JHoN33pUcvil/KPEvuOEe+cIhJMFZ/wjRnxMZXl+Dnmzjukc7" +
            "n8b4HN9dgyYv+YxGyiuvtaVBjItamLu54vhiqtEKR6ABboTl8JfRSDuD1mjbrfJWYBNewNkWJ12" +
            "gphFztYVxmJf";
    public AmazonS3 createAmazonS3ClientBuilder() {
        BasicSessionCredentials basicSessionCredentials = new BasicSessionCredentials(AWS_ACCESS_KEY_ID, AWS_SECRET_ACCESS_KEY, AWS_SESSION_TOKEN);
        AmazonS3 amazonS3object = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(basicSessionCredentials))
                .withRegion(Regions.US_EAST_1)
                .build();
        return amazonS3object;
    }
}
