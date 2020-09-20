package io.powwow.adapter.in.web.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StartStateMachineRequest implements Serializable {
    @NotBlank(message = "CorrelationId cannot be null or blank")
    private String correlationId;
    private Map<String, Object> initialInput;
}
