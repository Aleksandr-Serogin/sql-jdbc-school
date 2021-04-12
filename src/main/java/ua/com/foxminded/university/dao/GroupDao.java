package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.domain.DaoGroup;

import java.util.List;

public interface GroupDao extends GeneralDao <DaoGroup> {

    List<DaoGroup> getGroupsWithLess_EqualsStudent(String nameProperties, int number);
}
