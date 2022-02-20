package uk.m0nom.golf.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
public class TournamentDaoIntegrationTest {
    private final TournamentDao tournamentDao;

    @Autowired
    public TournamentDaoIntegrationTest(TournamentDao tournamentDao) {
        this.tournamentDao = tournamentDao;
    }

}
