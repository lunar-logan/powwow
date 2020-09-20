package io.powwow.clusterlock;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.UUID;

@Service
@AllArgsConstructor
public class RedisClusterLockServiceImpl implements ClusterLockService {
    private final Jedis redis;

    @Override
    public ClusterLock newLock(String key) {
        return new RedisClusterLockImpl(redis, key, getValue());
    }

    private String getValue() {
        return UUID.randomUUID().toString();
    }
}
