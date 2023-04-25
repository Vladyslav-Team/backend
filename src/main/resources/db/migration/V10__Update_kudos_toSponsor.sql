--UPDATE Kudos foreign key--

-- 1. Delete old foreign key
ALTER TABLE kudos DROP CONSTRAINT FK_KUDOS_ON_TALENT;

-- 2. rename column for foreign key
ALTER TABLE kudos RENAME COLUMN talent_id TO sponsor_id;

-- 3. Add new foreign key
ALTER TABLE kudos
    ADD CONSTRAINT FK_KUDOS_ON_SPONSOR FOREIGN KEY (sponsor_id) REFERENCES sponsor (sponsor_id);
