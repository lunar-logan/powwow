package io.powwow.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class StateMachineDef implements Serializable {
    @NotBlank(message = "Machine Id cannot be null or empty")
    private String id;

    private String name;

    private String description;

    private Long version;

    @NotEmpty(message = "Starting states must not be null or blank")
    private String startingState;

    @NotEmpty(message = "Accepting states must not be null or empty")
    private List<@NotBlank String> acceptingStates;

    @NotEmpty(message = "Transitions must not be null or empty")
    @Valid
    private List<@Valid Transition> transitions;

    @NotEmpty(message = "Tasks must not be null or empty")
    @Valid
    private List<@Valid TaskDef> tasks;

    private String createdBy;
}
