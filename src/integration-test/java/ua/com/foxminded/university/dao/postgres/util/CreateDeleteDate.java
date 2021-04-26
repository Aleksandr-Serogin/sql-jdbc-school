package ua.com.foxminded.university.dao.postgres.util;

import ua.com.foxminded.university.util.PostgreSqlRunSqlScript;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

public class CreateDeleteDate {

    public void createDate() throws URISyntaxException, IOException {
        URL res = CreateDeleteDate.class.getClassLoader().getResource("Init.sql");
        assert res != null;
        File file = Paths.get(res.toURI()).toFile();
        String absolutePath = file.getAbsolutePath();
        for (int i = 8; 0 <= i; i--) {
            absolutePath = absolutePath.substring(0, absolutePath.length() - 1);
        }
        File tempFile = File.createTempFile("temp", ".sql", new File(absolutePath));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile, true));
        writer.append(delete());
        writer.append(getInsertGroups());
        writer.append(getInsertCourses());
        writer.append(insertStudentWithGroup());
        writer.append(getInsertStudents_Courses());
        writer.close();
        PostgreSqlRunSqlScript postgreSqlRunSqlScript = new PostgreSqlRunSqlScript();
        postgreSqlRunSqlScript.runSqlScript("Init.sql", "config.properties");
        postgreSqlRunSqlScript.runSqlScript(tempFile.getName(), "config.properties");
        tempFile.deleteOnExit();
    }

    public void deleteDate() {
        PostgreSqlRunSqlScript postgreSqlRunSqlScript = new PostgreSqlRunSqlScript();
        postgreSqlRunSqlScript.runSqlScript("drop_Schema_University.sql", "config.properties");
    }

    private static String delete() {
        return """
                DELETE FROM university.students;
                DELETE FROM university.groups;
                DELETE FROM university.courses;
                DELETE FROM university.students_courses;
                 """;
    }

    private static Map<Integer, Integer> getGroupsID_studentsNumber() {
        Random rng = new Random();
        Set<Integer> groups = new LinkedHashSet<>();
        while (groups.size() < 10) {
            Integer next = rng.nextInt(10) + 1;
            groups.add(next);
        }
        Map<Integer, Integer> groups_students = new LinkedHashMap<>();
        int tempLastStudents = 200;
        int lastStudents = 200;
        for (Integer group : groups) {
            Random r = new Random();
            int low = 10;
            int high = 31;
            int numberStudents = r.nextInt(high - low) + low;
            tempLastStudents = tempLastStudents - numberStudents;
            if (tempLastStudents < 0) {
                break;
            }
            lastStudents = lastStudents - numberStudents;
            groups_students.put(group, numberStudents);
        }
        return groups_students;

    }

    private static String getAlphaString() {
        int n = 2;
        String AlphaNumericString = "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }

    private static String getNumericString() {
        int n = 2;
        String AlphaNumericString = "0123456789";
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }

    private static String getInsertStudents_Courses() {
        StringBuilder insertStudents_Courses = new StringBuilder();
        Map<Integer, Integer> student_Courses = new LinkedHashMap<>();
        Random r = new Random();
        int low = 1;
        int high = 4;
        for (int i = 1; i <= 200; i++) {
            int numberGroups = r.nextInt(high - low) + low;
            student_Courses.put(i, numberGroups);
        }
        for (int i = 1; i <= 200; i++) {
            Integer numberCircle = student_Courses.get(i);
            for (int y = 0; y < numberCircle; y++) {
                int lows = 1;
                int highs = 11;
                int numberCourses = r.nextInt(highs - lows) + lows;
                insertStudents_Courses.append("INSERT INTO university.students_courses (student_id, course_id) VALUES ");
                insertStudents_Courses.append("(").append(i).append(", '").append(numberCourses).append("');\n");
            }
        }
        return insertStudents_Courses.toString();
    }

    private static String insertStudentWithGroup() {
        Map<Integer, Integer> groups_students = getGroupsID_studentsNumber();
        StringBuilder insertStudents = new StringBuilder();
        List<String> firstName = new ArrayList<>();
        List<String> lastName = new ArrayList<>();
        firstName.add("Eshaal");
        firstName.add("Cain");
        firstName.add("Tyler");
        firstName.add("Davey");
        firstName.add("Ema");
        firstName.add("Juniper");
        firstName.add("Kaif");
        firstName.add("Teigan");
        firstName.add("Roxie");
        firstName.add("Marian");
        firstName.add("Martine");
        firstName.add("Eshaal");
        firstName.add("Zachery");
        firstName.add("Alfie");
        firstName.add("Samirah");
        firstName.add("Heidi");
        firstName.add("Dominika");
        firstName.add("Oriana");
        firstName.add("Ryley");
        firstName.add("Tahir");
        firstName.add("Kallum");

        lastName.add("Stark");
        lastName.add("Rogers");
        lastName.add("Ashley");
        lastName.add("Patton");
        lastName.add("Walker");
        lastName.add("Lott");
        lastName.add("Chapman");
        lastName.add("Dominguez");
        lastName.add("Bridges");
        lastName.add("Slater");
        lastName.add("Reilly");
        lastName.add("Pugh");
        lastName.add("Doyle");
        lastName.add("Bartlett");
        lastName.add("Lu");
        lastName.add("Rivas");
        lastName.add("Neill");
        lastName.add("Worthington");
        lastName.add("Hobbs");
        lastName.add("Jaramillo");
        Collections.shuffle(firstName);
        Collections.shuffle(lastName);
        Set<Integer> tempGroup_id = groups_students.keySet();
        Map<Integer, Integer> group_id = new LinkedHashMap<>();
        int numberGroups = 0;
        for (Integer group : tempGroup_id) {
            group_id.put(numberGroups, group);
            numberGroups++;
        }
        Collection<Integer> tempNumberStudents = groups_students.values();
        Map<Integer, Integer> numberStudents = new LinkedHashMap<>();
        int numberMapStudents = 0;
        for (Integer student : tempNumberStudents) {
            numberStudents.put(numberMapStudents, student);
            numberMapStudents++;
        }
        int aindex = 0;
        int nindex = 0;
        int lastStudents = 200;
        for (int i = 0; i < group_id.size(); i++) {
            Integer numberCircle = numberStudents.get(i);
            for (int y = 0; y < numberCircle; y++) {
                insertStudents.append("INSERT INTO university.students (group_id, first_name, last_name) VALUES ");
                insertStudents.append("(").append(group_id.get(i)).append(", '").append(firstName.get(aindex++))
                        .append("', '").append(lastName.get(nindex++)).append("');\n");
                if (aindex == firstName.size()) {
                    aindex = 0;
                    Collections.shuffle(firstName);
                }
                if (nindex == lastName.size()) {
                    nindex = 0;
                    Collections.shuffle(lastName);
                }
            }
            lastStudents = lastStudents - numberCircle;
        }
        for (int i = 1; i <= lastStudents; ++i) {
            insertStudents.append("INSERT INTO university.students (first_name, last_name) VALUES ");
            insertStudents.append("('").append(firstName.get(aindex++)).append("', '").append(lastName.get(nindex++)).append("');\n");
            if (aindex == firstName.size()) {
                aindex = 0;
                Collections.shuffle(firstName);
            }
            if (nindex == lastName.size()) {
                nindex = 0;
                Collections.shuffle(lastName);
            }
        }
        return insertStudents.toString();
    }

    private static String getInsertGroups() {
        StringBuilder insertGroups = new StringBuilder();
        for (int i = 1; i <= 10; i++) {
            insertGroups.append("INSERT INTO university.groups (group_name) VALUES ('")
                    .append(getAlphaString())
                    .append("-").append(getNumericString()).append("');\n");
        }
        return insertGroups.toString();
    }

    private static String getInsertCourses() {
        return """
                INSERT INTO university.courses (course_name, course_description) VALUES
                 ('Anthropology', 'Some course #1' ),
                 ('Archaeology', 'Some course #2' ),
                 ('Architecture', 'Some course #3' ),
                 ('Building', 'Some course #4' ),
                 ('Chemistry', 'Some course #5' ),
                 ('Chinese', 'Some course #6' ),
                 ('Dentistry', 'Some course #7' ),
                 ('Economics', 'Some course #8' ),
                 ('Geology', 'Some course #9' ),
                 ('German', 'Some course #10' );
                 """;
    }
}