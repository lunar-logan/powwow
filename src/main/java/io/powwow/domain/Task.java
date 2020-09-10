package io.powwow.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {

    private Long id;
    private TaskDefinition definition;
    private TaskStatus status;
    private Map<String, Object> input;
    private int retryCount;
}
