package io.powwow.application.clusterlock;

import java.util.concurrent.TimeUnit;

public interface ClusterLock {
    boolean tryLock(long time, TimeUnit unit) throws ClusterLockException;

    void unlock() throws ClusterLockException;
}
