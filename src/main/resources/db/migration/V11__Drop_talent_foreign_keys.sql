-----REBASE THE DB-----

-- 1. Delete old foreign keys

--- 1.2 Delete old foreign key between talent and talentInfo
ALTER TABLE talent_info DROP CONSTRAINT FK_TALENTINFO_ON_TALENT;
--- 1.3 Delete old foreign key between talent and proof
ALTER TABLE proof DROP CONSTRAINT FK_PROOF_ON_TALENT;

-- 2. Drop TALENT table
DROP TABLE IF EXISTS talent;

--- 3 Drop table talentInfo
DROP TABLE IF EXISTS talent_info;