import Service.Impl.GuiServiceImpl;
import exceptions.RatingOutOfBoundException;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, RatingOutOfBoundException {
        GuiServiceImpl guiService = new GuiServiceImpl();
        guiService.gui();
    }
}
