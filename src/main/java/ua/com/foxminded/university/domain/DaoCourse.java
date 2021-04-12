package ua.com.foxminded.university.domain;

public class DaoCourse {

    private int course_id;
    private String course_name;
    private String course_description;

    public DaoCourse(){

    }

    public void setCourseId(int course_id) {
        this.course_id = course_id;
    }

    public void setCourseName(String course_name) {
        this.course_name = course_name;
    }

    public void setCourseDescription(String course_description) {
        this.course_description = course_description;
    }

    public int getCourseId() {
        return course_id;
    }

    public String getCourseName() {
        return course_name;
    }

    public String getCourseDescription() {
        return course_description;
    }

    @Override
    public String toString() {
        return "DaoCourse{" +
               "course_id=" + course_id +
               ", course_name='" + course_name + '\'' +
               ", course_description='" + course_description + '\'' +
               '}';
    }
}