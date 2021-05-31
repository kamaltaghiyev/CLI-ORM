package Service.Services;

import exceptions.RatingOutOfBoundException;

import java.sql.SQLException;
import java.text.ParseException;

public interface GuiService {

    void gui() throws SQLException, RatingOutOfBoundException, ParseException;
    void movieOperations() throws SQLException, RatingOutOfBoundException;
    void genreOperations() throws SQLException;
    void personOperations() throws SQLException, ParseException;
    void professionOperations() throws SQLException;
}
