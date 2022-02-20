package uk.m0nom.golf.service;

import uk.m0nom.golf.domain.Tournament;

public interface TransformService {
    Tournament transformForPersistence(Tournament tournament);
}
