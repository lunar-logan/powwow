package io.powwow.adapter.out.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "STATE")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StateJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "IS_INITIAL")
    private boolean isInitial = false;

    @Column(name = "IS_TERMINAL")
    private boolean isTerminal = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AUTOMATA_ID")
    private AutomataJpaEntity automata;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "state")
    private List<TaskDefinitionJpaEntity> taskDefinitions;

    @Column(name = "CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Column(name = "UPDATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;
}
