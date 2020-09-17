package io.powwow.adapter.out.persistence.dao;

import io.powwow.adapter.out.persistence.entity.StateMachineDefMongoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface StateMachineDefDao extends MongoRepository<StateMachineDefMongoEntity, String> {
    List<StateMachineDefMongoEntity> findAllByNameIsLike(String nameRegex);
}
