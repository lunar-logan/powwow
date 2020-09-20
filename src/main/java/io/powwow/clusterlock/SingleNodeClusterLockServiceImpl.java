package io.powwow.clusterlock;

import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

//@Profile({"dev"})
//@Service
@AllArgsConstructor
public class SingleNodeClusterLockServiceImpl implements ClusterLockService {
    private final Map<String, Lock> lockMap;
    private final Supplier<Lock> lockSupplier;

    public SingleNodeClusterLockServiceImpl() {
        lockSupplier = () -> new ReentrantLock(true);
        lockMap = new ConcurrentHashMap<>();
    }

    @Override
    public ClusterLock newLock(String key) {

        return new SingleNodeClusterLockImpl(key);
    }

    private class SingleNodeClusterLockImpl implements ClusterLock {
        private final String key;
        private Lock lock;

        public SingleNodeClusterLockImpl(@NonNull String key) {
            this.key = key;
        }

        @Override
        public boolean tryLock(long time, TimeUnit unit) throws ClusterLockException {
            lock = lockMap.computeIfAbsent(key, $key -> lockSupplier.get());
            try {
                return lock.tryLock(time, unit);
            } catch (InterruptedException ex) {
                throw new ClusterLockException(ex);
            }
        }

        @Override
        public void unlock() throws ClusterLockException {
            try {
                lockMap.computeIfPresent(key, ($key, lock) -> {
                    lock.unlock();
                    return null;
                });
            } catch (Exception ex) {
                throw new ClusterLockException(ex);
            }
        }
    }
}
