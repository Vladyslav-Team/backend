-----REBASE THE DB-----

-- 1 Create new table Talent
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
--- 1.1 Create new foreign keys for user-talent
ALTER TABLE talent
    ADD CONSTRAINT FK_TALENT_ON_TALENT FOREIGN KEY (talent_id) REFERENCES users (id) ON DELETE CASCADE;

-- 2 Create new foreign keys for proof-talent

-- In this case the db will have used id's, so it will count from 30+ id
--- 2.1 Delete all information from proof
DELETE FROM proof;

--- 2.2 Add new keys for proof-talent
ALTER TABLE proof
    ADD CONSTRAINT FK_PROOF_ON_TALENT FOREIGN KEY (talent_id) REFERENCES talent (talent_id) ON DELETE CASCADE;