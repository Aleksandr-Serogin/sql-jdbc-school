package ua.com.foxminded.university.dao.postgres;

import ua.com.foxminded.university.dao.DaoFactory;
import ua.com.foxminded.university.dao.GroupDao;
import ua.com.foxminded.university.domain.DaoCourse;
import ua.com.foxminded.university.util.ReadSqlFile;
import ua.com.foxminded.university.domain.DaoGroup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostgreSqlGroupDao implements GroupDao {

    @Override
    public List<DaoGroup> getGroupsWithLess_EqualsStudent(String nameProperties, int number) {
        ReadSqlFile readSqlFile = new ReadSqlFile();
        String sql = readSqlFile.readFile("Finds_if_any_of_the_groups_has_less_than_equals_students.sql");
        DaoFactory daoFactory = new DaoFactory();
        Connection connection = null;
        PreparedStatement preparedStatement;
        ResultSet resultSet = null;
        DaoGroup daoGroup = new DaoGroup();
        List<DaoGroup> listGroups = new ArrayList<>();
        try {
            connection = daoFactory.getConnect(nameProperties);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, number);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int group_id = resultSet.getInt("group_id");
                String group_name = resultSet.getString("group_name");
                daoGroup.setGroupId(group_id);
                daoGroup.setGroupName(group_name);
                listGroups.add(daoGroup);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                assert resultSet != null;
                resultSet.close();
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return listGroups;
    }

    @Override
    public void create(String nameProperties, DaoGroup daoGroup) {
        ReadSqlFile readSqlFile = new ReadSqlFile();
        String sql = readSqlFile.readFile("Create_group.sql");
        DaoFactory daoFactory = new DaoFactory();
        Connection connection = null;
        PreparedStatement preparedStatement;
        try {
            connection = daoFactory.getConnect(nameProperties);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, daoGroup.getGroupId());
            preparedStatement.setString(2, daoGroup.getGroupName());
            preparedStatement.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                assert connection != null;
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public DaoGroup findById(String nameProperties, int id) {
        ReadSqlFile readSqlFile = new ReadSqlFile();
        String sql = readSqlFile.readFile("Find_group_by_id.sql");
        DaoFactory daoFactory = new DaoFactory();
        Connection connection = null;
        List<DaoGroup> daoGroups = new ArrayList<>();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            connection = daoFactory.getConnect(nameProperties);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int group_id = resultSet.getInt("group_id");
                String group_name = resultSet.getString("group_name");
                DaoGroup daoGroup = new DaoGroup();
                daoGroup.setGroupId(group_id);
                daoGroup.setGroupName(group_name);
                daoGroups.add(daoGroup);
            }
            if (daoGroups.isEmpty()){
                throw new RuntimeException("Not found eny group with id: " +id);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                assert connection != null;
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return daoGroups.get(0);
    }

    @Override
    public List<DaoGroup> findAll(String nameProperties) {
        ReadSqlFile readSqlFile = new ReadSqlFile();
        String sql = readSqlFile.readFile("Find_all_group.sql");
        DaoFactory daoFactory = new DaoFactory();
        Connection connection = null;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        List<DaoGroup> daoGroups = new ArrayList<>();
        try {
            connection = daoFactory.getConnect(nameProperties);
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int group_id = resultSet.getInt("group_id");
                String group_name = resultSet.getString("group_name");
                DaoGroup daoGroup = new DaoGroup();
                daoGroup.setGroupId(group_id);
                daoGroup.setGroupName(group_name);
                daoGroups.add(daoGroup);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                assert connection != null;
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return daoGroups;
    }

    @Override
    public void update(String nameProperties, DaoGroup daoGroup) {
        ReadSqlFile readSqlFile = new ReadSqlFile();
        String sql = readSqlFile.readFile("Update_group_by_id.sql");
        DaoFactory daoFactory = new DaoFactory();
        Connection connection = null;
        PreparedStatement preparedStatement;
        try {
            connection = daoFactory.getConnect(nameProperties);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, daoGroup.getGroupName());
            preparedStatement.setInt(2, daoGroup.getGroupId());
            preparedStatement.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                assert connection != null;
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public void delete(String nameProperties, int id) {
        ReadSqlFile readSqlFile = new ReadSqlFile();
        String sql = readSqlFile.readFile("Delete_group_by_id.sql");
        DaoFactory daoFactory = new DaoFactory();
        Connection connection = null;
        PreparedStatement preparedStatement;
        try {
            connection = daoFactory.getConnect(nameProperties);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                assert connection != null;
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}