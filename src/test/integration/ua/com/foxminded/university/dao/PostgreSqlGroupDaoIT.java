package integration.ua.com.foxminded.university.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.university.domain.DaoGroup;

import java.sql.*;
import java.util.*;

class PostgreSqlGroupDaoIT {

    private Map<Integer, Integer> getGroupsID_studentsNumber() {
        Random rng = new Random ();
        Set<Integer> groups = new LinkedHashSet<>();
        while (groups.size () < 10) {
            Integer next = rng.nextInt ( 10 ) + 1;
            groups.add ( next );
        }
        Map<Integer, Integer> groups_students = new LinkedHashMap<>();
        int tempLastStudents = 200;
        int lastStudents = 200;
        for (Integer group : groups) {
            Random r = new Random ();
            int low = 10;
            int high = 31;
            int numberStudents = r.nextInt ( high - low ) + low;
            tempLastStudents = tempLastStudents - numberStudents;
            if (tempLastStudents < 0) {
                break;
            }
            lastStudents = lastStudents - numberStudents;
            groups_students.put ( group, numberStudents );
        }
        return groups_students;
    }

    private static String getAlphaString() {
        int n = 2;
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder ( n );
        for (int i = 0; i < n; i++) {
            int index = (int) (AlphaNumericString.length () * Math.random ());
            sb.append ( AlphaNumericString.charAt ( index ) );
        }
        return sb.toString ();
    }

    private static String getNumericString() {
        int n = 2;
        String AlphaNumericString = "0123456789";
        StringBuilder sb = new StringBuilder ( n );
        for (int i = 0; i < n; i++) {
            int index = (int) (AlphaNumericString.length () * Math.random ());
            sb.append ( AlphaNumericString.charAt ( index ) );
        }
        return sb.toString ();
    }

    private String getInsertStudents_Courses() {
        StringBuilder insertStudents_Courses = new StringBuilder ();
        Map<Integer, Integer> student_Courses = new LinkedHashMap<> ();
        Random r = new Random ();
        int low = 1;
        int high = 4;
        for (int i = 1; i <= 200; i++) {
            int numberGroups = r.nextInt ( high - low ) + low;
            student_Courses.put ( i, numberGroups );
        }
        for (int i = 1; i <= 200; i++) {
            Integer numberCircle = student_Courses.get ( i );
            for (int y = 0; y < numberCircle; y++) {
                int lows = 1;
                int highs = 11;
                int numberCourses = r.nextInt ( highs - lows ) + lows;
                insertStudents_Courses.append ( "INSERT INTO university.students_courses (student_id, course_id) VALUES " );
                insertStudents_Courses.append ( "(" + i + ", '" + numberCourses + ("');\n") );
            }
        }
        return insertStudents_Courses.toString ();
    }

    private String insertStudentWithGroup() {
        Map<Integer, Integer> groups_students = getGroupsID_studentsNumber ();
        StringBuilder insertStudents = new StringBuilder ();
        List<String> firstName = new ArrayList<> ();
        List<String> lastName = new ArrayList<> ();
        firstName.add ( "Eshaal" );
        firstName.add ( "Cain" );
        firstName.add ( "Tyler-James" );
        firstName.add ( "Davey" );
        firstName.add ( "Ema" );
        firstName.add ( "Juniper" );
        firstName.add ( "Kaif" );
        firstName.add ( "Teigan" );
        firstName.add ( "Roxie" );
        firstName.add ( "Marian" );
        firstName.add ( "Martine" );
        firstName.add ( "Eshaal" );
        firstName.add ( "Zachery" );
        firstName.add ( "Alfie-Jay" );
        firstName.add ( "Samirah" );
        firstName.add ( "Heidi" );
        firstName.add ( "Dominika" );
        firstName.add ( "Oriana" );
        firstName.add ( "Ryley" );
        firstName.add ( "Tahir" );
        firstName.add ( "Kallum" );

        lastName.add ( "Stark" );
        lastName.add ( "Rogers" );
        lastName.add ( "Ashley" );
        lastName.add ( "Patton" );
        lastName.add ( "Walker" );
        lastName.add ( "Lott" );
        lastName.add ( "Chapman" );
        lastName.add ( "Dominguez" );
        lastName.add ( "Bridges" );
        lastName.add ( "Slater" );
        lastName.add ( "Reilly" );
        lastName.add ( "Pugh" );
        lastName.add ( "Doyle" );
        lastName.add ( "Bartlett" );
        lastName.add ( "Lu" );
        lastName.add ( "Rivas" );
        lastName.add ( "O''Neill" );
        lastName.add ( "Worthington" );
        lastName.add ( "Hobbs" );
        lastName.add ( "Jaramillo" );
        Collections.shuffle ( firstName );
        Collections.shuffle ( lastName );
        Set<Integer> tempGroup_id = groups_students.keySet ();
        Map<Integer, Integer> group_id = new LinkedHashMap<> ();
        int numberGroups = 0;
        for (Integer group : tempGroup_id) {
            group_id.put ( numberGroups, group );
            numberGroups++;
        }
        Collection<Integer> tempNumberStudents = groups_students.values ();
        Map<Integer, Integer> numberStudents = new LinkedHashMap<> ();
        int numberMapStudents = 0;
        for (Integer student : tempNumberStudents) {
            numberStudents.put ( numberMapStudents, student );
            numberMapStudents++;
        }
        int aindex = 0;
        int nindex = 0;
        int lastStudents = 200;
        for (int i = 0; i < group_id.size (); i++) {
            Integer numberCircle = numberStudents.get ( i );
            for (int y = 0; y < numberCircle; y++) {
                insertStudents.append ( "INSERT INTO university.students (group_id, first_name, last_name) VALUES " );
                insertStudents.append ( "(" + group_id.get ( i ) + ", '" + firstName.get ( aindex++ ) + "', '" + lastName.get ( nindex++ ) + ("');\n") );
                if (aindex == firstName.size ()) {
                    aindex = 0;
                    Collections.shuffle ( firstName );
                }
                if (nindex == lastName.size ()) {
                    nindex = 0;
                    Collections.shuffle ( lastName );
                }
            }
            lastStudents = lastStudents - numberCircle;
        }
        for (int i = 1; i <= lastStudents; ++i) {
            insertStudents.append ( "INSERT INTO university.students (first_name, last_name) VALUES " );
            insertStudents.append ( "('" + firstName.get ( aindex++ ) + "', '" + lastName.get ( nindex++ ) + ("');\n") );
            if (aindex == firstName.size ()) {
                aindex = 0;
                Collections.shuffle ( firstName );
            }
            if (nindex == lastName.size ()) {
                nindex = 0;
                Collections.shuffle ( lastName );
            }
        }
        return insertStudents.toString ();
    }

