import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

public class AWSConnection {
    private static final String AWS_ACCESS_KEY_ID = "ASIAVTXDLTV6LD3JP7XP";
    private static final String AWS_SECRET_ACCESS_KEY = "oAfnE+mL5PbyTSlq3cQznVZaz74m9z/fp8sdZsFd";
    private static final String AWS_SESSION_TOKEN = "FwoGZXIvYXdzEDMaDJuUS8TVX1ZwJ0pu7yLAAY37ekZ" +
            "/k27osh/TAmAR8b+EBrpYzEvl1pBH9mljWJKqeTgTBsdFXsG1oRPLEi5htvCthxbiSDIaokheN" +
            "k1g0AXr9e5eupa2hNuFvMRQvjkYxsruBUE27DrxIE68gR747QitPQV8UttqGWOpbQkZXHF2CzQ" +
            "ZCs9gQR+YMXWKwcz+2LVV5tJYm4nAwsG8ybY+0cVW6bz4/cSWVrtF/LCVsRfh+Pga1Cs0PAkgeA" +
            "ZnBhVITNg66Gy7RF2LoZVBlPH7ayjtp+2WBjItHAXixqxz3I98j9WRvJhDlrr7n7fvDrI/XwgAoD" +
            "AkNjADj56PH1mxrwcxt5nc";
    private static final BasicSessionCredentials AWS_CREDENTIALS = new BasicSessionCredentials(AWS_ACCESS_KEY_ID, AWS_SECRET_ACCESS_KEY, AWS_SESSION_TOKEN);

    public AmazonSQS createAmazonSQSClientBuilder() {
        return AmazonSQSClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(AWS_CREDENTIALS))
                .withRegion(Regions.US_EAST_1)
                .build();
    }
}
