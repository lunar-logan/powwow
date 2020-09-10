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
public class State {
    private Long id;
    private String name;
    private boolean isInitial = false;
    private boolean isTerminal = false;
    private List<TaskDefinition> taskDefinitions;
}
