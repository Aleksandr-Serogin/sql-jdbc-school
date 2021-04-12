package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.domain.DaoCourse;

import java.util.List;

public interface GeneralDao<T> {

    void create(String nameProperties, T t);

    Object findById(String nameProperties, int id);

    List<T> findAll(String nameProperties);

    void update(String nameProperties, T t);

    void delete(String nameProperties, int id);
}
