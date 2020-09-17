package io.powwow.model;

public enum MachineStatus {
    RUNNING,
    COMPLETED,
    FAILED,
    STOPPED;

    public static final String TYPE_RUNNING = "RUNNING";
    public static final String TYPE_COMPLETED = "COMPLETED";
    public static final String TYPE_STOPPED = "STOPPED";
    public static final String TYPE_FAILED = "FAILED";
}
