package io.powwow.core.dao;

import io.powwow.core.model.StateMachineDef;

import java.util.List;

public interface DefinitionDAO extends CrudGateway<StateMachineDef, String> {
    List<StateMachineDef> findAll(int page, int size);
}
