package Service.Services;

import java.sql.SQLException;

public interface GenreService {
    void addGenre() throws SQLException;
    void selectAllGenres() throws SQLException;
    void updateGenre() throws SQLException;
    void removeGenre() throws SQLException;
}
