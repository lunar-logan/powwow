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
        validateTransitions(builder, def);
        return builder.build();
    }

    private void validateTransitions(ValidationDTO.Builder builder, List<Transition> transitions, List<TaskDef> taskDefs) {
        final Set<String> definedTasks = getDistinctTasks(taskDefs);

        transitions.stream()
                .filter(transition -> transition.getTask() != null) // task could be null
                .forEach(transition -> {
                    if (!definedTasks.contains(transition.getTask())) {
                        builder.reason(String.format("Undefined task [%s] in transition[%s -> %s]", transition.getTask(), transition.getFromState(), transition.getToState()));
                    }
                });
    }

    private Set<String> getDistinctTasks(List<TaskDef> taskDefs) {
        return taskDefs.stream()
                .map(TaskDef::getName)
                .collect(Collectors.toSet());
    }

    private void validateTransitions(ValidationDTO.Builder builder, StateMachineDef def) {

        // Validate that every task mentioned in the transitions are valid and defined
        validateTransitions(builder, def.getTransitions(), def.getTasks());

        validateTransitionGraph(builder, def);
    }

    /**
     * Validate that the transition graph is valid. Make sure that every {@code startState} and {@code acceptingState}
     * pair is reachable.
     */
    private void validateTransitionGraph(ValidationDTO.Builder builder, StateMachineDef def) {

        // Create adjacency list representation of transition graph
        Map<String, List<String>> g = toAdjacencyList(def.getTransitions());

        // Ensure connectivity of each (start, terminal) state pair
        Set<String> allVisitedNodes = new HashSet<>();
        String startState = def.getStartingState();
        for (String acceptState : def.getAcceptingStates()) {
            ensureConnected(builder, allVisitedNodes, g, startState, acceptState);
        }

        // Ensure all nodes have been visited by now, if there are any nodes not visited by now
        // its's probably an error, meaning an invalid transition graph
        ensureAllNodesVisited(builder, getDistinctStates(def), allVisitedNodes);
    }

    private void ensureAllNodesVisited(ValidationDTO.Builder builder, Set<String> distinctNodes, Set<String> allVisitedNodes) {
        final List<String> nonVisitedStates = distinctNodes
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

    /**
     * @param builder          {@link ValidationDTO.Builder} instance to record any validation failures
     * @param allVisitedStates global set of nodes visited so far
     * @param g                transition graph
     * @param startState       starting node
     * @param acceptState      destination node, reaching this terminates the BFS
     */
    private void ensureConnected(ValidationDTO.Builder builder, Set<String> allVisitedStates, Map<String, List<String>> g, String startState, String acceptState) {
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
}
