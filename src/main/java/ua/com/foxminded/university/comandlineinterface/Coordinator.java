package ua.com.foxminded.university.comandlineinterface;

import ua.com.foxminded.university.dao.DaoFactory;
import ua.com.foxminded.university.dao.postgres.CourseDaoImpl;
import ua.com.foxminded.university.dao.postgres.GroupDaoImpl;
import ua.com.foxminded.university.dao.postgres.StudentDaoImpl;
import ua.com.foxminded.university.domain.entity.Course;
import ua.com.foxminded.university.domain.entity.Group;
import ua.com.foxminded.university.domain.entity.Student;

import java.util.List;
import java.util.Scanner;

/**
 * Date: Apr 27-2021 Class show menu for chose in command line,
 * and work with this chose
 *
 * @author Aleksandr Serohin
 * @version 1.0001
 */
public class Coordinator {

    private static final String namePropertiesFile = "config.properties";
    private static final String SPLITERATOR = ", ";
    private static final DaoFactory daoFactory = new DaoFactory(namePropertiesFile);
    private static final GroupDaoImpl groupDaoImpl = new GroupDaoImpl(daoFactory);
    private static final CourseDaoImpl courseDaoImpl = new CourseDaoImpl(daoFactory);
    private static final StudentDaoImpl studentDaoImpl = new StudentDaoImpl(daoFactory);

