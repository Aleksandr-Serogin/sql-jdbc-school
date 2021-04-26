package ua.com.foxminded.university.dao.postgres;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.university.dao.DaoFactory;
import ua.com.foxminded.university.dao.postgres.util.CreateDeleteDate;
import ua.com.foxminded.university.domain.entity.Course;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CourseDaoImplTest {
    private final DaoFactory daoFactory = new DaoFactory("config.properties");
    private final CourseDaoImpl courseDao = new CourseDaoImpl(daoFactory);
    private final CreateDeleteDate createDeleteDate = new CreateDeleteDate();

    @BeforeEach
    void setUp() throws URISyntaxException, IOException {
        createDeleteDate.createDate();
    }

    @AfterEach
    void tearDown() {
        createDeleteDate.deleteDate();
    }

    @Test
    void setCourseStudent_shouldReturnCorrectCourseStudent_whenCorrectIdStudentIdCourse() {
        courseDao.setCourseStudent(200, 10);
        Course testCourse = new Course();
        testCourse.setCourseId(10);
        testCourse.setCourseName("German");
        testCourse.setCourseDescription("Some course #10");
        List<Course> courses = courseDao.findStudentCourseByStudentID(200);
        assertTrue(courses.contains(testCourse));
    }

    @Test
    void deletedCourseStudent_shouldReturnOne_whenCorrectDataInserted() {
        CourseDaoImpl courseDao = new CourseDaoImpl(daoFactory);
        courseDao.setCourseStudent(200, 10);
        int numberOfDeletedRows = courseDao.deletedCourseStudent(200, 10);
        assertTrue(Integer.toString(numberOfDeletedRows).matches("^\\d+$"));
    }

    @Test
    public void findAll_shouldReturnListOfCourses() {
        List<Course> courses = courseDao.findAll();
        for (Course course : courses) {
            int courseId = course.getCourseId();
            String courseName = course.getCourseName();
            String courseDescription = course.getCourseDescription();
            assertTrue(Integer.toString(courseId).matches("^\\d+$"));
            assertTrue(courseName.matches("^[-a-z-A-Z]+$"));
            assertTrue(courseDescription.matches("^[-a-z-A-Z]+\\s+[-a-z-A-Z]+\\s+[#0-9]+$"));
        }
    }

    @Test
    void create_shouldReturnCorrectIdCourse_whenCorrectDataInserted() {
        Course testCourse = new Course();
        testCourse.setCourseName("Ukrainian");
        testCourse.setCourseDescription("Some course #11");
        courseDao.create(testCourse);
        assertEquals(11, testCourse.getCourseId());
    }

    @Test
    void findByID_shouldReturnCorrectCourse_whenCorrectIdInserted() {
        Course testCourse = new Course();
        testCourse.setCourseId(11);
        testCourse.setCourseName("Ukrainian");
        testCourse.setCourseDescription("Some course #11");
        courseDao.create(testCourse);
        List<Course> courses = courseDao.findById(11);
        for (Course course : courses) {
            assertEquals(testCourse, course);
        }
    }

    @Test
    void update_shouldUpdateCourse_whenCorrectDataInserted() {
        Course testCourse = new Course();
        testCourse.setCourseId(10);
        testCourse.setCourseName("Ukrainian");
        testCourse.setCourseDescription("Some course #10");
        courseDao.update(testCourse);
        List<Course> courses = courseDao.findById(10);
        for (Course course : courses) {
            assertEquals(testCourse, course);
        }
    }

    @Test
    void findById_shouldThrowRuntimeException_whenFindInputIdIsDelete() {
        courseDao.delete(10);
        assertThrows(RuntimeException.class,
                () -> courseDao.findById(10));
    }

    @Test
    void setCourseByStudent_shouldThrowRuntimeException_whenFindInputDataWrong() {
        assertThrows(RuntimeException.class,
                () -> courseDao.setCourseStudent(201,11));
    }

    @Test
    void deletedCourseStudent_shouldReturnZero_whenFindInputDataWrong(){
       assertEquals(0, courseDao.deletedCourseStudent(201,11));
    }

    @Test
    void delete_shouldReturnZero_whenInputIdIsInvalid() {
        assertEquals(0, courseDao.delete(12));
    }

    @Test
    void delete_shouldReturnOne_whenInputIdIsDelete() {
        assertEquals(1, courseDao.delete(10));
    }
}