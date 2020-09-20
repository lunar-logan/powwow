package io.powwow.clusterlock;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ClusterLockException extends RuntimeException {
    public ClusterLockException() {
    }

    public ClusterLockException(String message) {
        super(message);
    }

    public ClusterLockException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClusterLockException(Throwable cause) {
        super(cause);
    }

    public ClusterLockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
