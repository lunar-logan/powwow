package io.powwow.dao.redis;

import io.powwow.core.dao.DefinitionDAO;
import io.powwow.core.model.ImmutableStateMachineDef;
import io.powwow.core.model.StateMachineDef;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.stream.Collectors;


public class RedisDefinitionDAO extends RedisAbstractDAO<StateMachineDef, String> implements DefinitionDAO {
    public RedisDefinitionDAO(Jedis redis) {
        super(redis, ImmutableStateMachineDef.class, "fsm");
    }

    @Override
    protected String getEntityId(StateMachineDef entity) {
        return entity.getId();
    }

    @Override
    public List<StateMachineDef> findAll(int page, int size) {
        return redis.keys(redisKeyPrefix + "*")
                .stream()
                .skip(page * size)
                .map(redis::get)
                .map(this::fromJsonString)
                .limit(size)
                .collect(Collectors.toList());
    }
}
