package io.powwow.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class StateMachineDef implements Serializable {
    private String id;

    @NotBlank
    private String name;

    private String description;
    private long version;

    @NotNull
    @NotEmpty
    private List<String> startingStates;

    @NotNull
    @NotEmpty
    private List<String> acceptingStates;

    @NotNull
    @NotEmpty
    private List<@Valid Transition> transitions;

    @NotNull
    @NotEmpty
    private List<@Valid TaskDef> tasks;

    private String createdBy;
}
