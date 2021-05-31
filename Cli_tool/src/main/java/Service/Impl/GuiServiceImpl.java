package Service.Impl;



import Service.Services.GuiService;
import exceptions.RatingOutOfBoundException;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;

public class GuiServiceImpl implements GuiService {

    private static Scanner choice = new Scanner(System.in);

    public void movieOperations() throws SQLException, RatingOutOfBoundException {
        String scannedMovieChoice = "";
        MovieServiceImplement movieService = new MovieServiceImplement();
        while (!scannedMovieChoice.equals("5")) {
            System.out.println("Enter which operation you would like to do with movie table:\n" +
                    "1 -> List all movies\n" +
                    "2 -> Add new movie\n" +
                    "3 -> Update movie\n" +
                    "4 -> Remove movie\n" +
                    "5 -> To get back");
            scannedMovieChoice = choice.nextLine();
            switch (scannedMovieChoice) {
                case "1":
                    movieService.listAllMovies();
                    break;
                case "2":
                    movieService.addMovie();
                    break;
                case "3":
                    movieService.updateMovie();
                    break;
                case "4":
                    movieService.removeMovie();
                    break;
                case "5":
                    System.out.println("Returning back");
                    break;
            }
        }
    }

    public void genreOperations() throws SQLException {
        GenreServiceImpl genreService = new GenreServiceImpl();
        String scannedGenreChoice = "";
        while (!scannedGenreChoice.equals("5")) {
            System.out.println("Enter which operation you would like to do with movie table:\n" +
                    "1 -> List all genres\n" +
                    "2 -> Add new genre\n" +
                    "3 -> Update genre\n" +
                    "4 -> Remove genre\n" +
                    "5 -> To get back");
            scannedGenreChoice = choice.nextLine();
            switch (scannedGenreChoice) {
                case "1":
                    genreService.selectAllGenres();
                    break;
                case "2":
                    genreService.addGenre();
                    break;
                case "3":
                    genreService.updateGenre();
                    break;
                case "4":
                    genreService.removeGenre();
                    break;
                case "5":
                    System.out.println("Returning back");
                    break;
            }

        }
    }

    public void personOperations() throws SQLException, ParseException {
        PersonServiceImpl personService = new PersonServiceImpl();
        String scannedPersonChoice = "";
        while (!scannedPersonChoice.equals("5")) {
            System.out.println("Enter which operation you would like to do with movie table:\n" +
                    "1 -> List all profession\n" +
                    "2 -> Add new profession\n" +
                    "3 -> Update profession\n" +
                    "4 -> Remove profession\n" +
                    "5 -> To get back");
            scannedPersonChoice = choice.nextLine();
            switch (scannedPersonChoice) {
                case "1":
                    personService.selectAllPeople();
                    break;
                case "2":
                    personService.addPerson();
                    break;
                case "3":
                    personService.updatePerson();
                    break;
                case "4":
                    personService.removePerson();
                    break;
                case "5":
                    System.out.println("Returning back");
                    break;
            }

        }
    }

    public void professionOperations() throws SQLException {
        ProfessionServiceImpl professionService = new ProfessionServiceImpl();
        String scannedProfessionChoice = "";
        while (!scannedProfessionChoice.equals("5")) {
            System.out.println("Enter which operation you would like to do with movie table:\n" +
                    "1 -> List all profession\n" +
                    "2 -> Add new profession\n" +
                    "3 -> Update profession\n" +
                    "4 -> Remove profession\n" +
                    "5 -> To get back");
            scannedProfessionChoice = choice.nextLine();
            switch (scannedProfessionChoice) {
                case "1":
                    professionService.selectAllProfession();
                    break;
                case "2":
                    professionService.addProfession();
                    break;
                case "3":
                    professionService.updateProfession();
                    break;
                case "4":
                    professionService.removeProfession();
                    break;
                case "5":
                    System.out.println("Returning back");
                    break;
            }

        }
    }

    public void gui() throws SQLException, RatingOutOfBoundException, ParseException {
        String scannedChoice = "";
        System.out.println("Hello! You entered to CLI tool\n");
        while (!scannedChoice.equals("5")) {
            System.out.println("Enter choice:\n" +
                    "1 -> Movie\n" +
                    "2 -> Genre\n" +
                    "3 -> Person\n" +
                    "4 -> Profession\n" +
                    "5 -> Quit\n");
            scannedChoice = choice.nextLine();
            switch (scannedChoice) {
                case "1": movieOperations(); break;
                case "2": genreOperations(); break;
                case "3": personOperations(); break;
                case "4": professionOperations(); break;
                case "5": System.out.println("Ending.."); break;
            }
        }
    }
}
