package io.powwow.core.util;

@FunctionalInterface
public interface CheckedSupplier<V> {
    V get() throws Exception;
}
