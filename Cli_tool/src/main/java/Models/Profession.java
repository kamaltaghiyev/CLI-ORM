package Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "profession")
public class Profession {
    @Id
    private int id;
    @Column(name = "name")
    private String profession;

    public Profession() { }
    public Profession(String profession) {
        this.profession = profession;
    }
    public String getProfession() {
        return profession;
    }
    public void setProfession(String profession) {
        this.profession = profession;
    }

    @Override
    public String toString() {
        return "Profession: " + profession;
    }
}
