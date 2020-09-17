package io.powwow.adapter.in.web.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.powwow.model.StateMachineDef;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DefineStateMachineRequest implements Serializable {
    @NotNull
    @Valid
    private StateMachineDef stateMachineDef;
}
