package uk.m0nom.golf.api;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
    @ApiOperation(value = "Upload tournament data", nickname = "Tournament Upload")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Tournament uploaded, UUID for data returned"),
            @ApiResponse(code = 400, message = "Tournament data invalid"),
            @ApiResponse(code = 503, message = "Service unavailable, try again later")
    })
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

    @GetMapping(path = "/{id}", produces = "application/json")
    @ApiOperation(value = "Retrieve tournament data", nickname = "Tournament Download")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Tournament data retrieved"),
            @ApiResponse(code = 400, message = "Tournament UUID invalid"),
            @ApiResponse(code = 503, message = "Service unavailable, try again later")
    })
    public ResponseEntity<Tournament> read(@PathVariable("id") String uuid) {
        Tournament foundTournament = tournamentService.read(uuid);
        if (foundTournament == null) {
            return notFound().build();
        } else {
            return ok(foundTournament);
        }
    }

    @DeleteMapping(path = "/{id}", produces = "application/json")
    @GetMapping(path = "/{id}", produces = "application/json")
    @ApiOperation(value = "Delete tournament data", nickname = "Tournament Download")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Tournament data for UUID deleted"),
            @ApiResponse(code = 400, message = "Tournament UUID invalid"),
            @ApiResponse(code = 503, message = "Service unavailable, try again later")
    })
    public ResponseEntity<Tournament> delete(@PathVariable String id) {
        if (tournamentService.deleteById(id)) {
            return ok().build();
        } else {
            return notFound().build();
        }
    }
}
