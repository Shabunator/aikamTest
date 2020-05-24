package Entities;

public class Buyers {
    Integer id;
    String lastName;
    String firstName;

    @Override
    public String toString() {
        return "Buyers{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                '}';
    }

    public Buyers(String lastName, String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public Buyers(Integer id, String lastName, String firstName) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public Integer getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
