package io.powwow.adapter.in.web;

import io.powwow.application.AutomataService;
import io.powwow.domain.Automata;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/automata")
@AllArgsConstructor
public class AutomataController {
    private final AutomataService service;

    @GetMapping("/{id}")
    public ResponseEntity<Automata> get(@PathVariable("id") Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
