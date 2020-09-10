package io.powwow.application.impl;

import io.powwow.adapter.out.persistence.dao.AutomataDao;
import io.powwow.application.AutomataService;
import io.powwow.domain.Automata;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AutomataServiceImpl implements AutomataService {
    private final AutomataDao automataDao;

    @Override
    public Optional<Automata> findById(Long id) {
        return Optional.empty();
    }
}
