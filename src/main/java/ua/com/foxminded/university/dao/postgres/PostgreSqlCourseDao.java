package ua.com.foxminded.university.dao.postgres;

import ua.com.foxminded.university.dao.CourseDao;
import ua.com.foxminded.university.dao.DaoFactory;
import ua.com.foxminded.university.util.ReadSqlFile;
import ua.com.foxminded.university.domain.DaoCourse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public  class  PostgreSqlCourseDao implements CourseDao {

    @Override
    public void setCourseStudent(String nameProperties, int student_id, int course_id) {
        ReadSqlFile readSqlFile = new ReadSqlFile ();
        String sql = readSqlFile.readFile ("Set_student_course.sql");
        DaoFactory daoFactory = new DaoFactory ();
        Connection connection = null;
        PreparedStatement preparedStatement;
        try {
            connection = daoFactory.getConnect ( nameProperties );
            preparedStatement = connection.prepareStatement ( sql );
            preparedStatement.setInt ( 1, student_id );
            preparedStatement.setInt ( 2, course_id );
            preparedStatement.executeQuery ();
        } catch (SQLException throwables) {
            throwables.printStackTrace ();
        } finally {
            try {
                assert connection != null;
                connection.close ();
            } catch (SQLException throwables) {
                throwables.printStackTrace ();
            }
        }
    }

    @Override
    public void deletedCourseStudent(String nameProperties, int student_id, int course_id) {
        ReadSqlFile readSqlFile = new ReadSqlFile ();
        String sql = readSqlFile.readFile ("Delete_the_student_from_one_of_his_or_her_courses.sql");
        DaoFactory daoFactory = new DaoFactory ();
        Connection connection = null;
        PreparedStatement preparedStatement;
        try {
            connection = daoFactory.getConnect ( nameProperties );
            preparedStatement = connection.prepareStatement ( sql );
            preparedStatement.setInt ( 1, student_id );
            preparedStatement.setInt ( 2, course_id );
            preparedStatement.executeQuery ();
        } catch (SQLException throwables) {
            throwables.printStackTrace ();
        } finally {
            try {
                assert connection != null;
                connection.close ();
            } catch (SQLException throwables) {
                throwables.printStackTrace ();
            }
        }
    }

    @Override
    public List<DaoCourse> findAll(String nameProperties) {
        ReadSqlFile readSqlFile = new ReadSqlFile ();
        String sql = readSqlFile.readFile ("Find_all_courses.sql");
        DaoFactory daoFactory = new DaoFactory ();
        Connection connection = null;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        List<DaoCourse> daoCourses = new ArrayList<> ();
        try {
            connection = daoFactory.getConnect ( nameProperties );
            preparedStatement = connection.prepareStatement ( sql );
            resultSet = preparedStatement.executeQuery ();
            while (resultSet.next ()) {
                int course_id = resultSet.getInt ( "course_id" );
                String course_name = resultSet.getString ( "course_name" );
                String course_description = resultSet.getString ( "course_description" );
                DaoCourse daoCourse = new DaoCourse();
                daoCourse.setCourseId ( course_id );
                daoCourse.setCourseName(course_name);
                daoCourse.setCourseDescription(course_description);
                daoCourses.add ( daoCourse );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace ();
        } finally {
            try {
                assert connection != null;
                connection.close ();
            } catch (SQLException throwables) {
                throwables.printStackTrace ();
            }
        }
        return daoCourses;
    }

    @Override
    public void create(String nameProperties, DaoCourse daoCourse) {
        ReadSqlFile readSqlFile = new ReadSqlFile ();
        String sql = readSqlFile.readFile ( "7_Create_course.sql" );
        DaoFactory daoFactory = new DaoFactory ();
        Connection connection = null;
        PreparedStatement preparedStatement;
        try {
            connection = daoFactory.getConnect ( nameProperties );
            preparedStatement = connection.prepareStatement ( sql );
            preparedStatement.setString ( 1, daoCourse.getCourseName() );
            preparedStatement.setString ( 2, daoCourse.getCourseDescription() );
            preparedStatement.executeQuery ();
        } catch (SQLException throwables) {
            throwables.printStackTrace ();
        } finally {
            try {
                assert connection != null;
                connection.close ();
            } catch (SQLException throwables) {
                throwables.printStackTrace ();
            }
        }
    }

    @Override
    public DaoCourse findById(String nameProperties, int id) {
        ReadSqlFile readSqlFile = new ReadSqlFile ();
        String sql = readSqlFile.readFile ("Find_course_by_id.sql");
        DaoFactory daoFactory = new DaoFactory ();
        Connection connection = null;
        List<DaoCourse> daoCourses = new ArrayList<>();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            connection = daoFactory.getConnect ( nameProperties );
            preparedStatement = connection.prepareStatement ( sql );
            preparedStatement.setInt ( 1, id );
            resultSet = preparedStatement.executeQuery ();
            while (resultSet.next ()) {
                int course_id = resultSet.getInt ( "course_id" );
                String course_name = resultSet.getString ( "course_name" );
                String course_description = resultSet.getString ( "course_description" );
                DaoCourse daoCourse = new DaoCourse();
                daoCourse.setCourseId ( course_id );
                daoCourse.setCourseName(course_name);
                daoCourse.setCourseDescription(course_description);
                daoCourses.add(daoCourse);
            }
            if (daoCourses.isEmpty()){
                throw new RuntimeException("Not found eny course with id: " +id);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace ();
        } finally {
            try {
                assert connection != null;
                connection.close ();
            } catch (SQLException throwables) {
                throwables.printStackTrace ();
            }
        }
        return daoCourses.get(0);
    }

    @Override
    public void update(String nameProperties, DaoCourse daoCourse) {
        ReadSqlFile readSqlFile = new ReadSqlFile ();
        String sql = readSqlFile.readFile ("Update_course_by_id.sql");
        DaoFactory daoFactory = new DaoFactory ();
        Connection connection = null;
        PreparedStatement preparedStatement;
        try {
            connection = daoFactory.getConnect ( nameProperties );
            preparedStatement = connection.prepareStatement ( sql );
            preparedStatement.setInt ( 1, daoCourse.getCourseId() );
            preparedStatement.setString ( 2, daoCourse.getCourseName() );
            preparedStatement.setString ( 3, daoCourse.getCourseDescription() );
            preparedStatement.executeQuery ();
        } catch (SQLException throwables) {
            throwables.printStackTrace ();
        } finally {
            try {
                assert connection != null;
                connection.close ();
            } catch (SQLException throwables) {
                throwables.printStackTrace ();
            }
        }
    }

    @Override
    public void delete(String nameProperties, int id) {
        ReadSqlFile readSqlFile = new ReadSqlFile ();
        String sql = readSqlFile.readFile ("Delete_course_by_id.sql");
        DaoFactory daoFactory = new DaoFactory ();
        Connection connection = null;
        PreparedStatement preparedStatement;
        try {
            connection = daoFactory.getConnect ( nameProperties );
            preparedStatement = connection.prepareStatement ( sql );
            preparedStatement.setInt ( 1, id );
            preparedStatement.executeQuery ();
        } catch (SQLException throwables) {
            throwables.printStackTrace ();
        } finally {
            try {
                assert connection != null;
                connection.close ();
            } catch (SQLException throwables) {
                throwables.printStackTrace ();
            }
        }
    }
}