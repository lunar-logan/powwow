package io.powwow.core.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapBuilder<K, V> {
    private final Map<K, V> provider;

    public MapBuilder(Map<K, V> provider) {
        this.provider = provider;
    }

    public MapBuilder() {
        this(new LinkedHashMap<>());
    }

    public MapBuilder<K, V> withEntry(K key, V value) {
        provider.put(key, value);
        return this;
    }

    public MapBuilder<K, V> withEntries(K k1, V v1, K k2, V v2) {
        provider.put(k1, v1);
        provider.put(k2, v2);
        return this;
    }

    public MapBuilder<K, V> withEntries(K k1, V v1, K k2, V v2, K k3, V v3) {
        provider.put(k1, v1);
        provider.put(k2, v2);
        provider.put(k3, v3);
        return this;
    }

    public MapBuilder<K, V> withEntries(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        provider.put(k1, v1);
        provider.put(k2, v2);
        provider.put(k3, v3);
        provider.put(k4, v4);
        return this;
    }

    public MapBuilder<K, V> withEntries(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        provider.put(k1, v1);
        provider.put(k2, v2);
        provider.put(k3, v3);
        provider.put(k4, v4);
        provider.put(k5, v5);
        return this;
    }

    public Map<K, V> build() {
        return provider;
    }
}
