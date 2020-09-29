package io.powwow.core.service.impl;

import io.powwow.core.dao.ExecutionDAO;
import io.powwow.core.model.StateMachineDef;
import io.powwow.core.model.Transition;
import io.powwow.core.model.event.Event;
import io.powwow.core.model.execution.*;
import io.powwow.core.service.DefinitionService;
import io.powwow.core.service.ExecutionService;
import io.powwow.core.service.NotificationManager;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExecutionServiceImpl implements ExecutionService {
    private final DefinitionService definitionService;
    private final NotificationManager notificationManager;
    private final ExecutionDAO executionDAO;

    public ExecutionServiceImpl(DefinitionService definitionService, NotificationManager notificationManager, ExecutionDAO executionDAO) {
        this.definitionService = definitionService;
        this.notificationManager = notificationManager;
        this.executionDAO = executionDAO;
    }

    @Override
    public ExecutionContext start(StartStateMachineRequest request) {
        if (request.getCorrelationId() != null && request.getCorrelationId().isBlank()) {
            throw new IllegalArgumentException("Correlation Id cannot be blank. Set it null to auto-generate");
        }
        if (request.getFsmId() == null) {
            throw new IllegalArgumentException("FSM ID cannot be null");
        }
        StateMachineDef stateMachineDef = definitionService.findById(request.getFsmId()).orElseThrow(() -> new IllegalArgumentException("No FSM with ID: " + request.getFsmId()));
        ExecutionContext executionContext = buildExecutionContext(stateMachineDef, generateIfNull(request.getCorrelationId()), emptyIfNull(request.getInitialInput()));
        return executionDAO.insert(executionContext);
    }

    private Map<String, ?> emptyIfNull(Map<String, ?> initialInput) {
        return initialInput == null ? Map.of() : new HashMap<>(initialInput);
    }

    private ImmutableExecutionContext buildExecutionContext(StateMachineDef stateMachineDef, String correlationId, Map<String, ?> initialInput) {
        return ImmutableExecutionContext.builder()
                .stateMachineDef(stateMachineDef)
                .correlationId(correlationId)
                .executionStatus(ExecutionStatusType.RUNNING)
                .context(initialInput)
                .state(stateMachineDef.getStartingState())
                .started(new Date())
                .build();
    }

    private String generateIfNull(String correlationId) {
        return correlationId == null ? UUID.randomUUID().toString() : correlationId;
    }

    @Override
    public StatusChangeResponse cancel(String correlationId) {

        return null;
    }

    @Override
    public StatusChangeResponse pause(String correlationId) {
        return null;
    }

    @Override
    public StatusChangeResponse unpause(String correlationId) {
        return null;
    }

    @Override
    public Optional<ExecutionContext> findById(String correlationId) {
        return executionDAO.findById(correlationId);
    }

    @Override
    public void applyEvent(String correlationId, Event event) {
        if (correlationId == null || correlationId.isBlank()) {
            throw new IllegalArgumentException("Correlation ID cannot be null or empty");
        }

        if (event == null) {
            throw new IllegalArgumentException("Event cannot be null");
        }
        ExecutionContext executionContext = executionDAO.findById(correlationId).orElseThrow(() -> new IllegalArgumentException("No execution context corresponding for Correlation ID: " + correlationId));
        applyEvent(executionContext, event);
    }

    @Override
    public List<ExecutionContext> findAll(int page, int size) {
        return executionDAO.findAll(page, size);
    }

    private void applyEvent(ExecutionContext executionContext, Event event) {
        if (!canApply(executionContext)) {
            return;
        }

        executionContext.getStateMachineDef().getTransitions()
                .stream()
                .filter(t -> t.getFromState().equals(executionContext.getState()))
                .filter(t -> t.getEvent().equals(event.getName()))
                .findFirst()
                .ifPresent(transition -> {
                    applyTransition(executionContext, transition, event);
                    notificationManager.send(executionContext, transition);
                });
    }

    private boolean canApply(ExecutionContext executionContext) {
        return executionContext.getExecutionStatus() == ExecutionStatusType.RUNNING;
    }

    private void applyTransition(ExecutionContext executionContext, Transition transition, Event event) {
        ExecutionContext updatedExecutionContext = ImmutableExecutionContext.builder()
                .from(executionContext)
                .state(transition.getToState())
                .context(mergeContext(executionContext.getContext(), event.getContext()))
                .lastUpdated(new Date())
                .executionStatus(decideExecutionStatus(executionContext.getStateMachineDef(), transition))
                .addTransitionHistory(ImmutableTransitionHistoryElement.builder()
                        .fromState(transition.getFromState())
                        .toState(transition.getToState())
                        .event(event.getName())
                        .timestamp(new Date())
                        .build())
                .build();
        executionDAO.save(updatedExecutionContext);
    }

    private ExecutionStatusType decideExecutionStatus(StateMachineDef stateMachineDef, Transition transition) {
        if (stateMachineDef.getAcceptingStates().contains(transition.getToState())) {
            return ExecutionStatusType.COMPLETED;
        }
        return ExecutionStatusType.RUNNING;
    }

    private Map<String, Object> mergeContext(Map<String, Object> context, Map<String, Object> eventContext) {
        if (eventContext == null || eventContext.isEmpty()) {
            return context;
        }
        HashMap<String, Object> updatedContext = new HashMap<>(context);
        updatedContext.putAll(eventContext);
        return updatedContext;
    }
}
