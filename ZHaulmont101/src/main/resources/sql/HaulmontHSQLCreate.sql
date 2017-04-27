DROP TABLE students IF EXISTS;
DROP TABLE groups IF EXISTS;

CREATE TABLE groups (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1) PRIMARY KEY,
  number INTEGER NOT NULL,
  faculty_name VARCHAR(64),
  UNIQUE (number)
 ); 
 
CREATE TABLE students (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1) PRIMARY KEY,
  last_name VARCHAR(64) DEFAULT NULL,
  first_name VARCHAR(64) DEFAULT NULL,
  middle_name VARCHAR(64) DEFAULT NULL,
  date_of_birth DATE,
  student_group INTEGER NOT NULL,
  FOREIGN KEY(student_group) REFERENCES groups(number)
);

COMMIT;
