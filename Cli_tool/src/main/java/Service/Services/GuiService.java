package Service.Services;

import exceptions.RatingOutOfBoundException;

import java.sql.SQLException;

public interface GuiService {

    void gui() throws SQLException, RatingOutOfBoundException;
    void movieOperations() throws SQLException, RatingOutOfBoundException;
    void genreOperations() throws SQLException;
    void personOperations();
    void professionOperations() throws SQLException;
}
