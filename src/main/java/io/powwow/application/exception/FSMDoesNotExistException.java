package io.powwow.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FSMDoesNotExistException extends RuntimeException {
    public FSMDoesNotExistException() {
    }

    public FSMDoesNotExistException(String message) {
        super(message);
    }

    public FSMDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public FSMDoesNotExistException(Throwable cause) {
        super(cause);
    }

    public FSMDoesNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public static FSMDoesNotExistException forFsmId(String fsmId) {
        return new FSMDoesNotExistException(String.format("FSM with id [%s] does not exists", fsmId));
    }
}
