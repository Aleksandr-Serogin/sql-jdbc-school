CREATE SCHEMA IF NOT EXISTS university;

CREATE TABLE IF NOT EXISTS university.groups
(
    group_id   INT GENERATED ALWAYS AS IDENTITY,
    group_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (group_id)
);

CREATE TABLE IF NOT EXISTS university.students
(
    student_id INT GENERATED ALWAYS AS IDENTITY,
    group_id   INT,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    PRIMARY KEY (student_id),
    CONSTRAINT fk_groups
        FOREIGN KEY (group_id)
            REFERENCES university.groups (group_id)
            ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS university.courses
(
    course_id          INT GENERATED ALWAYS AS IDENTITY,
    course_name        VARCHAR(255) NOT NULL,
    course_description VARCHAR(300) NOT NULL,
    PRIMARY KEY (course_id)
);

CREATE TABLE IF NOT EXISTS university.students_courses
(
    student_id INT REFERENCES university.students (student_id) ON DELETE CASCADE ON UPDATE CASCADE,
    course_id  INT REFERENCES university.courses (course_id) ON DELETE CASCADE ON UPDATE CASCADE
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
    RETURN QUERY SELECT g.group_name,
                        s.group_id,
                        COUNT(s.group_id)
                 FROM university.students s
                          INNER JOIN university.groups g ON s.group_id = g.group_id
                 GROUP BY s.group_id, g.group_name
                 HAVING COUNT(s.group_id) <= arg;
END;
$$ LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION university.findStudentsByCourseName(arg text)
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

CREATE OR REPLACE FUNCTION university.findCoursesByStudentId(id INTEGER)
    RETURNS TABLE
            (
                course_id          INTEGER,
                course_name        VARCHAR,
                course_description VARCHAR
            )
AS
$$
BEGIN
    RETURN QUERY SELECT c.*
                 FROM university.courses as c
                          LEFT JOIN university.students_courses as sc
                                    ON c.course_id = sc.course_id
                          LEFT JOIN university.students as s
                                    ON sc.student_id = s.student_id
                 GROUP BY c.course_id, c.course_name, sc.student_id
                 HAVING sc.student_id = id;
END;
$$ LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION university.createStudent(id INTEGER, f_name VARCHAR, l_name VARCHAR, OUT s_id int)
AS
$$
BEGIN
    INSERT INTO university.students (group_id, first_name, last_name)
    VALUES (id, f_name, l_name)
    RETURNING student_id INTO s_id;
END;
$$ LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION university.findAllCourses()
    RETURNS TABLE
            (
                course_id          INTEGER,
                course_name        VARCHAR(100),
                course_description VARCHAR(300)
            )
AS
$$
BEGIN
    RETURN QUERY SELECT c.*
                 FROM university.courses c;
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

CREATE OR REPLACE FUNCTION university.findCourseById(id INTEGER)
    RETURNS TABLE
            (
                course_id          INTEGER,
                course_name        VARCHAR(100),
                course_description VARCHAR(300)
            )
AS
$$
BEGIN
    RETURN QUERY SELECT c.*
                 FROM university.courses c
                 WHERE c.course_id = id;
END;
$$ LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION university.createCourse(name VARCHAR, description VARCHAR, OUT id int)
AS
$$
BEGIN
    INSERT INTO university.courses (course_name, course_description)
    VALUES (name, description)
    RETURNING course_id INTO id;
END;
$$ LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION university.updateCourseById(id INTEGER, name VARCHAR, description VARCHAR)
    RETURNS VOID
AS
$$
BEGIN
    UPDATE university.courses SET course_name = name, course_description = description WHERE course_id = id;
END;
$$ LANGUAGE 'plpgsql';


CREATE OR REPLACE FUNCTION university.createGroup(name VARCHAR, OUT id int)
AS
$$
BEGIN
    INSERT INTO university.groups (group_name)
    VALUES (name)
    RETURNING group_id INTO id;
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
                 FROM university.groups g
                 WHERE g.group_id = id;
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
    UPDATE university.groups SET group_name = name WHERE group_id = id;
END;
$$ LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION university.deleteGroupById(id INTEGER)
    RETURNS int
AS
$$
DECLARE
    deleted_g int;
BEGIN
    DELETE
    FROM university.groups
    WHERE group_id = id;
    GET DIAGNOSTICS deleted_g = ROW_COUNT;
    RETURN deleted_g;
END
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION university.deleteStudentById(id INTEGER)
    RETURNS int
AS
$$
DECLARE
    deleted int;
BEGIN
    DELETE
    FROM university.students
    WHERE student_id = id;
    GET DIAGNOSTICS deleted = ROW_COUNT;
    RETURN deleted;
END
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION university.deleteCourseById(id INTEGER)
    RETURNS int
AS
$$
DECLARE
    deleted int;
BEGIN
    DELETE
    FROM university.courses
    WHERE course_id = id;
    GET DIAGNOSTICS deleted = ROW_COUNT;
    RETURN deleted;
END
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION university.deleteCourseFromStudent(student INTEGER, course INTEGER)
    RETURNS int
AS
$$
DECLARE
    deleted int;
BEGIN
    DELETE
    FROM university.students_courses
    WHERE student_id = student
      AND course_id = course;
    GET DIAGNOSTICS deleted = ROW_COUNT;
    RETURN deleted;
END
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION university.findStudentById(id INTEGER)
    RETURNS TABLE
            (
                student_id INTEGER,
                group_id   INTEGER,
                first_name VARCHAR(100),
                last_name  VARCHAR(100)
            )
AS
$$
BEGIN
    RETURN QUERY SELECT s.*
                 FROM university.students s
                 WHERE s.student_id = id;
END;
$$ LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION university.findAllStudents()
    RETURNS TABLE
            (
                student_id INTEGER,
                group_id   INTEGER,
                first_name VARCHAR(100),
                last_name  VARCHAR(100)
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
    UPDATE university.students SET group_id = g_id, first_name = f_name, last_name = l_name WHERE student_id = id;
END;
$$ LANGUAGE 'plpgsql';