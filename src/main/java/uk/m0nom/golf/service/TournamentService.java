package uk.m0nom.golf.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import uk.m0nom.golf.dao.TournamentDao;
import uk.m0nom.golf.domain.Tournament;

@Service
public class TournamentService {
    private final TransformService transformService;
    private final TournamentDao dao;

    public TournamentService(@Qualifier("dbBasedTransformService") TransformService transformService, TournamentDao dao) {
        this.transformService = transformService;
        this.dao = dao;
    }

    public Tournament create(Tournament tournament) {
        Tournament tournamentToPersist = transformService.transformForPersistence(tournament);
        return dao.create(tournamentToPersist);
    }

    public Tournament read(String id) {
        return dao.get(id);
    }

    public boolean deleteById(String id) {
        return dao.deleteById(id);
    }
}
