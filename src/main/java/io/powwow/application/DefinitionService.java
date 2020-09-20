package io.powwow.application;

import io.powwow.application.request.DefineStateMachineRequest;
import io.powwow.application.response.ServiceResponse;
import io.powwow.model.StateMachineDef;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface DefinitionService {
    Optional<StateMachineDef> findById(String id);

    List<StateMachineDef> findAllByName(String name);

    ServiceResponse<StateMachineDef> define(@NotNull @Valid DefineStateMachineRequest request);
}
