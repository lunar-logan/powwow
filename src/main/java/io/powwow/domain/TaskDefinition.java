package io.powwow.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDefinition {
    private Long id;
    private String name;
    private String queueName;
    private Map<String, Object> inputParameters;
    private List<TaskStatus> statuses;
    private Map<TaskStatus, List<Event>> eventMap;
    private String retryPolicy;
    private int maxRetries;
}
