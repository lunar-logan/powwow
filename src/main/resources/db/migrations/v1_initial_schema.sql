create table AUTOMATA
(
    ID          bigserial not null primary key,
    NAME        text      not null,
    DESCRIPTION text,
    VERSION     bigint    not null default 1,
    CREATED     timestamp,
    UPDATED     timestamp,
    unique (NAME, VERSION)
);

create table AUTOMATA_STATUS_MAPPING
(
    ID           bigserial    not null primary key,
    AUTOMATA_ID  bigserial    not null,
    NAME         varchar(256) not null unique,
    IS_RETRYABLE boolean default FALSE,
    CREATED      timestamp,
    UPDATED      timestamp,
    foreign key (AUTOMATA_ID) references AUTOMATA (id) on delete cascade
);

create index IDX_AUTOMATA_STATUS_MAPPING_AUTOMATA_ID on AUTOMATA_STATUS_MAPPING (AUTOMATA_ID);

create table STATE
(
    ID          bigserial not null primary key,
    AUTOMATA_ID bigserial not null,
    NAME        text      not null,
    IS_INITIAL  boolean   not null default FALSE,
    IS_TERMINAL boolean   not null default FALSE,
    CREATED     timestamp,
    UPDATED     timestamp,
    foreign key (AUTOMATA_ID) references AUTOMATA (id) on delete cascade,
    unique (AUTOMATA_ID, NAME)
);

create table EVENT
(
    ID          bigserial not null primary key,
    AUTOMATA_ID bigserial not null,
    NAME        text      not null,
    DESCRIPTION text,
    CREATED     timestamp,
    UPDATED     timestamp,
    foreign key (AUTOMATA_ID) references AUTOMATA (id) on delete cascade,
    unique (AUTOMATA_ID, NAME)
);

create table TASK_DEFINITION
(
    ID               bigserial not null primary key,
    AUTOMATA_ID      bigserial not null,
    STATE_ID         bigserial not null,
    NAME             text      not null,
    QUEUE_NAME       varchar(256),
    INPUT_PARAMETERS json,
    RETRY_POLICY     varchar(256),
    MAX_RETRIES      integer   not null default 1,
    CREATED          timestamp,
    UPDATED          timestamp,
    foreign key (AUTOMATA_ID) references AUTOMATA (ID) on delete cascade,
    foreign key (STATE_ID) references STATE (ID) on delete cascade
);

create index IDX_TASK_DEFN_AUTOMATA_ID on TASK_DEFINITION (AUTOMATA_ID);
create index IDX_TASK_DEFN_STATE_ID on TASK_DEFINITION (STATE_ID);


create table TASK_STATUS_MAPPING
(
    ID                 bigserial    not null primary key,
    TASK_DEFINITION_ID bigserial    not null,
    NAME               varchar(256) not null unique,
    IS_RETRYABLE       boolean default FALSE,
    CREATED            timestamp,
    UPDATED            timestamp,
    foreign key (TASK_DEFINITION_ID) references TASK_DEFINITION (ID) on delete cascade
);

create table TASK_EVENT_MAPPING
(
    ID             bigserial not null primary key,
    EVENT_ID       bigserial not null,
    TASK_STATUS_ID bigserial not null,
    CREATED        timestamp,
    UPDATED        timestamp,
    foreign key (EVENT_ID) references EVENT (ID) on delete cascade,
    foreign key (TASK_STATUS_ID) references TASK_STATUS_MAPPING (ID) on delete cascade
);

create index IDX_TASK_EVENT_MAPPING_TASK_STATUS_ID on TASK_EVENT_MAPPING (TASK_STATUS_ID);

create table TRANSITION
(
    ID            bigserial not null primary key,
    AUTOMATA_ID   bigserial not null,
    FROM_STATE_ID bigserial not null,
    TO_STATE_ID   bigserial not null,
    EVENT_ID      bigserial not null,
    CREATED       timestamp,
    UPDATED       timestamp,
    foreign key (AUTOMATA_ID) references AUTOMATA (ID) on delete cascade,
    foreign key (FROM_STATE_ID) references STATE (ID) on delete cascade,
    foreign key (TO_STATE_ID) references STATE (ID) on delete cascade,
    foreign key (EVENT_ID) references EVENT (ID) on delete cascade
);

create index IDX_TRANSITION_AUTOMATA_ID ON TRANSITION (AUTOMATA_ID);

create table STATE_MACHINE
(
    ID            bigserial not null primary key,
    AUTOMATA_ID   bigserial not null,
    STATUS_ID     bigserial,
    INITIAL_INPUT text,
    CREATED       timestamp,
    UPDATED       timestamp,
    foreign key (AUTOMATA_ID) references AUTOMATA (id) on delete cascade,
    foreign key (STATUS_ID) references AUTOMATA_STATUS_MAPPING (ID) on delete set NULL
);

create table FSM_STATE_GROUP
(
    ID               bigserial not null primary key,
    STATE_MACHINE_ID bigserial not null,
    STATE_ID         bigserial not null,
    IS_CURRENT       boolean   not null,
    CREATED          timestamp,
    UPDATED          timestamp,
    foreign key (STATE_MACHINE_ID) references STATE_MACHINE (ID) on delete cascade,
    foreign key (STATE_ID) references STATE (ID) on delete cascade
);

create index IDX_FSM_STATE_GROUP_STATE_MACHINE_ID on FSM_STATE_GROUP (STATE_MACHINE_ID);

create table TASK
(
    ID                 bigserial not null primary key,
    TASK_DEFINITION_ID bigserial not null,
    STATE_MACHINE_ID   bigserial not null,
    INPUT              text,
    STATUS_ID          bigserial,
    RETRY_COUNT        integer   not null default 0,
    CREATED            timestamp,
    UPDATED            timestamp,
    foreign key (TASK_DEFINITION_ID) references TASK_DEFINITION (ID) on delete cascade,
    foreign key (STATE_MACHINE_ID) references STATE_MACHINE (ID) on delete cascade,
    foreign key (STATUS_ID) references TASK_STATUS_MAPPING (ID) on delete set NULL
);
create index IDX_TASK_FSM_ID on TASK (STATE_MACHINE_ID);

