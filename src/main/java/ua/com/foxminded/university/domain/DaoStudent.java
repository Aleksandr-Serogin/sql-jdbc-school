package ua.com.foxminded.university.domain;

public class DaoStudent {
    private int student_id;
    private int group_id;
    private String first_name;
    private String last_name;

    public DaoStudent(){

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
    public String toString() {
        return "DaoStudent{" +
                "student_id=" + student_id +
                ", group_id=" + group_id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                '}';
    }
}