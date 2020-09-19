package io.powwow.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskDef implements Serializable {
    @NotBlank(message = "Task name cannot be null or blank")
    private String name;

    private String queueName;

    private Map<String, Object> inputParameters;

    @NotEmpty(message = "Event map must not be null or empty")
    private Map<TaskStatus, List<String>> eventMap;

    @Builder.Default
    private RetryPolicy retryPolicy = RetryPolicy.FIXED_DELAY;

    @Min(value = 1, message = "maxRetries must be greater than or equal to 1")
    @Builder.Default
    private int maxRetries = 3;
}
