CREATE SCHEMA IF NOT EXISTS university;

CREATE TABLE IF NOT EXISTS university.groups(
      group_id      INTEGER                   PRIMARY KEY,
      group_name    VARCHAR(100)              NOT NULL
);
CREATE UNIQUE INDEX IF NOT EXISTS groups_unique_id_idx ON university.groups (group_id);

CREATE TABLE IF NOT EXISTS university.students(
    student_id         INTEGER           AUTO_INCREMENT,
    group_id           INTEGER           REFERENCES university.groups (group_id),
    first_name         VARCHAR(100)      NOT NULL,
    last_name          VARCHAR(100)      NOT NULL
);
CREATE UNIQUE INDEX IF NOT EXISTS students_unique_id_idx ON university.students (student_id);

CREATE TABLE IF NOT EXISTS university.courses(
     course_id            INTEGER                    PRIMARY KEY,
     course_name          VARCHAR(100)                   NOT NULL,
     course_description   VARCHAR(300)                   NOT NULL
);
CREATE UNIQUE INDEX IF NOT EXISTS courses_unique_id_idx ON university.courses (course_id);

CREATE TABLE IF NOT EXISTS university.students_courses(
    student_id           INTEGER     REFERENCES university.students (student_id) on delete cascade on update cascade,
    course_id            INTEGER     REFERENCES university.courses (course_id)   on delete cascade on update cascade
);