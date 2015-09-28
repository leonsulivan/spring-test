CREATE TABLE person (
  ID int IDENTITY,
  FIRST_NAME varchar(255) NOT NULL,
  LAST_NAME varchar(255) NOT NULL
);
-- ALTER TABLE person ADD CONSTRAINT PERSON_NAME UNIQUE (FIRST_NAME,LAST_NAME);

CREATE TABLE IF NOT EXISTS bill (
  ID int IDENTITY,
  PERSON_ID int,
  PRICE int
);

-- ALTER TABLE bill ADD CONSTRAINT fk_bill_to_person FOREIGN KEY (PERSON_ID) REFERENCES person (ID);