package io.powwow.adapter.out.persistence.dao;

import io.powwow.adapter.out.persistence.entity.execution.TaskExecutionContextMongoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TaskExecutionDao extends MongoRepository<TaskExecutionContextMongoEntity, String> {
    List<TaskExecutionContextMongoEntity> findByCorrelationId(String correlationId);
}
