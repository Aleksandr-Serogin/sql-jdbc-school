package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.domain.entity.Student;

import java.util.List;

public interface StudentDao extends GeneralDao <Student>  {

    List<Student> findStudentsByCourseName(String group_name);

}