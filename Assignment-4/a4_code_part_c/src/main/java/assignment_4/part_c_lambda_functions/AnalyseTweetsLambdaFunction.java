package assignment_4.part_c_lambda_functions;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.google.gson.Gson;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.comprehend.ComprehendClient;
import software.amazon.awssdk.services.comprehend.model.DetectSentimentRequest;
import software.amazon.awssdk.services.comprehend.model.DetectSentimentResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class AnalyseTweetsLambdaFunction implements RequestHandler<S3Event, String> {
    final static String LOG = "assignment_4.part_c_lambda_functions.AnalyseTweetsLambdaFunction";
    @Override
    public String handleRequest(S3Event s3Event, Context context) {
        try {
            Map<String, String> entities = new LinkedHashMap<>();
            StringBuilder tweetsInJson = new StringBuilder();
            Region region = Region.US_EAST_1;
            ComprehendClient comClient = ComprehendClient.builder()
                    .region(region)
                    .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                    .build();

            String bucketName = s3Event.getRecords().get(0).getS3().getBucket().getName();
            String fileNameKey = URLDecoder.decode(s3Event.getRecords().get(0).getS3().getObject().getKey().replace('+', ' '), "UTF-8");

            context.getLogger().log(LOG + ": BucketName: " + bucketName + " FileName key: " + fileNameKey);

            String fileContent = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build().getObjectAsString(bucketName, fileNameKey);

            context.getLogger().log(LOG + fileContent);

            String[] tweets = fileContent.replaceAll("\\n", " ").split("RT @\\w+:");

            Gson gsonObj = new Gson();
            tweetsInJson.append("[");
            for (int i=1; i<tweets.length; i++){
                DetectSentimentRequest detectSentimentRequest = DetectSentimentRequest.builder()
                        .text(tweets[i])
                        .languageCode("en")
                        .build();
                DetectSentimentResponse detectSentimentResult = comClient.detectSentiment(detectSentimentRequest);
                entities.put("Tweet",tweets[i].replaceAll("#\\w+", "").replaceAll("[\\u00A0]+", "").replaceAll("\\.","").trim());
                entities.put("Positive Score",detectSentimentResult.sentimentScore().positive().toString());
                entities.put("Negative Score",detectSentimentResult.sentimentScore().negative().toString());
                entities.put("Neutral  Score",detectSentimentResult.sentimentScore().neutral().toString());
                entities.put("Overall Sentiment Result",detectSentimentResult.sentimentAsString());
                tweetsInJson.append(gsonObj.toJson(entities)).append(",");
            }
            tweetsInJson.replace(tweetsInJson.length() - 1, tweetsInJson.length(), "");
            tweetsInJson.append("]");
            context.getLogger().log(LOG + tweetsInJson);


            comClient.close();

            String newFileName = "analyzedtweets.json";
            AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build().putObject("sentimentanalysisb008999516", newFileName, tweetsInJson.toString());
            context.getLogger().log(LOG + ": " + "Content written to file " + newFileName + " successfully!");
            return "correct";
        } catch (UnsupportedEncodingException e) {
            context.getLogger().log(LOG + ": " + e.getMessage());
            return "error";
        }
    }
}
