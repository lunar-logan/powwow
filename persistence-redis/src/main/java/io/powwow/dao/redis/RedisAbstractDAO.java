package io.powwow.dao.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.powwow.core.dao.CrudGateway;
import io.powwow.core.dao.TransactionHandle;
import io.powwow.core.util.CheckedFunction;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.Optional;

public abstract class RedisAbstractDAO<T, ID> implements CrudGateway<T, ID> {
    protected final Jedis redis;
    protected final Class<? extends T> typeClass;
    protected final String redisKeyPrefix;
    protected static final ObjectMapper MAPPER = new ObjectMapper();

    public RedisAbstractDAO(Jedis redis, Class<? extends T> typeClass, String redisKeyPrefix) {
        this.redis = redis;
        this.typeClass = typeClass;
        this.redisKeyPrefix = redisKeyPrefix;
    }

    @Override
    public Optional<T> findById(ID id) {
        String redisKey = getRedisKeyFromId(id);
        String json = redis.get(redisKey);
        if (json != null) {
            return Optional.of(fromJsonString(json));
        }
        return Optional.empty();
    }

    @Override
    public void deleteById(ID id) {
        redis.del(getRedisKeyFromId(id));
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

    protected String toJsonString(T t) {
        try {
            return MAPPER.writeValueAsString(t);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }

    protected T fromJsonString(String jsonString) {
        try {
            return MAPPER.readValue(jsonString, typeClass);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public T save(T t) {
        redis.set(getRedisKey(t), toJsonString(t));
        return t;
    }

    @Override
    public T insert(T t) {
        String key = getRedisKey(t);
        if (redis.exists(key)) {
            throw new RuntimeException("Duplicate key: " + key);
        }
        redis.set(key, toJsonString(t));
        return t;
    }

    protected abstract String getEntityId(T entity);

    protected String getRedisKey(T t) {
        return String.format("%s:%s", redisKeyPrefix, getEntityId(t));
    }

    protected String getRedisKeyFromId(ID id) {
        return String.format("%s:%s", redisKeyPrefix, id);
    }
}
