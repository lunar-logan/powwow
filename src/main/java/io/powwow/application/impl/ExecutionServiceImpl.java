package io.powwow.application.impl;

import io.powwow.application.DefinitionService;
import io.powwow.application.ExecutionService;
import io.powwow.application.clusterlock.ClusterLock;
import io.powwow.application.clusterlock.ClusterLockService;
import io.powwow.application.exception.FSMDoesNotExistException;
import io.powwow.model.StateMachineDef;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@AllArgsConstructor
public class ExecutionServiceImpl implements ExecutionService {
    private final DefinitionService definitionService;

    private final ClusterLockService clusterLockService;


    @Override
    public String startStateMachine(String fsmId, String correlationId, Map<String, Object> initialInput) {
        ClusterLock lock = clusterLockService.newLock(fsmId);
        if (lock.tryLock(5, TimeUnit.SECONDS)) {
            try {
                final StateMachineDef def = definitionService.findById(fsmId).orElseThrow(() -> FSMDoesNotExistException.forFsmId(fsmId));
            } finally {
                lock.unlock();
            }
        }
        return null;
    }
}
