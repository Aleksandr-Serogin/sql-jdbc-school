package ua.com.foxminded.university.domain.entity;

public class Course {

    private int course_id;
    private String course_name;
    private String course_description;

    public Course(){
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return course_id == course.course_id && course_name.equals(course.course_name) && course_description.equals(course.course_description);
    }
}