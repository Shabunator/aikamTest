package Entities;

/**
 * Сущность покупателей с полями
 */
public class Buyers {
    Integer id;
    String lastName;
    String firstName;

    public Buyers(String lastName, String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;
    }

    @Override
    public String toString() {
        return "Buyers{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                '}';
    }
}
