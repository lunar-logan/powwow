package io.powwow.clusterlock;

public interface ClusterLockService {
    ClusterLock newLock(String key);
}
