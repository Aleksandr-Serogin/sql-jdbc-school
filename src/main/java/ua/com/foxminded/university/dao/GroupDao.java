package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.domain.entity.Group;

import java.util.List;

public interface GroupDao extends GeneralDao <Group> {

    List<Group> getGroupsWithLess_EqualsStudent(int number);
}
