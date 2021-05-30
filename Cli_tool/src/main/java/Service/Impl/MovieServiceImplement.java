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
    public void addMovie() throws SQLException, RatingOutOfBoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter name of the movie:\n");
        String name = scanner.nextLine();
        if(name.matches("([a-zA-Z]+)")){


            System.out.println("Enter rating\n");
            String scannedRating = scanner.nextLine();
            if (scannedRating.matches("([0-5].*[0-9]*)")){
                double rating = Double.parseDouble(scannedRating);

                System.out.println("Enter budget\n");
                String scannedBudget = scanner.nextLine();
                if (scannedBudget.matches("([1-9]+.[0-9]{0,3})")){
                    double budget = Double.parseDouble(scannedBudget);
                    String genreChoice = "";
                    System.out.println("Select genre from the list below:\n");
                    GenreServiceImpl genreService = new GenreServiceImpl();
                    genreService.selectAllGenres();
                    Movie movie = new Movie(name,rating,budget);
                    try {
                        connection = Provider.getConnection();
                        preparedStatement = connection.prepareStatement("insert into movie(name,rating,budget) values (?,?,?)");
                        preparedStatement.setString(1,movie.getName());
                        preparedStatement.setDouble(2,movie.getRating());
                        preparedStatement.setDouble(3,movie.getBudget());
                        preparedStatement.executeUpdate();
                    }catch (Exception e){
                        System.out.println(e);
                    }finally {
                        connection.close();
                        preparedStatement.close();
                    }
                    while(!Objects.equals(genreChoice, "back")) {
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
                            preparedStatement.setInt(2,genreId);
                            preparedStatement.executeUpdate();
                            preparedStatement.close();
                        }else {
                            System.out.println("Enter numeric value!");
                        }
                    }
                }else{
                    System.out.println("Enter properly!");
                }
            }else{
                System.out.println("Enter properly!");
            }
        }else{
            System.out.println("Enter properly!");
        }
    }
    public void listAllMovies() throws SQLException {
        try {
            connection = Provider.getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM movie LEFT JOIN movie_genre_relation ON movie.id = movie_genre_relation.movie_id LEFT JOIN genre ON genre.id = movie_genre_relation.genre_id");
            while (resultSet.next()) {
                System.out.println("Id: " + resultSet.getInt("movie.id"));
                System.out.println("Name: " + resultSet.getString("movie.name"));
                System.out.println("Rating: " + resultSet.getDouble("movie.rating"));
                System.out.println("Budget: " + resultSet.getDouble("movie.budget"));
                System.out.println("Genre: " + resultSet.getDouble("genre.name"));
            }
            System.out.println("\n");
        } catch (Exception e){
            System.out.println(e);
        } finally {
            connection.close();
        }
    }
}
