--UPDATE type of publication_date--

ALTER TABLE proof
ALTER COLUMN publication_date TYPE TIMESTAMP WITHOUT TIME ZONE;

--UPDATE VALUES IN publication_date--

UPDATE proof SET publication_date = '2000-01-01 14:30' WHERE id = 1;
UPDATE proof SET publication_date = '1995-02-14 09:00' WHERE id = 2;
UPDATE proof SET publication_date = '1987-03-17 15:45' WHERE id = 3;
UPDATE proof SET publication_date = '1976-04-20 11:30' WHERE id = 4;
UPDATE proof SET publication_date = '1983-05-05 17:15' WHERE id = 5;
UPDATE proof SET publication_date = '1999-06-10 08:00' WHERE id = 6;
UPDATE proof SET publication_date = '2005-07-15 13:30' WHERE id = 7;
UPDATE proof SET publication_date = '1992-08-23 10:15' WHERE id = 8;
UPDATE proof SET publication_date = '1980-09-03 16:00' WHERE id = 9;
UPDATE proof SET publication_date = '1974-10-14 12:45' WHERE id = 10;
UPDATE proof SET publication_date = '1989-11-18 18:30' WHERE id = 11;
UPDATE proof SET publication_date = '2001-12-25 07:15' WHERE id = 12;
UPDATE proof SET publication_date = '1998-01-29 14:00' WHERE id = 13;
UPDATE proof SET publication_date = '1985-02-22 10:45' WHERE id = 14;
UPDATE proof SET publication_date = '1977-03-27 16:30' WHERE id = 15;
UPDATE proof SET publication_date = '1993-04-30 13:15' WHERE id = 16;
UPDATE proof SET publication_date = '1981-05-10 19:00' WHERE id = 17;
UPDATE proof SET publication_date = '1975-06-21 08:45' WHERE id = 18;
UPDATE proof SET publication_date = '1990-07-25 15:30' WHERE id = 19;
UPDATE proof SET publication_date = '2002-08-30 12:15' WHERE id = 20;
UPDATE proof SET publication_date = '1997-09-04 18:00' WHERE id = 21;
UPDATE proof SET publication_date = '1986-10-17 08:45' WHERE id = 22;
UPDATE proof SET publication_date = '1978-11-20 14:30' WHERE id = 23;
UPDATE proof SET publication_date = '1994-12-31 11:15' WHERE id = 24;
UPDATE proof SET publication_date = '1991-01-08 17:00' WHERE id = 25;
UPDATE proof SET publication_date = '1988-02-12 13:45' WHERE id = 26;
UPDATE proof SET publication_date = '1979-03-15 19:30' WHERE id = 27;
UPDATE proof SET publication_date = '1996-04-18 08:15' WHERE id = 28;
UPDATE proof SET publication_date = '1984-05-31 15:00' WHERE id = 29;
UPDATE proof SET publication_date = '1973-06-11 11:45' WHERE id = 30;
UPDATE proof SET publication_date = '2003-07-19 17:30' WHERE id = 31;
UPDATE proof SET publication_date = '1999-08-24 14:15' WHERE id = 32;
UPDATE proof SET publication_date = '1982-09-29 20:00' WHERE id = 33;
UPDATE proof SET publication_date = '1971-10-30 08:45' WHERE id = 34;
UPDATE proof SET publication_date = '1992-11-11 15:30' WHERE id = 35;
UPDATE proof SET publication_date = '2004-12-16 12:15' WHERE id = 36;
UPDATE proof SET publication_date = '1990-01-21 18:00' WHERE id = 37;
UPDATE proof SET publication_date = '1985-03-04 16:30' WHERE id = 38;
UPDATE proof SET publication_date = '1998-04-07 13:15' WHERE id = 39;
UPDATE proof SET publication_date = '2002-05-12 19:00' WHERE id = 40;
UPDATE proof SET publication_date = '1994-06-27 15:45' WHERE id = 41;
UPDATE proof SET publication_date = '1993-05-26 14:44' WHERE id = 42;
