package io.powwow.dao.rocksdb;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.rocksdb.TransactionDB;

public class RocksDBTemplate {
    protected final RocksDB rocksDB;
    protected final TransactionDB transactionDB;
    private final ObjectMapper mapper = new ObjectMapper();

    public RocksDBTemplate(RocksDB rocksDB, TransactionDB transactionDB) {
        this.rocksDB = rocksDB;
        this.transactionDB = transactionDB;
    }

    public void insert(String key, Object value) {

    }

    public void insert(byte[] key, byte[] value) {

    }

    public void put(byte[] key, byte[] value) {
        try {
            rocksDB.put(key, value);
        } catch (RocksDBException ex) {
            throw new PersistenceException(ex);
        }
    }
}
