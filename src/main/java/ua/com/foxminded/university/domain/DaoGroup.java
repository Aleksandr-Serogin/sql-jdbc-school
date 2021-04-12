package ua.com.foxminded.university.domain;

public class DaoGroup {

    private String group_name;
    private int group_id;

    public DaoGroup(){

    }

    public void setGroupName(String group_name) {
        this.group_name = group_name;
    }

    public void setGroupId(int group_id) {
        this.group_id = group_id;
    }

    public String getGroupName() {
        return group_name;
    }

    public int getGroupId() {
        return group_id;
    }

    @Override
    public String toString() {
        return "DaoGroup{" +
               "group_name='" + group_name + '\'' +
               ", group_id=" + group_id +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DaoGroup)) return false;

        DaoGroup daoGroup = (DaoGroup) o;

        if (group_id != daoGroup.group_id) return false;
        return group_name.equals ( daoGroup.group_name );
    }

    @Override
    public int hashCode() {
        int result = group_name.hashCode ();
        result = 31 * result + group_id;
        return result;
    }


}