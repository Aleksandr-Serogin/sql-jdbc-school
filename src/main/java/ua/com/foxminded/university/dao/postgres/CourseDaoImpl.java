package ua.com.foxminded.university.dao.postgres;

import ua.com.foxminded.university.dao.CourseDao;
import ua.com.foxminded.university.dao.DaoFactory;
import ua.com.foxminded.university.domain.entity.Course;
import ua.com.foxminded.university.util.ReadSqlFile;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public record CourseDaoImpl(DaoFactory daoFactory) implements CourseDao {

    @Override
    public void setCourseStudent(int student_id, int course_id) {
        ReadSqlFile readSqlFile = new ReadSqlFile();
        String sql = readSqlFile.readFile("Set_student_course.sql");
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = daoFactory.getConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, student_id);
            preparedStatement.setInt(2, course_id);
            preparedStatement.executeQuery();
        } catch (SQLException throwables) {
            throw new RuntimeException("Cannot set course for student", throwables);
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

    @Override
    public int deletedCourseStudent(int student_id, int course_id) {
        ReadSqlFile readSqlFile = new ReadSqlFile();
        String sql = readSqlFile.readFile("Delete_the_student_from_one_of_his_or_her_courses.sql");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int numberOfDeletedRows = 0;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, student_id);
            preparedStatement.setInt(2, course_id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                numberOfDeletedRows = resultSet.getInt(1);
            }
        } catch (SQLException throwables) {
            throw new RuntimeException("Cannot delete course for student", throwables);
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
    public List<Course> findAll() {
        ReadSqlFile readSqlFile = new ReadSqlFile();
        String sql = readSqlFile.readFile("Find_all_courses.sql");
        Connection connection = null;
        PreparedStatement preparedStatement;
        ResultSet resultSet = null;
        List<Course> courses = new ArrayList<>();
        try {
            connection = daoFactory.getConnect();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int course_id = resultSet.getInt("course_id");
                String course_name = resultSet.getString("course_name");
                String course_description = resultSet.getString("course_description");
                Course course = new Course();
                course.setCourseId(course_id);
                course.setCourseName(course_name);
                course.setCourseDescription(course_description);
                courses.add(course);
            }
        } catch (SQLException throwables) {
            throw new RuntimeException("Cannot find courses", throwables);
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
        return courses;
    }

    @Override
    public List<Course> findStudentCourseByStudentID(int id) {
        ReadSqlFile readSqlFile = new ReadSqlFile();
        String sql = readSqlFile.readFile("Find_courses_by_student_Id.sql");
        Connection connection = null;
        PreparedStatement preparedStatement;
        ResultSet resultSet = null;
        List<Course> courses = new ArrayList<>();
        try {
            connection = daoFactory.getConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int course_id = resultSet.getInt("course_id");
                String course_name = resultSet.getString("course_name");
                String course_description = resultSet.getString("course_description");
                Course course = new Course();
                course.setCourseId(course_id);
                course.setCourseName(course_name);
                course.setCourseDescription(course_description);
                courses.add(course);
            }
        } catch (SQLException throwables) {
            throw new RuntimeException("Cannot find courses", throwables);
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
        return courses;
    }

    @Override
    public void create(Course course) {
        ReadSqlFile readSqlFile = new ReadSqlFile();
        String sql = readSqlFile.readFile("Create_course.sql");
        Connection connection = null;
        PreparedStatement preparedStatement;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, course.getCourseName());
            preparedStatement.setString(2, course.getCourseDescription());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                course.setCourseId(resultSet.getInt(1));
            }
        } catch (SQLException throwables) {
            throw new RuntimeException("Cannot create course", throwables);
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
    public List<Course> findById(int id) {
        ReadSqlFile readSqlFile = new ReadSqlFile();
        String sql = readSqlFile.readFile("Find_course_by_id.sql");
        Connection connection = null;
        List<Course> courses = new ArrayList<>();
        PreparedStatement preparedStatement;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int course_id = resultSet.getInt("course_id");
                String course_name = resultSet.getString("course_name");
                String course_description = resultSet.getString("course_description");
                Course course = new Course();
                course.setCourseId(course_id);
                course.setCourseName(course_name);
                course.setCourseDescription(course_description);
                courses.add(course);
            }
            if (courses.isEmpty()) {
                throw new RuntimeException("Not found eny course with id: " + id);
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
        return courses;
    }

    @Override
    public void update(Course course) {
        ReadSqlFile readSqlFile = new ReadSqlFile();
        String sql = readSqlFile.readFile("Update_course_by_id.sql");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = daoFactory.getConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, course.getCourseId());
            preparedStatement.setString(2, course.getCourseName());
            preparedStatement.setString(3, course.getCourseDescription());
            preparedStatement.executeQuery();
        } catch (SQLException throwables) {
            throw new RuntimeException("Cannot update course", throwables);
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

    @Override
    public int delete(int id) {
        ReadSqlFile readSqlFile = new ReadSqlFile();
        String sql = readSqlFile.readFile("Delete_course_by_id.sql");
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