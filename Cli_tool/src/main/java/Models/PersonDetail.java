package Models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "person_detalies")
public class PersonDetail {
    @Id
    private int id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profession_id", referencedColumnName = "id")
    private Profession prof;
    @ManyToMany
    Set<Movie> movieSet;

    public PersonDetail(Person person, Profession prof) {
        this.person = person;
        this.prof = prof;
    }

    public PersonDetail() {
    }

    public int getId() {
        return id;
    }

    public Person getPerson() {
        return person;
    }

    public Profession getProf() {
        return prof;
    }

    public Set<Movie> getMovieSet() {
        return movieSet;
    }

    @Override
    public String toString() {
        return "PersonDetail{" +
                "id=" + id +
                ", person=" + person +
                ", prof=" + prof +
                ", movieSet=" + movieSet +
                '}';
    }
}
