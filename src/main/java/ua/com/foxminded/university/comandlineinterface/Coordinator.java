package ua.com.foxminded.university.comandlineinterface;

import ua.com.foxminded.university.dao.postgres.PostgreSqlCourseDao;
import ua.com.foxminded.university.dao.postgres.PostgreSqlGroupDao;
import ua.com.foxminded.university.dao.postgres.PostgreSqlStudentDao;
import ua.com.foxminded.university.domain.DaoCourse;
import ua.com.foxminded.university.domain.DaoGroup;
import ua.com.foxminded.university.domain.DaoStudent;

import java.util.List;
import java.util.Scanner;

public class Coordinator {

    private static final String SPLITERATOR = ", ";

    public void startCommandLine() {
        final String namePropertiesFile = "bd.properties";
        PostgreSqlGroupDao postgreSqlGroupDao = new PostgreSqlGroupDao();
        PostgreSqlCourseDao postgreSqlCourseDao = new PostgreSqlCourseDao();
        PostgreSqlStudentDao postgreSqlStudentDao = new PostgreSqlStudentDao();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Input your request: ");
            String input = scanner.nextLine();
            if ("exit".equals(input)) {
                break;
            } else if ("a".equals(input)) {
                System.out.print("Input count: ");
                String answer = scanner.nextLine();
                int numberStudents = Integer.parseInt(answer);
                List<DaoGroup> groups = postgreSqlGroupDao.getGroupsWithLess_EqualsStudent(namePropertiesFile, numberStudents);
                String message = String.format("All groups whose number of students less than %d: ", numberStudents);
                System.out.println(message);
                for (DaoGroup group : groups) {
                    int id = group.getGroupId();
                    String name = group.getGroupName();
                    System.out.println(String.format("%d %s", id, name));
                }
            } else if ("b".equals(input)) {
                System.out.print("Input course name: ");
                String courseName = scanner.nextLine();
                List<DaoStudent> students = postgreSqlStudentDao.findStudentsByCourse(namePropertiesFile, courseName);
                String message = String.format("All students that relate to course %s: ", courseName);
                System.out.println(message);
                for (DaoStudent student : students) {
                    System.out.println(String.format("%d %d %s %s", student.getStudentId(), student.getGroupId(),
                            student.getFirstName(), student.getLastName()));
                }
            } else if ("c".equals(input)) {
                System.out.print("Input student full name: <First Name> <Last Name>. Example: Jon Dou." + System.lineSeparator());
                String answer = scanner.nextLine();
                String[] nameStudent = answer.split(SPLITERATOR);
                DaoStudent daoStudent = new DaoStudent();
                daoStudent.setFirstName(nameStudent[0]);
                daoStudent.setLastName(nameStudent[1]);
                postgreSqlStudentDao.create(namePropertiesFile, daoStudent);
                String message =
                        String.format("Student %d %s %s has been added.", daoStudent.getStudentId(), nameStudent[0], nameStudent[1]);
                System.out.println(message);
            } else if ("d".equals(input)) {
                System.out.print("Input student ID: ");
                String answer = scanner.nextLine();
                int studentID = Integer.parseInt(answer);
                postgreSqlStudentDao.delete(namePropertiesFile, studentID);
                String message =
                        String.format("Student %d has been deleted.", studentID);
                System.out.println(message);
            } else if ("e".equals(input)) {
                System.out.print("Input student id and course. Pattern: <student id> <course id>. Example: 150 15." + System.lineSeparator());
                String answer = scanner.nextLine();
                int studentID = Integer.parseInt(answer.split(SPLITERATOR)[0]);
                int courseID = Integer.parseInt(answer.split(SPLITERATOR)[1]);
                try {
                    postgreSqlCourseDao.setCourseStudent(namePropertiesFile, studentID, courseID);
                    String message = String.format("Student with id %d has been added to the course %d.",
                            studentID, courseID);
                    System.out.println(message);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            } else if ("f".equals(input)) {
                System.out.print("Input student id and his/her course. Pattern: <student id> <course id>." +
                        " Example: 150 15." + System.lineSeparator());
                String answer = scanner.nextLine();
                int studentID = Integer.parseInt(answer.split(SPLITERATOR)[0]);
                int courseID = Integer.parseInt(answer.split(SPLITERATOR)[1]);
                try {
                    postgreSqlCourseDao.deletedCourseStudent(namePropertiesFile, studentID, courseID);
                    String message =
                            String.format("Student with id %d has been deleted from his/her %d course.",
                                    studentID, courseID);
                    System.out.println(message);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            } else if ("g".equals(input)) {
                System.out.print("Input course name and description: <Course Name>, <Course Description>." +
                        " Example: Mathematics, Higher mathematics" + System.lineSeparator());
                String answer = scanner.nextLine();
                String[] course = answer.split(",");
                DaoCourse daoCourse = new DaoCourse();
                daoCourse.setCourseName(course[0]);
                daoCourse.setCourseDescription(course[1].substring(1));
                postgreSqlCourseDao.create(namePropertiesFile, daoCourse);
                String message =
                        String.format("Course %d %s %s has been added.", daoCourse.getCourseId(), course[0], course[1]);
                System.out.println(message);
            } else if ("h".equals(input)) {
                System.out.print("Input group name: <Group Name>." +
                        " Example: nh-23" + System.lineSeparator());
                String answer = scanner.nextLine();
                DaoGroup daoGroup = new DaoGroup();
                daoGroup.setGroupName(answer);
                postgreSqlGroupDao.create(namePropertiesFile, daoGroup);
                String message =
                        String.format("Group %d , %s has been added.", daoGroup.getGroupId(), answer);
                System.out.println(message);
            } else if ("i".equals(input)) {
                System.out.print("Input course id: <id>." +
                        " Example: 23" + System.lineSeparator());
                String answer = scanner.nextLine();
                int id = Integer.parseInt(answer);
                postgreSqlCourseDao.delete(namePropertiesFile, id);
                String message =
                        String.format("Course %d has been delete.", id);
                System.out.println(message);
            } else if ("k".equals(input)) {
                System.out.print("Input group id: <id>." +
                        " Example: 23" + System.lineSeparator());
                String answer = scanner.nextLine();
                int id = Integer.parseInt(answer);
                postgreSqlGroupDao.delete(namePropertiesFile, id);
                String message =
                        String.format("Group %d has been delete.", id);
                System.out.println(message);
            } else if ("l".equals(input)) {
                System.out.print("Input student id, group id, first name, last name: <id, group, first name, last name>." +
                        " Example: 23, 10, Jon, Dou" + System.lineSeparator());
                String answer = scanner.nextLine();
                String[] student = answer.split(",");
                int studentId = Integer.parseInt(student[0]);
                int groupId = Integer.parseInt(student[1].substring(1));
                String firstName = student[2].substring(1);
                String lastName = student[3].substring(1);
                DaoStudent daoStudent = new DaoStudent();
                daoStudent.setStudentId(studentId);
                daoStudent.setGroupId(groupId);
                daoStudent.setFirstName(firstName);
                daoStudent.setLastName(lastName);
                postgreSqlStudentDao.update(namePropertiesFile, daoStudent);
                String message =
                        String.format("Student %d, %d, %s, %s has been update.", studentId, groupId, firstName, lastName);
                System.out.println(message);
            } else if ("m".equals(input)) {
                System.out.print("Input course id, name, description: <Course id, course name, course description>." +
                        " Example: 10, Mathematics, Higher mathematics" + System.lineSeparator());
                String answer = scanner.nextLine();
                String[] course = answer.split(",");
                int courseId = Integer.parseInt(course[0]);
                String courseName = course[1].substring(1);
                String courseDescription = course[2].substring(1);
                DaoCourse daoCourse = new DaoCourse();
                daoCourse.setCourseId(courseId);
                daoCourse.setCourseName(courseName);
                daoCourse.setCourseDescription(courseDescription);
                postgreSqlCourseDao.update(namePropertiesFile, daoCourse);
                String message =
                        String.format("Group %d , %s, %s has been update.", courseId, courseName, courseDescription);
                System.out.println(message);
            } else if ("n".equals(input)) {
                System.out.print("Input group id, name: <Group id, group name>." +
                        " Example: 10, 23-fg" + System.lineSeparator());
                String answer = scanner.nextLine();
                String[] course = answer.split(",");
                int groupId = Integer.parseInt(course[0].substring(1));
                String groupName = course[1];
                DaoGroup daoGroup = new DaoGroup();
                daoGroup.setGroupId(groupId);
                daoGroup.setGroupName(groupName);
                postgreSqlGroupDao.update(namePropertiesFile, daoGroup);
                String message =
                        String.format("Group %d , %s has been update.", groupId, groupName);
                System.out.println(message);
            } else if ("o".equals(input)) {
                List<DaoCourse> daoCourses = postgreSqlCourseDao.findAll(namePropertiesFile);
                System.out.println("All courses at school:");
                System.out.println("ID" + assemblyString(13) + "Name" + assemblyString(101) + "Description");
                StringBuilder result = new StringBuilder();
                daoCourses.forEach(daoCourse -> result
                        .append(daoCourse.getCourseId())
                        .append(makeDividerLensID(daoCourse.getCourseId()))
                        .append(daoCourse.getCourseName())
                        .append(makeDividerNameCourse(daoCourse.getCourseName()))
                        .append(daoCourse.getCourseDescription())
                        .append("\n"));
                System.out.println(result);
            } else if ("p".equals(input)) {
                List<DaoGroup> daoGroups = postgreSqlGroupDao.findAll(namePropertiesFile);
                System.out.println("All groups at school:");
                System.out.println("ID\tName");
                for (DaoGroup daoGroup : daoGroups) {
                    int groupId = daoGroup.getGroupId();
                    String groupName = daoGroup.getGroupName();
                    System.out.println(String.format("%d\t%s", groupId, groupName));
                }
            } else if ("q".equals(input)) {
                List<DaoStudent> daoStudents = postgreSqlStudentDao.findAll(namePropertiesFile);
                System.out.println("All students at school:");
                System.out.println("ID\t\tGroupID\tName");
                for (DaoStudent daoStudent : daoStudents) {
                    int studentId = daoStudent.getStudentId();
                    int groupID = daoStudent.getGroupId();
                    String firstName = daoStudent.getFirstName();
                    String lastName = daoStudent.getLastName();
                    System.out.println(String.format("%d\t\t%d\t\t%s %s", studentId, groupID, firstName, lastName));
                }
            } else if ("r".equals(input)) {
                System.out.print("Input student id: <id>." +
                        " Example: 10" + System.lineSeparator());
                String answer = scanner.nextLine();
                int id = Integer.parseInt(answer);
                DaoStudent daoStudents = postgreSqlStudentDao.findById(namePropertiesFile, id);
                String message =
                        String.format("Student id: %d, group id: %d, name: %s %s.", daoStudents.getStudentId(),
                                daoStudents.getGroupId(), daoStudents.getFirstName(), daoStudents.getLastName());
                System.out.println(message);

            } else if ("s".equals(input)) {
                System.out.print("Input course id: <id>." +
                        " Example: 10" + System.lineSeparator());
                String answer = scanner.nextLine();
                int id = Integer.parseInt(answer);
                DaoCourse daoCourse = postgreSqlCourseDao.findById(namePropertiesFile, id);
                String message =
                        String.format("Course id: %d, name: %s, description %s.", daoCourse.getCourseId(),
                                daoCourse.getCourseName(), daoCourse.getCourseDescription());
                System.out.println(message);
            } else if ("t".equals(input)) {
                System.out.print("Input group id: <id>." +
                        " Example: 10" + System.lineSeparator());
                String answer = scanner.nextLine();
                int id = Integer.parseInt(answer);
                DaoGroup daoGroups = postgreSqlGroupDao.findById(namePropertiesFile, id);
                String message =
                        String.format("Group id: %d , name: %s.", daoGroups.getGroupId(), daoGroups.getGroupName());
                System.out.println(message);
            } else {
                System.out.print("Wrong input. Try again.");
            }
        }
    }

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