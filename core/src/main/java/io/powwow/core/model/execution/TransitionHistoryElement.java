package io.powwow.core.model.execution;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.Date;

@Value.Immutable
@JsonSerialize(as=ImmutableTransitionHistoryElement.class)
@JsonDeserialize(as=ImmutableTransitionHistoryElement.class)
public interface TransitionHistoryElement {
    String getFromState();

    String getToState();

    String getEvent();

    Date getTimestamp();
}
