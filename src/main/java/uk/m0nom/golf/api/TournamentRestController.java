package uk.m0nom.golf.api;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uk.m0nom.golf.domain.Tournament;
import uk.m0nom.golf.service.TournamentService;

import java.net.URI;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping(path = "/tournament")
public class TournamentRestController {

    private static final Logger logger = Logger.getLogger(TournamentRestController.class.getName());

    private final TournamentService tournamentService;

    public TournamentRestController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @PostMapping(path = "/put", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Tournament> create(@RequestHeader("source_id") int sourceId, @RequestBody Map<String, String> tournamentData) {
        logger.log(Level.INFO, tournamentData.toString());
        Tournament tournament = new Tournament(sourceId, tournamentData);
        Tournament createdTournament = tournamentService.create(tournament);
        if (createdTournament == null) {
            return notFound().build();
        } else {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdTournament.getId()).toUri();
            return created(uri).body(createdTournament);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tournament> read(@PathVariable("id") String uuid) {
        Tournament foundTournament = tournamentService.read(uuid);
        if (foundTournament == null) {
            return notFound().build();
        } else {
            return ok(foundTournament);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Tournament> delete(@PathVariable String id) {
        if (tournamentService.deleteById(id)) {
            return ok().build();
        } else {
            return notFound().build();
        }
    }
}
