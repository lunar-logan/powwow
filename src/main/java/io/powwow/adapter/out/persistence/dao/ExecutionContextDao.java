package io.powwow.adapter.out.persistence.dao;

import io.powwow.adapter.out.persistence.entity.execution.ExecutionContextMongoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExecutionContextDao extends MongoRepository<ExecutionContextMongoEntity, String> {
}
