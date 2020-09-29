package io.powwow.server.model.definition;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransitionDTO implements Serializable {
    @NotBlank
    private String fromState;

    @NotBlank
    private String toState;

    @NotBlank
    private String event;
}
