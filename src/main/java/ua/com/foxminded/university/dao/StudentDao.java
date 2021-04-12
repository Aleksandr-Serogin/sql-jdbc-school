package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.domain.DaoStudent;

import java.util.List;

public interface StudentDao extends GeneralDao <DaoStudent>  {
    List<DaoStudent> findStudentsByCourse(String nameProperties, String group_name);
}