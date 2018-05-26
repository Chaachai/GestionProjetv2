package bean;

import java.io.Serializable;

/**
 * Created by CHAACHAI Youssef on 2/3/2018.
 */

public class User implements Serializable {

    private static final Long serialVersionUID = 1L;
    private String id;
    private String password;
    private String lastName;
    private String firstName;
    private int nbrConnection;

    public User(String id, String password, String lastName, String firstName, int nbrConnection) {
        this.id = id;
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;
        this.nbrConnection = nbrConnection;
    }

    public User(String id) {
        this.id = id;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNbrConnection() {
        return nbrConnection;
    }

    public void setNbrConnection(int nbrConnection) {
        this.nbrConnection = nbrConnection;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id != null ? id.equals(user.id) : user.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                '}';
    }
}
