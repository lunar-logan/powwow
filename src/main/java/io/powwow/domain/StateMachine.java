package io.powwow.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StateMachine {
    private Long id;
    private Automata automata;
    private AutomataStatus status;
    private Map<String, Object> initialInput;
    private List<Task> tasks;
    private List<FsmStateGroup> stateGroups;
}
