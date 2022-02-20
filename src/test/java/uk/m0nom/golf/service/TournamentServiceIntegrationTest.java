package uk.m0nom.golf.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uk.m0nom.golf.domain.Tournament;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TournamentServiceIntegrationTest {
    private final TournamentService tournamentService;

    @Autowired
    public TournamentServiceIntegrationTest(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @Test
    public void serviceAcceptAndTransformDs1Data() {
        int source = 1;
        Tournament tournamentDs1 = new Tournament(source);

        Map<String, String> data = new HashMap<>();
        data.put("tournamentId", "174638");
        data.put("tournamentName", "Women's Open Championship");
        data.put("forecast", "fair");
        data.put("courseName", "Sunnydale Golf Course");
        data.put("countryCode", "GB");
        data.put("startDate", "09/07/21");
        data.put("endDate", "13/07/21");
        data.put("roundCount", "4");

        tournamentDs1.setData(data);

        Tournament createdTournament = tournamentService.create(tournamentDs1);

        assertNotNull(createdTournament);
        assertNotNull(createdTournament.getId());

        assertEquals(source, createdTournament.getSource());

        // Mapped & transformed Data
        assertEquals("09/07/2021", createdTournament.get("start"));
        assertEquals("13/07/2021", createdTournament.get("end"));
        assertEquals("United Kingdom", createdTournament.get("country"));

        // Mapped data
        assertEquals("174638", createdTournament.get("sourceId"));
        assertEquals("Women's Open Championship", createdTournament.get("name"));
        assertEquals("Sunnydale Golf Course", createdTournament.get("course"));
        assertEquals("4", createdTournament.get("rounds"));

        // Unmapped data
        assertEquals("fair", createdTournament.get("forecast"));

    }


    @Test
    public void serviceAcceptAndTransformDs2Data() {
        int source = 2;
        Tournament tournamentDs2 = new Tournament(source);

        Map<String, String> data = new HashMap<>();
        data.put("tournamentUUID", "southWestInvitational");
        data.put("golfCourse", "Happy Days Golf Club");
        data.put("competitionName", "South West Invitational");
        data.put("hostCountry", "United States Of America");
        data.put("epochStart", "1638349200");
        data.put("epochFinish", "1638468000");
        data.put("rounds", "2");
        data.put("playerCount", "35");

        tournamentDs2.setData(data);

        Tournament createdTournament = tournamentService.create(tournamentDs2);

        assertNotNull(createdTournament);
        assertNotNull(createdTournament.getId());

        assertEquals(source, createdTournament.getSource());

        // Mapped & transformed Data
        assertEquals("01/12/2021", createdTournament.get("start"));
        assertEquals("02/12/2021", createdTournament.get("end"));
        assertEquals("United States Of America", createdTournament.get("country"));

        // Mapped data
        assertEquals("southWestInvitational", createdTournament.get("sourceId"));
        assertEquals("South West Invitational", createdTournament.get("name"));
        assertEquals("Happy Days Golf Club", createdTournament.get("course"));
        assertEquals("2", createdTournament.get("rounds"));

        // Unmapped data
        assertEquals("35", createdTournament.get("playerCount"));

    }
}
