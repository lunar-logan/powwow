package io.powwow.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskDef implements Serializable {
    @NotBlank
    private String name;

    private String queueName;

    private Map<String, Object> inputParameters;

    @NotNull
    @NotEmpty
    private Map<TaskStatus, @NotEmpty @NotNull List<String>> eventMap;

    private String retryPolicy;

    @Min(1)
    private int maxRetries = 3;
}
