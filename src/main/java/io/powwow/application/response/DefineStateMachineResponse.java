package io.powwow.application.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.powwow.model.StateMachineDef;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class DefineStateMachineResponse implements Serializable {
    @Builder.Default
    private boolean success = true;

    private String reason;

    private StateMachineDef body;
}
