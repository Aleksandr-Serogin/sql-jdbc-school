CREATE OR REPLACE FUNCTION university.createCourse(c_name VARCHAR, c_description VARCHAR, OUT id int)
AS
$$
BEGIN
    INSERT INTO university.courses (course_name, course_description)
    VALUES (c_name, c_description)
    RETURNING course_id INTO  id;
END;
$$ LANGUAGE 'plpgsql';

DROP FUNCTION university.createcourse(character varying,character varying);

SELECT * FROM university.createcourse('sacc', 'fulsac');