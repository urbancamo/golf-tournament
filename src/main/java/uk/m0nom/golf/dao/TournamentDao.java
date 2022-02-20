package uk.m0nom.golf.dao;

import org.springframework.stereotype.Repository;
import uk.m0nom.golf.domain.Tournament;
import uk.m0nom.golf.dynamodb.ConfiguredDynamoDbClient;

import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import static java.lang.String.format;

@Repository
public class TournamentDao extends DynamoDbDao {
    private static final Logger logger = Logger.getLogger(TournamentDao.class.getName());

    public TournamentDao(ConfiguredDynamoDbClient dbClient) {
        super(dbClient);
    }

    public Tournament create(Tournament tournament) {
        UUID uuid = UUID.randomUUID();
        tournament.put(Tournament.ID_KEYNAME, uuid.toString());
        tournament.put(Tournament.SOURCE_KEYNAME, format("%d", tournament.getSource()));
        logger.info(String.format("Persisting tournament %s", uuid));

        putItem(tournament.getData());
        return tournament;
    }

    public Tournament get(String uuid) {
        Map<String, String> items = getItem("tournament", Tournament.ID_KEYNAME, uuid);
        return new Tournament(items);
    }

    public boolean deleteById(String uuid) {
        return false;
    }
}
