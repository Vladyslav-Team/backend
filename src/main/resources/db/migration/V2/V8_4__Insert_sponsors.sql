--Add column last_played_date into Sponsor table--
ALTER TABLE sponsor ADD COLUMN last_played_date DATE;


--Update values into Talent table--
UPDATE talent SET location = 'Ukraine' WHERE talent_id IN (27, 26, 25, 24, 23, 22, 21, 16);


--Insert into User table--
INSERT INTO users (email, password, name, surname)
VALUES
    ('kotsyubynskyi@hotmail.com', '$2a$10$jG/IMDQSVylEW9ftCOGOk.qqar7SuFoawEJMU8Cr9qSPValoM7tyi', 'Mykhailo', 'Kotsyubynskyi'),
    ('stus@gmail.com', '$2a$10$v.g33QyXIT04bmSKs0SlNOZhYGBk8tFzwsz5ZBecbRBEDmw9BWdDu', 'Vasyl', 'Stus'),
    ('khvylovy@gmail.com', '$2a$10$uZ0AAOndhgyRiZWU8dI0duo6yDWKopFp./PADIJnwBESzo3o.229m', 'Mykola', 'Khvylovy'),
    ('vynnychenko@gmail.com','$2a$10$kGFG14ukMJ8gAoSu.gL6OeozXw.E0xZ.tDPMhP94ftc84wT8MhQyC', 'Volodymyr', 'Vynnychenko'),
    ('dovzhenko@gmail.com', '$2a$10$PwzOGIEE3QRVvsjcbSWztu2wbzF2TFpNXO.wvb6KDwdJj7UUZvwmm', 'Oleksandr', 'Dovzhenko'),
    ('andruhovych@gmail.com', '$2a$10$OPvc9Bu0fmfxM96.HHtZkeZiXpdPW2Nqtb3N7Q3ytYnW8jKLuDRR.', 'Yuriy', 'Andruhovych'),
    ('pavlychko@gmail.com', '$2a$10$lkWDulfV2O94ihtdzzum8Oi3RC70sKZrw5ha0bFl6l8bi4niqM.Tu', 'Dmytro', 'Pavlychko'),
    ('drach@gmail.com', '$2a$10$h5LoFl9QzioxPpE6C1BTV.yWsY.0sqYNXx8xZdx0uBBhR392aShnm', 'Ivan', 'Drach'),
    ('dashvar@gmail.com', '$2a$10$YKGKmleglar8iazRuHnEM.E5zAzL0EqHC7qkUMbziUr9pyD2c2rV.', 'Lyuko', 'Dashvar'),
    ('zabuzhko@stehr.com', '$2a$10$sTOd.gJzrs0LREnuKagWquleUzQNipdo2BJBW528cxsFMDnlgtM3S', 'Oksana', 'Zabuzhko'),
    ('karpa@gmail.com', '$2a$10$Q7ePbVVBHmn9oVt0psQD6ej882RU2UZWoIVGCQ6aWrqwLQwZi3EVa', 'Irena', 'Karpa'),
    ('mathios@gmail.com', '$2a$10$EtCCYJDF9Jjle0FQa3.nkOM.7alH5jhnsx.AsbVTnFCfUJbzUiR12', 'Maria', 'Mathios'),
    ('deresh@gmail.com', '$2a$10$bryAz.SX2FZXHxWQQDKOuuLnHVBm7QwUegkKWWKhi4U9d8b1vj0nm', 'Lubko', 'Deresh');


--Insert into User_roles--
INSERT INTO user_roles (user_id, role) VALUES (28, 'ROLE_SPONSOR');
INSERT INTO user_roles (user_id, role) VALUES (29, 'ROLE_SPONSOR');
INSERT INTO user_roles (user_id, role) VALUES (30, 'ROLE_SPONSOR');
INSERT INTO user_roles (user_id, role) VALUES (31, 'ROLE_SPONSOR');
INSERT INTO user_roles (user_id, role) VALUES (32, 'ROLE_SPONSOR');
INSERT INTO user_roles (user_id, role) VALUES (33, 'ROLE_SPONSOR');
INSERT INTO user_roles (user_id, role) VALUES (34, 'ROLE_SPONSOR');
INSERT INTO user_roles (user_id, role) VALUES (35, 'ROLE_SPONSOR');
INSERT INTO user_roles (user_id, role) VALUES (36, 'ROLE_SPONSOR');
INSERT INTO user_roles (user_id, role) VALUES (37, 'ROLE_SPONSOR');
INSERT INTO user_roles (user_id, role) VALUES (38, 'ROLE_SPONSOR');
INSERT INTO user_roles (user_id, role) VALUES (39, 'ROLE_SPONSOR');
INSERT INTO user_roles (user_id, role) VALUES (40, 'ROLE_SPONSOR');


--Insert into Sponsor table--
INSERT INTO sponsor (sponsor_id, location, phone, birthday, balance) VALUES (28, 'Ukraine', '380103536263', '1998-02-13', 10);
INSERT INTO sponsor (sponsor_id, location, phone, birthday, balance) VALUES (29, 'Ukraine', '380199479826', '1992-12-26', 10);
INSERT INTO sponsor (sponsor_id, location, phone, birthday, balance) VALUES (30, 'Ukraine', '380199479826', '1992-11-27', 10);
INSERT INTO sponsor (sponsor_id, location, phone, birthday, balance) VALUES (31, 'Ukraine', '380136115559', '2001-10-28', 10);
INSERT INTO sponsor (sponsor_id, location, phone, birthday, balance) VALUES (32, 'Ukraine', '380136115559', '1952-09-29', 10);
INSERT INTO sponsor (sponsor_id, location, phone, birthday, balance) VALUES (33, 'Ukraine', '380136115559', '1962-08-01', 10);
INSERT INTO sponsor (sponsor_id, location, phone, birthday, balance) VALUES (34, 'Ukraine', '380136115559', '1972-07-02', 10);
INSERT INTO sponsor (sponsor_id, location, phone, birthday, balance) VALUES (35, 'Ukraine', '380136115559', '1982-06-03', 10);
INSERT INTO sponsor (sponsor_id, location, phone, birthday, balance) VALUES (36, 'Ukraine', '380136115559', '1992-05-04', 10);
INSERT INTO sponsor (sponsor_id, location, phone, birthday, balance) VALUES (37, 'Ukraine', '380136115559', '1902-04-05', 10);
INSERT INTO sponsor (sponsor_id, location, phone, birthday, balance) VALUES (38, 'Ukraine', '380136115559', '1932-03-06', 10);
INSERT INTO sponsor (sponsor_id, location, phone, birthday, balance) VALUES (39, 'Ukraine', '380136115559', '1932-02-09', 10);
INSERT INTO sponsor (sponsor_id, location, phone, birthday, balance) VALUES (40, 'Ukraine', '380136115559', '1932-01-08', 10);
