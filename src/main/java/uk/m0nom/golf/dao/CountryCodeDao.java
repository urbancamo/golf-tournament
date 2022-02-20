package uk.m0nom.golf.dao;

import org.springframework.stereotype.Repository;
import uk.m0nom.golf.dynamodb.ConfiguredDynamoDbClient;

@Repository
public class CountryCodeDao extends DynamoDbDao {
    public CountryCodeDao(ConfiguredDynamoDbClient client) {
        super(client);
    }

    public String getCountryNameFromCode(String code) {
        return getValueFromTable("countrycodes", "Code", code, "Name");
    }
}
