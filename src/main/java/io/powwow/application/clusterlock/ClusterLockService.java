package io.powwow.application.clusterlock;

public interface ClusterLockService {
    ClusterLock newLock(String key);
}
