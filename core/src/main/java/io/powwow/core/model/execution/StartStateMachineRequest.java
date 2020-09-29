package io.powwow.core.model.execution;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.powwow.core.annotation.Nullable;
import org.immutables.value.Value;

import java.util.Map;

@Value.Immutable
@JsonSerialize(as = ImmutableStartStateMachineRequest.class)
@JsonDeserialize(as = ImmutableStartStateMachineRequest.class)
public interface StartStateMachineRequest {
    String getFsmId();

    @Nullable
    Map<String, Object> getInitialInput();

    @Nullable
    String getCorrelationId();
}
