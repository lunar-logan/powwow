package io.powwow.dao.rocksdb;

import io.powwow.core.dao.DefinitionDAO;
import io.powwow.core.dao.TransactionHandle;
import io.powwow.core.model.StateMachineDef;
import io.powwow.core.util.CheckedFunction;
import org.rocksdb.RocksDB;

import java.util.List;
import java.util.Optional;


public class RocksDBDefinitionDAOImpl implements DefinitionDAO {
    private final RocksDB rocksDB = null;
    private final RocksDBTemplate template;

    public RocksDBDefinitionDAOImpl(RocksDBTemplate template) {
        this.template = template;
    }

    @Override
    public StateMachineDef save(StateMachineDef stateMachineDef) {
        return stateMachineDef;
    }

    @Override
    public StateMachineDef insert(StateMachineDef stateMachineDef) {
        return null;
    }

    @Override
    public Optional<StateMachineDef> findById(String s) {
        return Optional.empty();
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public <V> V withinTransaction(CheckedFunction<TransactionHandle, V> action) {
        return null;
    }

    @Override
    public List<StateMachineDef> findAll(int page, int size) {
        return null;
    }
}
