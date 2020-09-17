package io.powwow.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class StateMachine implements Serializable {
    private Long id;
    private MachineStatus status;
    private Map<String, Object> initialInput;
//    private List<Task> tasks;
//    private List<FsmStateGroup> stateGroups;
}
