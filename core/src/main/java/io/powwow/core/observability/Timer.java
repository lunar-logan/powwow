package io.powwow.core.observability;

import io.powwow.core.util.Either;

import java.time.Duration;
import java.util.concurrent.Callable;

public interface Timer {
    static <V> TimedResponse<V> timed(Callable<V> action) {
        long start = System.nanoTime();
        V result = null;
        Exception err = null;
        try {
            result = action.call();
        } catch (Exception ex) {
            err = ex;
        }
        long endTime = System.nanoTime();
        return ImmutableTimedResponse.<V>builder()
                .value(new Either<>(err, result))
                .executionTime(Duration.ofNanos(endTime - start))
                .build();
    }
}
