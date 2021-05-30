package Service.Services;

import java.sql.SQLException;

public interface ProfessionService {
    void addProfession() throws SQLException;
    void selectAllProfession() throws SQLException;
    void updateProfession() throws SQLException;
    void removeProfession() throws SQLException;
}
