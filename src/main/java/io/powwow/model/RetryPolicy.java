package io.powwow.model;

public enum RetryPolicy {
    FIXED_DELAY,
    EXPONENTIAL_DELAY;

    public final String FIXED_DELAY_RETRY_POLICY = "FIXED_DELAY";
    public static final String EXPONENTIAL_DELAY_RETRY_POLICY = "EXPONENTIAL_DELAY";
}
