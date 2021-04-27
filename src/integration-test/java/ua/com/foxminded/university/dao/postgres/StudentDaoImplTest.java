package ua.com.foxminded.university.dao.postgres;

import org.junit.jupiter.api.*;
import ua.com.foxminded.university.dao.DaoFactory;
import ua.com.foxminded.university.dao.postgres.util.CreateDeleteDate;
import ua.com.foxminded.university.domain.entity.Student;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentDaoImplTest {
    private final DaoFactory daoFactory = new DaoFactory("config.properties");
    private final StudentDaoImpl studentDao = new StudentDaoImpl(daoFactory);
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
    void findStudentsByCourse_ShouldReturnListStudents_WhenCorrectDataInserted() {
      List<Student>  students = studentDao.findStudentsByCourseName("Anthropology");
        for (Student student : students) {
            int groupId = student.getGroupId();
            int studentId = student.getStudentId();
            String firstName = student.getFirstName();
            String lastName = student.getLastName();
            assertTrue(Integer.toString(groupId).matches("^\\d+$"));
            assertTrue(Integer.toString(studentId).matches("^\\d+$"));
            assertTrue(firstName.matches("^[-a-z-A-Z]+$"));
            assertTrue(lastName.matches("^[-a-z-A-Z]+$"));
        }
    }

    @Test
    void delete_shouldReturnOne_whenInputIdIsDelete() {
        assertEquals(1, studentDao.delete(10));
    }

    @Test
    void delete_shouldReturnZero_whenInputIdIsInvalid() {
        assertEquals(0, studentDao.delete(201));
    }

    @Test
    void create_shouldReturnCorrectIdCourse_whenCorrectDataInserted() {
        Student testStudent = new Student();
        testStudent.setStudentId(201);
        testStudent.setGroupId(0);
        testStudent.setFirstName("Test");
        testStudent.setLastName("Test");
        studentDao.create(testStudent);
        List<Student> students = studentDao.findById(201);
        for (Student student : students) {
            assertEquals(testStudent, student);
        }
    }

    @Test
    void findById_shouldThrowRuntimeException_whenFindInputIdIsDelete() {
        studentDao.delete(10);
        assertThrows(RuntimeException.class,
                () -> studentDao.findById(10));
    }

    @Test
    public void findAll_shouldReturnNotEmptyListOfStudents() {
        List<Student> students = studentDao.findAll();
        for (Student student : students) {
            int groupId = student.getGroupId();
            int studentId = student.getStudentId();
            String firstName = student.getFirstName();
            String lastName = student.getLastName();
            assertTrue(Integer.toString(groupId).matches("^\\d+$"));
            assertTrue(Integer.toString(studentId).matches("^\\d+$"));
            assertTrue(firstName.matches("^[-a-z-A-Z]+$"));
            assertTrue(lastName.matches("^[-a-z-A-Z]+$"));
        }
    }

    @Test
    void update_shouldReturnUpdateStudent() {
        Student testStudent = new Student();
        testStudent.setStudentId(200);
        testStudent.setGroupId(10);
        testStudent.setFirstName("Test");
        testStudent.setLastName("Test");
        studentDao.update(testStudent);
        List<Student> students = studentDao.findById(200);
        for (Student student : students) {
            assertEquals(testStudent, student);
        }
    }
}