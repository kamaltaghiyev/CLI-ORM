package Models;
//
//import Exceptions.RatingOutOfBoundException;

import exceptions.RatingOutOfBoundException;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "movie")
public class Movie {
    @Id
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "rating")
    private double rating;
    @Column(name = "budget")
    private double budget;
    @ManyToMany
    @JoinTable(
            name = "movie_genre_relation",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    Set<Genre> genres;
    public Movie(String name, double rating, double budget) throws RatingOutOfBoundException {

        this.name = name;
        setRating(rating);
        this.budget = budget;
    }

    public Movie() {
        this.name = "";
        this.rating = 0;
        this.budget = 0;
    }

    private static String listOfGenresToString(List<Genre> genres) {
        StringBuilder sb = new StringBuilder("[");
        int counter = 0;
        for (Genre x : genres)
        {
            counter++;
            sb.append(genres);
            if (counter != genres.size())
                sb.append(", ");
        }
        sb.append("]");

        return sb.toString();
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) throws RatingOutOfBoundException {
        if (rating < 0 || rating > 5)
            throw new RatingOutOfBoundException();

        this.rating = rating;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    @Override
    public String toString() {
        return "Movie: " +
                name +
                ", with rating: " + rating +
                ", and budget: " + budget +
                '.';
    }
}
