package io.powwow.core.dao.memory;

import io.powwow.core.dao.DefinitionDAO;
import io.powwow.core.model.StateMachineDef;

import java.util.List;
import java.util.Objects;

//@Repository
public class DefinitionDAOImpl extends AbstractInMemoryDAO<StateMachineDef, String> implements DefinitionDAO {
    @Override
    public StateMachineDef save(StateMachineDef stateMachineDef) {
        Objects.requireNonNull(stateMachineDef);
        map.put(stateMachineDef.getId(), stateMachineDef);
        return stateMachineDef;
    }

    @Override
    public StateMachineDef insert(StateMachineDef stateMachineDef) {
        Objects.requireNonNull(stateMachineDef);
        synchronized (map) {
            if (map.containsKey(stateMachineDef.getId())) {
                throw new IllegalArgumentException("Duplicate primary key ID: " + stateMachineDef.getId());
            }
            map.put(stateMachineDef.getId(), stateMachineDef);
        }
        return stateMachineDef;
    }

    @Override
    public List<StateMachineDef> findAll(int page, int size) {
        return null;
    }
}
