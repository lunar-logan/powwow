package io.powwow.adapter.out.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StateMachineDefMongoEntity implements Serializable {

    @Id
    private String id;
    private String name;
    private String description;

    @Version
    private long version;

    private List<String> startingStates;
    private List<String> acceptingStates;
    private List<TaskDefMongoEntity> tasks;
    private List<TransitionMongoEntity> transitions;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private LocalDateTime created;
}
