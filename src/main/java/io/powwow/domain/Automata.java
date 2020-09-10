package io.powwow.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Automata {
    private Long id;
    private String name;
    private String description;
    private int version;
    private List<AutomataStatus> statuses;
    private List<State> states;
    private List<Event> events;
    private List<Transition> transitions;
    private List<TaskDefinition> taskDefinitions;
}
