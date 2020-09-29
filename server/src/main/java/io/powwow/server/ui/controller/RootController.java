package io.powwow.server.ui.controller;

import io.powwow.core.model.StateMachineDef;
import io.powwow.core.model.execution.ExecutionContext;
import io.powwow.core.service.DefinitionService;
import io.powwow.core.service.ExecutionService;
import io.powwow.core.util.MapBuilder;
import io.powwow.server.model.ui.TableModel;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Controller
public class RootController {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final DefinitionService definitionService;
    private final ExecutionService executionService;

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getRootView(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                    @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        List<ExecutionContext> contexts = executionService.findAll(page, size);
        if (contexts == null) contexts = List.of();
        log.info("rootView - page: {} size: {} contexts: {}", page, size, contexts.size());
        return new ModelAndView("execution/list/contexts", new ModelMap("contexts", contexts));
    }

    @GetMapping(value = "/execution/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getExecutionContextView(@PathVariable("id") String correlationId) {
        return executionService.findById(correlationId)
                .map(ctx -> new ModelAndView("execution/context", new ModelMap("ctx", ctx)))
                .orElseGet(() -> new ModelAndView("execution/notFound", new ModelMap("correlationId", correlationId)));
    }

    @GetMapping(value = "/fsm", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getListFsmView(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                       @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        List<StateMachineDef> stateMachines = definitionService.findAll(page, size);
        return new ModelAndView("definition/list", new ModelMap("fsms", stateMachines).addAttribute("page", "Definitions"));
    }

    /**
     * Displays individual state machine
     *
     * @return
     */
    @GetMapping(value = "/fsm/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getIndividualFsmView(@PathVariable("id") String fsmId) {
        StateMachineDef fsm = definitionService.findById(fsmId).orElseThrow();
        Map<String, String> infoBox = new MapBuilder<String, String>()
                .withEntries(
                        "FSM ID", fsm.getId(),
                        "Description", String.valueOf(fsm.getDescription()),
                        "Starting State", fsm.getStartingState(),
                        "Accepting States", String.join(", ", fsm.getAcceptingStates())
                ).build();

        TableModel table = TableModel.builder()
                .headers(List.of("fromState", "toState", "event"))
                .rows(toTableRows(fsm))
                .build();
        return new ModelAndView("definition/fsm", new ModelMap("infoBox", infoBox)
                .addAttribute("fsm", fsm)
                .addAttribute("transitions", table)
                .addAttribute("page", "Definitions"));
    }

    private List<List<?>> toTableRows(StateMachineDef fsm) {
        return fsm.getTransitions()
                .stream()
                .map(t -> List.of(t.getFromState(), t.getToState(), t.getEvent()))
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/health", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getHealthView() {
        return new ModelAndView("health/index", new ModelMap("page", "Health"));
    }
}
