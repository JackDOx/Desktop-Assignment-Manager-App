package model;

import java.util.List;
import org.json.JSONObject;

// Abstract class for Person - which is extended by Student and Teacher

public abstract class Person {

    public abstract String getRole();

    public abstract void setName(String name);

    public abstract String getName();

    public abstract int getId();

    public abstract String getPassword();

    public abstract String getUserName();

    public abstract JSONObject toJson();

}
