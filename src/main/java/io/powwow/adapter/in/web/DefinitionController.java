package io.powwow.adapter.in.web;

import io.powwow.adapter.in.web.request.DefineStateMachineRequest;
import io.powwow.adapter.in.web.request.SearchStateMachineDefRequest;
import io.powwow.application.DefinitionService;
import io.powwow.application.response.ServiceResponse;
import io.powwow.model.StateMachineDef;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class DefinitionController {
    private final DefinitionService service;

    @GetMapping("/fsm/{id}")
    public ResponseEntity<StateMachineDef> findById(@PathVariable("id") String id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/fsm")
    public ResponseEntity<List<StateMachineDef>> find(SearchStateMachineDefRequest request) {
        List<StateMachineDef> matchingFsmDefs = new ArrayList<>();
        if (request.getName() != null && !request.getName().isEmpty()) {
            for (String name : request.getName()) {
                List<StateMachineDef> matchingDefs = service.findAllByName(name);
                matchingFsmDefs.addAll(matchingDefs);
            }
        }
        return ResponseEntity.ok(matchingFsmDefs);
    }

    @PostMapping("/fsm")
    public ResponseEntity<ServiceResponse<StateMachineDef>> defineFSM(@RequestBody @Valid DefineStateMachineRequest request) {
        final ServiceResponse<StateMachineDef> response = service.define(request);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }
}
