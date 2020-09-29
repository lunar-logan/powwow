package io.powwow.dao.redis;

import io.powwow.core.dao.ExecutionDAO;
import io.powwow.core.model.execution.ExecutionContext;
import io.powwow.core.model.execution.ImmutableExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.Collectors;

public class RedisExecutionDAO extends RedisAbstractDAO<ExecutionContext, String> implements ExecutionDAO {
    private final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public RedisExecutionDAO(Jedis redis) {
        super(redis, ImmutableExecutionContext.class, "execution");
    }

    @Override
    public List<ExecutionContext> findAll(int page, int size) {
        return redis.keys("execution*")
                .stream()
                .skip(page * size)
                .map(redis::get)
                .peek(json -> log.info("{}", json))
                .map(this::fromJsonString)
                .limit(size)
                .collect(Collectors.toList());
    }

    @Override
    protected String getEntityId(ExecutionContext entity) {
        return entity.getCorrelationId();
    }
}
