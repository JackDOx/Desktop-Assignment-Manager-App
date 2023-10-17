package model;

import java.util.List;

// Abstract class for Person - which is extended by Student and Teacher
public abstract class Person {

    public abstract String getRole();

    public abstract void setName(String name);

    public abstract String getName();

    public abstract int getId();

    public abstract String getPassword();

    public abstract String getUserName();

    public abstract void addCourse(Course c);

    public abstract void removeCourse(Course c);

    public abstract List<Course> getCourse();
}
