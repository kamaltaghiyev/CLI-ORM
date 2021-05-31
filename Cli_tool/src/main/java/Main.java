import Service.Impl.GuiServiceImpl;
import exceptions.RatingOutOfBoundException;

import java.sql.SQLException;
import java.text.ParseException;

public class Main {
    public static void main(String[] args) throws SQLException, RatingOutOfBoundException, ParseException {
        GuiServiceImpl guiService = new GuiServiceImpl();
        guiService.gui();
    }
}
