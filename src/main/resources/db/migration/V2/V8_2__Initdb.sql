--User--
CREATE TABLE user_roles
(
    user_id BIGINT NOT NULL,
    role    VARCHAR(255)
);

CREATE TABLE users
(
    id       BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    email    VARCHAR(254),
    password VARCHAR(64),
    name     VARCHAR(64),
    surname  VARCHAR(64),
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_user_roles_on_user FOREIGN KEY (user_id) REFERENCES users (id);
--Talent--
CREATE TABLE talent
(
    talent_id  BIGINT NOT NULL,
    image      VARCHAR(255),
    experience VARCHAR(254),
    location   VARCHAR(32),
    phone      VARCHAR(16),
    birthday   date,
    education  VARCHAR(64),
    about      VARCHAR(1000),
    CONSTRAINT pk_talent PRIMARY KEY (talent_id)
);

ALTER TABLE talent
    ADD CONSTRAINT FK_TALENT_ON_TALENT FOREIGN KEY (talent_id) REFERENCES users (id) ON DELETE CASCADE;
--Proof--
CREATE TABLE proof
(
    id               BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    talent_id        BIGINT                                  NOT NULL,
    publication_date TIMESTAMP WITHOUT TIME ZONE,
    title            VARCHAR(100),
    description      VARCHAR(2000),
    status           VARCHAR(255)                            NOT NULL,
    CONSTRAINT pk_proof PRIMARY KEY (id)
);

ALTER TABLE proof
    ADD CONSTRAINT FK_PROOF_ON_TALENT FOREIGN KEY (talent_id) REFERENCES talent (talent_id) ON DELETE CASCADE;

CREATE DOMAIN ProofStatus AS VARCHAR(20) CHECK (VALUE IN ('DRAFT', 'HIDDEN', 'PUBLISHED'));
--Sponsor--
CREATE TABLE sponsor
(
    sponsor_id BIGINT NOT NULL,
    location   VARCHAR(32),
    phone      VARCHAR(16),
    image      VARCHAR(255),
    birthday   date,
    CONSTRAINT pk_sponsor PRIMARY KEY (sponsor_id)
);

ALTER TABLE sponsor
    ADD CONSTRAINT FK_SPONSOR_ON_SPONSOR FOREIGN KEY (sponsor_id) REFERENCES users (id) ON DELETE CASCADE;
--Kudos--
CREATE TABLE kudos
(
    id         BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    sponsor_id BIGINT,
    proof_id   BIGINT                                  NOT NULL,
    amount     INTEGER,
    kudos_date TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_kudos PRIMARY KEY (id)
);

ALTER TABLE kudos
    ADD CONSTRAINT FK_KUDOS_ON_PROOF FOREIGN KEY (proof_id) REFERENCES proof (id) ON DELETE CASCADE;

ALTER TABLE kudos
    ADD CONSTRAINT FK_KUDOS_ON_SPONSOR FOREIGN KEY (sponsor_id) REFERENCES sponsor (sponsor_id);