package io.powwow.server.api;

import io.powwow.core.model.*;
import io.powwow.core.model.execution.ExecutionContext;
import io.powwow.core.model.execution.ImmutableStartStateMachineRequest;
import io.powwow.core.service.DefinitionService;
import io.powwow.core.service.ExecutionService;
import io.powwow.server.model.definition.CreateStateMachineRequest;
import io.powwow.server.model.definition.SubscriberDTO;
import io.powwow.server.model.definition.TransitionDTO;
import io.powwow.server.model.execution.StartFSMRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/fsm")
public class DefinitionApi {
    private final DefinitionService definitionService;
    private final ExecutionService executionService;

    @GetMapping("/{id}")
    public ResponseEntity<StateMachineDef> find(@PathVariable("id") String fsmId) {
        return definitionService.findById(fsmId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/start")
    public ResponseEntity<ExecutionContext> startStateMachine(@PathVariable("id") String fsmId, @RequestBody @Valid StartFSMRequest request) {
        ExecutionContext ctx = executionService.start(ImmutableStartStateMachineRequest.builder()
                .fsmId(fsmId)
                .correlationId(request.getCorrelationId())
                .initialInput(request.getInitialInput())
                .build());
        return ResponseEntity.ok(ctx);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StateMachineDef> deleteById(@PathVariable("id") String fsmId) {
        definitionService.deleteById(fsmId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/")
    public ResponseEntity<StateMachineDef> create(@RequestBody @Valid CreateStateMachineRequest request) {
        StateMachineDef fsm = ImmutableStateMachineDef.builder()
                .id(request.getId())
                .description(request.getDescription())
                .startingState(request.getStartingState())
                .acceptingStates(request.getAcceptingStates())
                .transitions(getTransitions(request.getTransitions()))
                .subscribers(getSubscribers(request.getSubscribers()))
                .build();
        return ResponseEntity.ok(definitionService.create(fsm));
    }

    private List<Transition> getTransitions(List<TransitionDTO> transitions) {
        if (transitions == null || transitions.isEmpty()) {
            return List.of();
        }
        return transitions.stream()
                .map(this::toBusinessModel)
                .collect(Collectors.toList());
    }

    private List<Subscriber> getSubscribers(List<SubscriberDTO> subscribers) {
        if (subscribers == null || subscribers.isEmpty()) {
            return List.of();
        }
        return subscribers.stream()
                .map(this::toBusinessModel)
                .collect(Collectors.toList());
    }

    private Subscriber toBusinessModel(SubscriberDTO dto) {
        return ImmutableSubscriber.builder()
                .name(dto.getName())
                .callbackUrl(dto.getCallbackUrl())
                .properties(dto.getProperties())
                .state(dto.getState())
                .build();
    }

    private Transition toBusinessModel(TransitionDTO dto) {
        return ImmutableTransition.builder()
                .fromState(dto.getFromState())
                .toState(dto.getToState())
                .event(dto.getEvent())
                .build();
    }
}
