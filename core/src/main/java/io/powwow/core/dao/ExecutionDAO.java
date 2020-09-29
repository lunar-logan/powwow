package io.powwow.core.dao;

import io.powwow.core.model.execution.ExecutionContext;

import java.util.List;

public interface ExecutionDAO extends CrudGateway<ExecutionContext, String> {
    List<ExecutionContext> findAll(int page, int size);
}
