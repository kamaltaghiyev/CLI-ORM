package Service.Services;

import java.sql.SQLException;
import java.text.ParseException;

public interface PersonService {
    void addPerson() throws SQLException, ParseException;
    void selectAllPeople() throws SQLException;
    void updatePerson() throws SQLException;
    void removePerson() throws SQLException;

}
