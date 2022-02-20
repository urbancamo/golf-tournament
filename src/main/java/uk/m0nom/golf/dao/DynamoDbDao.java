package uk.m0nom.golf.dao;

import software.amazon.awssdk.services.dynamodb.model.*;
import uk.m0nom.golf.dynamodb.ConfiguredDynamoDbClient;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DynamoDbDao {
    protected final ConfiguredDynamoDbClient dbClient;

    public DynamoDbDao(ConfiguredDynamoDbClient dbClient) {
        this.dbClient = dbClient;
    }

    protected String getValueFromTable(String tableName, String keyName, String keyValue, String valueName) {
        Map<String, AttributeValue> keyMap = new HashMap<>();
        keyMap.put(keyName, AttributeValue.builder().s(keyValue).build());
        Map<String, AttributeValue> valueMap;
        valueMap = dbClient.getClient().getItem(
                GetItemRequest.builder()
                        .tableName(tableName)
                        .key(keyMap)
                        .consistentRead(true)
                        .build()).item();
        AttributeValue value = valueMap.get(valueName);
        if (value != null) {
            return value.s();
        }
        return null;
    }

    protected void putItem(Map<String, String> values) {
        Map<String, AttributeValue> itemValues = new HashMap<>();
        for (String key : values.keySet()) {
            String value = values.get(key);
            itemValues.put(key, AttributeValue.builder().s(value).build());
        }

        PutItemRequest request = PutItemRequest.builder()
                .tableName("tournament")
                .item(itemValues)
                .build();
        try {
            dbClient.putItem(request);
            System.out.println("tournament" +" was successfully updated");

        } catch (ResourceNotFoundException e) {
            System.err.format("Error: The Amazon DynamoDB table \"%s\" can't be found.\n", "tournament");
            System.err.println("Be sure that it exists and that you've typed its name correctly!");
            System.exit(1);
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    public Map<String,String> getItem(String tableName, String key, String keyVal ) {
        Map<String, String> items = new HashMap<>();

        HashMap<String,AttributeValue> keyToGet = new HashMap<>();

        keyToGet.put(key, AttributeValue.builder()
                .s(keyVal).build());

        GetItemRequest request = GetItemRequest.builder()
                .key(keyToGet)
                .tableName(tableName)
                .build();

        try {
            GetItemResponse response = dbClient.getItem(request);
            if (response.hasItem()) {
                Map<String, AttributeValue> returnedItem = response.item();

                if (returnedItem != null) {
                    Set<String> keys = returnedItem.keySet();
                    for (String rtnKey : keys) {
                        items.put(rtnKey, returnedItem.get(rtnKey).s());
                    }

                    return items;
                }
            }
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return null;
    }

    public int deleteItem(String tableName, String key, String keyVal) {
        Map<String, AttributeValue> keyOfItemToDelete = new HashMap<>();
        keyOfItemToDelete.put(key, AttributeValue.builder().s(keyVal).build());
        DeleteItemResponse response = dbClient.deleteItem(DeleteItemRequest.builder().tableName(tableName).key(keyOfItemToDelete).build());
        return response.sdkHttpResponse().statusCode();
    }
}
