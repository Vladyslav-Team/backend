CREATE TABLE talent
(
    id       BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    email    VARCHAR(254),
    password VARCHAR(64),
    name     VARCHAR(64),
    surname  VARCHAR(64),
    CONSTRAINT pk_talent PRIMARY KEY (id)
);

CREATE TABLE talent_info
(
    talent_id  BIGINT NOT NULL,
    image      VARCHAR(255),
    experience VARCHAR(254),
    location   VARCHAR(32),
    phone      VARCHAR(16),
    birthday   date,
    education  VARCHAR(64),
    about      VARCHAR(1000),
    CONSTRAINT pk_talentinfo PRIMARY KEY (talent_id)
);

ALTER TABLE talent_info
    ADD CONSTRAINT FK_TALENTINFO_ON_TALENT FOREIGN KEY (talent_id) REFERENCES talent (id) ON DELETE CASCADE;


CREATE TABLE proof
(
    id               BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    talent_id        BIGINT,
    publication_date date,
    title            VARCHAR(100),
    description      VARCHAR(255),
    status           VARCHAR(255)                            NOT NULL,
    CONSTRAINT pk_proof PRIMARY KEY (id)
);

ALTER TABLE proof
    ADD CONSTRAINT FK_PROOF_ON_TALENT FOREIGN KEY (talent_id) REFERENCES talent (id) ON DELETE CASCADE;

CREATE DOMAIN ProofStatus AS VARCHAR(20) CHECK (VALUE IN ('DRAFT', 'HIDDEN', 'PUBLISHED'));

CREATE TABLE kudos
(
    id        BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    talent_id BIGINT,
    proof_id  BIGINT                                  NOT NULL,
    amount      INTEGER,
    kudos_date TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_kudos PRIMARY KEY (id)
);

ALTER TABLE kudos
    ADD CONSTRAINT FK_KUDOS_ON_PROOF FOREIGN KEY (proof_id) REFERENCES proof (id) ON DELETE CASCADE;

ALTER TABLE kudos
    ADD CONSTRAINT FK_KUDOS_ON_TALENT FOREIGN KEY (talent_id) REFERENCES talent (id);