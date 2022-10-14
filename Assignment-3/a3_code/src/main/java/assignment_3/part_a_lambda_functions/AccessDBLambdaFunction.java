package assignment_3.part_a_lambda_functions;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AccessDBLambdaFunction implements RequestHandler<S3Event, String> {
    String LOG = "assignment_3.part_a_lambda_functions.AccessDBLambdaFunction";

    @Override
    public String handleRequest(S3Event s3Event, Context context) {
        String bucketName = null;
        String fileNameKey = null;
        String ENTITIES_AND_FREQUENCY_TABLE = "EntitiesAndFrequencyTable";
        String NAME_ENTITY_COLUMN = "NameEntity";
        String FREQUENCY_COLUMN = "Frequency";
        String TIMESTAMP_OF_ENTRY = "TimestampOfEntry";
        Map<String, Integer> entityAndFrequencyMap = new HashMap<>();
        try {
            bucketName = s3Event.getRecords().get(0).getS3().getBucket().getName();
            fileNameKey = URLDecoder.decode(s3Event.getRecords().get(0).getS3().getObject().getKey().replace('+', ' '), "UTF-8");

            String fileContent = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build().getObjectAsString(bucketName, fileNameKey);
            String[] entities = fileContent.split("\\{")[1].replaceAll("}", "").split(",");

            context.getLogger().log(LOG + ": " + entities);

            for (String entity : entities) {
                String entityName = entity.split(":")[0].replaceAll("\"", "").replaceAll("\\\\u0027", "'");
                Integer entityFrequency = Integer.parseInt(entity.split(":")[1]);
                entityAndFrequencyMap.put(entityName, entityFrequency);
            }

            context.getLogger().log(LOG + ": " + entityAndFrequencyMap);

            try {
                final CreateTableResult createTableResult = AmazonDynamoDBClientBuilder
                        .standard().withRegion(Regions.EU_WEST_1)
                        .build().createTable(new CreateTableRequest()
                        .withAttributeDefinitions(new AttributeDefinition(NAME_ENTITY_COLUMN, ScalarAttributeType.S))
                        .withKeySchema(new KeySchemaElement(NAME_ENTITY_COLUMN, KeyType.HASH))
                        .withProvisionedThroughput(new ProvisionedThroughput(15L, 15L))
                        .withTableName(ENTITIES_AND_FREQUENCY_TABLE));
            } catch (AmazonServiceException e) {
                context.getLogger().log(LOG + ": " + e.getMessage());
            }

            for (Map.Entry<String, Integer> entity : entityAndFrequencyMap.entrySet()) {
                Map<String, AttributeValue> columnItem = new HashMap<>();
                columnItem.put(NAME_ENTITY_COLUMN, new AttributeValue(entity.getKey()));
                columnItem.put(FREQUENCY_COLUMN, new AttributeValue(String.valueOf(entity.getValue())));
                columnItem.put(TIMESTAMP_OF_ENTRY, new AttributeValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSS-SS")
                        .format(new Date())));

                AmazonDynamoDBClientBuilder
                        .standard().withRegion(Regions.EU_WEST_1)
                        .build().putItem(ENTITIES_AND_FREQUENCY_TABLE, columnItem);

                context.getLogger().log(LOG + ": " + columnItem);
            }
            return "correct";
        } catch (UnsupportedEncodingException e) {
            return "error";
        }
    }
}
