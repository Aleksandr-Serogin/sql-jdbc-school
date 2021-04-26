package ua.com.foxminded.university.dao;

import java.util.List;

public interface GeneralDao<T> {

    void create(T t);

    Object findById(int id);

    List<T> findAll();

    void update(T t);

    int delete(int id);
}
