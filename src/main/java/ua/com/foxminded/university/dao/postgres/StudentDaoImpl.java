package ua.com.foxminded.university.dao.postgres;

import ua.com.foxminded.university.dao.DaoFactory;
import ua.com.foxminded.university.dao.StudentDao;
import ua.com.foxminded.university.domain.entity.Student;
import ua.com.foxminded.university.util.ReadSqlFile;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public record StudentDaoImpl(DaoFactory daoFactory) implements StudentDao {

    @Override
    public List<Student> findStudentsByCourseName(String course_name) {
        ReadSqlFile readSqlFile = new ReadSqlFile();
        String sql = readSqlFile.readFile("Find_all_students_related_to_course_with_given_name.sql");
        Connection connection = null;
        PreparedStatement preparedStatement;
        ResultSet resultSet = null;
        Student student = new Student();
        List<Student> listStudents = new ArrayList<>();
        try {
            connection = daoFactory.getConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, course_name);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int student_id = resultSet.getInt("student_id");
                int group_id = resultSet.getInt("group_id");
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                student.setStudentId(student_id);
                student.setGroupId(group_id);
                student.setLastName(last_name);
                student.setFirstName(first_name);
                listStudents.add(student);
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
        return listStudents;
    }

    @Override
    public int delete(int id) {
        ReadSqlFile readSqlFile = new ReadSqlFile();
        String sql = readSqlFile.readFile("Delete_student_by_id.sql");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
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

    @Override
    public void create(Student student) {
        ReadSqlFile readSqlFile = new ReadSqlFile();
        String sql = readSqlFile.readFile("Create_student.sql");
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement;
        try {
            connection = daoFactory.getConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, student.getGroupId());
            preparedStatement.setString(2, student.getFirstName());
            preparedStatement.setString(3, student.getLastName());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                student.setStudentId(resultSet.getInt(1));
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
    }

    @Override
    public List<Student> findById(int id) {
        ReadSqlFile readSqlFile = new ReadSqlFile();
        String sql = readSqlFile.readFile("Find_student_by_id.sql");
        Connection connection = null;
        PreparedStatement preparedStatement;
        ResultSet resultSet = null;
        List<Student> students = new ArrayList<>();
        try {
            connection = daoFactory.getConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int student_id = resultSet.getInt("student_id");
                int group_id = resultSet.getInt("group_id");
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                Student student = new Student();
                student.setStudentId(student_id);
                student.setGroupId(group_id);
                student.setLastName(last_name);
                student.setFirstName(first_name);
                students.add(student);
            }
            if (students.isEmpty()) {
                throw new RuntimeException("Not found eny student with id: " + id);
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
        return students;
    }

    @Override
    public List<Student> findAll() {
        ReadSqlFile readSqlFile = new ReadSqlFile();
        String sql = readSqlFile.readFile("Find_all_Students.sql");
        Connection connection = null;
        PreparedStatement preparedStatement;
        ResultSet resultSet = null;
        List<Student> students = new ArrayList<>();
        try {
            connection = daoFactory.getConnect();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int student_id = resultSet.getInt("student_id");
                int group_id = resultSet.getInt("group_id");
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                Student student = new Student();
                student.setStudentId(student_id);
                student.setGroupId(group_id);
                student.setLastName(last_name);
                student.setFirstName(first_name);
                students.add(student);
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
        return students;
    }

    @Override
    public void update(Student student) {
        ReadSqlFile readSqlFile = new ReadSqlFile();
        String sql = readSqlFile.readFile("Update_student_by_id.sql");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = daoFactory.getConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, student.getStudentId());
            if (student.getGroupId() == 0) {
                preparedStatement.setNull(2, Types.NULL);
            } else {
                preparedStatement.setInt(2, student.getGroupId());
            }
            preparedStatement.setString(3, student.getFirstName());
            preparedStatement.setString(4, student.getLastName());
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
}