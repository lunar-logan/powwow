package io.powwow.adapter.out.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransitionMongoEntity implements Serializable {
    private String fromState;
    private String toState;
    private String event;
    private List<String> tasks;
}
