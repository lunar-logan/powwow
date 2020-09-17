package io.powwow.adapter.out.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDefMongoEntity implements Serializable {
    private String name;
    private String queueName;
    private Map<String, Object> inputParameters;
    private Map<String, List<String>> statusEventMap;
    private String retryPolicy;
    private int maxRetries;
}
