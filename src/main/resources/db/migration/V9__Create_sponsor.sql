CREATE TABLE sponsor
(
    sponsor_id BIGINT NOT NULL,
    location VARCHAR(32),
    phone    VARCHAR(16),
    image    VARCHAR(255),
    birthday date,
    CONSTRAINT pk_sponsor PRIMARY KEY (sponsor_id)
);

ALTER TABLE sponsor
    ADD CONSTRAINT FK_SPONSOR_ON_USERS FOREIGN KEY (sponsor_id) REFERENCES users (id) ON DELETE CASCADE;