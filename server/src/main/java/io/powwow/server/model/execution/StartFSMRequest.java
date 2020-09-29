package io.powwow.server.model.execution;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.powwow.core.annotation.Nullable;
import org.immutables.value.Value;

import java.util.Map;

@Value.Immutable
@JsonSerialize(as = ImmutableStartFSMRequest.class)
@JsonDeserialize(as = ImmutableStartFSMRequest.class)
public interface StartFSMRequest {
    @Nullable
    String getCorrelationId();

    @Nullable
    Map<String, Object> getInitialInput();
}
