package io.powwow.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskStatus {
    public enum Type {
        RUNNING,
        COMPLETED,
        RETRYABLE_FAILURE,
        FAILED;

        public static final String TYPE_RUNNING = "RUNNING";
        public static final String TYPE_COMPLETED = "COMPLETED";
        public static final String TYPE_RETRYABLE_FAILURE = "RETRYABLE_FAILURE";
        public static final String TYPE_FAILED = "FAILED";
    }


    private Long id;
    private String name;
    private boolean isRetryable;
}
