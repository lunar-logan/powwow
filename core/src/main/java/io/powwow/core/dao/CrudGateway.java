package io.powwow.core.dao;

import io.powwow.core.util.CheckedFunction;

import java.util.Optional;

public interface CrudGateway<T, ID> {
    T save(T t);

    T insert(T t);

    Optional<T> findById(ID id);

    void deleteById(ID id);

    <V> V withinTransaction(CheckedFunction<TransactionHandle, V> action);
}
