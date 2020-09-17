package io.powwow.application.impl;

import io.powwow.application.ValidationService;
import io.powwow.application.dto.ValidationDTO;
import io.powwow.model.StateMachineDef;
import io.powwow.model.TaskDef;
import io.powwow.model.Transition;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ValidationServiceImpl implements ValidationService {

    @Override
    public ValidationDTO validate(StateMachineDef def) {
        ValidationDTO.Builder builder = ValidationDTO.builder();
        validateFsmDef(builder, def);
        validateTransitions(builder, def);
        return builder.build();
    }

    private void validateFsmDef(ValidationDTO.Builder builder, StateMachineDef def) {
        if (def.getName() == null || def.getName().isBlank()) {
            builder.reason("State machine name cannot be null or blank");
        }

        if (def.getAcceptingStates() == null || def.getAcceptingStates().isEmpty()) {
            builder.reason("acceptingStates cannot be null or empty");
        }

        if (def.getStartingStates() == null || def.getStartingStates().isEmpty()) {
            builder.reason("startingStates cannot be null or empty");
        }
    }

    private void validateTransitions(ValidationDTO.Builder builder, List<Transition> transitions, List<TaskDef> taskDefs) {
        if (transitions == null || transitions.isEmpty()) {
            builder.reason("Transitions cannot be null or empty");
        }
        if (transitions != null) {
            final Set<String> definedTasks = taskDefs.stream()
                    .map(TaskDef::getName)
                    .collect(Collectors.toSet());

            transitions.forEach(transition -> {
                transition.getTasks().forEach(declaredTask -> {
                    if (!definedTasks.contains(declaredTask)) {
                        builder.reason("Undefined task '" + declaredTask + "'");
                    }
                });
            });

        }
    }


    private void validateTransitions(ValidationDTO.Builder builder, StateMachineDef def) {
        if (def.getTasks() == null || def.getTasks().isEmpty()) {
            builder.reason("tasks must not be null or empty");
        }

        validateTransitions(builder, def.getTransitions(), def.getTasks());

        Optional<Transition> optionalFirstInvalidTransition = def.getTransitions().stream()
                .filter(t -> isNullOrEmpty(t.getFromState()) || isNullOrEmpty(t.getToState()) || isNullOrEmpty(t.getEvent()))
                .findFirst();

        if (optionalFirstInvalidTransition.isPresent()) {
            validateIndividualTransition(builder, optionalFirstInvalidTransition.get());
            return;
        }

        validateTransitionGraph(builder, def);
    }

    private void validateTransitionGraph(ValidationDTO.Builder builder, StateMachineDef def) {
        Map<String, List<String>> g = toAdjacencyList(def.getTransitions());

        // Lets test connectivity of each (start, terminal) state pair
        Set<String> allVisitedNodes = new HashSet<>();
        for (String startState : def.getStartingStates()) {
            for (String acceptState : def.getAcceptingStates()) {
                validateIsConnected(builder, allVisitedNodes, g, startState, acceptState);
            }
        }

        final List<String> nonVisitedStates = getDistinctStates(def)
                .stream()
                .filter(state -> !allVisitedNodes.contains(state))
                .collect(Collectors.toList());
        if (!nonVisitedStates.isEmpty()) {
            builder.reason("States: " + nonVisitedStates.toString() + " are not reachable");
        }
    }

    private Set<String> getDistinctStates(StateMachineDef def) {
        Set<String> states = new LinkedHashSet<>();
        for (Transition t : def.getTransitions()) {
            states.add(t.getFromState());
            states.add(t.getToState());
        }
        return states;
    }

    private void validateIsConnected(ValidationDTO.Builder builder, Set<String> allVisitedStates, Map<String, List<String>> g, String startState, String acceptState) {
        Set<String> visited = new HashSet<>();

        Deque<String> q = new LinkedList<>();
        q.offer(startState);
        while (!q.isEmpty()) {
            String intermediateState = q.poll();
            visited.add(intermediateState);
            allVisitedStates.add(intermediateState);

            if (intermediateState.equals(acceptState)) {
                return;
            }
            for (String connectedState : g.getOrDefault(intermediateState, List.of())) {
                if (!visited.contains(connectedState)) {
                    q.offer(connectedState);
                }
            }
        }

        builder.reason("state[" + acceptState + "] is not reachable from state[" + startState + "]");
    }

    private Map<String, List<String>> toAdjacencyList(List<Transition> transitions) {
        Map<String, List<String>> g = new LinkedHashMap<>();
        for (Transition transition : transitions) {
            addEdge(g, transition);
        }
        return g;
    }

    private void addEdge(Map<String, List<String>> g, Transition transition) {
        g.computeIfAbsent(transition.getFromState(), k -> new ArrayList<>()).add(transition.getToState());
    }

    private void validateIndividualTransition(ValidationDTO.Builder builder, Transition transition) {
        if (isNullOrEmpty(transition.getFromState())) {
            builder.reason("fromState of a transition must not be null or blank");
        }

        if (isNullOrEmpty(transition.getToState())) {
            builder.reason("toState for a transition must not be null or blank");
        }

        if (isNullOrEmpty(transition.getEvent())) {
            builder.reason("event for a transition must not be null or blank");
        }
    }

    private static boolean isNullOrEmpty(String seq) {
        return seq == null || seq.isBlank();
    }


}
