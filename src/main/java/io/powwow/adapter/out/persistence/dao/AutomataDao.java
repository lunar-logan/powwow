package io.powwow.adapter.out.persistence.dao;

import io.powwow.adapter.out.persistence.entity.AutomataJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutomataDao extends JpaRepository<AutomataJpaEntity, Long> {
}
