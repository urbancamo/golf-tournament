package uk.m0nom.golf.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import uk.m0nom.golf.domain.Tournament;
import uk.m0nom.golf.service.TournamentService;
import uk.m0nom.golf.util.ResourceUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@RunWith(SpringRunner.class)
@WebMvcTest(TournamentRestController.class)
public class TournamentRestControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ResourceLoader resourceLoader;

    @MockBean
    private TournamentService tournamentService;


    private Tournament createTournament(int source) {
        String uuid = UUID.randomUUID().toString();

        return createExpectedResult(source, uuid);
    }

    @Test
    public void testPutOkDs1() throws Exception {
        int source = 1;
        String jsonPutData = ResourceUtils.readResource(resourceLoader, String.format("json/tournamentDs%d.json", source));
        Tournament createdTournament = createTournament(source);

        // Test Put
        String expectedUri = createPutExpectedUri(createdTournament);
        when(tournamentService.create(any(Tournament.class))).thenReturn(createdTournament);
        MvcResult mvcResult = mvc.perform(post("/tournament/")
                .header("source_id", String.format("%d", 1))
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPutData)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
        String uri = mvcResult.getResponse().getHeader("Location");
        assertEquals(expectedUri, uri);
    }

    @Test
    public void testPutOkDs2() throws Exception {
        int source = 2;
        String jsonPutData = ResourceUtils.readResource(resourceLoader, String.format("json/tournamentDs%d.json", source));
        Tournament createdTournament = createTournament(source);

        // Test Put
        String expectedUri = createPutExpectedUri(createdTournament);
        when(tournamentService.create(any(Tournament.class))).thenReturn(createdTournament);
        MvcResult mvcResult = mvc.perform(post("/tournament/")
                .header("source_id", String.format("%d", 2))
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPutData)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
        String uri = mvcResult.getResponse().getHeader("Location");
        assertEquals(expectedUri, uri);
    }

    @Test
    public void testGetOk() throws Exception {
        Tournament createdTournament = createTournament(1);
        when(tournamentService.read(createdTournament.getId())).thenReturn(createdTournament);

        MvcResult mvcResult = mvc.perform(get(String.format("/tournament/%s", createdTournament.getId()))).andReturn();
        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(200, status);

        String expectedContent = createGetExpectedJson(createdTournament);
        assertEquals(expectedContent, content);
    }

    @Test
    public void testGetNotFound() throws Exception {
        Tournament createdTournament = createTournament(1);
        when(tournamentService.read(createdTournament.getId())).thenReturn(null);

        MvcResult mvcResult = mvc.perform(get(String.format("/tournament/%s", createdTournament.getId()))).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
    }

    @Test
    public void testDeleteOk() throws Exception {
        Tournament createdTournament = createTournament(1);
        when(tournamentService.deleteById(createdTournament.getId())).thenReturn(200);

        MvcResult mvcResult = mvc.perform(delete(String.format("/tournament/%s", createdTournament.getId()))).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        Tournament createdTournament = createTournament(1);
        when(tournamentService.deleteById(createdTournament.getId())).thenReturn(400);

        MvcResult mvcResult = mvc.perform(delete(String.format("/tournament/%s", createdTournament.getId()))).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    private Tournament createExpectedResult(int source, String uuid) {
        Tournament tournament = new Tournament(source);
        Map<String, String> tournamentData = new HashMap<>();
        tournamentData.put(Tournament.ID_KEYNAME, uuid);
        tournament.setData(tournamentData);
        return tournament;
    }

    private String createPutExpectedUri(Tournament tournament) {
        return String.format("http://localhost/tournament/%s", tournament.getId());
    }

    private String createGetExpectedJson(Tournament tournament) {
        return String.format("{\"source\":%d,\"data\":{\"id\":\"%s\"},\"id\":\"%s\"}", tournament.getSource(), tournament.getId(), tournament.getId());
    }
}


