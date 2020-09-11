package io.powwow.adapter.out.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "AUTOMATA")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AutomataJpaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "VERSION")
    private Long version;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "automata")
    private List<EventJpaEntity> events;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "automata")
    private List<StateJpaEntity> states;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "automata")
    private List<AutomataStatusJpaEntity> statuses;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "automata")
    private List<TaskDefinitionJpaEntity> taskDefinitions;

    @Column(name = "CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Column(name = "UPDATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;
}
