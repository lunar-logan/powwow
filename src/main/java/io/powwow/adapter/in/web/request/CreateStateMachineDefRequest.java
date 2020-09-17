package io.powwow.adapter.in.web.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateStateMachineDefRequest implements Serializable {
    private String id;

    @NotBlank(message = "State machine name cannot be null or empty")
    private String name;

    private String description;

    @Min(value = 1)
    private long version = 1;
}
