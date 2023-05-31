CREATE TABLE talent_skill
(
    talent_id  BIGINT NOT NULL,
    skill_id     BIGINT NOT NULL,
    CONSTRAINT pk_talent_skill PRIMARY KEY (talent_id, skill_id)
);

ALTER TABLE talent_skill
    ADD CONSTRAINT fk_talski_on_talent FOREIGN KEY (talent_id) REFERENCES talent (talent_id);

ALTER TABLE talent_skill
    ADD CONSTRAINT fk_talski_on_skill FOREIGN KEY (skill_id) REFERENCES skill (id);


INSERT INTO talent_skill (talent_id, skill_id)
VALUES (2, 1),
       (3, 12), (3, 13), (3, 14), (3, 15), (3, 18), (3, 20), (3, 21), (3, 22), (3, 23), (3, 24), (3, 27), (3, 32),
       (3, 35), (3, 36), (3, 49), (3, 50), (3, 55), (3, 56), (3, 57), (3, 58), (3, 59), (3, 60), (3, 61), (3, 62),
       (3, 63), (3, 64), (3, 65), (3, 66), (3, 67),
       (23, 4), (23, 5),
       (24, 6), (24, 7), (24, 8), (24, 45), (24, 35), (24, 62), (24, 11),
       (27, 6), (27, 4), (27, 3), (27, 50), (27, 51);


DELETE FROM proof_skill where proof_id=42;

ALTER TABLE proof_skill ADD CONSTRAINT pk_proof_skill PRIMARY KEY (proof_id, skill_id);

INSERT INTO proof_skill (proof_id, skill_id)
VALUES (42, 8), (42, 9), (42, 10), (42, 50),
       (5, 12), (5, 13), (5, 14),
       (6, 18), (6, 20);