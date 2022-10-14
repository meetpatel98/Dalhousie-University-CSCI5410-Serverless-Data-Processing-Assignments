package part_c_aws_s3;

import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;

import java.util.List;

public class RetriveData {
    public void retriveTableContent(String tableName){
        DynamoDBConnection dynamoDBConnection = new DynamoDBConnection();
        try {
            TableKeysAndAttributes tableKeysAndAttributes = new TableKeysAndAttributes(tableName);
            tableKeysAndAttributes.addHashOnlyPrimaryKeys("Name",
                    "Amherst Shore",
                    "Annapolis Basin Look Off",
                    "Barrachois",
                    "Black Duck Cove",
                    "Crystal Crescent Beach");
            BatchGetItemOutcome batchGetItemOutcome = dynamoDBConnection.createDynamoDBClientBuilder()
                    .batchGetItem(tableKeysAndAttributes);
            for (String name : batchGetItemOutcome.getTableItems().keySet()) {
                List<Item> items = batchGetItemOutcome.getTableItems().get(name);
                for (Item item : items) {
                    System.out.println(item.toJSONPretty());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
