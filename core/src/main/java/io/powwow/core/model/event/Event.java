package io.powwow.core.model.event;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.powwow.core.annotation.Nullable;
import org.immutables.value.Value;

import java.util.Map;

@Value.Immutable
@JsonSerialize(as = ImmutableEvent.class)
@JsonDeserialize(as = ImmutableEvent.class)
public interface Event {
    String getName();

    @Value.Default
    default EventType getEventType() {
        return EventType.EXTERNAL;
    }

    @Nullable
    Map<String, Object> getContext();
}
