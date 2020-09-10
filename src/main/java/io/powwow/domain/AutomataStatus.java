package io.powwow.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AutomataStatus {
    public enum Type {
        RUNNING,
        COMPLETED,
        FAILED,
        STOPPED;

        public static final String TYPE_RUNNING = "RUNNING";
        public static final String TYPE_COMPLETED = "COMPLETED";
        public static final String TYPE_STOPPED = "STOPPED";
        public static final String TYPE_FAILED = "FAILED";
    }

    private Long id;
    private String name;
    private boolean isRetryable;
}
