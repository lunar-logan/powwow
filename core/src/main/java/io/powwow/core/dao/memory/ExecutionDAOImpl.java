package io.powwow.core.dao.memory;

import io.powwow.core.dao.ExecutionDAO;
import io.powwow.core.model.execution.ExecutionContext;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

//@Repository
public class ExecutionDAOImpl extends AbstractInMemoryDAO<ExecutionContext, String> implements ExecutionDAO {
    @Override
    public ExecutionContext save(ExecutionContext executionContext) {
        Objects.requireNonNull(executionContext);
        map.put(executionContext.getCorrelationId(), executionContext);
        return executionContext;
    }

    @Override
    public ExecutionContext insert(ExecutionContext executionContext) {
        Objects.requireNonNull(executionContext);
        synchronized (map) {
            if (map.containsKey(executionContext.getCorrelationId())) {
                throw new IllegalArgumentException("Duplicate primary key ID: " + executionContext.getCorrelationId());
            }

            map.put(executionContext.getCorrelationId(), executionContext);
        }
        return executionContext;
    }

    @Override
    public List<ExecutionContext> findAll(int page, int size) {
        return map.values()
                .stream()
                .skip(page * size)
                .limit(size)
                .collect(Collectors.toList());
    }
}
