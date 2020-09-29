package io.powwow.core.observability;

import io.powwow.core.util.Either;
import org.immutables.value.Value;

import java.time.Duration;

@Value.Immutable
public interface TimedResponse<V> {
    Either<Exception, V> getValue();

    Duration getExecutionTime();
}
