package ua.com.foxminded.university.dao.postgres;

import ua.com.foxminded.university.dao.DaoFactory;
import ua.com.foxminded.university.domain.DaoCourse;
import ua.com.foxminded.university.util.ReadSqlFile;
import ua.com.foxminded.university.dao.StudentDao;
import ua.com.foxminded.university.domain.DaoStudent;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgreSqlStudentDao implements StudentDao {

    @Override
    public List<DaoStudent> findStudentsByCourse(String nameProperties, String group_name) {
        ReadSqlFile readSqlFile = new ReadSqlFile();
        String sql = readSqlFile.readFile("Find_all_students_related_to_course_with_given_name.sql");
        DaoFactory daoFactory = new DaoFactory();
        Connection connection = null;
        PreparedStatement preparedStatement;
        ResultSet resultSet = null;
        DaoStudent daoStudent = new DaoStudent();
        List<DaoStudent> listStudents = new ArrayList<>();
        try {
            connection = daoFactory.getConnect(nameProperties);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, group_name);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int student_id = resultSet.getInt("student_id");
                int group_id = resultSet.getInt("group_id");
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                daoStudent.setStudentId(student_id);
                daoStudent.setGroupId(group_id);
                daoStudent.setLastName(last_name);
                daoStudent.setFirstName(first_name);
                listStudents.add(daoStudent);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                assert connection != null;
                connection.close();
                assert resultSet != null;
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return listStudents;
    }

    @Override
    public void delete(String nameProperties, int id) {
        ReadSqlFile readSqlFile = new ReadSqlFile();
        String sql = readSqlFile.readFile("Delete_student_by_id.sql");
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

    @Override
    public void create(String nameProperties, DaoStudent daoStudent) {
        ReadSqlFile readSqlFile = new ReadSqlFile();
        String sql = readSqlFile.readFile("Create_student.sql");
        DaoFactory daoFactory = new DaoFactory();
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement;
        try {
            connection = daoFactory.getConnect(nameProperties);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, daoStudent.getGroupId());
            preparedStatement.setString(2, daoStudent.getFirstName());
            preparedStatement.setString(3, daoStudent.getLastName());
            preparedStatement.execute();
            resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()) {
                daoStudent.setStudentId(resultSet.getInt(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                assert connection != null;
                connection.close();
                assert resultSet != null;
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public DaoStudent findById(String nameProperties, int id) {
        ReadSqlFile readSqlFile = new ReadSqlFile();
        String sql = readSqlFile.readFile("Find_student_by_id.sql");
        DaoFactory daoFactory = new DaoFactory();
        Connection connection = null;
        PreparedStatement preparedStatement;
        ResultSet resultSet = null;
        List<DaoStudent> daoStudents = new ArrayList<>();
        try {
            connection = daoFactory.getConnect(nameProperties);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int student_id = resultSet.getInt("student_id");
                int group_id = resultSet.getInt("group_id");
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                DaoStudent daoStudent = new DaoStudent();
                daoStudent.setStudentId(student_id);
                daoStudent.setGroupId(group_id);
                daoStudent.setLastName(last_name);
                daoStudent.setFirstName(first_name);
                daoStudents.add(daoStudent);
            }
            if (daoStudents.isEmpty()) {
                throw new RuntimeException("Not found eny student with id: " + id);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                assert connection != null;
                connection.close();
                assert resultSet != null;
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return daoStudents.get(0);
    }

    @Override
    public List<DaoStudent> findAll(String nameProperties) {
        ReadSqlFile readSqlFile = new ReadSqlFile();
        String sql = readSqlFile.readFile("Find_all_Students.sql");
        DaoFactory daoFactory = new DaoFactory();
        Connection connection = null;
        PreparedStatement preparedStatement;
        ResultSet resultSet = null;
        List<DaoStudent> daoStudents = new ArrayList<>();
        try {
            connection = daoFactory.getConnect(nameProperties);
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int student_id = resultSet.getInt("student_id");
                int group_id = resultSet.getInt("group_id");
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                DaoStudent daoStudent = new DaoStudent();
                daoStudent.setStudentId(student_id);
                daoStudent.setGroupId(group_id);
                daoStudent.setLastName(last_name);
                daoStudent.setFirstName(first_name);
                daoStudents.add(daoStudent);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                assert connection != null;
                connection.close();
                assert resultSet != null;
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return daoStudents;
    }

    @Override
    public void update(String nameProperties, DaoStudent daoStudent) {
        ReadSqlFile readSqlFile = new ReadSqlFile();
        String sql = readSqlFile.readFile("Update_student_by_id.sql");
        DaoFactory daoFactory = new DaoFactory();
        Connection connection = null;
        PreparedStatement preparedStatement;
        try {
            connection = daoFactory.getConnect(nameProperties);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, daoStudent.getStudentId());
            if (daoStudent.getGroupId() == 0) {
                preparedStatement.setNull(2, Types.NULL);
            } else {
                preparedStatement.setInt(2, daoStudent.getGroupId());
            }
            preparedStatement.setString(3, daoStudent.getFirstName());
            preparedStatement.setString(4, daoStudent.getLastName());
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