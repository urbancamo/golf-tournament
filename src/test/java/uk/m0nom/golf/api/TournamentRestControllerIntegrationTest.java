package uk.m0nom.golf.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import uk.m0nom.golf.domain.Tournament;
import uk.m0nom.golf.service.TournamentService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TournamentRestController.class)
public class TournamentRestControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TournamentService service;

    @Test
    public void givenTournament_whenGetTournament_thenReturnJsonArray()
            throws Exception {

        Tournament t1 = new Tournament(1);
        String guid = "1234-5678-1234-1234";

        given(service.read(guid)).willReturn(t1);

        mvc.perform(get("/tournament")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //        .andExpect(jsonPath("$", hasSize(1)))
        //        .andExpect(jsonPath("$[0].name", is(t1.getData().get("name")));
    }
}


