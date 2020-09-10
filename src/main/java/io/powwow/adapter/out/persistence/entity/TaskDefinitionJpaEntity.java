package io.powwow.adapter.out.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "TASK_DEFINITION")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDefinitionJpaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "QUEUE_NAME")
    private String queueName;

    @Column(name = "INPUT_PARAMETERS", columnDefinition = "json")
    private String inputParameters;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "AUTOMATA_ID")
    private AutomataJpaEntity automata;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "STATE_ID")
    private StateJpaEntity state;

    @Column(name = "RETRY_POLICY")
    private String retryPolicy;

    @Column(name = "MAX_RETRIES")
    private int maxRetries;

    @Column(name = "CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Column(name = "UPDATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;
}
