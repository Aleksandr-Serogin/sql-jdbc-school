package ua.com.foxminded.university.dao.postgres;

import ua.com.foxminded.university.dao.DaoFactory;
import ua.com.foxminded.university.dao.GroupDao;
import ua.com.foxminded.university.util.ReadSqlFile;
import ua.com.foxminded.university.domain.entity.Group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: Apr 27-2021 Class get connect to data base,
 * return and work with class group object
 *
 * @author Aleksandr Serohin
 * @version 1.0001
 */
public record GroupDaoImpl(DaoFactory daoFactory) implements GroupDao {

    /**
     * @param number number student in group for search
     * @return list find group
     */
    @Override
    public List<Group> getGroupsWithLess_EqualsStudent(int number) {
        ReadSqlFile readSqlFile = new ReadSqlFile();
        String sql = readSqlFile.readFile("Finds_if_any_of_the_groups_has_less_than_equals_students.sql");
        Connection connection = null;
        PreparedStatement preparedStatement;
        ResultSet resultSet = null;
        List<Group> groups = new ArrayList<>();
        try {
            connection = daoFactory.getConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, number);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Group group = new Group();
                int group_id = resultSet.getInt("group_id");
                String group_name = resultSet.getString("group_name");
                group.setGroupId(group_id);
                group.setGroupName(group_name);
                groups.add(group);
            }
            if (groups.isEmpty()) {
                throw new RuntimeException("Not found eny group with number student: " + number);
            }
        } catch (SQLException throwables) {
            throw new RuntimeException("Cannot find eny groups", throwables);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return groups;
    }

    /**
     * @param group Class group object  with parameter to create group in db
     */
    @Override
    public void create(Group group) {
        ReadSqlFile readSqlFile = new ReadSqlFile();
        String sql = readSqlFile.readFile("Create_group.sql");
        Connection connection = null;
        PreparedStatement preparedStatement;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, group.getGroupName());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                group.setGroupId(resultSet.getInt(1));
            }
        } catch (SQLException throwables) {
            throw new RuntimeException("Cannot create group", throwables);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /**
     * @param id Group id for search
     * @return list of found groups
     */
    @Override
    public List<Group> findById(int id) {
        ReadSqlFile readSqlFile = new ReadSqlFile();
        String sql = readSqlFile.readFile("Find_group_by_id.sql");
        Connection connection = null;
        List<Group> groups = new ArrayList<>();
        PreparedStatement preparedStatement;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int group_id = resultSet.getInt("group_id");
                String group_name = resultSet.getString("group_name");
                Group group = new Group();
                group.setGroupId(group_id);
                group.setGroupName(group_name);
                groups.add(group);
            }
            if (groups.isEmpty()) {
                throw new RuntimeException("Not found eny group with id: " + id);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return groups;
    }

    /**
     * @return list of found groups
     */
    @Override
    public List<Group> findAll() {
        ReadSqlFile readSqlFile = new ReadSqlFile();
        String sql = readSqlFile.readFile("Find_all_group.sql");
        Connection connection = null;
        PreparedStatement preparedStatement;
        ResultSet resultSet = null;
        List<Group> groups = new ArrayList<>();
        try {
            connection = daoFactory.getConnect();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int group_id = resultSet.getInt("group_id");
                String group_name = resultSet.getString("group_name");
                Group group = new Group();
                group.setGroupId(group_id);
                group.setGroupName(group_name);
                groups.add(group);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return groups;
    }

    /**
     * @param group Class group object  with parameter to update group in db
     */
    @Override
    public void update(Group group) {
        ReadSqlFile readSqlFile = new ReadSqlFile();
        String sql = readSqlFile.readFile("Update_group_by_id.sql");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = daoFactory.getConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, group.getGroupId());
            preparedStatement.setString(2, group.getGroupName());
            preparedStatement.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /**
     * @param id Group id for delete
     * @return number of deleted rows
     */
    @Override
    public int delete(int id) {
        ReadSqlFile readSqlFile = new ReadSqlFile();
        String sql = readSqlFile.readFile("Delete_group_by_id.sql");
        Connection connection = null;
        PreparedStatement preparedStatement;
        int numberOfDeletedRows = 0;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                numberOfDeletedRows = resultSet.getInt(1);
            }
        } catch (SQLException throwables) {
            throw new RuntimeException("Cannot delete course", throwables);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return numberOfDeletedRows;
    }
}