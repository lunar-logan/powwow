package io.powwow.server.api;

import io.powwow.core.model.event.ImmutableEvent;
import io.powwow.core.model.execution.ExecutionContext;
import io.powwow.core.service.ExecutionService;
import io.powwow.server.model.execution.ApplyEventRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/execution")
public class ExecutionApi {
    private final ExecutionService executionService;

    @PostMapping("/{id}/event/apply")
    public ResponseEntity<?> applyEvent(@PathVariable("id") String correlationId, @RequestBody @Valid ApplyEventRequest request) {
        executionService.applyEvent(correlationId, ImmutableEvent.builder()
                .name(request.getEvent())
                .context(request.getContext())
                .build());
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExecutionContext> fetchExecutionContext(@PathVariable("id") String correlationId) {
        return executionService.findById(correlationId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ExecutionContext>> fetchExecutionContext(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                        @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        List<ExecutionContext> contexts = executionService.findAll(page, size);
        return ResponseEntity.ok(contexts);
    }
}
