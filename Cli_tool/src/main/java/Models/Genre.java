package Models;



import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "genre")
public class Genre {
    @Id
    @Column(name = "id")
    private long id;
    @Column(name = "name")
    private String name;
    @ManyToMany
    @JoinTable(
            name = "movie_genre_relation",
            joinColumns = @JoinColumn(name = "genre_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id"))
    Set<Movie> movies;
    public long getId() { return id; }

    @Override
    public String toString() { return "Genre: " + name; }

    public String getName() { return name; }

    public Genre() { this.name = "comedy"; }

    public Genre(String name) { this.name = name; }
}
