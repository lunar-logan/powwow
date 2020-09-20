package io.powwow.application.impl;

import io.powwow.adapter.out.persistence.dao.StateMachineDefDao;
import io.powwow.adapter.out.persistence.entity.StateMachineDefMongoEntity;
import io.powwow.adapter.out.persistence.entity.TaskDefMongoEntity;
import io.powwow.adapter.out.persistence.entity.TransitionMongoEntity;
import io.powwow.application.DefinitionService;
import io.powwow.application.ValidationService;
import io.powwow.application.dto.ValidationDTO;
import io.powwow.application.request.DefineStateMachineRequest;
import io.powwow.application.response.ServiceResponse;
import io.powwow.model.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DefinitionServiceImpl implements DefinitionService {
    private final StateMachineDefDao stateMachineDefDao;
    private final ValidationService validationService;

    @Override
    public Optional<StateMachineDef> findById(String id) {
        return stateMachineDefDao.findById(id)
                .map(this::toDomainModel);
    }

    @Override
    public List<StateMachineDef> findAllByName(String name) {
        return stateMachineDefDao.findAllByNameIsLike(name)
                .stream()
                .map(this::toDomainModel)
                .collect(Collectors.toList());
    }

    private StateMachineDef toDomainModel(StateMachineDefMongoEntity savedEntity) {
        return StateMachineDef.builder()
                .id(savedEntity.getId())
                .name(savedEntity.getName())
                .description(savedEntity.getDescription())
                .version(savedEntity.getVersion())
                .transitions(savedEntity.getTransitions().stream().map(this::toDomainModel).collect(Collectors.toList()))
                .tasks(savedEntity.getTasks().stream().map(this::toDomainModel).collect(Collectors.toList()))
                .startingState(savedEntity.getStartingState())
                .acceptingStates(savedEntity.getAcceptingStates())
                .createdBy(savedEntity.getCreatedBy())
                .build();
    }

    private Transition toDomainModel(TransitionMongoEntity transitionMongoEntity) {
        return Transition.builder()
                .fromState(transitionMongoEntity.getFromState())
                .toState(transitionMongoEntity.getToState())
                .event(transitionMongoEntity.getEvent())
                .task(transitionMongoEntity.getTask())
                .build();
    }

    @Transactional
    @Override
    public ServiceResponse<StateMachineDef> define(@NotNull @Valid DefineStateMachineRequest request) {
        ValidationDTO validate = validationService.validate(request.getStateMachineDef());
        if (!validate.isSuccess()) {
            return ServiceResponse.<StateMachineDef>builder()
                    .success(false)
                    .reasons(validate.getReasons())
                    .build();
        }

        final StateMachineDef fsmDef = saveStateMachineDef(request);
        return ServiceResponse.<StateMachineDef>builder()
                .body(fsmDef)
                .build();
    }

    private StateMachineDef saveStateMachineDef(DefineStateMachineRequest request) {
        // Save the FSM definition
        final StateMachineDefMongoEntity fsmDefEntity = stateMachineDefDao.insert(toEntity(request.getStateMachineDef()));

        // Convert the Mongo entity back to domain model
        return toDomainModel(fsmDefEntity);
    }

    private StateMachineDefMongoEntity toEntity(StateMachineDef fsmDef) {
        return StateMachineDefMongoEntity.builder()
                .id(fsmDef.getId())
                .name(fsmDef.getName())
                .description(fsmDef.getDescription())
                .startingState(fsmDef.getStartingState())
                .acceptingStates(fsmDef.getAcceptingStates())
                .tasks(fsmDef.getTasks().stream().map(this::toEntity).collect(Collectors.toList()))
                .transitions(fsmDef.getTransitions().stream().map(this::toEntity).collect(Collectors.toList()))
                .created(LocalDateTime.now())
                .build();
    }

    private TransitionMongoEntity toEntity(Transition transition) {
        return TransitionMongoEntity.builder()
                .fromState(transition.getFromState())
                .toState(transition.getToState())
                .event(transition.getEvent())
                .task(transition.getTask())
                .build();
    }


    private TaskDef toDomainModel(TaskDefMongoEntity taskDefMongoEntity) {
        return TaskDef.builder()
                .name(taskDefMongoEntity.getName())
                .queueName(taskDefMongoEntity.getQueueName())
                .inputParameters(taskDefMongoEntity.getInputParameters())
                .eventMap(toDomainEventMap(taskDefMongoEntity.getStatusEventMap()))
                .maxRetries(taskDefMongoEntity.getMaxRetries())
                .retryPolicy(RetryPolicy.valueOf(taskDefMongoEntity.getRetryPolicy()))
                .build();
    }

    private Map<TaskStatus, List<String>> toDomainEventMap(Map<String, List<String>> evMap) {
        return evMap.entrySet()
                .stream()
                .map(e -> Map.entry(TaskStatus.valueOf(e.getKey()), e.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private TaskDefMongoEntity toEntity(TaskDef taskDef) {
        return TaskDefMongoEntity.builder()
                .name(taskDef.getName())
                .queueName(taskDef.getQueueName())
                .inputParameters(taskDef.getInputParameters())
                .statusEventMap(toSerializableEventMap(taskDef.getEventMap()))
                .maxRetries(taskDef.getMaxRetries())
                .retryPolicy(taskDef.getRetryPolicy().name())
                .build();
    }

    private Map<String, List<String>> toSerializableEventMap(Map<TaskStatus, List<String>> eventMap) {
        return eventMap.entrySet()
                .stream()
                .map(e -> Map.entry(e.getKey().name(), e.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
