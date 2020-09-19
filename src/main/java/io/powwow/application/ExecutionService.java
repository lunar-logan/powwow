package io.powwow.application;

import java.util.Map;

public interface ExecutionService {
    String startStateMachine(String fsmId, String correlationId, Map<String, Object> initialInput);
}
