package io.powwow.server.model.execution;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.powwow.core.annotation.Nullable;
import org.immutables.value.Value;

import javax.validation.constraints.NotBlank;
import java.util.Map;

@Value.Immutable
@JsonSerialize(as = ImmutableApplyEventRequest.class)
@JsonDeserialize(as = ImmutableApplyEventRequest.class)
public interface ApplyEventRequest {
    @NotBlank
    String getEvent();

    @Nullable
    Map<String, Object> getContext();
}
