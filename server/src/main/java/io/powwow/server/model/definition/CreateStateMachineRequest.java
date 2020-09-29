package io.powwow.server.model.definition;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateStateMachineRequest implements Serializable {
    @NotBlank(message = "FSM ID cannot be null or empty")
    private String id;

    private String description;

    @NotBlank(message = "Starting state cannot be null or empty")
    private String startingState;

    @NotEmpty(message = "Accepting states must not be empty")
    private List<@NotBlank(message = "Accepting state cannot be null or empty") String> acceptingStates;

    @NotEmpty(message = "Transitions cannot be empty")
    @Valid
    private List<TransitionDTO> transitions;

    @Valid
    private List<SubscriberDTO> subscribers;
}
