package io.powwow.adapter.out.persistence.entity.execution;

import io.powwow.model.StateMachineDef;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExecutionContextMongoEntity implements Serializable {
    @Id
    private String correlationId;

    private StateMachineDef fsmDef;

    private Map<String, Object> initialInput;

    @Builder.Default
    private ExecutionContextStatusType status = ExecutionContextStatusType.RUNNING;

    // Current state of this FSM
    private String state;

    @Version
    private Long version;

    @CreatedDate
    private LocalDateTime created;
    private LocalDateTime updated;
}
