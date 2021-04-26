package ua.com.foxminded.university.domain.entity;

public class Group {

    private String group_name;
    private int group_id;

    public Group(){
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        if (group_id != group.group_id) return false;
        return group_name.equals(group.group_name);
    }
}