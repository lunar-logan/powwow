package io.powwow.core.breaker;

import net.jodah.failsafe.CircuitBreaker;

import java.time.Duration;
import java.util.List;

public final class CircuitBreakers {
    private CircuitBreakers() {
    }

    public static <R> CircuitBreaker<R> newCircuitBreaker(Class<? extends Throwable>... failures) {
        return new CircuitBreaker<R>()
                .handle(failures)
                .withFailureThreshold(3, 10)
                .withSuccessThreshold(5)
                .withDelay(Duration.ofMinutes(1));
    }

    public static <R> CircuitBreaker<R> newCircuitBreaker(List<Class<? extends Throwable>> failures) {
        return new CircuitBreaker<R>()
                .handle(failures)
                .withFailureThreshold(3, 10)
                .withSuccessThreshold(5)
                .withDelay(Duration.ofMinutes(1));
    }

}
