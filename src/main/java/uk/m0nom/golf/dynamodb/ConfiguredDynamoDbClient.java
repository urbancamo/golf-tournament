package uk.m0nom.golf.dynamodb;

import lombok.Getter;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.net.URI;

@Component
@Getter
public class ConfiguredDynamoDbClient {
    private final DynamoDbConfig config;
    private final DynamoDbClient client;

    public ConfiguredDynamoDbClient(DynamoDbConfig config) {
        this.config = config;
        client = build();
    }

    public DynamoDbClient build() {
        return DynamoDbClient.builder()
                .endpointOverride(URI.create(config.getUri()))
                // The region is meaningless for local DynamoDb but required for client builder validation
                .region(Region.EU_WEST_1)
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(config.getAccessKeyId(), config.getSecretAccessKey())))
                .build();
    }

    public void putItem(PutItemRequest request) {
        client.putItem(request);
    }

    public GetItemResponse getItem(GetItemRequest request) {
        return client.getItem(request);
    }

    public DeleteItemResponse deleteItem(DeleteItemRequest request) {
        return client.deleteItem(request);
    }
}
