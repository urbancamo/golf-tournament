package uk.m0nom.golf.dynamodb;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;
import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import org.apache.commons.cli.ParseException;
import org.junit.Ignore;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


//@SpringBootTest
public class ProgrammaticStartupAndShutdown {

    private static AmazonDynamoDB dynamoDb;
    private static DynamoDBProxyServer server;

    //@BeforeAll
    public static void startupDynamoDbEmbedded() {
        // Create an in-memory and in-process instance of DynamoDB Local that skips HTTP
        dynamoDb = DynamoDBEmbedded.create().amazonDynamoDB();

        final String[] localArgs = {"-inMemory"};
        try {
            server = ServerRunner.createServerFromCommandLineArgs(localArgs);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        dynamoDb = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(
                        // we can use any region here
                        new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
                .build();
    }

    //@AfterAll
    public static void shutdownDynamoDbEmbedded() {
        if (dynamoDb != null) {
            try {
                server.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
            dynamoDb.shutdown();
        }

    }

    //@Test
    public void testEmbeddedDynamoDbIntegrationTest() throws Exception {
        listTables(dynamoDb.listTables(), "DynamoDB Local over HTTP");
    }

    private void listTables(ListTablesResult listTables, String dbName) {

    }
}
