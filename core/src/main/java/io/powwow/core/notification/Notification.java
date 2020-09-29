package io.powwow.core.notification;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.powwow.core.annotation.Nullable;
import org.immutables.value.Value;

import java.net.URI;
import java.util.Map;
import java.util.Properties;

@Value.Immutable
@JsonSerialize(as = ImmutableNotification.class)
@JsonDeserialize(as = ImmutableNotification.class)
public interface Notification {
    @Value.Default
    default String getId() {
        return getConnectionUri().getHost();
    }

    URI getConnectionUri();

    @Nullable
    Properties getConnectionProperties();

    Map<String, Object> getBody();
}
