import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.AmazonSNSException;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.amazonaws.services.sns.model.ListTopicsRequest;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.Topic;
import java.util.List;

public final class HalifaxCarsLambda implements RequestHandler<SQSEvent, Void> {
    @Override
    public Void handleRequest(final SQSEvent sqsEvent,
                              final Context context) {
        StringBuilder emailContent = new StringBuilder();
        for (SQSEvent.SQSMessage sqsMessage : sqsEvent.getRecords()) {
            emailContent.append("List of orders that are prepared and ready for delivery")
                    .append("\n\n")
                    .append(sqsMessage.getBody())
                    .append("\n\n");;
        }
        context.getLogger().log(HalifaxCarsLambda.class.getSimpleName() + " : " + emailContent);
        try {
            String SNSTopicARN = null;
            boolean completed = false;
            List<Topic> topics = AmazonSNSClientBuilder.defaultClient().listTopics(new ListTopicsRequest()).getTopics();
            for (Topic topic : topics) {
                String[] topicARNSplit = topic.getTopicArn().split(":");
                if (topicARNSplit[topicARNSplit.length - 1].equals("HalifaxCarsSNS")) {
                    SNSTopicARN = topic.getTopicArn();
                    completed = true;
                    break;
                }
            }
            SNSTopicARN = checkStatus(SNSTopicARN, completed);
            if (SNSTopicARN == null) return null;
            PublishRequest request = new PublishRequest();
            request.setMessage(emailContent.toString());
            request.setTopicArn(SNSTopicARN);
            context.getLogger().log(HalifaxCarsLambda.class.getSimpleName() + " : " +
                    AmazonSNSClientBuilder.defaultClient().publish(request)
                            .getMessageId() + " Message sent SuccessFully. " +
                    "Status is " + AmazonSNSClientBuilder.defaultClient().publish(request).getSdkHttpMetadata().getHttpStatusCode());
        } catch (AmazonSNSException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String checkStatus(String SNSTopicARN, boolean completed) {
        if (!completed) {
            CreateTopicResult result1 = AmazonSNSClientBuilder.defaultClient().createTopic(new CreateTopicRequest().withName("HalifaxCarsSNS"));
            SNSTopicARN = result1.getTopicArn();
        }
        if (SNSTopicARN == null) {
            return null;
        }
        return SNSTopicARN;
    }
}