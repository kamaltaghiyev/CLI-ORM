package Service.Impl;

import DBUtils.Provider;
import Models.Movie;
import Service.Services.GenreService;
import Service.Services.MovieService;
import exceptions.RatingOutOfBoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

public class MovieServiceImplement implements MovieService {
    private static Connection connection;
    private static PreparedStatement preparedStatement;
    private static PreparedStatement preparedStatement1;
    Scanner scanner = new Scanner(System.in);

    public void addMovie() throws SQLException, RatingOutOfBoundException {

        System.out.println("Enter name of the movie:\n");
        String name = scanner.nextLine();
        if (name.matches("([a-zA-Z]+)")) {


            System.out.println("Enter rating\n");
            String scannedRating = scanner.nextLine();
            if (scannedRating.matches("([0-5].?[0-9]*)")) {
                double rating = Double.parseDouble(scannedRating);

                System.out.println("Enter budget\n");
                String scannedBudget = scanner.nextLine();
                if (scannedBudget.matches("([1-9]+.[0-9]{0,3})")) {
                    double budget = Double.parseDouble(scannedBudget);
                    String genreChoice = "";
                    System.out.println("Select genre from the list below:\n");
                    GenreServiceImpl genreService = new GenreServiceImpl();
                    genreService.selectAllGenres();
                    Movie movie = new Movie(name, rating, budget);
                    try {
                        connection = Provider.getConnection();
                        preparedStatement = connection.prepareStatement("insert into movie(name,rating,budget) values (?,?,?)");
                        preparedStatement.setString(1, movie.getName());
                        preparedStatement.setDouble(2, movie.getRating());
                        preparedStatement.setDouble(3, movie.getBudget());
                        preparedStatement.executeUpdate();
                    } catch (Exception e) {
                        System.out.println(e);
                    } finally {
                        connection.close();
                        preparedStatement.close();
                    }
                    while (!Objects.equals(genreChoice, "back")) {
                        genreChoice = scanner.nextLine();
                        System.out.println("Enter next value or back!");
                        if (genreChoice.matches("([0-9]+)")) {
                            connection = Provider.getConnection();
                            preparedStatement = connection.prepareStatement("SELECT * FROM movie WHERE id = (SELECT MAX (id) FROM movie)");
                            ResultSet rs = preparedStatement.executeQuery();
                            rs.next();
                            int id = rs.getInt("id");
                            rs.close();
                            preparedStatement = connection.prepareStatement("insert into movie_genre_relation values (?,?)");
                            preparedStatement.setInt(1, id);
                            int genreId = Integer.parseInt(genreChoice);
                            preparedStatement.setInt(2, genreId);
                            preparedStatement.executeUpdate();
                            preparedStatement.close();
                        } else {
                            System.out.println("Enter numeric value!");
                        }
                    }
                } else {
                    System.out.println("Enter properly!");
                }
            } else {
                System.out.println("Enter properly!");
            }
        } else {
            System.out.println("Enter properly!");
        }
    }

    public void listAllMovies() throws SQLException {
        try {
            connection = Provider.getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT movie.id, movie.\"name\",movie.rating, movie.budget, genre.\"name\" AS genre_name  FROM movie LEFT JOIN movie_genre_relation ON movie.id = movie_genre_relation.movie_id LEFT JOIN genre ON genre.id = movie_genre_relation.genre_id");
            while (resultSet.next()) {
                System.out.println("Id: " + resultSet.getInt("id"));
                System.out.println("Name: " + resultSet.getString("name"));
                System.out.println("Rating: " + resultSet.getDouble("rating"));
                System.out.println("Budget: " + resultSet.getDouble("budget"));
                System.out.println("Genre: " + resultSet.getString("genre_name"));
                System.out.println("---------------------------------------------------------------------");
            }
            System.out.println("\n");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            connection.close();
        }
    }

    public void updateMovie() throws SQLException {
        connection = Provider.getConnection();
        String forUpdate, id;
        System.out.print("Which movie you would like to UPDATE: ");
        id = scanner.nextLine();
        System.out.print("What do you want to update from the list below:\n " +
                "1 -> name\n" +
                "2 -> rating\n" +
                "3 -> budget\n");
        String updateChoice = scanner.nextLine();
        try {
            switch (updateChoice) {
                case "1":
                    System.out.println("Enter name to update");
                    forUpdate = scanner.nextLine();
                    preparedStatement = connection.prepareStatement("UPDATE movie SET name = ? WHERE id = ?");
                    preparedStatement.setString(1, forUpdate);
                    preparedStatement.setInt(2, Integer.parseInt(id));
                    preparedStatement.executeUpdate();
                    break;
                case "2":
                    System.out.println("Enter rating to update");
                    forUpdate = scanner.nextLine();
                    preparedStatement = connection.prepareStatement("UPDATE movie SET rating = ? WHERE id = ?");
                    preparedStatement.setDouble(1, Double.parseDouble(forUpdate));
                    preparedStatement.setInt(2, Integer.parseInt(id));
                    preparedStatement.executeUpdate();
                    break;
                case "3":
                    System.out.println("Enter budget to update");
                    forUpdate = scanner.nextLine();
                    preparedStatement = connection.prepareStatement("UPDATE movie SET budget = ? WHERE id = ?");
                    preparedStatement.setDouble(1, Double.parseDouble(forUpdate));
                    preparedStatement.setInt(2, Integer.parseInt(id));
                    preparedStatement.executeUpdate();
                    break;
            }


        } catch (Exception e) {
            System.out.println(e);
        } finally {
            connection.close();
            preparedStatement.close();
        }
    }

    public void removeMovie() throws SQLException {
        try {
            connection = Provider.getConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM movie WHERE id = ?");
            String id;
            System.out.print("Which movie you would like to DELETE: ");
            id = scanner.nextLine();
            preparedStatement.setInt(1, Integer.parseInt(id));
            preparedStatement.executeUpdate();
            preparedStatement1 = connection.prepareStatement("DELETE FROM movie_genre_relation WHERE id = ?");
            preparedStatement1.setInt(1, Integer.parseInt(id));
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            connection.close();
            preparedStatement.close();
            preparedStatement1.close();
        }
    }
}
