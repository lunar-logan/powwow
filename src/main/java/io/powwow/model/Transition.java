package io.powwow.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Transition implements Serializable {
    @NotBlank(message = "fromState cannot be null or blank")
    private String fromState;

    @NotBlank(message = "toState cannot be null or blank")
    private String toState;

    @NotBlank(message = "Transition event pattern cannot be null or blank")
    private String event;

    private String task;
}