    /**
     * Working with chose in command line and return result chose
     */
    public void startCommandLine() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Input your request: ");
            String input = scanner.nextLine();
            if ("exit".equals(input)) {
                break;
            } else if ("a".equals(input)) {
                System.out.print("Input count: ");
                String answer = scanner.nextLine();
                getGroupsWithLess_EqualsStudent(answer);
            } else if ("b".equals(input)) {
                System.out.print("Input course name: ");
                String answer = scanner.nextLine();
                findStudentsByCourse(answer);
            } else if ("c".equals(input)) {
                System.out.print("Input student full name: <First Name>, <Last Name>. Example: Jon, Dou." + System.lineSeparator());
                String answer = scanner.nextLine();
                createStudent(answer);
            } else if ("d".equals(input)) {
                System.out.print("Input student ID: ");
                String answer = scanner.nextLine();
                deleteStudent(answer);
            } else if ("e".equals(input)) {
                findAllCourses();
                System.out.print("Input student id and course. Pattern: <student id>, <course id>. Example: 150, 15." + System.lineSeparator());
                String answer = scanner.nextLine();
                setCourseStudent(answer);
            } else if ("f".equals(input)) {
                System.out.print("Input student id and his/her course. Pattern: <student id>, <course id>." +
                        " Example: 150, 15." + System.lineSeparator());
                String answer = scanner.nextLine();
                deletedCourseStudent(answer);
            } else if ("g".equals(input)) {
                System.out.print("Input course name and description: <Course Name>, <Course Description>." +
                        " Example: Mathematics, Higher mathematics" + System.lineSeparator());
                String answer = scanner.nextLine();
                createCourse(answer);
            } else if ("h".equals(input)) {
                System.out.print("Input group name: <Group Name>." +
                        " Example: nh-23" + System.lineSeparator());
                String answer = scanner.nextLine();
                createGroup(answer);
            } else if ("i".equals(input)) {
                System.out.print("Input course id: <id>." +
                        " Example: 23" + System.lineSeparator());
                String answer = scanner.nextLine();
                deleteCourse(answer);
            } else if ("k".equals(input)) {
                System.out.print("Input group id: <id>." +
                        " Example: 23" + System.lineSeparator());
                String answer = scanner.nextLine();
                deleteGroup(answer);
            } else if ("l".equals(input)) {
                System.out.print("Input student id, group id, first name, last name: <id, group, first name, last name>." +
                        " Example: 23, 10, Jon, Dou" + System.lineSeparator());
                String answer = scanner.nextLine();
                updateStudent(answer);
            } else if ("m".equals(input)) {
                System.out.print("Input course id, name, description: <Course id, course name, course description>." +
                        " Example: 10, Mathematics, Higher mathematics" + System.lineSeparator());
                String answer = scanner.nextLine();
                updateCourse(answer);
            } else if ("n".equals(input)) {
                System.out.print("Input group id, name: <Group id, group name>." +
                        " Example: 10, 23-fg" + System.lineSeparator());
                String answer = scanner.nextLine();
                updateGroup(answer);
            } else if ("o".equals(input)) {
                findAllCourses();
            } else if ("p".equals(input)) {
                findAllGroups();
            } else if ("q".equals(input)) {
                findAllStudents();
            } else if ("r".equals(input)) {
                System.out.print("Input student id: <id>." +
                        " Example: 10" + System.lineSeparator());
                String answer = scanner.nextLine();
                findStudent(answer);
            } else if ("s".equals(input)) {
                System.out.print("Input course id: <id>." +
                        " Example: 10" + System.lineSeparator());
                String answer = scanner.nextLine();
                findCourse(answer);
            } else if ("t".equals(input)) {
                System.out.print("Input group id: <id>." +
                        " Example: 10" + System.lineSeparator());
                String answer = scanner.nextLine();
                findGroup(answer);
            } else {
                System.out.print("Wrong input. Try again.");
            }
        }
    }

    /**
     * Show menu for chose in command line
     */
    public static void showMenu() {
        String menu = "a. Find all groups with less or equals student count" + System.lineSeparator() +
                "b. Find all students related to course with given name" + System.lineSeparator() +
                "c. Add new student" + System.lineSeparator() +
                "d. Delete student by STUDENT_ID" + System.lineSeparator() +
                "e. Add a student to the course (from a list)" + System.lineSeparator() +
                "f. Remove the student from one of his or her courses" + System.lineSeparator() +
                "g. Add new course" + System.lineSeparator() +
                "h. Add new group" + System.lineSeparator() +
                "i. Delete course by COURSE_ID" + System.lineSeparator() +
                "k. Delete group by GROUP_ID" + System.lineSeparator() +
                "l. Update student by STUDENT_ID" + System.lineSeparator() +
                "m. Update course by COURSE_ID" + System.lineSeparator() +
                "n. Update group by GROUP_ID" + System.lineSeparator() +
                "o. Find all courses" + System.lineSeparator() +
                "p. Find all groups" + System.lineSeparator() +
                "q. Find all students" + System.lineSeparator() +
                "r. Find student by STUDENT_ID" + System.lineSeparator() +
                "s. Find course by COURSE_ID" + System.lineSeparator() +
                "t. Find group by GROUP_ID" + System.lineSeparator();
        System.out.println(menu);
    }

    private void getGroupsWithLess_EqualsStudent(String answer) {
        int numberStudents = Integer.parseInt(answer);
        List<Group> groups = groupDaoImpl.getGroupsWithLess_EqualsStudent(numberStudents);
        String message = String.format("All groups whose number of students less than %d: ", numberStudents);
        System.out.println(message);
        for (Group group : groups) {
            int id = group.getGroupId();
            String name = group.getGroupName();
            System.out.println(String.format("%d %s", id, name));
        }
    }

    private void findStudentsByCourse(String answer) {
        List<Student> students = studentDaoImpl.findStudentsByCourseName(answer);
        String message = String.format("All students that relate to course %s: ", answer);
        System.out.println(message);
        for (Student student : students) {
            System.out.println(String.format("Student id: %d, group id: %d, student name: %s %s", student.getStudentId(), student.getGroupId(),
                    student.getFirstName(), student.getLastName()));
        }
    }

    private void createStudent(String answer) {
        String[] nameStudent = answer.split(SPLITERATOR);
        Student student = new Student();
        student.setFirstName(nameStudent[0]);
        student.setLastName(nameStudent[1]);
        studentDaoImpl.create(student);
        String message =
                String.format("Student id: %d, student name: %s %s has been added.", student.getStudentId(), nameStudent[0], nameStudent[1]);
        System.out.println(message);
    }

    private void setCourseStudent(String answer) {
        String[] id = answer.split(SPLITERATOR);
        int studentID = Integer.parseInt(id[0]);
        int courseID = Integer.parseInt(id[1]);
        courseDaoImpl.setCourseStudent(studentID, courseID);
        String message = String.format("Student with id: %d has been added to the course with id: %d.",
                studentID, courseID);
        System.out.println(message);
    }

    private void deleteStudent(String answer) {
        int studentID = Integer.parseInt(answer);
        int numberOfDeletedRows = studentDaoImpl.delete(studentID);
        if (numberOfDeletedRows > 0) {
            String message =
                    String.format("Student with id: %d, has been deleted.", studentID);
            System.out.println(message);
        } else {
            System.out.println("Nothing delete");
        }
    }

    private void deletedCourseStudent(String answer) {
        String[] id = answer.split(SPLITERATOR);
        int studentID = Integer.parseInt(id[0]);
        int courseID = Integer.parseInt(id[1]);
        int numberOfDeletedRows = courseDaoImpl.deletedCourseStudent(studentID, courseID);
        if (numberOfDeletedRows > 0) {
        String message =
                String.format("Student with id: %d, has been deleted from his/her course with id: %d.",
                        studentID, courseID);
        System.out.println(message);
        } else {
            System.out.println("Nothing delete");
        }
    }

    private void createCourse(String answer) {
        String[] course = answer.split(",");
        Course daoCourse = new Course();
        daoCourse.setCourseName(course[0]);
        daoCourse.setCourseDescription(course[1].substring(1));
        courseDaoImpl.create(daoCourse);
        String message =
                String.format("Course id: %d, name: %s, description: %s, has been added.", daoCourse.getCourseId(), course[0], course[1]);
        System.out.println(message);
    }

    private void createGroup(String answer) {
        Group group = new Group();
        group.setGroupName(answer);
        groupDaoImpl.create(group);
        String message =
                String.format("Group id: %d, name: %s, has been added.", group.getGroupId(), answer);
        System.out.println(message);
    }

    private void deleteCourse(String answer) {
        int id = Integer.parseInt(answer);
        int numberOfDeletedRows = courseDaoImpl.delete(id);
        if (numberOfDeletedRows > 0) {
        String message =
                String.format("Course id: %d, has been delete.", id);
        System.out.println(message);
        } else {
            System.out.println("Nothing delete");
        }
    }

    private void deleteGroup(String answer) {
        int id = Integer.parseInt(answer);
        int numberOfDeletedRows =  groupDaoImpl.delete(id);
        if (numberOfDeletedRows > 0) {
        String message =
                String.format("Group id: %d, has been delete.", id);
        System.out.println(message);
        } else {
            System.out.println("Nothing delete");
        }

    }

    private void updateStudent(String answer) {
        String[] student = answer.split(",");
        int studentId = Integer.parseInt(student[0]);
        int groupId = Integer.parseInt(student[1].substring(1));
        String firstName = student[2].substring(1);
        String lastName = student[3].substring(1);
        Student daoStudent = new Student();
        daoStudent.setStudentId(studentId);
        daoStudent.setGroupId(groupId);
        daoStudent.setFirstName(firstName);
        daoStudent.setLastName(lastName);
        studentDaoImpl.update(daoStudent);
        String message =
                String.format("Student id: %d, group: %d, name: %s %s, has been update.", studentId, groupId, firstName, lastName);
        System.out.println(message);
    }

    private void updateCourse(String answer) {
        String[] course = answer.split(",");
        int courseId = Integer.parseInt(course[0]);
        String courseName = course[1].substring(1);
        String courseDescription = course[2].substring(1);
        Course daoCourse = new Course();
        daoCourse.setCourseId(courseId);
        daoCourse.setCourseName(courseName);
        daoCourse.setCourseDescription(courseDescription);
        courseDaoImpl.update(daoCourse);
        String message =
                String.format("Course id: %d, name: %s, description: %s, has been update.", courseId, courseName, courseDescription);
        System.out.println(message);
    }

    private void updateGroup(String answer) {
        String[] course = answer.split(",");
        int groupId = Integer.parseInt(course[0]);
        String groupName = course[1].substring(1);
        Group group = new Group();
        group.setGroupId(groupId);
        group.setGroupName(groupName);
        groupDaoImpl.update(group);
        String message =
                String.format("Group id: %d, name: %s, has been update.", groupId, groupName);
        System.out.println(message);
    }

    private void findAllCourses() {
        List<Course> courses = courseDaoImpl.findAll();
        System.out.println("All courses at school:");
        System.out.println("ID" + assemblyString(13) + "Name" + assemblyString(101) + "Description");
        StringBuilder result = new StringBuilder();
        courses.forEach(course -> result
                .append(course.getCourseId())
                .append(makeDividerLensID(course.getCourseId()))
                .append(course.getCourseName())
                .append(makeDividerNameCourse(course.getCourseName()))
                .append(course.getCourseDescription())
                .append("\n"));
        System.out.println(result);
    }

    private void findAllGroups() {
        List<Group> groups = groupDaoImpl.findAll();
        System.out.println("All groups at school:");
        System.out.println("ID\tName");
        for (Group group : groups) {
            int groupId = group.getGroupId();
            String groupName = group.getGroupName();
            System.out.println(String.format("%d\t%s", groupId, groupName));
        }
    }

    private void findAllStudents() {
        List<Student> students = studentDaoImpl.findAll();
        System.out.println("All students at school:");
        System.out.println("ID\t\tGroupID\tName");
        for (Student student : students) {
            int studentId = student.getStudentId();
            int groupID = student.getGroupId();
            String firstName = student.getFirstName();
            String lastName = student.getLastName();
            System.out.println(String.format("%d\t\t%d\t\t%s %s", studentId, groupID, firstName, lastName));
        }
    }

    private void findStudent(String answer) {
        int id = Integer.parseInt(answer);
        List<Student> students = studentDaoImpl.findById(id);
        for (Student student : students) {
            int studentId = student.getStudentId();
            int groupID = student.getGroupId();
            String firstName = student.getFirstName();
            String lastName = student.getLastName();
            System.out.println(String.format("Student id: %d, group id: %d, name: %s %s", studentId, groupID, firstName, lastName));
        }
    }

    private void findCourse(String answer) {
        int id = Integer.parseInt(answer);
        List<Course> courses = courseDaoImpl.findById(id);
        for (Course course : courses) {
            System.out.println(String.format("Course id: %d, name: %s, description: %s.", course.getCourseId(),
                    course.getCourseName(), course.getCourseDescription()));
        }
    }

    private void findGroup(String answer) {
        int id = Integer.parseInt(answer);
        List<Group> groups = groupDaoImpl.findById(id);
        for (Group group : groups) {
            int groupId = group.getGroupId();
            String groupName = group.getGroupName();
            System.out.println(String.format("Group id: %d, name: %s.", groupId, groupName));
        }
    }

    private String makeDividerLensID(int id) { // append in string tabs and dash
        int longIndex = String.valueOf(id).length();
        int numbersTabs = (15 - longIndex);
        return assemblyString(numbersTabs);
    }

    private String makeDividerNameCourse(String nameCourse) { // append in string tabs and dash
        int longIndex = nameCourse.length();
        int numbersTabs = (105 - longIndex);
        return assemblyString(numbersTabs);
    }

    private String assemblyString(int numbersOfSymbols) {
        return String.valueOf(' ').repeat(Math.max(0, numbersOfSymbols));
    }
}