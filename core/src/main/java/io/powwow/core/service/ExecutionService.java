package io.powwow.core.service;

import io.powwow.core.model.event.Event;
import io.powwow.core.model.execution.ExecutionContext;
import io.powwow.core.model.execution.StartStateMachineRequest;
import io.powwow.core.model.execution.StatusChangeResponse;

import java.util.List;
import java.util.Optional;

public interface ExecutionService {
    ExecutionContext start(StartStateMachineRequest startStateMachineRequest);

    StatusChangeResponse cancel(String correlationId);

    StatusChangeResponse pause(String correlationId);

    StatusChangeResponse unpause(String correlationId);

    Optional<ExecutionContext> findById(String correlationId);

    void applyEvent(String correlationId, Event event);

    List<ExecutionContext> findAll(int page, int size);
}
