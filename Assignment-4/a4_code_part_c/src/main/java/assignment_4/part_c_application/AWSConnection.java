package assignment_4.part_c_application;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
public class AWSConnection {
    private static final String AWS_ACCESS_KEY_ID = "ASIAVTXDLTV6ALDVPWYP";
    private static final String AWS_SECRET_ACCESS_KEY = "VRutoq2x0c/AN48o0w74jtgiIxPB1OypDTQiqrXI";
    private static final String AWS_SESSION_TOKEN = "FwoGZXIvYXdzEFQaDJ8a2dsDCc7TA81gGiLAAUUm" +
            "fKbvZIoWmXzVmpyQHRbsCrAD/HQLqbx7rnDbLDJEheH09voYBHRX42XSBd36BTYHKQMiowsQe1Ode+PdHd" +
            "0cvgOP6RtArLis4WrMFltFXLcUmVbFZgm6YqgKIjRCNCYpnd6rnPC4R8LuK6vrj3AWwE8pvH8nsJRB2Eh3" +
            "nhdyephMDP1rLIDrfjh7h37oYwyn9alyBAFYglHKctUyEyBV9ro27o2Ql010vZWUQ3fGh43MWftpU5Egn" +
            "XWCwx35gyjcjISWBjIttvFQfqa9D4x2hSgZwaZkSko3n7rVuJQPtmDXEfxewthmfkx1o543j1CThAUK";
    public AmazonS3 createAmazonS3ClientBuilder() {
        BasicSessionCredentials basicSessionCredentials = new BasicSessionCredentials(AWS_ACCESS_KEY_ID, AWS_SECRET_ACCESS_KEY, AWS_SESSION_TOKEN);
        AmazonS3 amazonS3object = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(basicSessionCredentials))
                    .withRegion(Regions.US_EAST_1)
                    .build();
            return amazonS3object;
    }
}
