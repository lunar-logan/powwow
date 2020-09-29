package io.powwow.core.service;

import io.powwow.core.model.Transition;
import io.powwow.core.model.execution.ExecutionContext;

public interface NotificationManager {
    void send(ExecutionContext executionContext, Transition transition);
}
