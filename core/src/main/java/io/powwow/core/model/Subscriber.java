package io.powwow.core.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.powwow.core.annotation.Nullable;
import org.immutables.value.Value;

import java.util.Properties;

@Value.Immutable
@JsonSerialize(as = ImmutableSubscriber.class)
@JsonDeserialize(as = ImmutableSubscriber.class)
public interface Subscriber {
    String getName();

    String getCallbackUrl();

    @Nullable
    Properties getProperties();

    String getState();
}
