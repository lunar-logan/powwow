package io.powwow.core.breaker;

import io.powwow.core.util.CheckedSupplier;
import net.jodah.failsafe.CircuitBreaker;
import net.jodah.failsafe.Failsafe;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static io.powwow.core.breaker.CircuitBreakers.newCircuitBreaker;

@Service
public class CircuitBreakerService {
    private final Map<String, CircuitBreaker<?>> breakers = new ConcurrentHashMap<>();

    public <R> R get(String key, CheckedSupplier<R> supplier) {
        return Failsafe.with(getOrCreateBreaker(key)).get(supplier::get);
    }

    @SuppressWarnings("unchecked")
    private <R> CircuitBreaker<R> getOrCreateBreaker(String key) {
        return (CircuitBreaker<R>) breakers.computeIfAbsent(key, ignore -> newCircuitBreaker(Exception.class));
    }
}
