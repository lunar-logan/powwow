package io.powwow.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public enum TaskStatus implements Serializable {
    RUNNING,
    COMPLETED,
    RETRYABLE_FAILURE,
    FAILED;

    public static final String TYPE_RUNNING = "RUNNING";
    public static final String TYPE_COMPLETED = "COMPLETED";
    public static final String TYPE_RETRYABLE_FAILURE = "RETRYABLE_FAILURE";
    public static final String TYPE_FAILED = "FAILED";

}
