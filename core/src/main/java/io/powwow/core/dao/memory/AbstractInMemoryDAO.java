package io.powwow.core.dao.memory;

import io.powwow.core.dao.CrudGateway;
import io.powwow.core.dao.TransactionHandle;
import io.powwow.core.util.CheckedFunction;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListMap;

public abstract class AbstractInMemoryDAO<T, ID> implements CrudGateway<T, ID> {
    protected final Map<ID, T> map = new ConcurrentSkipListMap<>();

    @Override
    public T save(T t) {
        return null;
    }

    @Override
    public T insert(T t) {
        return null;
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public void deleteById(ID id) {
        map.remove(id);
    }

    @Override
    public <V> V withinTransaction(CheckedFunction<TransactionHandle, V> action) {
        try {
            return action.apply(() -> {
            });
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