    private static String getInsertGroups() {
        StringBuilder insertGroups = new StringBuilder ();
        for (int i = 0; i <= 10; i++) {
            insertGroups.append ( "INSERT INTO university.groups (group_id, group_name) VALUES ('" ).
                    append ( i ).append ( "', '" ).append ( getAlphaString () )
                    .append ( "-" ).append ( getNumericString () ).append ( "');\n" );
        }
        return insertGroups.toString ();
    }

    private static String getInsertCourses() {
        String insertCourses = """
                INSERT INTO university.courses (course_id, course_name, course_description) VALUES
                 (1, 'Anthropology', 'Some course #1' ),
                 (2, 'Archaeology', 'Some course #2' ),
                 (3, 'Architecture', 'Some course #3' ),
                 (4, 'Building', 'Some course #4' ),
                 (5, 'Chemistry', 'Some course #5' ),
                 (6, 'Chinese', 'Some course #6' ),
                 (7, 'Dentistry', 'Some course #7' ),
                 (8, 'Economics', 'Some course #8' ),
                 (9, 'Geology', 'Some course #9' ),
                 (10, 'German', 'Some course #10' );
                 
                 """;
        return insertCourses;
    }

    Statement statement;
    Connection conn;

    @Test
    public void fsd() throws SQLException, ClassNotFoundException {
        StringBuilder writer = new StringBuilder();
        String delete = """
                DELETE FROM university.students;
                DELETE FROM university.groups;
                DELETE FROM university.courses;
                DELETE FROM university.students_courses;
                """;
        writer.append ( delete );
        writer.append ( getInsertGroups () );
        writer.append ( getInsertCourses () );
        writer.append ( insertStudentWithGroup () );
        writer.append ( getInsertStudents_Courses () );
        Class.forName ( "org.h2.Driver" );
        conn = DriverManager.
                getConnection("jdbc:h2:mem:test;MODE=PostgreSQL;IGNORECASE=TRUE;INIT=RUNSCRIPT FROM 'src/test/resources/test.sql'");
        statement = conn.createStatement ();
        statement.execute ( writer.toString() );
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        List<DaoGroup> daoGroups = new ArrayList<>();
            preparedStatement = conn.prepareStatement("SELECT * FROM university.groups");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int group_id = resultSet.getInt("group_id");
                String group_name = resultSet.getString("group_name");
                DaoGroup daoGroup = new DaoGroup();
                daoGroup.setGroupId(group_id);
                daoGroup.setGroupName(group_name);
                daoGroups.add(daoGroup);
            }
            if (daoGroups.isEmpty()) {
                throw new RuntimeException("Not found eny group with id: ");
            }
        System.out.println("All groups at school:");
        System.out.println("ID\tName");
        for (DaoGroup daoGroup : daoGroups) {
            int groupId = daoGroup.getGroupId();
            String groupName = daoGroup.getGroupName();
            System.out.println(String.format("%d\t%s", groupId, groupName));
        }
    }

}
