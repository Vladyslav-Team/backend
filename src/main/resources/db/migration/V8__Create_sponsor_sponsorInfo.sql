CREATE TABLE sponsor
(
    id       BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    email    VARCHAR(254),
    password VARCHAR(64),
    name     VARCHAR(64),
    surname  VARCHAR(64),
    CONSTRAINT pk_sponsor PRIMARY KEY (id)
);

CREATE TABLE sponsor_info
(
    sponsor_id BIGINT NOT NULL,
    location   VARCHAR(32),
    phone      VARCHAR(16),
    image      VARCHAR(255),
    birthday   date,
    CONSTRAINT pk_sponsorinfo PRIMARY KEY (sponsor_id)
);

ALTER TABLE sponsor_info
    ADD CONSTRAINT FK_SPONSORINFO_ON_SPONSOR FOREIGN KEY (sponsor_id) REFERENCES sponsor (id) ON DELETE CASCADE;