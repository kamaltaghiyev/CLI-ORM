package Models;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "person")
public class Person {
    @Id
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "birthday")
    private Date birthdate;

    public Person() {
        this.name = "";
        this.surname = "";
        this.birthdate = null;
    }

    public Person( String name, String surname, Date birthdate) {
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getId() { return id; }

    public Date getBirthdate() { return birthdate; }

    public void setBirthdate(Date birthdate) { this.birthdate = birthdate; }

    @Override
    public String toString() {
        return "Person: " +
                "name: " + name +
                ", surname: " + surname +
                ", birthdate: " + birthdate;
    }
}
