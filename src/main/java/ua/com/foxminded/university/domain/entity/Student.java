package ua.com.foxminded.university.domain.entity;

public class Student {
    private int student_id;
    private int group_id;
    private String first_name;
    private String last_name;

    public Student(){

    }

    public void setStudentId(int student_id) {
        this.student_id = student_id;
    }

    public void setGroupId(int group_id) {
        this.group_id = group_id;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public int getStudentId() {
        return student_id;
    }

    public int getGroupId() {
        return group_id;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getLastName() {
        return last_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (student_id != student.student_id) return false;
        if (group_id != student.group_id) return false;
        if (!first_name.equals(student.first_name)) return false;
        return last_name.equals(student.last_name);
    }
}