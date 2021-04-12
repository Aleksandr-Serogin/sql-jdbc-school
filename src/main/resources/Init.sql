\connect "dbname=university user=postgres password=1234"

CREATE SCHEMA IF NOT EXISTS university;
CREATE SEQUENCE IF NOT EXISTS university.global_seq START 1;

CREATE TABLE IF NOT EXISTS university.groups(
	group_id      SERIAL                    PRIMARY KEY,
	group_name    VARCHAR(100)              NOT NULL
	);
CREATE UNIQUE INDEX IF NOT EXISTS groups_unique_id_idx ON university.groups (group_id);

CREATE TABLE IF NOT EXISTS university.students(
	student_id         INTEGER       PRIMARY KEY DEFAULT nextval('university.global_seq'),
	group_id           INTEGER                           REFERENCES university.groups (group_id),
	first_name         VARCHAR(100)                      NOT NULL,
	last_name          VARCHAR(100)                      NOT NULL
    );
CREATE UNIQUE INDEX IF NOT EXISTS students_unique_id_idx ON university.students (student_id);

CREATE TABLE IF NOT EXISTS university.courses(
	course_id            SERIAL                    PRIMARY KEY,
	course_name          VARCHAR(100)                   NOT NULL,
    course_description   VARCHAR(300)                   NOT NULL
   );
CREATE UNIQUE INDEX IF NOT EXISTS courses_unique_id_idx ON university.courses (course_id);

CREATE TABLE IF NOT EXISTS university.students_courses(
    student_id           INTEGER     REFERENCES university.students (student_id) on delete cascade on update cascade,
    course_id            INTEGER     REFERENCES university.courses (course_id)   on delete cascade on update cascade
    );
CREATE OR REPLACE FUNCTION university.getGroupsWithLess_EqualsStudent(arg INTEGER)
    RETURNS TABLE
            (
                group_name   character varying(100),
                group_id     INTEGER,
                count_number BIGINT
            )
AS
$$
BEGIN
RETURN QUERY SELECT
    g.group_name,
    s.group_id,
    COUNT(s.group_id)
    FROM university.students s
    INNER JOIN university.groups g ON s.group_id = g.group_id
    GROUP BY s.group_id, g.group_name
    HAVING COUNT(s.group_id)<=arg;
END;
$$ LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION university.findStudentsByGroupName(arg text)
    RETURNS TABLE
            (
                student_id INTEGER,
                group_id   INTEGER,
                first_name VARCHAR,
                last_name  VARCHAR
            )
AS
$$
BEGIN
RETURN QUERY SELECT s.*
                 FROM university.courses as c
                          RIGHT JOIN university.students_courses as sc
                                     ON c.course_id = sc.course_id
                          LEFT JOIN university.students as s
                                    ON sc.student_id = s.student_id
                 GROUP BY s.student_id, course_name
                 HAVING c.course_name = arg;
END;
$$ LANGUAGE 'plpgsql';


CREATE OR REPLACE FUNCTION university.createStudent(id INTEGER, f_name VARCHAR, l_name VARCHAR)
    RETURNS VOID
AS
$$
BEGIN
INSERT INTO university.students (group_id, first_name, last_name)
VALUES (id, f_name, l_name);
END;
$$ LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION university.deleteStudentById(arg INTEGER)
    RETURNS VOID
AS
$$
BEGIN
DELETE FROM university.students WHERE student_id = arg;
END;
$$ LANGUAGE 'plpgsql';


CREATE OR REPLACE FUNCTION university.selectAllCourses()
    RETURNS TABLE
            (
                course_id   INTEGER,
                course_name VARCHAR(100),
                course_description  VARCHAR(300)
            )
AS
$$
BEGIN
RETURN QUERY SELECT
    c.* FROM university.courses c;
END;
$$ LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION university.setCourseForStudent(student INTEGER, course INTEGER)
    RETURNS VOID
AS
$$
BEGIN
INSERT INTO university.students_courses (student_id, course_id)
VALUES (student, course);
END;
$$ LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION university.deletedCourseFromStudent(student INTEGER, course INTEGER)
    RETURNS VOID
AS
$$
BEGIN
DELETE
FROM university.students_courses
WHERE student_id = student
  AND course_id = course;
END;
$$ LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION university.findCourseById(id INTEGER)
    RETURNS TABLE
            (
                course_id   INTEGER,
                course_name VARCHAR(100),
                curse_description VARCHAR(300)
            )
AS
$$
BEGIN
RETURN QUERY SELECT c.*
    FROM university.courses c WHERE c.course_id = id;
END;
$$ LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION university.createCourse(id INTEGER, c_name VARCHAR, c_description VARCHAR)
    RETURNS VOID
AS
$$
BEGIN
INSERT INTO university.courses (course_id, course_name, course_description)
VALUES (id, c_name, c_description);
END;
$$ LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION university.updateCourseById(id INTEGER, name VARCHAR, description VARCHAR)
    RETURNS VOID
AS
$$
BEGIN
UPDATE university.courses SET course_name = name , course_description = description WHERE course_id = id;
END;
$$ LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION university.deletedCourseById(id INTEGER)
    RETURNS VOID
AS
$$
BEGIN
DELETE
FROM university.courses
WHERE course_id = id;
END;
$$ LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION university.createGroup(id INTEGER, g_name VARCHAR)
    RETURNS VOID
AS
$$
BEGIN
INSERT INTO university.groups (group_id, group_name)
VALUES (id, g_name);
END;
$$ LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION university.findGroupById(id INTEGER)
    RETURNS TABLE
            (
                group_id   INTEGER,
                group_name VARCHAR(100)
            )
AS
$$
BEGIN
RETURN QUERY SELECT g.*
                 FROM university.groups g WHERE g.group_id = id;
END;
$$ LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION university.findAllGroup()
    RETURNS TABLE
            (
                group_id   INTEGER,
                group_name VARCHAR(100)
            )
AS
$$
BEGIN
RETURN QUERY SELECT g.*
                 FROM university.groups g;
END;
$$ LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION university.updateGroupById(id INTEGER, name VARCHAR)
    RETURNS VOID
AS
$$
BEGIN
UPDATE university.groups SET group_name = name  WHERE group_id = id;
END;
$$ LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION university.deletedGroupById(id INTEGER)
    RETURNS VOID
AS
$$
BEGIN
DELETE
FROM university.groups
WHERE group_id = id;
END;
$$ LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION university.findStudentById(id INTEGER)
    RETURNS TABLE
            (
                student_id      INTEGER,
                group_id        INTEGER,
                first_name      VARCHAR(100),
                last_name       VARCHAR(100)
            )
AS
$$
BEGIN
RETURN QUERY SELECT s.*
                 FROM university.students s WHERE s.student_id = id;
END;
$$ LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION university.findAllStudents()
    RETURNS TABLE
            (
                student_id      INTEGER,
                group_id        INTEGER,
                first_name      VARCHAR(100),
                last_name       VARCHAR(100)
            )
AS
$$
BEGIN
RETURN QUERY SELECT s.*
                 FROM university.students s;
END;
$$ LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION university.updateStudentById(id INTEGER, g_id INTEGER, f_name VARCHAR, l_name VARCHAR)
    RETURNS VOID
AS
$$
BEGIN
UPDATE university.students SET group_id = g_id, first_name = f_name, last_name = l_name   WHERE student_id = id;
END;
$$ LANGUAGE 'plpgsql';