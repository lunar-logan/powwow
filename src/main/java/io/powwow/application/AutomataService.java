package io.powwow.application;

import io.powwow.domain.Automata;

import java.util.Optional;

public interface AutomataService {
    Optional<Automata> findById(Long id);
}
