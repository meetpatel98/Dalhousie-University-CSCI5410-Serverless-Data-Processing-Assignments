package part_b_aws_s3;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
public class AWSConnection {
    private static final String AWS_ACCESS_KEY_ID = "ASIAVTXDLTV6F6HW5576";
    private static final String AWS_SECRET_ACCESS_KEY = "mose7hvZ6Pnr6B4VKJDPZJH56PJkXZij7F0WEBNb";
    private static final String AWS_SESSION_TOKEN = "FwoGZXIvYXdzENH//////////wEaDFM6oYlUicxLePbaSCLAASO/" +
            "nvZcYU8Rwrd38cOvDsFsiRa4TPYckIopzwHjddq7A+9RwgLaTC/a2edG/K8RBOMdG1exzbPSkxJ2hDXTBHDWCumORszjrrf" +
            "ACpRNqty/bn3sR6h6B/42OW/Ie7R/fPUMevOWklqiKf4x/dTEXgDKfyqSCgq3jCqSkgpMDEHiVekpsz1WWFjD5IJa6oa1TcnbB" +
            "xFgVsTBrw+D0GXrEAGDCEDFrq448xhwgupiMqVIlEScG7/mUo01TuLZiAJ6XSjgx76UBjItV9IrC2T50fMfEFcMtpS29OAnEewg" +
            "79IWDEhdbhbwwELe5clggKNoORjmR9II";
    public AmazonS3 createAmazonS3ClientBuilder() {

        BasicSessionCredentials basicSessionCredentials = new BasicSessionCredentials(AWS_ACCESS_KEY_ID, AWS_SECRET_ACCESS_KEY, AWS_SESSION_TOKEN);

        AmazonS3 amazonS3object = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(basicSessionCredentials))
                    .withRegion(Regions.US_EAST_1)
                    .build();
            return amazonS3object;
    }
}
