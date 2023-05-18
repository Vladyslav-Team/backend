ALTER TABLE kudos ADD COLUMN skill_id BIGINT NOT NULL;
ALTER TABLE kudos ADD CONSTRAINT FK_KUDOS_ON_SKILL FOREIGN KEY (skill_id) REFERENCES skill (id);

INSERT INTO kudos (sponsor_id, proof_id, amount, kudos_date, skill_id) VALUES
    (28, 5, 10, '2023-05-19 12:08', 12)