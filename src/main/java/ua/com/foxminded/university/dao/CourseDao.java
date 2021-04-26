package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.domain.entity.Course;

import java.util.List;

public interface CourseDao extends GeneralDao<Course> {

    void setCourseStudent(int student_id, int course_id);

    int deletedCourseStudent(int student_id, int course_id);

    List<Course> findStudentCourseByStudentID(int id);
}
