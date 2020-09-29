package io.powwow.core.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.powwow.core.annotation.Nullable;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@JsonSerialize(as = ImmutableStateMachineDef.class)
@JsonDeserialize(as = ImmutableStateMachineDef.class)
public interface StateMachineDef {
    String getId();

    @Nullable
    String getDescription();

    List<Transition> getTransitions();

    String getStartingState();

    List<String> getAcceptingStates();

    @Nullable
    List<Subscriber> getSubscribers();
}
