package uk.m0nom.golf.dao;

import org.springframework.stereotype.Repository;
import uk.m0nom.golf.domain.Transform;
import uk.m0nom.golf.dynamodb.ConfiguredDynamoDbClient;


@Repository
public class TransformDao extends DynamoDbDao {
    public TransformDao(ConfiguredDynamoDbClient dbClient) {
        super(dbClient);
    }

    public Transform getTransform(Integer source, String key) {
        String mappedKey = getValueFromTable(String.format("ingestmap%d", source), "key", key, "value");
        if (mappedKey != null) {
            String transform = getValueFromTable(String.format("ingestmap%d", source), "key", key, "transform");
            return new Transform(source, key, mappedKey, transform);
        }
        return null;
    }
}
