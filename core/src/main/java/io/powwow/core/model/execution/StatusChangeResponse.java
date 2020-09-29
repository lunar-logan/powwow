package io.powwow.core.model.execution;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@JsonSerialize(as = ImmutableStatusChangeResponse.class)
@JsonDeserialize(as = ImmutableStatusChangeResponse.class)
public interface StatusChangeResponse {
    boolean getIsSuccess();

    List<String> getReason();
}
