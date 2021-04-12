package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.domain.DaoCourse;

public interface CourseDao extends GeneralDao<DaoCourse> {

    void setCourseStudent(String nameProperties, int student_id, int course_id);

    void deletedCourseStudent(String nameProperties, int student_id, int course_id);
}
