package passwordmanager.backend.remote.dynamo;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import com.amazonaws.services.dynamodbv2.model.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import passwordmanager.backend.EntryFields;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DynamoAPITests {

    private static DynamoDBProxyServer server;
    private static AmazonDynamoDB client;
    private static DynamoDB dynamoDB;

    @BeforeAll
    public static void setup() throws Exception {

        // Server setup
        System.setProperty("sqlite4java.library.path", "native-libs");
        String port = "8000";
        server = ServerRunner.createServerFromCommandLineArgs(
                new String[]{"-inMemory", "-port", port});
        server.start();

        // Credentials required to access server
        BasicAWSCredentials credentials = new BasicAWSCredentials("keyId", "secretId");

        client = AmazonDynamoDBClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
                .build();

        dynamoDB = new DynamoDB(client);

    }

    @Test @Order(0)
    void createTable() throws Exception {

        // Define attribute definitions
        // These are defined in DynamoDBEntryAttributes.java as well

        AttributeDefinition titleAttr = new AttributeDefinition()
                .withAttributeName(EntryFields.TITLE.toString())
                .withAttributeType(ScalarAttributeType.S);

        AttributeDefinition emailAttr = new AttributeDefinition()
                .withAttributeName(EntryFields.EMAIL.toString())
                .withAttributeType(ScalarAttributeType.S);

        AttributeDefinition secondaryEmailAttr = new AttributeDefinition()
                .withAttributeName(EntryFields.SECONDARY_EMAIL.toString())
                .withAttributeType(ScalarAttributeType.S);

        AttributeDefinition passwordAttr = new AttributeDefinition()
                .withAttributeName(EntryFields.PASSWORD.toString())
                .withAttributeType(ScalarAttributeType.S);

        AttributeDefinition usernameAttr = new AttributeDefinition()
                .withAttributeName(EntryFields.USERNAME.toString())
                .withAttributeType(ScalarAttributeType.S);

        AttributeDefinition phoneNumberAttr = new AttributeDefinition()
                .withAttributeName(EntryFields.PHONE_NUMBER.toString())
                .withAttributeType(ScalarAttributeType.S);

        AttributeDefinition linkAttr = new AttributeDefinition()
                .withAttributeName(EntryFields.LINK.toString())
                .withAttributeType(ScalarAttributeType.S);

        AttributeDefinition categoryAttr = new AttributeDefinition()
                .withAttributeName(EntryFields.CATEGORY.toString())
                .withAttributeType(ScalarAttributeType.S);

        AttributeDefinition dateCreatedAttr = new AttributeDefinition()
                .withAttributeName(EntryFields.DATE_CREATED.toString())
                .withAttributeType(ScalarAttributeType.S);

        AttributeDefinition dateModifiedAttr = new AttributeDefinition()
                .withAttributeName(EntryFields.DATE_MODIFIED.toString())
                .withAttributeType(ScalarAttributeType.S);

        // Define key schema
        KeySchemaElement keySchema = new KeySchemaElement()
                .withAttributeName(EntryFields.TITLE.toString())
                .withKeyType(KeyType.HASH);  // Partition key

        // Define provisioned throughput
        ProvisionedThroughput throughput = new ProvisionedThroughput()
                .withReadCapacityUnits(5L)
                .withWriteCapacityUnits(5L);

        // Create table request
        CreateTableRequest createTableRequest = new CreateTableRequest()
                .withTableName("Entries")
                .withKeySchema(keySchema)
                .withAttributeDefinitions(
                        titleAttr
                )
                .withProvisionedThroughput(throughput);

        // Create table
        Table table = dynamoDB.createTable(createTableRequest);
        table.waitForActive();

        // Checking if table was created (should be one)
        TableCollection<ListTablesResult> tables = dynamoDB.listTables();
        for (Page<Table, ListTablesResult> page : tables.pages()) {

            // 1 table
            assertEquals(1, page.size());

            // Iterator iterates through table objects
            var createdTable = page.iterator().next();

            // Checks if table name is correct
            assertEquals("Entries", createdTable.getTableName());

        }
    }

    @AfterAll
    public static void teardown() throws Exception {
        server.stop();
    }

}
