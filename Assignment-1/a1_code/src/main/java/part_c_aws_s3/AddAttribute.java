package part_c_aws_s3;

import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddAttribute {
    DynamoDBConnection dynamoDBConnection;
    List<Map<String, Object>> items;
    public AddAttribute() {
        dynamoDBConnection = new DynamoDBConnection();
        items = new ArrayList<>();
    }

    public void createNewAttibute(String tableName) {
        Table table = dynamoDBConnection.createDynamoDBClientBuilder().getTable(tableName);

        try {
            for (Item dynamoDBItem: table.scan()){
                Map<String, Object> map = new HashMap<>();
                map.put("Name", dynamoDBItem.asMap().get("Name"));
                addTypeOfCampsites(dynamoDBItem, map);
                items.add(map);
            }
            updateAttributetoItem(table);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void addTypeOfCampsites(Item dynamoDBItem, Map<String, Object> map) {
        if (dynamoDBItem.asMap().get("Name").toString().toLowerCase().contains(
                "Amherst Shore".toLowerCase())) {
            map.put("types_of_Campsites", "Tent Camping");
        } else if (dynamoDBItem.asMap().get("Name").toString().toLowerCase().contains(
                "Annapolis Basin Look Off".toLowerCase())) {
            map.put("types_of_Campsites", "Backpacking/Hiking Camping");
        } else if (dynamoDBItem.asMap().get("Name").toString().toLowerCase().contains(
                "Barrachois".toLowerCase())) {
            map.put("types_of_Campsites", "Car Camping");
        } else if (dynamoDBItem.asMap().get("Name").toString().toLowerCase().contains(
                "Black Duck Cove".toLowerCase())) {
            map.put("types_of_Campsites", "RV/Van Camping");
        } else if (dynamoDBItem.asMap().get("Name").toString().toLowerCase().contains(
                "Crystal Crescent Beach".toLowerCase())) {
            map.put("types_of_Campsites", "Survival Camping");
        } else {
            map.put("types_of_Campsites", "");
        }
    }
    private void updateAttributetoItem(Table table) {
        for (Map<String, Object> map : items) {
            if (map.get("types_of_Campsites").toString().length() > 1) {
                UpdateItemSpec updateItemSpec =
                        new UpdateItemSpec().withPrimaryKey("Name",
                                        map.get("Name").toString())
                                .withUpdateExpression("set #keyAttribute = :valueAttribute")
                                .withNameMap(new NameMap()
                                        .with("#keyAttribute", "types_of_Campsites"))
                                .withValueMap(new ValueMap()
                                        .withString(":valueAttribute", map.get("types_of_Campsites")
                                                .toString())).withReturnValues(ReturnValue.ALL_NEW);
                table.updateItem(updateItemSpec);
            }
        }
    }
}
