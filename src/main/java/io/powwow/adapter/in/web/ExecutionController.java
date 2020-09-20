package io.powwow.adapter.in.web;

import io.powwow.adapter.in.web.request.StartStateMachineRequest;
import io.powwow.application.ExecutionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class ExecutionController {
    private final ExecutionService executionService;

    @PostMapping("/fsm/{id}/start")
    public ResponseEntity<String> startFsm(
            @PathVariable("id") String fsmId,
            @RequestBody @Valid StartStateMachineRequest request) {
        String correlationId = executionService.startStateMachine(fsmId, request.getCorrelationId(), request.getInitialInput());
        return ResponseEntity.ok(correlationId);
    }
}
