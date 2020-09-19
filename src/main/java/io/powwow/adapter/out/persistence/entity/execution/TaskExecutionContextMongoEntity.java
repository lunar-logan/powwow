package io.powwow.adapter.out.persistence.entity.execution;

import io.powwow.model.TaskDef;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskExecutionContextMongoEntity implements Serializable {
    @Id
    private String id;

    private TaskDef taskDef;

    private TaskExecutionStatusType status;

    private Map<String, Object> input;

    @Builder.Default
    private int retryCount = 0;

    @CreatedDate
    private LocalDateTime created;

    private LocalDateTime updated;
}
