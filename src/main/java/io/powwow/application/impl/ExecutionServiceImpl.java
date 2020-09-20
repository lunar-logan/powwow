package io.powwow.application.impl;

import io.powwow.adapter.out.persistence.dao.ExecutionContextDao;
import io.powwow.adapter.out.persistence.dao.TaskExecutionDao;
import io.powwow.adapter.out.persistence.entity.execution.ExecutionContextMongoEntity;
import io.powwow.adapter.out.persistence.entity.execution.ExecutionContextStatusType;
import io.powwow.adapter.out.persistence.entity.execution.TaskExecutionContextMongoEntity;
import io.powwow.adapter.out.persistence.entity.execution.TaskExecutionStatusType;
import io.powwow.application.DefinitionService;
import io.powwow.application.ExecutionService;
import io.powwow.application.exception.FSMDoesNotExistException;
import io.powwow.clusterlock.ClusterLock;
import io.powwow.clusterlock.ClusterLockService;
import io.powwow.model.StateMachineDef;
import io.powwow.model.TaskDef;
import io.powwow.model.Transition;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@AllArgsConstructor
public class ExecutionServiceImpl implements ExecutionService {
    private final DefinitionService definitionService;
    private final ClusterLockService clusterLockService;
    private final ExecutionContextDao executionContextDao;
    private final TaskExecutionDao taskExecutionDao;


    @Override
    public String startStateMachine(String fsmId, String correlationId, Map<String, Object> initialInput) {
        String lockKey = getLockKey(fsmId, correlationId);

        ClusterLock lock = clusterLockService.newLock(lockKey);
        if (lock.tryLock(5, TimeUnit.SECONDS)) {
            long startTime = System.nanoTime();
            try {
                final StateMachineDef def = definitionService.findById(fsmId).orElseThrow(() -> FSMDoesNotExistException.forFsmId(fsmId));
                final ExecutionContextMongoEntity executionContext = buildExecutionContext(def, correlationId, initialInput);
                executionContextDao.insert(executionContext);
                applyEvent(correlationId, "auto");
                return correlationId;
            } finally {
                long elapsedTime = System.nanoTime() - startTime;
                log.info("Machine started for correlationId {} in {} ms", correlationId, TimeUnit.NANOSECONDS.toMillis(elapsedTime));
                lock.unlock();
            }
        }
        return null;
    }

    private void applyEvent(String correlationId, String event) {
        executionContextDao.findById(correlationId)
                .ifPresentOrElse(executionContext -> applyEvent(executionContext, event),
                        () -> log.info("No execution context found for correlationID [{}]", correlationId));
    }

    private void applyEvent(ExecutionContextMongoEntity executionContext, String event) {
        String currentState = executionContext.getState();
        executionContext.getFsmDef()
                .getTransitions()
                .stream()
                .filter(transition -> transition.getFromState().equals(currentState))
                .filter(transition -> transition.getEvent().equals(event))
                .findFirst()
                .ifPresent(transition -> applyTransition(executionContext, transition));
    }

    private void applyTransition(ExecutionContextMongoEntity executionContext, Transition transition) {
        String task = transition.getTask();
        if (task != null) {
            startTask(executionContext, task);
        }
    }

    private void startTask(ExecutionContextMongoEntity executionContext, String taskName) {
        executionContext.getFsmDef()
                .getTasks()
                .stream()
                .filter(task -> task.getName().equals(taskName))
                .findFirst()
                .ifPresent(taskDef -> startTask(executionContext, taskDef));
    }

    private void startTask(ExecutionContextMongoEntity executionContext, TaskDef taskDef) {
        TaskExecutionContextMongoEntity taskContext = taskExecutionDao.insert(createTaskExecutionContext(executionContext, taskDef));

        //Notify queue
        log.info("Writing to queue [{}], taskId={}, correlationId={}", taskDef.getQueueName(), taskContext.getId(), taskContext.getCorrelationId());
    }

    private TaskExecutionContextMongoEntity createTaskExecutionContext(ExecutionContextMongoEntity executionContext, TaskDef taskDef) {
        return TaskExecutionContextMongoEntity.builder()
                .correlationId(executionContext.getCorrelationId())
                .input(resolveInputParameters(executionContext, taskDef.getInputParameters()))
                .retryCount(0)
                .status(TaskExecutionStatusType.RUNNING)
                .taskDef(taskDef)
                .created(new Date())
                .build();
    }

    private Map<String, Object> resolveInputParameters(ExecutionContextMongoEntity executionContext, Map<String, Object> inputParameters) {
        Map<String, Object> context = new HashMap<>();
        taskExecutionDao.findByCorrelationId(executionContext.getCorrelationId())
                .stream()
                .filter(taskExecutionContextMongoEntity -> taskExecutionContextMongoEntity.getStatus() == TaskExecutionStatusType.COMPLETED)
                .forEach(taskExecutionContext -> context.put(taskExecutionContext.getTaskDef().getName(), taskExecutionContext.getOutput()));
        context.put("root", executionContext.getInitialInput());
        return Collections.unmodifiableMap(context);
    }

    private ExecutionContextMongoEntity buildExecutionContext(StateMachineDef def, String correlationId, Map<String, Object> initialInput) {
        return ExecutionContextMongoEntity.builder()
                .correlationId(correlationId)
                .fsmDef(def)
                .status(ExecutionContextStatusType.RUNNING)
                .state(def.getStartingState())
                .initialInput(initialInput)
                .created(new Date())
                .build();
    }

    private String getLockKey(String fsmId, String correlationId) {
        return String.format("%s:%s", fsmId, correlationId);
    }
}
