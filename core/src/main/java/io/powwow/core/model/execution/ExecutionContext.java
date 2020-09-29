package io.powwow.core.model.execution;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.powwow.core.annotation.Nullable;
import io.powwow.core.model.StateMachineDef;
import org.immutables.value.Value;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Value.Immutable
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(as = ImmutableExecutionContext.class)
@JsonDeserialize(as = ImmutableExecutionContext.class)
public interface ExecutionContext {
    String getCorrelationId();

    StateMachineDef getStateMachineDef();

    String getState();

    ExecutionStatusType getExecutionStatus();

    @Nullable
    Map<String, Object> getContext();

    Date getStarted();

    @Nullable
    Date getLastUpdated();

    @Nullable
    List<TransitionHistoryElement> getTransitionHistory();
}
