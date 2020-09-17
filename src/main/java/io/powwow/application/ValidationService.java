package io.powwow.application;

import io.powwow.application.dto.ValidationDTO;
import io.powwow.model.StateMachineDef;

public interface ValidationService {
    ValidationDTO validate(StateMachineDef def);
}
