package io.powwow.core.service.impl;

import io.powwow.core.dao.DefinitionDAO;
import io.powwow.core.model.ImmutableStateMachineDef;
import io.powwow.core.model.StateMachineDef;
import io.powwow.core.model.Subscriber;
import io.powwow.core.service.DefinitionService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class DefinitionServiceImpl implements DefinitionService {
    private final DefinitionDAO definitionDAO;

    public DefinitionServiceImpl(DefinitionDAO definitionDAO) {
        this.definitionDAO = definitionDAO;
    }

    @Override
    public StateMachineDef create(StateMachineDef stateMachineDef) {
        return definitionDAO.insert(stateMachineDef);
    }

    @Override
    public StateMachineDef addSubscriber(String fsmId, Subscriber subscriber) {
        return definitionDAO.withinTransaction(th -> {
            StateMachineDef def = definitionDAO.findById(fsmId).orElseThrow();
            return definitionDAO.save(ImmutableStateMachineDef.builder()
                    .from(def)
                    .addSubscribers(subscriber)
                    .build());
        });
    }

    @Override
    public StateMachineDef addSubscribers(String fsmId, Collection<Subscriber> subscriber) {
        return definitionDAO.withinTransaction(th -> {
            StateMachineDef def = definitionDAO.findById(fsmId).orElseThrow();
            return definitionDAO.save(ImmutableStateMachineDef.builder()
                    .from(def)
                    .addAllSubscribers(subscriber)
                    .build());
        });
    }

    @Override
    public StateMachineDef removeSubscriber(String fsmId, String name) {
        return definitionDAO.withinTransaction(th -> {
            StateMachineDef def = definitionDAO.findById(fsmId).orElseThrow();
            final List<Subscriber> subscribers = def.getSubscribers();
            if (subscribers != null && !subscribers.isEmpty()) {
                return definitionDAO.save(ImmutableStateMachineDef.builder()
                        .from(def)
                        .addAllSubscribers(filterBy(subscribers, subs -> !subs.getName().equals(name)))
                        .build());
            }
            return def;
        });
    }

    private <T> Collection<T> filterBy(Collection<T> collection, Predicate<T> pred) {
        return collection.stream()
                .filter(pred)
                .collect(Collectors.toList());
    }

    @Override
    public StateMachineDef removeSubscribers(String fsmId, Collection<String> names) {
        // Set of subscribers to be removed
        HashSet<String> subscribersToRemove = new HashSet<>(names);

        return definitionDAO.withinTransaction(th -> {
            StateMachineDef def = definitionDAO.findById(fsmId).orElseThrow();
            final List<Subscriber> subscribers = def.getSubscribers();
            if (subscribers != null && !subscribers.isEmpty()) {
                return definitionDAO.save(ImmutableStateMachineDef.builder()
                        .from(def)
                        .addAllSubscribers(filterBy(subscribers, subs -> !subscribersToRemove.contains(subs.getName())))
                        .build());
            }
            return def;
        });
    }

    @Override
    public Optional<StateMachineDef> findById(String fsmId) {
        return definitionDAO.findById(fsmId);
    }

    @Override
    public void deleteById(String fsmId) {
        definitionDAO.deleteById(fsmId);
    }

    @Override
    public List<StateMachineDef> findAll(int page, int size) {
        return definitionDAO.findAll(page, size);
    }
}
