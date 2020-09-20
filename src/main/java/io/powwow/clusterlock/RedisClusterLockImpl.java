package io.powwow.clusterlock;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.List;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
public class RedisClusterLockImpl implements ClusterLock {
    private static final String REDIS_SUCCESS_COMMAND_RESPONSE = "OK";
    private static final long DEFAULT_LOCK_TTL_MILLIS = 16000; // 16 seconds
    private static final long DEFAULT_ACQUIRE_RESOLUTION_MILLIS = 1000; // 1 second
    private static final String DELETE_KEY_LUA = "if redis.call(\"get\",KEYS[1]) == ARGV[1] then\n" +
            "return redis.call(\"del\",KEYS[1])\n" +
            "else\n" +
            "return 0\n" +
            "end";

    @NonNull
    private final Jedis redis;

    @NonNull
    private final String key;

    @NonNull
    private final String value;

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws ClusterLockException {
        return tryLock(time, unit, DEFAULT_LOCK_TTL_MILLIS, DEFAULT_ACQUIRE_RESOLUTION_MILLIS);
    }

    public boolean tryLock(long time, TimeUnit unit, long lockTtlMillis, long acquireResolutionMillis) throws ClusterLockException {
        long acquiresTimeout = unit.toMillis(time);
        while (acquiresTimeout > 0) {
            try {
                if (tryAcquire(lockTtlMillis)) {
                    return true;
                }
                acquiresTimeout -= acquireResolutionMillis;
                Thread.sleep(acquireResolutionMillis);
            } catch (Exception ex) {
                throw new ClusterLockException(ex);
            }
        }
        return false;
    }

    private boolean tryAcquire(long ttlMillis) {
        return redis.set(key, value, SetParams.setParams().nx().px(ttlMillis)).equals(REDIS_SUCCESS_COMMAND_RESPONSE);
    }

    @Override
    public void unlock() throws ClusterLockException {
        try {
            redis.eval(DELETE_KEY_LUA, List.of(key), List.of(value));
        } catch (Exception ex) {
            throw new ClusterLockException(ex);
        }
    }
}
