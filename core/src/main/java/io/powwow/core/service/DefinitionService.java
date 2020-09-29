package io.powwow.core.service;

import io.powwow.core.model.StateMachineDef;
import io.powwow.core.model.Subscriber;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface DefinitionService {
    StateMachineDef create(StateMachineDef stateMachineDef);

    StateMachineDef addSubscriber(String fsmId, Subscriber subscriber);

    StateMachineDef addSubscribers(String fsmId, Collection<Subscriber> subscriber);

    StateMachineDef removeSubscriber(String fsmId, String name);

    StateMachineDef removeSubscribers(String fsmId, Collection<String> names);

    Optional<StateMachineDef> findById(String fsmId);

    void deleteById(String fsmId);

    List<StateMachineDef> findAll(int page, int size);
}
