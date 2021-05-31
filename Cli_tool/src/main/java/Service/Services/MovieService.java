package Service.Services;

import exceptions.RatingOutOfBoundException;

import java.sql.SQLException;

public interface MovieService {
    void addMovie() throws SQLException, RatingOutOfBoundException;
    void listAllMovies() throws SQLException;
    void updateMovie() throws SQLException;
    void removeMovie() throws SQLException;
}
